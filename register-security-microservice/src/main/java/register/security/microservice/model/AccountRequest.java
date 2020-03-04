/**
 * 
 */
package register.security.microservice.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bruno.rivera
 *
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nickname;
	private String email;
	public AccountRequest() {}
	
	public AccountRequest(String email) {
		this.email = email;
	}
	
	
}
