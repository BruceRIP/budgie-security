/**
 * 
 */
package mx.budgie.security.sso.builder;

import org.springframework.stereotype.Component;

import mx.budgie.billers.accounts.mongo.documents.OauthClientDetailsDocument;
import mx.budgie.security.sso.vo.ClientVO;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 27, 2017
 */
@Component
public class ClientBuilder extends AbstractBuilder<OauthClientDetailsDocument, ClientVO>{	
	
	private ClientVO client;

	@Override
	public ClientVO createObject() {
		client = new ClientVO();
		return client;
	}
	
	@Override
	public ClientVO buildDocumentFromSource(OauthClientDetailsDocument source) {
		createObject();
		client.setAccessTokenValiditySeconds(source.getAccessTokenValidity());
		client.setAdditionalInformation(source.getAdditionalInformation());
		client.setAuthorities(source.getAuthorities());
		client.setAuthorizedGrantTypes(source.getAuthorizationGrantTypes());
		client.setAutoApprove(source.isAutoApprove());
		client.setClientId(source.getClientId());
		client.setClientSecret(source.getClientSecret());
		client.setRefreshTokenValiditySeconds(source.getRefreshTokenValidity());
		client.setRegisteredRedirectUri(source.getRedirectUris());
		client.setResourcesId(source.getResourceIds());
		client.setScope(source.getScope());
//		client.setScoped(source.isScoped());
//		client.setSecretRequired(source.isSecretRequired());		
		return client;
	}
}
