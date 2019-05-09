/**
 * 
 */
package register.security.microservice.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bruno.rivera
 *
 */
@Getter
@Setter
public class AccountRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nickname;
	private String email;
}
