/**
 * 
 */
package mx.budgie.billers.accounts.vo;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import mx.budgie.billers.accounts.response.ResponseMessage;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 25, 2017
 */
public class ClientAuthVO extends ResponseMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String clientId;
	private String clientSecret;
	private String clientEmail;
	private Set<String> resourcesId;
	private boolean secretRequired;
	private boolean scoped;
	private Set<String> scope;
	private Set<String> authorizedGrantTypes;
	private Set<String> registeredRedirectUri;
	private Collection<String> authorities;
	private int accessTokenValiditySeconds;
	private int refreshTokenValiditySeconds;
	private boolean autoApprove;
	private Map<String, Object> additionalInformation;

	
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

	public Set<String> getResourcesId() {
		return resourcesId;
	}

	public void setResourcesId(Set<String> resourcesId) {
		this.resourcesId = resourcesId;
	}

	public boolean isSecretRequired() {
		return secretRequired;
	}

	public void setSecretRequired(boolean secretRequired) {
		this.secretRequired = secretRequired;
	}

	public boolean isScoped() {
		return scoped;
	}

	public void setScoped(boolean scoped) {
		this.scoped = scoped;
	}

	public Set<String> getScope() {
		return scope;
	}

	public void setScope(Set<String> scope) {
		this.scope = scope;
	}

	public Set<String> getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	public Set<String> getRegisteredRedirectUri() {
		return registeredRedirectUri;
	}

	public void setRegisteredRedirectUri(Set<String> registeredRedirectUri) {
		this.registeredRedirectUri = registeredRedirectUri;
	}

	public Collection<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<String> authorities) {
		this.authorities = authorities;
	}

	public int getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}

	public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}

	public int getRefreshTokenValiditySeconds() {
		return refreshTokenValiditySeconds;
	}

	public void setRefreshTokenValiditySeconds(int refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}

	public boolean isAutoApprove() {
		return autoApprove;
	}

	public void setAutoApprove(boolean autoApprove) {
		this.autoApprove = autoApprove;
	}

	public Map<String, Object> getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(Map<String, Object> additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String getClientEmail() {
		return clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

}
