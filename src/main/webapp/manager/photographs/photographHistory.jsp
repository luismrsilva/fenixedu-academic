<%--

    Copyright © 2002 Instituto Superior Técnico

    This file is part of FenixEdu Academic.

    FenixEdu Academic is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    FenixEdu Academic is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with FenixEdu Academic.  If not, see <http://www.gnu.org/licenses/>.

--%>
<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/taglib/enum" prefix="e"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr"%>

<html:xhtml />

<h2>Histórico de Fotografias<!-- bean:message key="documents.management.title" bundle="MANAGER_RESOURCES" / --></h2>

<fr:form action="/photographs/history.do?method=historyFilter">
    <h3>Foto</h3>
    <fr:edit id="historyFilter" name="filter">
    	<fr:schema type="org.fenixedu.academic.dto.photographs.PhotographFilterBean" bundle="DOMAIN_RESOURCES">
    		<fr:slot name="state" key="label.Photograph.state" />
			<fr:slot name="type" key="label.Photograph.type" />
    		<fr:slot name="startDate" key="label.Photograph.startDateModified" layout="picker" >
        		<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.DateValidator" />
				<fr:property name="size" value="12"/>
				<fr:property name="maxLength" value="10"/>
			</fr:slot>
			<fr:slot name="endDate" key="label.Photograph.endDateModified" layout="picker" >
        		<fr:validator name="pt.ist.fenixWebFramework.renderers.validators.DateValidator" />
				<fr:property name="size" value="12"/>
				<fr:property name="maxLength" value="10"/>
			</fr:slot>
			<fr:slot name="userName" key="label.Party.username">
	    		<fr:property name="size" value="15" />
			</fr:slot>    	
    	</fr:schema>
    	
        <fr:layout name="tabular-editable">
            <fr:property name="classes" value="tstyle1" />
            <fr:property name="columnClasses" value=",,noborder" />
        </fr:layout>
    </fr:edit>
    <html:submit />
</fr:form>
<logic:present name="history">
	<logic:iterate id="userHistory" name="history">
	
		<p class="separator2 mtop2" style="font-size: 1.1em;">
            <bean:write name="userHistory" property="person.username" /> - <bean:write name="userHistory" property="person.name"/>
        </p>
		
		<logic:iterate id="photo" name="userHistory" property="photographs">
		
			<bean:define id="photoId" name="photo" property="externalId"/>

            <table class="dinline">
	            <tr>
		            <td>
						<html:img align="middle"
						    src="${fr:checksum('/person/retrievePersonalPhoto.do?method=retrievePendingByID&photoCode='.concat(photoId))}"
						altKey="personPhoto" bundle="IMAGE_RESOURCES"
						style="border: 1px solid #aaa; padding: 3px;" />
		            </td>
	            </tr>
	            <tr>
		            <td>
                        <fr:view name="photo" property="photoType"/><br/>
			            <fr:view name="photo" property="state"/><br/>
                        <logic:notEmpty name="photo" property="stateChange">
                            <fr:view name="photo" property="stateChange" layout="no-time" />
                        </logic:notEmpty>
		            </td>
	            </tr>
            </table>

		</logic:iterate>
		
	</logic:iterate>
</logic:present>
