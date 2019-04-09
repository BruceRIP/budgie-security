/**
 * 
 */
package mx.budgie.billers.accounts.mongo.documents;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author bruno.rivera
 *
 */
public class TokenAuthentication implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String accessToken;
	private String refreshTokenAuth;
	private String tokenTypeAuth;
	private Date expirationDateAuth;
	private Integer expiresIn;
	private Set<String> scopes;
	private Map<String, Object> additionalInformation;
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshTokenAuth() {
		return refreshTokenAuth;
	}
	public void setRefreshTokenAuth(String refreshTokenAuth) {
		this.refreshTokenAuth = refreshTokenAuth;
	}
	public String getTokenTypeAuth() {
		return tokenTypeAuth;
	}
	public void setTokenTypeAuth(String tokenTypeAuth) {
		this.tokenTypeAuth = tokenTypeAuth;
	}
	public Date getExpirationDateAuth() {
		return expirationDateAuth;
	}
	public void setExpirationDateAuth(Date expirationDateAuth) {
		this.expirationDateAuth = expirationDateAuth;
	}
	public Integer getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}
	public Set<String> getScopes() {
		return scopes;
	}
	public void setScopes(Set<String> scopes) {
		this.scopes = scopes;
	}
	public Map<String, Object> getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(Map<String, Object> additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
		
}
