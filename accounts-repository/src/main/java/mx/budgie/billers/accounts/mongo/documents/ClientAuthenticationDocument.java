/**
 * 
 */
package mx.budgie.billers.accounts.mongo.documents;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author bruno-rivera
 *
 */
@Document(collection = "ClientsAuthentication")
public class ClientAuthenticationDocument implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private long id;
	private String name;
	private String clientToken;
	private String clientId;
	private String clientSecret;
	private String email;	
	private String key;
	private String expirationDate;
	private boolean enabled;
	
	private Set<String> resourcesId;
	private Set<String> scope;
	private Set<String> authorizedGrantTypes;
	private Set<String> registeredRedirectUri;
	private Collection<String> authorities;
	private int accessTokenValiditySeconds;
	private int refreshTokenValiditySeconds;
	private Map<String, Object> additionalInformation;
	private boolean autoApprove;

	private boolean secretRequired;
	private boolean scoped;
	

	public String getClientToken() {
		return clientToken;
	}

	public void setClientToken(String clientToken) {
		this.clientToken = clientToken;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
