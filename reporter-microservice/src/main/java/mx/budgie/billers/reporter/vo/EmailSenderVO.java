/**
 * 
 */
package mx.budgie.billers.reporter.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author bruno.rivera
 *
 */
public class EmailSenderVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank
	private String from;
	@NotBlank
	private String to;
	private String bcc;
	private String message;
	private String subject;
	@NotNull
	private EmailTemplateType templateType;	
	private String link;
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public EmailTemplateType getTemplateType() {
		return templateType;
	}
	public void setTemplateType(EmailTemplateType templateType) {
		this.templateType = templateType;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	
}
