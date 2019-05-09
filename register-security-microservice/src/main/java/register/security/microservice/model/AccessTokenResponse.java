/**
 * 
 */
package register.security.microservice.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bruno.rivera
 *
 */
@Getter
@Setter
public class AccessTokenResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty(value = "access_token")
	private String accessToken;
	@JsonProperty(value = "token_type")
	private String tokenType;
	@JsonProperty(value = "expires_in")
	private Long expiresIn;
	@JsonProperty(value = "scope")
	private String scope;
}
