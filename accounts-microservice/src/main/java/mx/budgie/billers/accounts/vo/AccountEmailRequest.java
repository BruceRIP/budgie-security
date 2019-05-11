/**
 * 
 */
package mx.budgie.billers.accounts.vo;

import java.io.Serializable;

/**
 * @author bruno.rivera
 *
 */
public class AccountEmailRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
