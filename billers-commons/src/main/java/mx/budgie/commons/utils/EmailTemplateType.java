/**
 * 
 */
package mx.budgie.commons.utils;

/**
 * @author bruno.rivera
 *
 */
public enum EmailTemplateType {

	ACTIVATE_ACCOUNT("src/main/resources/static/email-templates/activate_account.html"),
	CLIENT_CREDENTIALS("src/main/resources/static/email-templates/client_credentials.html"),
	RESET_PASSWORD("src/main/resources/static/email-templates/reset_password.html");

	
	private String path;

	private EmailTemplateType(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
	
	
}
