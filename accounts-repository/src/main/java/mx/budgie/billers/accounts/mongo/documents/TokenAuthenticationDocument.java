/**
 * 
 */
package mx.budgie.billers.accounts.mongo.documents;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author bruno-rivera
 *
 */
public class TokenAuthenticationDocument implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tokenAuth;
	private String refreshTokenAuth;
	private String tokenTypeAuth;
	private String expirationDateAuth;
	private Integer expiresIn;
	private Set<String> scopes;
	private Map<String, Object> additionalInformation;
	
	public TokenAuthenticationDocument(){
		
	}
	
	public TokenAuthenticationDocument(Integer expireIn, String clientAuthentication, String tokenView){
		additionalInformation = new LinkedHashMap<>();
		additionalInformation.put("clientAuthentication", clientAuthentication);
		additionalInformation.put("tokenView", tokenView);
		this.expiresIn = expireIn;
	}
	
	public String getTokenAuth() {
		return tokenAuth;
	}
	public void setTokenAuth(String tokenAuth) {
		this.tokenAuth = tokenAuth;
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
	public String getExpirationDateAuth() {
		return expirationDateAuth;
	}
	public void setExpirationDateAuth(String expirationDateAuth) {
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
