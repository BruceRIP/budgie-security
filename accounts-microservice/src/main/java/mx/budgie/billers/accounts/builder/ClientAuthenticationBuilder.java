/**
 * 
 */
package mx.budgie.billers.accounts.builder;

import java.util.HashSet;
import java.util.Iterator;

import org.springframework.stereotype.Component;

import mx.budgie.billers.accounts.mongo.documents.OauthClientDetailsDocument;
import mx.budgie.billers.accounts.vo.ClientAuthenticationVO;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 27, 2017
 */
@Component
public class ClientAuthenticationBuilder extends AbstractBuilder<ClientAuthenticationVO, OauthClientDetailsDocument>{	
	
	private ClientAuthenticationVO client;
	private OauthClientDetailsDocument document;
	
	@Override
	public ClientAuthenticationVO createSource() {
		client = new ClientAuthenticationVO();
		return client;
	}
	
	@Override
	public OauthClientDetailsDocument createDocument() {
		document = new OauthClientDetailsDocument();
		return document;
	}
	
	@Override
	public ClientAuthenticationVO buildSourceFromDocument(OauthClientDetailsDocument document) {
		createSource();
		client.setAuthorities(document.getAuthorities());
		client.setAuthorizationGrantTypes(document.getAuthorizationGrantTypes());
		client.setAutoApprove(document.isAutoApprove());
		client.setClientId(document.getClientId());
		client.setExpirationDate(document.getExpirationDate());
		client.setRedirectUris(document.getRedirectUris());
		client.setResourceIds(document.getResourceIds());
		client.setScope(document.getScope());
		client.setTokenType(document.getTokenType());
		client.setAccessTokenValidity(document.getAccessTokenValidity());
		client.setRefreshTokenValidity(document.getRefreshTokenValidity());
		return client;
	}
	
	@Override
	public OauthClientDetailsDocument buildDocumentFromSource(ClientAuthenticationVO source) {
		createDocument();
		if(source.getAuthorities() != null) {
			if(document.getAuthorities() == null) {
				document.setAuthorities(new HashSet<>());
			}
			while(source.getAuthorities().iterator().hasNext()) {
				document.getAuthorities().add(source.getAuthorities().iterator().next());
			}
		}
		if(source.getAuthorizationGrantTypes() != null) {
			if(document.getAuthorizationGrantTypes() == null) {
				document.setAuthorizationGrantTypes(new HashSet<>());
			}
			while(source.getAuthorizationGrantTypes().iterator().hasNext()) {
				document.getAuthorizationGrantTypes().add(source.getAuthorizationGrantTypes().iterator().next());
			}
		}		
		if(source.getExpirationDate() != null) {
			document.setExpirationDate(source.getExpirationDate());
		}
		if(source.getRedirectUris() != null) {
			if(document.getRedirectUris() == null) {
				document.setRedirectUris(new HashSet<>());
			}
			while(source.getRedirectUris().iterator().hasNext()) {
				document.getRedirectUris().add(source.getRedirectUris().iterator().next());
			}
		}
		if(source.getResourceIds() != null) {
			if(document.getResourceIds() == null) {
				document.setResourceIds(new HashSet<>());
			}
			while(source.getResourceIds().iterator().hasNext()) {
				document.getResourceIds().add(source.getResourceIds().iterator().next());
			}
		}
		if(source.getScope() != null) {
			if(document.getScope() == null) {
				document.setScope(new HashSet<>());
			}
			while(source.getScope().iterator().hasNext()) {
				document.getScope().add(source.getScope().iterator().next());
			}
		}
		if(source.getTokenType() != null) {
			document.setTokenType(source.getTokenType());
		}
		if(source.isAutoApprove() != null) {
			document.setAutoApprove(source.isAutoApprove());
		}
		return document;
	}
	
	public OauthClientDetailsDocument buildDocumentFromSource(OauthClientDetailsDocument document, ClientAuthenticationVO source, boolean deleted) {
		if(deleted && source.getAuthorities() != null) {
			document.getAuthorities().removeIf(x -> source.getAuthorities().contains(x));
		}else {
			if(source.getAuthorities() != null) {
				if(document.getAuthorities() == null) {
					document.setAuthorities(new HashSet<>());
				}
				Iterator<String> authorities = source.getAuthorities().iterator();
				while(authorities.hasNext()) {
					document.getAuthorities().add(authorities.next() + "");
				}
			}
		}
		if(deleted && source.getAuthorizationGrantTypes() != null) {
			document.getAuthorizationGrantTypes().removeIf(x -> source.getAuthorizationGrantTypes().contains(x));
		}else {
			if(source.getAuthorizationGrantTypes() != null) {
				if(document.getAuthorizationGrantTypes() == null) {
					document.setAuthorizationGrantTypes(new HashSet<>());
				}
				Iterator<String> authorizationGrantTypes = source.getAuthorizationGrantTypes().iterator();
				while(authorizationGrantTypes.hasNext()) {
					document.getAuthorizationGrantTypes().add(authorizationGrantTypes.next() + "");
				}
			}		
		}
		if(deleted && source.getRedirectUris() != null) {
			document.getRedirectUris().removeIf(x -> source.getRedirectUris().contains(x));
		}else {
			if(source.getRedirectUris() != null) {
				if(document.getRedirectUris() == null) {
					document.setRedirectUris(new HashSet<>());
				}
				Iterator<String> redirectUris = source.getRedirectUris().iterator();
				while(redirectUris.hasNext()) {
					document.getRedirectUris().add(redirectUris.next() + "");
				}
			}
		}
		if(deleted && source.getResourceIds() != null) {
			document.getResourceIds().removeIf(x -> source.getResourceIds().contains(x));
		}else {
			if(source.getResourceIds() != null) {
				if(document.getResourceIds() == null) {
					document.setResourceIds(new HashSet<>());
				}
				Iterator<String> resourceIds = source.getResourceIds().iterator();
				while(resourceIds.hasNext()) {
					document.getResourceIds().add(resourceIds.next() + "");
				}
			}
		}
		if(deleted && source.getScope() != null) {
			document.getScope().removeIf(x -> source.getScope().contains(x));			
		}else {
			if(source.getScope() != null) {
				if(document.getScope() == null) {
					document.setScope(new HashSet<>());
				}
				Iterator<String> scope = source.getScope().iterator();
				while(scope.hasNext()) {
					document.getScope().add(scope.next() + "");
				}
			}
		}
		if(source.getExpirationDate() != null && !deleted) {
			document.setExpirationDate(source.getExpirationDate());
		}
		if(source.getTokenType() != null && !deleted) {
			document.setTokenType(source.getTokenType());
		}
		if(source.isAutoApprove() != null && !deleted) {
			document.setAutoApprove(source.isAutoApprove());
		}
		return document;
	}
}
