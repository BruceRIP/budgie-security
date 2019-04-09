/**
 * 
 */
package mx.budgie.billers.accounts.mongo.documents;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 25, 2017
 */
@Document(collection = "OauthClientCredentials")
public class OauthClientDetailsDocument implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Identificador numerico
	@Id
	private long id;
	// Nombre del cliente. Asignado al momento de crear la cuenta de cliente
	private String name;
	// Fecha de expiracion del token de accesso (authenticationToken)
	private Date expirationDate;
	// Minutos de expiracion del token de accesso (authenticationToken)
	private int expiresIn;
	// Tipo de token. Ej: Bearer, Basic
	private String tokenType;
	// Token que es generado para peticion con HTTP Basic
	private String authenticationToken;
	// identificador que sera entregado al momento de solicitar un token de acceso
	private String clientId;
	// Valor que sera entregado al momento de solicitar un token de acceso
	private String clientSecret;
	// Recursos a los que tiene permitido ingresar el cliente
	private Set<String> resourceIds;
	// Acciones que tiene el cliente sobre la plataforma (escritura, lectura, ejecucion)
	private Set<String> scope;		
	// Indica que tipo de autorizacion soporta (authorization_code, implicit, client_credentials, password)
	private Set<String> authorizationGrantTypes; 
	// Una vez que el usuario esta autorizado, este campo indica a donde sera la redireccion
	private Set<String> redirectUris;
	 // Indica que tipo de roles tiene el cliente
	private Set<String> authorities;
	// Periodo de validez del token de acceso (Milisegundos)
	private int accessTokenValidity;
	// Periodo de validez del token de refresh, cuando expira el token de acceso es necesario enviar el refresh token para generar uno nuevo (Milisegundos)
	private int refreshTokenValidity;
	private Map<String, Object> additionalInformation;
	private boolean autoApprove;
	// Guarda los token generados para el cliente por el Servidor de Autorizacion
	private TokenAuthentication tokenAuthentication;
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
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public String getAuthenticationToken() {
		return authenticationToken;
	}
	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
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
	public boolean isAutoApprove() {
		return autoApprove;
	}
	public void setAutoApprove(boolean autoApprove) {
		this.autoApprove = autoApprove;
	}
	public TokenAuthentication getTokenAuthentication() {
		return tokenAuthentication;
	}
	public void setTokenAuthentication(TokenAuthentication tokenAuthentication) {
		this.tokenAuthentication = tokenAuthentication;
	}
		
}
