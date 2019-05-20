/**
 * 
 */
package register.security.microservice.model;

import java.util.Map;
import java.util.Set;

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
	private String applicationName;
	private String clientId;
	private String clientSecret;
	@JsonProperty(value = "clientAccessToken")
	private String clientAccessToken;
	private Set<String> resourceIds;
	private Set<String> scope;	
	private Set<String> authorizationGrantTypes; 
	private Set<String> redirectUris;
	private Set<String> authorities;
	private Boolean autoApprove;
	private int accessTokenValidity;
	private int refreshTokenValidity;
	private Map<String, Object> additionalInformation;
}
