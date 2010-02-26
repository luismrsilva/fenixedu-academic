package net.sourceforge.fenixedu.presentationTier.Action.administrativeOffice.documents;

import java.util.Collection;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sourceforge.fenixedu.applicationTier.Filtro.exception.FenixFilterException;
import net.sourceforge.fenixedu.applicationTier.Servico.exceptions.FenixServiceException;
import net.sourceforge.fenixedu.applicationTier.Servico.person.SearchPerson;
import net.sourceforge.fenixedu.applicationTier.Servico.person.SearchPerson.SearchParameters;
import net.sourceforge.fenixedu.applicationTier.Servico.person.SearchPerson.SearchPersonPredicate;
import net.sourceforge.fenixedu.dataTransferObject.person.SimpleSearchPersonWithStudentBean;
import net.sourceforge.fenixedu.domain.Person;
import net.sourceforge.fenixedu.domain.accounting.Event;
import net.sourceforge.fenixedu.domain.documents.AnnualIRSDeclarationDocument;
import net.sourceforge.fenixedu.domain.exceptions.DomainException;
import net.sourceforge.fenixedu.injectionCode.AccessControl;
import net.sourceforge.fenixedu.presentationTier.Action.base.FenixDispatchAction;
import net.sourceforge.fenixedu.presentationTier.docs.IRSCustomDeclaration;
import net.sourceforge.fenixedu.presentationTier.docs.IRSCustomDeclaration.IRSDeclarationDTO;
import net.sourceforge.fenixedu.presentationTier.formbeans.FenixActionForm;
import net.sourceforge.fenixedu.util.report.ReportsUtils;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import pt.ist.fenixWebFramework.struts.annotations.Forward;
import pt.ist.fenixWebFramework.struts.annotations.Forwards;
import pt.ist.fenixWebFramework.struts.annotations.Mapping;
import pt.utl.ist.fenix.tools.util.CollectionPager;
import pt.utl.ist.fenix.tools.util.i18n.Language;

@Mapping(path = "/generatedDocuments", module = "academicAdminOffice", formBeanClass = FenixActionForm.class)
@Forwards( {

@Forward(name = "searchPerson", path = "/academicAdminOffice/generatedDocuments/searchPerson.jsp"),

@Forward(name = "showAnnualIRSDocuments", path = "/academicAdminOffice/generatedDocuments/showAnnualIRSDocuments.jsp"),

@Forward(name = "payments.manageIRSDocuments", path = "/academicAdminOffice/generatedDocuments/payments/manageIRSDocuments.jsp")

})
public class GeneratedDocumentsDA extends FenixDispatchAction {

    public ActionForward prepareSearchPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	    HttpServletResponse response) {

	request.setAttribute("searchPersonBean", new SimpleSearchPersonWithStudentBean());

	return mapping.findForward("searchPerson");
    }

    public ActionForward prepareSearchPersonInvalid(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	    HttpServletResponse response) {

	request.setAttribute("searchPersonBean", getObjectFromViewState("searchPersonBean"));

	return mapping.findForward("searchPerson");
    }

    public ActionForward searchPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	    HttpServletResponse response) throws FenixFilterException, FenixServiceException {
	final SimpleSearchPersonWithStudentBean searchPersonBean = (SimpleSearchPersonWithStudentBean) getObjectFromViewState("searchPersonBean");
	request.setAttribute("searchPersonBean", searchPersonBean);

	request.setAttribute("persons", searchPerson(request, searchPersonBean));

	return mapping.findForward("searchPerson");
    }

    @SuppressWarnings("unchecked")
    private Collection<Person> searchPerson(HttpServletRequest request, SimpleSearchPersonWithStudentBean searchPersonBean)
	    throws FenixFilterException, FenixServiceException {
	final SearchParameters searchParameters = new SearchPerson.SearchParameters(searchPersonBean.getName(), null,
		searchPersonBean.getUsername(), searchPersonBean.getDocumentIdNumber(),
		searchPersonBean.getIdDocumentType() != null ? searchPersonBean.getIdDocumentType().toString() : null, null,
		null, null, null, null, searchPersonBean.getStudentNumber(), Boolean.FALSE);

	final SearchPersonPredicate predicate = new SearchPerson.SearchPersonPredicate(searchParameters);

	final CollectionPager<Person> result = (CollectionPager<Person>) executeService("SearchPerson", new Object[] {
		searchParameters, predicate });

	return result.getCollection();
    }

    public ActionForward showAnnualIRSDocuments(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	    HttpServletResponse response) {

	request.setAttribute("person", getPerson(request));

	request.setAttribute("annualIRSDocuments", getPerson(request).getAnnualIRSDocuments());

	return mapping.findForward("showAnnualIRSDocuments");
    }

    private Person getPerson(HttpServletRequest request) {
	return (Person) rootDomainObject.readPartyByOID(getIntegerFromRequest(request, "personId"));
    }

    public ActionForward showAnnualIRSDocumentsInPayments(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) {
	showAnnualIRSDocuments(mapping, actionForm, request, response);
	return mapping.findForward("payments.manageIRSDocuments");
    }

    public ActionForward generateNewAnnualIRSDeclarationDocument(ActionMapping mapping, ActionForm actionForm,
	    HttpServletRequest request, HttpServletResponse response) throws JRException {

	try {
	    final AnnualIRSDeclarationDocument document = getDomainObject(request, "annualIRSDocumentOid");
	    request.setAttribute("personId", document.getAddressee().getIdInternal());

	    byte[] declaration = buildIRSCustomDeclaration(document.getAddressee(), document.getYear().intValue());
	    document.generateAnotherDeclaration(AccessControl.getPerson(), declaration);
	    addActionMessage("success", request, "message.new.irs.annual.document.generated.with.success");

	} catch (final DomainException e) {
	    addActionMessage("error", request, e.getMessage(), e.getArgs());
	}

	return showAnnualIRSDocumentsInPayments(mapping, actionForm, request, response);
    }

    private byte[] buildIRSCustomDeclaration(Person person, int civilYear) throws JRException {
	final IRSDeclarationDTO declarationDTO = new IRSDeclarationDTO(civilYear, person);

	for (final Event event : person.getEvents()) {
	    if (event.hasPaymentsByPersonForCivilYear(civilYear)
		    && event.getMaxDeductableAmountForLegalTaxes(civilYear).isPositive()) {

		declarationDTO.addAmount(event, civilYear);
	    }
	}

	final IRSCustomDeclaration customDeclaration = new IRSCustomDeclaration(declarationDTO);

	return ReportsUtils.exportToPdfFileAsByteArray(customDeclaration.getReportTemplateKey(), customDeclaration
		.getParameters(), ResourceBundle.getBundle("resources.AcademicAdminOffice", Language.getLocale()),
		customDeclaration.getDataSource());
    }
}
