/**
 * 
 */
package mx.budgie.billers.accounts.vo;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import mx.budgie.billers.accounts.response.ResponseMessage;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 25, 2017
 */
public class ClientAuthenticationVO extends ResponseMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String clientId;
	private Date expirationDate;
	private String tokenType;
	private Set<String> resourceIds;
	private Set<String> scope;	
	private Set<String> authorizationGrantTypes; 
	private Set<String> redirectUris;
	private Set<String> authorities;
	private Boolean autoApprove;
	
	private String clientSecret;
	private int accessTokenValidity;
	private int refreshTokenValidity;
	private Map<String, Object> additionalInformation;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public Set<String> getResourceIds() {
		return resourceIds;
	}
	public void setResourceIds(Set<String> resourceIds) {
		this.resourceIds = resourceIds;
	}
	public Set<String> getScope() {
		return scope;
	}
	public void setScope(Set<String> scope) {
		this.scope = scope;
	}
	public Set<String> getAuthorizationGrantTypes() {
		return authorizationGrantTypes;
	}
	public void setAuthorizationGrantTypes(Set<String> authorizationGrantTypes) {
		this.authorizationGrantTypes = authorizationGrantTypes;
	}
	public Set<String> getRedirectUris() {
		return redirectUris;
	}
	public void setRedirectUris(Set<String> redirectUris) {
		this.redirectUris = redirectUris;
	}
	public Set<String> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}
	public Boolean isAutoApprove() {
		return autoApprove;
	}
	public void setAutoApprove(Boolean autoApprove) {
		this.autoApprove = autoApprove;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public int getAccessTokenValidity() {
		return accessTokenValidity;
	}
	public void setAccessTokenValidity(int accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}
	public int getRefreshTokenValidity() {
		return refreshTokenValidity;
	}
	public void setRefreshTokenValidity(int refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
	}
	public Map<String, Object> getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(Map<String, Object> additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	
}
