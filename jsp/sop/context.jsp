<%@ page language="java" %>
<%@ page import="ServidorApresentacao.Action.sop.utils.SessionConstants" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

	
<logic:present name="executionDegree">
	<html:form action="/chooseContext">

		<html:hidden property="page" value="1"/>
		<html:hidden property="method" value="choose"/>
		<html:hidden property="<%= SessionConstants.EXECUTION_PERIOD_OID %>"
					 value="<%= pageContext.findAttribute("executionPeriodOID").toString() %>"/>

		<table>
			<tr>
				<td colspan="2">
					<html:select property="executionDegreeOID"
								 size="1"
								 value="<%= pageContext.findAttribute("executionDegreeOID").toString() %>"
								 onchange="document.chooseScheduleContextForm.submit();">
				  		<html:options collection="licenciaturas"
		    	   					  property="value"
		       						  labelProperty="label"/>
			       </html:select>
				</td>
			</tr>
			<tr>
				<td>
					<html:select property="curricularYear" size="1"
								 value="<%= pageContext.findAttribute("curricularYearOID").toString() %>"
								 onchange="document.chooseScheduleContextForm.submit();">
			       		<html:options collection="anosCurriculares"
		    	   					  property="value"
		       						  labelProperty="label"/>
			       	</html:select> Ano
				</td>

	</html:form>
	<html:form action="/chooseExecutionPeriod">
				<td>
		<html:hidden property="method" value="choose"/>
		<html:hidden property="page" value="1"/>
		<html:select property="index" size="1"
					 value="<%= pageContext.findAttribute("executionPeriodOID").toString() %>"
					 onchange="document.pagedIndexForm.submit();">
	    	<html:options	property="value" 
	     					labelProperty="label" 
							collection="<%= SessionConstants.LABELLIST_EXECUTIONPERIOD%>"
							/>
		</html:select>
	</html:form>    
				</td>
			</tr>
		</table>
</logic:present>
<%--
<logic:present name="<%= SessionConstants.CLASS_VIEW %>"  >
	<bean:define id="infoTurma" name="<%= SessionConstants.CLASS_VIEW %>" scope="request"/>
	<br />
	<bean:message key="label.class"/> <jsp:getProperty name="infoTurma" property="nome" />
	<br />
</logic:present>

<logic:present name="executionCourse">
	<br />
	<bean:message key="property.course"/>: <bean:write name="executionCourse" property="nome"/>
	<br />
</logic:present>

<logic:present name="shift">
	<bean:message key="property.shift"/>: <bean:write name="shift" property="nome"/>
	<br />
</logic:present>
--%>