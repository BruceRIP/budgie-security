/**
 * 
 */
package mx.budgie.billers.accounts.builder;

import org.springframework.stereotype.Component;

import mx.budgie.billers.accounts.mongo.documents.ClientAuthenticationDocument;
import mx.budgie.billers.accounts.vo.ClientAuthVO;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 27, 2017
 */
@Component
public class ClientAuthBuilder extends AbstractBuilder<ClientAuthVO, ClientAuthenticationDocument>{	
	
	private ClientAuthVO client;
	private ClientAuthenticationDocument document;
	
	@Override
	public ClientAuthVO createSource() {
		client = new ClientAuthVO();
		return client;
	}
	
	@Override
	public ClientAuthenticationDocument createDocument() {
		document = new ClientAuthenticationDocument();
		return document;
	}
	
	@Override
	public ClientAuthVO buildSourceFromDocument(ClientAuthenticationDocument document) {
		createSource();
		client.setAccessTokenValiditySeconds(document.getAccessTokenValiditySeconds());
		client.setAdditionalInformation(document.getAdditionalInformation());
		client.setAuthorities(document.getAuthorities());
		client.setAuthorizedGrantTypes(document.getAuthorizedGrantTypes());
		client.setAutoApprove(document.isAutoApprove());
		client.setClientId(document.getClientId());
		client.setClientSecret(document.getClientSecret());		
		client.setRefreshTokenValiditySeconds(document.getRefreshTokenValiditySeconds());
		client.setRegisteredRedirectUri(document.getRegisteredRedirectUri());
		client.setResourcesId(document.getResourcesId());
		client.setScope(document.getScope());
		client.setScoped(document.isScoped());
		client.setSecretRequired(document.isSecretRequired());	
		client.setClientEmail(document.getEmail());
		return client;
	}
	
	@Override
	public ClientAuthenticationDocument buildDocumentFromSource(ClientAuthVO source) {
		createDocument();
		document.setAccessTokenValiditySeconds(source.getAccessTokenValiditySeconds());
		document.setAdditionalInformation(source.getAdditionalInformation());
		document.setAuthorities(source.getAuthorities());
		document.setAuthorizedGrantTypes(source.getAuthorizedGrantTypes());
		document.setAutoApprove(source.isAutoApprove());
		document.setName(source.getClientId());
		document.setClientId(source.getClientId());
		document.setClientSecret(source.getClientSecret());
		document.setRefreshTokenValiditySeconds(source.getRefreshTokenValiditySeconds());
		document.setRegisteredRedirectUri(source.getRegisteredRedirectUri());
		document.setResourcesId(source.getResourcesId());
		document.setScope(source.getScope());
		document.setScoped(source.isScoped());
		document.setSecretRequired(source.isSecretRequired());	
		document.setEmail(source.getClientEmail());
		return document;
	}
}
