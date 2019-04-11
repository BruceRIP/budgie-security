/**
 * 
 */
package mx.budgie.security.sso.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 25, 2017
 */
public class ClientVO implements ClientDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String clientId;
	private String clientSecret;
	private Set<String> resourcesId;
	private boolean secretRequired;
	private boolean scoped;
	private Set<String> scope;
	private Set<String> authorizedGrantTypes;
	private Set<String> registeredRedirectUri;
	private Collection<GrantedAuthority> authorities;
	private int accessTokenValiditySeconds;
	private int refreshTokenValiditySeconds;
	private boolean autoApprove;
	private Map<String, Object> additionalInformation;

	@Override
	public String getClientId() {
		return this.clientId;
	}

	@Override
	public String getClientSecret() {
		return this.clientSecret;
	}
	
	@Override
	public Set<String> getResourceIds() {
		return resourcesId;
	}

	@Override
	public boolean isSecretRequired() {
		return secretRequired;
	}

	@Override
	public boolean isScoped() {
		return scoped;
	}

	@Override
	public Set<String> getScope() {
		return scope;
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		return registeredRedirectUri;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return refreshTokenValiditySeconds;
	}

	@Override
	public boolean isAutoApprove(String scope) {
		return autoApprove;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return additionalInformation;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public void setResourcesId(Set<String> resourcesId) {
		this.resourcesId = resourcesId;
	}

	public void setSecretRequired(boolean secretRequired) {
		this.secretRequired = secretRequired;
	}

	public void setScoped(boolean scoped) {
		this.scoped = scoped;
	}

	public void setScope(Set<String> scope) {		
		this.scope = scope;
	}

	public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	public void setRegisteredRedirectUri(Set<String> registeredRedirectUri) {
		this.registeredRedirectUri = registeredRedirectUri;
	}

	public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}

	public void setRefreshTokenValiditySeconds(int refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}

	public void setAutoApprove(boolean autoApprove) {
		this.autoApprove = autoApprove;
	}

	public void setAdditionalInformation(Map<String, Object> additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	
	@SuppressWarnings("serial")
	public void setAuthorities(Collection<String> authorities) {				
		if(null != authorities && !authorities.isEmpty()){
			this.authorities = new ArrayList<>();
			for(String auth : authorities){
				this.authorities.add(new GrantedAuthority() {			
					@Override
					public String getAuthority() {
						return auth;
					}
				});
			}
		}
	}
}
