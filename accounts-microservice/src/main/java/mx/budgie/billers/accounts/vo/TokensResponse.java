/**
 * 
 */
package mx.budgie.billers.accounts.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import mx.budgie.billers.accounts.response.ResponseMessage;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 28, 2017
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokensResponse extends ResponseMessage{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String clientId;	
	private String clientSecret;
	@JsonProperty(value="clientAccessToken")
	private String accessToken;
	@JsonIgnore
	private String key;
				
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}	
	
	
}
