package net.sourceforge.fenixedu.renderers.utils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sourceforge.fenixedu.renderers.components.state.ComponentLifeCycle;
import net.sourceforge.fenixedu.renderers.components.state.IViewState;
import net.sourceforge.fenixedu.renderers.components.state.LifeCycleConstants;
import net.sourceforge.fenixedu.renderers.plugin.RenderersRequestProcessor;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ModuleUtils;

public class RenderUtils {
    private static Logger logger = Logger.getLogger(RenderUtils.class);
    
    public static String RESOURCE_LABEL_PREFIX = "label";
    
    public static String getSlotLabel(Class objectType, String slotName, String key) {
        return getSlotLabel(objectType, slotName, null, key);
    }
   
    public static String getSlotLabel(Class objectType, String slotName, String bundle, String key) {
        String label = null;
        
        if (key != null) {
            label = RenderUtils.getResourceString(bundle, key);
        }
    
        if (label != null) {
            return label;
        } else if (key != null) {
            logger.warn("Key specified for slot '" + slotName + "' does not exist: " + key);
        }
    
        label = RenderUtils.getResourceString(bundle, RenderUtils.RESOURCE_LABEL_PREFIX + "." + objectType.getName() + "."
                + slotName);
    
        if (label != null) {
            return label;
        }
    
        label = RenderUtils.getResourceString(bundle, RenderUtils.RESOURCE_LABEL_PREFIX + "." + slotName);
        
        if (label != null) {
            return label;
        }
        
        label = RenderUtils.getResourceString(bundle, slotName);
    
        if (label != null) {
            return label;
        }
    
        if (slotName.contains(".")) {
            label = RenderUtils.getResourceString(bundle, slotName.substring(slotName.lastIndexOf(".") + 1));
        }
    
        if (label != null) {
            return label;
        }
        
        return slotName;
    }
    
    public static String getResourceString(String key) {
        return getResourceString(null, key);
    }
    
    public static String getResourceString(String bundle, String key) {
        if (bundle == null) {
            try {
                // TODO: allow the name to be configured or fetch the resources in other way
                MessageResources resources = getMessageResources("RENDERER_RESOURCES");
                
                if (resources.isPresent(key)) {
                    return resources.getMessage(key);
                }
            }
            catch (Exception e) {
                // could not found renderer resources, ignore and procede
            }
        }
        
        MessageResources resources = getMessageResources(bundle);

        if (! resources.isPresent(key)) {
            return null;
        }
        
        return resources.getMessage(key);
    }

    public static MessageResources getMessageResources() {
        return (MessageResources) getMessageResources(null);
    }

    public static MessageResources getMessageResources(String bundle) {
        ServletContext context = RenderersRequestProcessor.getCurrentContext();
        HttpServletRequest request = RenderersRequestProcessor.getCurrentRequest();
        
        MessageResources resources = null;

        if (bundle == null) {
            bundle = Globals.MESSAGES_KEY;
        }

        if (resources == null) {
            resources = (MessageResources) request.getAttribute(bundle);
        }

        if (resources == null) {
            ModuleConfig moduleConfig = ModuleUtils.getInstance().getModuleConfig(request, context);
            resources = (MessageResources) context.getAttribute(bundle + moduleConfig.getPrefix());
        }

        if (resources == null) {
            resources = (MessageResources) context.getAttribute(bundle);
        }

        if (resources == null) {
            // TODO: make a more specific exception
            throw new RuntimeException("could not find message resources");
        }
        
        return resources;
    }
    
    public static String getFormatedResourceString(String key, String ... args) {
        String text = getResourceString(key);
        
        MessageFormat format = new MessageFormat(text);
        
        return format.format(args);
    }
    
    public static String getFormatedProperties(String format, Object object) {
        // "${a.b} - ${a.c} - ${b,-4.5tY}" 
        // String.format("%s - %s - %-4.5tY", object.getA().getB(), object.getA().getC(), object.getB())
        
        // TODO: use a separator different than ',' because the comma can be used as a flag in the format 
        
        List args = new ArrayList();
        StringBuilder builder = new StringBuilder();
        
        if (format != null) {
            int lastIndex = 0, index;
            
            while ((index = format.indexOf("${", lastIndex)) != -1) {
                int end = format.indexOf("}", index + 2);
                
                if (end == -1) {
                    throw new RuntimeException("'" + format + "':unmatched group at pos " + index);
                }
                
                builder.append(format.substring(lastIndex, index));
                lastIndex = end + 1;

                if (end - index == 2) {
                    continue;
                }
                
                String spec = format.substring(index + 2, end);
                String[] parts = spec.split(",");
                
                String property = parts[0];
                
                if (parts.length > 1) {
                    builder.append(parts[1]);
                }
                else {
                    builder.append("%s");
                }
                
                try {
                    Object value = PropertyUtils.getProperty(object, property);
                    
                    args.add(value);
                } catch (Exception e) {
                    throw new RuntimeException("could not retrieve property '" + property + "' for object " + object, e);
                }
            }
            
            builder.append(format.substring(lastIndex));
        }
        
        return String.format(builder.toString(), args.toArray());
    }
    
    public static void setProperties(Object target, Properties properties) {
        if (properties == null) {
            return;
        }
        
        for (Object property : properties.keySet()) {
            String propertyName = null;

            try {
                propertyName = (String) property;

                if (PropertyUtils.isWriteable(target, propertyName)) {
                    BeanUtils
                            .copyProperty(target, propertyName, properties.getProperty(propertyName));
                }
                else {
                    // even so try to write it because PropertyUtils.isWriteable() does not work for mapped items
                    try {
                        PropertyUtils.setProperty(target, propertyName, properties.getProperty(propertyName));
                    } catch (Exception e) {
                        logger.warn("The object " + target + " does not support property '" + propertyName + "': Not writeable!");
                    }
                }
            } catch (Exception e) {
                logger.warn("The object " + target + " does not support property '" + propertyName + "': " + e);
            } // IllegalAccessException, InvocationTargetException, NoSuchMethodException
        }
    }
    
    public static  String getModuleRelativePath(HttpServletRequest request, String path) {
        ModuleConfig module = ModuleUtils.getInstance().getModuleConfig(request);

        String returnPath;
        
        if (module != null) {
            returnPath = module.getPrefix() + path;
        }
        else {
            returnPath = path;
        }
        
        return getContextRelativePath(request, returnPath);
    }

    public static String getModuleRelativePath(String path) {
        return getModuleRelativePath(RenderersRequestProcessor.getCurrentRequest(), path);
    }

    public static String getContextRelativePath(HttpServletRequest request, String path) {
        String contextPath = request.getContextPath();
        
        return contextPath + path;
    }

    public static String getContextRelativePath(String path) {
        return getContextRelativePath(RenderersRequestProcessor.getCurrentRequest(), path);
    }

    //
    // ViewState related accessors to be used in actions
    // TODO: check the use of the methods for potential problems with the renderers' common lifecycle
    //
    
    /**
     * Obtains the renderer's view state processed and contained in the current request.
     */
    public static IViewState getViewState() {
        return (IViewState) RenderersRequestProcessor.getCurrentRequest().getAttribute(LifeCycleConstants.VIEWSTATE_PARAM_NAME);
    }
    
    /**
     * Updates the current request to contain a custom view state. This methods is intended to be used
     * with a view state obtained from it's serialized form and not directly with {@link #getViewState()}.
     * After calling the method the request can be forwarded to the location the original view state
     * was intended.
     */
    public static void setViewState(IViewState viewState) throws InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {
        HttpServletRequest currentRequest = RenderersRequestProcessor.getCurrentRequest();
        
        ComponentLifeCycle.getInstance().restoreComponent(viewState);
        ComponentLifeCycle.getInstance().prepareDestination(viewState, currentRequest);
    }

    /**
     * Removes the renderer's view state from the current request.
     */
    public static void invalidateViewState() {
        RenderersRequestProcessor.getCurrentRequest().setAttribute(LifeCycleConstants.VIEWSTATE_PARAM_NAME, null);
    }
}
