/**
 * 
 */
package mx.budgie.commons.email;

import java.io.Serializable;
import java.util.Map;

import mx.budgie.commons.utils.EmailTemplateType;

/**
 * @author bruno.rivera
 *
 */

public class EmailRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, String> headers; 
	private String authorizationToken;
	private EmailTemplateType templateType; 
	private String to; 
	private String from;
	private String subject;
	private Map<String, String> custom;
	
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	public String getAuthorizationToken() {
		return authorizationToken;
	}
	public void setAuthorizationToken(String authorizationToken) {
		this.authorizationToken = authorizationToken;
	}
	public EmailTemplateType getTemplateType() {
		return templateType;
	}
	public void setTemplateType(EmailTemplateType templateType) {
		this.templateType = templateType;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String emailTo) {
		this.to = emailTo;
	}
	public Map<String, String> getCustom() {
		return custom;
	}
	public void setCustom(Map<String, String> custom) {
		this.custom = custom;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String emailFrom) {
		this.from = emailFrom;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String emailSubject) {
		this.subject = emailSubject;
	} 
	
}
