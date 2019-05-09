/**
 * 
 */
package register.security.microservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bruno.rivera
 *
 */
@Getter
@Setter
public class ClientResponse extends AccessTokenResponse{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String clientId;
	private String clientSecret;
	@JsonProperty(value = "clientAccessToken")
	private String clientAccessToken;
}
