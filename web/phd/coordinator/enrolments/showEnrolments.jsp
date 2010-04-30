<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<%@page import="net.sourceforge.fenixedu.domain.Enrolment"%>


<%@page import="net.sourceforge.fenixedu.presentationTier.Action.phd.ManageEnrolmentsBean"%><logic:present role="COORDINATOR">

<em><bean:message key="label.phd.coordinator.breadcrumb" bundle="PHD_RESOURCES"/></em>
<h2><bean:message key="label.phd.manage.enrolments" bundle="PHD_RESOURCES" /></h2>

<%--  ###  Return Links / Steps Information(for multistep forms)  ### --%>

<%-- add method to bean to retrieve phd program --%>
<bean:define id="phdProgramOid" name="manageEnrolmentsBean" property="curricularCourse.degreeCurricularPlan.degree.phdProgram.externalId" />

<html:link action="<%= "/phdEnrolmentsManagement.do?method=showPhdProgram&phdProgramOid=" + phdProgramOid.toString() %>">
	<bean:message bundle="PHD_RESOURCES" key="label.back"/>
</html:link>

<%--  ### Return Links / Steps Information (for multistep forms)  ### --%>

<br/>
<fr:form action="/phdEnrolmentsManagement.do?method=manageEnrolments">
	<fr:edit id="manageEnrolmentsBean" name="manageEnrolmentsBean">
	
		<fr:schema bundle="PHD_RESOURCES" type="<%= ManageEnrolmentsBean.class.getName() %>">
			<fr:slot name="semester" layout="menu-select-postback">
				<fr:property name="providerClass" value="<%= ManageEnrolmentsBean.CurricularCourseDegreeExecutionSemesterProvider.class.getName()  %>"/>
				<fr:property name="format" value="${qualifiedName}" />
			</fr:slot>
		</fr:schema>
	
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle5 thlight thright mtop05" />
			<fr:property name="columnClasses" value=",,tdclear tderror1" />
		</fr:layout>
		
		<fr:destination name="postback" path="/phdEnrolmentsManagement.do?method=manageEnrolments" />
	</fr:edit>
</fr:form>

<h3><bean:write name="manageEnrolmentsBean" property="curricularCourse.degreeCurricularPlan.name" />: <bean:write name="manageEnrolmentsBean" property="curricularCourseName" /></h3>
<fr:view name="manageEnrolmentsBean" property="enrolments">

	<fr:schema bundle="PHD_RESOURCES" type="<%= Enrolment.class.getName() %>">
		<fr:slot name="person.name" />
		<fr:slot name="person.institutionalEmailAddressValue" />
		<fr:slot name="finalGrade" />
	</fr:schema>
	
	<fr:layout name="tabular">
		<fr:property name="classes" value="tstyle2 thlight mtop10" />
		<fr:property name="sortBy" value="person.name=asc" />
		
		<fr:link name="view" label="label.view,PHD_RESOURCES" target="blank" link="/phdIndividualProgramProcess.do?method=viewProcess&processId=${registration.phdIndividualProgramProcess.externalId}"/>
	</fr:layout>

</fr:view>

<logic:empty name="manageEnrolmentsBean" property="enrolments">
	<em><bean:message key="label.phd.no.enrolments.found" bundle="PHD_RESOURCES" /></em>
</logic:empty>

</logic:present>
