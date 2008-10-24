package net.sourceforge.fenixedu.domain.util.email;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import net.sourceforge.fenixedu.domain.DomainReference;
import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.Language;

public class EmailBean implements Serializable {

    private DomainReference<Sender> sender;
    private Set<DomainReference<Recipient>> recipients;
    private String tos, ccs, bccs;
    private String subject, message;

    public EmailBean() {
    }

    public Sender getSender() {
	return sender == null ? null : sender.getObject();
    }

    public void setSender(final Sender sender) {
	this.sender = sender == null ? null : new DomainReference<Sender>(sender);
    }

    public List<Recipient> getRecipients() {
	final List<Recipient> result = new ArrayList<Recipient>();
	if (recipients != null) {
	    for (final DomainReference<Recipient> recipient : recipients) {
		result.add(recipient.getObject());
	    }
	}
	return result;
    }

    public void setRecipients(List<Recipient> recipients) {
	if (recipients == null) {
	    this.recipients = null;
	} else {
	    this.recipients = new HashSet<DomainReference<Recipient>>();
	    for (final Recipient recipient : recipients) {
		this.recipients.add(new DomainReference<Recipient>(recipient));
	    }
	}
    }

    public String getTos() {
	return tos;
    }

    public void setTos(String tos) {
	this.tos = tos;
    }

    public String getCcs() {
	return ccs;
    }

    public void setCcs(String ccs) {
	this.ccs = ccs;
    }

    public String getBccs() {
	return bccs;
    }

    public void setBccs(String bccs) {
	this.bccs = bccs;
    }

    public String getSubject() {
	return subject;
    }

    public void setSubject(String subject) {
	this.subject = subject;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    @Service
    public Message send() {
	final ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.ApplicationResources", Language.getLocale());

	final StringBuilder message = new StringBuilder();
	message.append(getMessage());
	message.append("\n\n---\n");
	message.append(resourceBundle.getString("message.email.footer.prefix"));
	message.append(getSender().getFromName());
	message.append(resourceBundle.getString("message.email.footer.prefix.suffix"));
	for (final Recipient recipient : getRecipients()) {
	    message.append("\n\t");
	    message.append(recipient.getToName());
	}
	message.append("\n");

	return new Message(getSender(), getRecipients(), getSubject(), message.toString(), getBccs());
    }

}
