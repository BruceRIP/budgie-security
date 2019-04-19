/**
 * 
 */
package mx.budgie.billers.accounts.request;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author bruno.rivera
 *
 */
public class UpdateRolesParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank
	private String billerID;
	@NotNull
	private Set<String> roles;
	
	public String getBillerID() {
		return billerID;
	}
	public void setBillerID(String billerID) {
		this.billerID = billerID;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
}
