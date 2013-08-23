<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<html:xhtml/>

<em><bean:message key="label.candidacies" bundle="APPLICATION_RESOURCES"/></em>
<h2><bean:message key="label.candidacy.edit" bundle="APPLICATION_RESOURCES"/></h2>

<br/>
<html:messages id="message" message="true" bundle="APPLICATION_RESOURCES">
	<span class="error0"> <bean:write name="message" /> </span>
	<br />
</html:messages>
<fr:hasMessages for="individualCandidacyProcessBean.precedentDegreeInformation" type="conversion">
	<ul class="nobullet list6">
		<fr:messages>
			<li><span class="error0"><fr:message/></span></li>
		</fr:messages>
	</ul>
</fr:hasMessages>

<bean:define id="processId" name="process" property="externalId" />

<fr:form action='<%="/caseHandlingDegreeCandidacyForGraduatedPersonIndividualProcess.do?userAction=editCandidacyQualifications&processId=" + processId.toString() %>' id="candidacyForm">

	<input type="hidden" id="methodId" name="method" value="executeEditCandidacyInformation"/>
	<input type="hidden" id="removeIndexId" name="removeIndex" value=""/>
	<input type="hidden" id="skipValidationId" name="skipValidation" value="false"/>
 	

	<fr:edit id="individualCandidacyProcessBean" name="individualCandidacyProcessBean" visible="false" />
	
	<fr:edit id="individualCandidacyProcessBean.candidacyDate" 
		 name="individualCandidacyProcessBean"
		 schema="SecondCycleIndividualCandidacyProcessBean.candidacyDate">
		<fr:layout name="tabular-editable">
			<fr:property name="classes" value="tstyle4 thlight thright mtop025"/>
	        <fr:property name="columnClasses" value="width12em,,tdclear tderror1"/>
		</fr:layout>
		<fr:destination name="invalid" path="<%= "/caseHandlingDegreeCandidacyForGraduatedPersonIndividualProcess.do?method=executeEditCandidacyInformationInvalid&amp;processId=" + processId.toString() %>" />
	</fr:edit>

	<h3 class="mtop15 mbottom025"><bean:message key="label.selectDegree" bundle="APPLICATION_RESOURCES"/>:</h3>
	<fr:edit id="individualCandidacyProcessBean.degree"
		name="individualCandidacyProcessBean"
		schema="DegreeCandidacyForGraduatedPersonIndividualProcessBean.selectDegree.manage">
		<fr:layout name="tabular-editable">
			<fr:property name="classes" value="tstyle4 thlight thright mtop025"/>
	        <fr:property name="columnClasses" value="width12em,,tdclear tderror1"/>
		</fr:layout>
		<fr:destination name="invalid" path="<%= "/caseHandlingDegreeCandidacyForGraduatedPersonIndividualProcess.do?method=executeEditCandidacyInformationInvalid&amp;processId=" + processId.toString() %>" />
	</fr:edit>
		
	<h2 class="mtop1"><bean:message key="title.educational.background" bundle="CANDIDATE_RESOURCES"/></h2>
	
	<p><strong><bean:message key="title.bachelor.degree.owned" bundle="CANDIDATE_RESOURCES"/></strong></p>
	<p style="margin-bottom: 0.5em;"><bean:message key="label.degree.candidacy.for.graduated.person.institution.attended" bundle="CANDIDATE_RESOURCES"/>: <span class="redtxt">*</span></p>
	<div class="flowerror">
	<fr:edit id="individualCandidacyProcessBean.institutionUnitName"
		name="individualCandidacyProcessBean"
		schema="PublicCandidacyProcessBean.institutionUnitName.manage.autoComplete">
		<fr:layout name="flow">
			<fr:property name="labelExcluded" value="true"/>
		</fr:layout>
	</fr:edit>
	</div>
	
	<p style="margin-bottom: 0.5em;"><bean:message key="label.university.previously.attended.country" bundle="CANDIDATE_RESOURCES"/>: <span class="redtxt">*</span></p>
	<div class="flowerror">
	<fr:edit id="individualCandidacyProcessBean.country"
		name="individualCandidacyProcessBean"
		schema="PublicCandidacyProcessBean.institution.country.manage">
	  	<fr:layout name="flow">
			   <fr:property name="labelExcluded" value="true"/>
		</fr:layout>
	</fr:edit>			
	</div>
	
	<p style="margin-bottom: 0.5em;"><bean:message key="label.bachelor.degree.previously.enrolled" bundle="CANDIDATE_RESOURCES"/>: <span class="redtxt">*</span></p>
	<div class="flowerror">
	<fr:edit id="individualCandidacyProcessBean.degreeDesignation"
		name="individualCandidacyProcessBean"
		schema="PublicCandidacyProcessBean.degreeDesignation.manage">
	  	<fr:layout name="flow">
			   <fr:property name="labelExcluded" value="true"/>
		</fr:layout>
	</fr:edit>
	</div>

	<p style="margin-bottom: 0.5em;"><bean:message key="label.bachelor.degree.conclusion.date" bundle="CANDIDATE_RESOURCES"/>: <span class="redtxt">*</span></p>
	<div class="flowerror">
	<fr:edit id="individualCandidacyProcessBean.conclusionDate"
		name="individualCandidacyProcessBean"
		schema="PublicCandidacyProcessBean.precedent.degree.information.conclusionDate">
	  	<fr:layout name="flow">
			   <fr:property name="labelExcluded" value="true"/>
		</fr:layout>
	</fr:edit>
	</div>
	
	<p style="margin-bottom: 0.5em;"><bean:message key="label.bachelor.degree.conclusion.grade" bundle="CANDIDATE_RESOURCES"/>: <span class="redtxt">*</span></p>
	<div class="flowerror">
	<fr:edit id="individualCandidacyProcessBean.conclusionGrade"
		name="individualCandidacyProcessBean"
		schema="PublicCandidacyProcessBean.precedent.degree.information.conclusionGrade">
	  	<fr:layout name="flow">
			   <fr:property name="labelExcluded" value="true"/>
		</fr:layout>
	</fr:edit>
	</div>

	<p class="mtop15 mbottom05"><strong><bean:message key="title.other.academic.titles" bundle="CANDIDATE_RESOURCES"/></strong></p>
	<logic:iterate id="academicTitle" name="individualCandidacyProcessBean" property="formationConcludedBeanList" indexId="index">
		<bean:define id="academicTitleId" name="academicTitle" property="id"/>
		<bean:define id="designationId"><%= "individualCandidacyProcessBean.habilitation.concluded.designation:" + academicTitleId %></bean:define>
		<bean:define id="institutionNameId"><%= "individualCandidacyProcessBean.habilitation.concluded.institutionName:" + academicTitleId %></bean:define>
		<bean:define id="beginYearId"><%= "individualCandidacyProcessBean.habilitation.concluded.begin.year:" + academicTitleId %></bean:define>
		<bean:define id="endYearId"><%= "individualCandidacyProcessBean.habilitation.concluded.end.year:" + academicTitleId %></bean:define>
		<bean:define id="conclusionGradeId"><%= "individualCandidacyProcessBean.habilitation.concluded.conclusion.grade:" + academicTitleId %></bean:define>
		
		<table class="tstyle5 thlight thleft mtop0 mbottom0">
			<tr>
				<th><bean:message key="label.other.academic.titles.program.name" bundle="CANDIDATE_RESOURCES"/>: <span class="redtxt">*</span></th>
				<td>
					<div class="flowerror_hide">
						<fr:edit 	id='<%= designationId %>' 
									name="academicTitle"
									schema="PublicCandidacyProcessBean.formation.designation">
							<fr:layout name="flow"> <fr:property name="labelExcluded" value="true"/> </fr:layout>
						</fr:edit>
					</div>
				</td>
				<td rowspan="4">
					<p><a onclick='<%= "document.getElementById(\"skipValidationId\").value=\"true\"; document.getElementById(\"removeIndexId\").value=" + index + "; document.getElementById(\"methodId\").value=\"removeConcludedHabilitationsEntry\"; document.getElementById(\"candidacyForm\").submit();" %>' href="#" ><bean:message key="label.remove" bundle="CANDIDATE_RESOURCES"/></a></p>
				</td>
				<td class="tdclear">
					<span class="error0"><fr:message for="<%= designationId %>"/></span>
				</td>
			</tr>
			<tr>
				<th><bean:message key="label.other.academic.titles.institution" bundle="CANDIDATE_RESOURCES"/>: <span class="redtxt">*</span></th>
				<td>
					<div class="flowerror_hide">
						<fr:edit 	id='<%= institutionNameId %>' 
							name="academicTitle"
							schema="PublicCandidacyProcessBean.formation.institutionUnitName">
							<fr:layout name="flow"> <fr:property name="labelExcluded" value="true"/> </fr:layout>
						</fr:edit>
					</div>
				</td>
				<td class="tdclear">
					<span class="error0"><fr:message for="<%= institutionNameId %>"/></span>
				</td>					
			</tr>
			<tr>
				<th><bean:message key="label.other.academic.titles.conclusion.date" bundle="CANDIDATE_RESOURCES"/>: <span class="redtxt">*</span></th>
				<td>
					<div class="flowerror_hide">
						<fr:edit 	id='<%= endYearId %>'
									name="academicTitle"
									schema="PublicCandidacyProcessBean.formation.conclusion.date">
							<fr:layout name="flow"> <fr:property name="labelExcluded" value="true"/> </fr:layout>
						</fr:edit>
						dd/mm/aaaa
					</div>
				</td>
				<td class="tdclear">
					<span class="error0"><fr:message for="<%= endYearId %>"/></span>					
				</td>					
			</tr>
			<tr>
				<th><bean:message key="label.other.academic.titles.conclusion.grade" bundle="CANDIDATE_RESOURCES"/>: <span class="redtxt">*</span></th>
				<td>
					<div class="flowerror_hide">
						<fr:edit 	id='<%= conclusionGradeId %>'
									name="academicTitle"
									schema="PublicCandidacyProcessBean.formation.conclusion.grade">
							<fr:layout name="flow"> <fr:property name="labelExcluded" value="true"/> </fr:layout>
						</fr:edit>
					</div>
				</td>
				<td class="tdclear">
					<span class="error0"><fr:message for="<%= conclusionGradeId %>"/></span>					
				</td>					
			</tr>
		</table>
		<p/>
	</logic:iterate>
	<p class="mtop05 mbottom2"><a onclick="document.getElementById('skipValidationId').value='true'; document.getElementById('methodId').value='addConcludedHabilitationsEntry'; document.getElementById('candidacyForm').submit();" href="#">+ <bean:message key="label.add" bundle="CANDIDATE_RESOURCES"/></a></p>

	<p style="margin-bottom: 0.5em;"><bean:message key="label.observations" bundle="CANDIDATE_RESOURCES"/>:</p>
	<fr:edit id="individualCandidacyProcessBean.observations"
		name="individualCandidacyProcessBean"
		schema="PublicCandidacyProcessBean.observations">
		  <fr:layout name="flow">
	    <fr:property name="labelExcluded" value="true"/>
	  </fr:layout>
	</fr:edit>

	<h3><bean:message key="message.is.student.of.utl.network" bundle="CANDIDATE_RESOURCES"/> <span class="red">*</span></h3>
	<fr:edit id="individualCandidacyProcessBean.utlStudent" name="individualCandidacyProcessBean"
		schema="PublicCandidacyProcessBean.utl.student">
		<fr:layout name="flow">
			<fr:property name="labelExcluded" value="true"/>
		</fr:layout>
	</fr:edit>
	
	<p>
		<html:submit onclick="this.form.method.value='executeEditCandidacyInformation'; return true;"><bean:message key="label.edit" bundle="APPLICATION_RESOURCES" /></html:submit>
		<html:cancel onclick="this.form.method.value='listProcessAllowedActivities'; return true;"><bean:message key="label.back" bundle="APPLICATION_RESOURCES" /></html:cancel>
	</p>
</fr:form>