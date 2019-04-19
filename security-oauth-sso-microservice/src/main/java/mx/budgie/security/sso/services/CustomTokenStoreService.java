/**
 * 
 */
package mx.budgie.security.sso.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import mx.budgie.billers.accounts.mongo.constants.RepositoryConstants;
import mx.budgie.billers.accounts.mongo.documents.OauthClientDetailsDocument;
import mx.budgie.billers.accounts.mongo.documents.TokenAuthentication;
import mx.budgie.billers.accounts.mongo.repositories.AccountsRepository;
import mx.budgie.billers.accounts.mongo.repositories.OauthClientDetailsRepository;
import mx.budgie.security.sso.constants.SecurityConstants;
import mx.budgie.security.sso.vo.RefreshTokenVO;
import mx.budgie.security.sso.vo.TokenVO;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 25, 2017
 */
@Service(SecurityConstants.SERVICE_CUSTOM_TOKEN_STORE)
public class CustomTokenStoreService implements TokenStore{
	
	private static final Logger LOGGER = LogManager.getLogger(CustomTokenStoreService.class);	
	
	@Value(SecurityConstants.BILLERS_FORMAT_DATE)
	private String formatDate;
	
	@Autowired
	@Qualifier(RepositoryConstants.MONGO_BILLER_ACCOUNT_REPOSITORY)
	private AccountsRepository accountRepository;
	
	@Autowired	
	@Qualifier(SecurityConstants.SERVICE_CUSTOM_USER_DETAIL)
	private UserDetailsService userDetailsService;
	
	@Autowired
	private OauthClientDetailsRepository oauthClientDetailsRepository;
	
	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		LOGGER.info(" *--- Creating accessToken for ClientID [{}] ---* ", authentication.getOAuth2Request().getClientId());
		OauthClientDetailsDocument oauthClientDocument = oauthClientDetailsRepository.findOauthClientByClientId(authentication.getOAuth2Request().getClientId());
		TokenAuthentication tokenAuthentication = new TokenAuthentication();
		tokenAuthentication.setAdditionalInformation(token.getAdditionalInformation() != null ? token.getAdditionalInformation() : new LinkedHashMap<>());
		tokenAuthentication.setExpirationDateAuth(token.getExpiration());
		tokenAuthentication.setExpiresIn(token.getExpiresIn());
		tokenAuthentication.setScopes(token.getScope());
		tokenAuthentication.setAccessToken(token.getValue());
		tokenAuthentication.setTokenTypeAuth(token.getTokenType());
		tokenAuthentication.setRefreshTokenAuth(token.getRefreshToken() != null ? token.getRefreshToken().getValue() : null);
		oauthClientDocument.setTokenAuthentication(tokenAuthentication);
		oauthClientDetailsRepository.save(oauthClientDocument);
	}
	
	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		LOGGER.info(" *--- Reading accessToken with {} ---* ", tokenValue);
		OauthClientDetailsDocument oauthClientDocument = oauthClientDetailsRepository.findOauthClientByTokenAuthenticationAccessToken(tokenValue);
		if(null != oauthClientDocument && oauthClientDocument.getTokenAuthentication() != null) {
			TokenVO tokenVO = new TokenVO();
			tokenVO.setAdditionalInformation(oauthClientDocument.getTokenAuthentication().getAdditionalInformation());
			tokenVO.setExpirationDate(oauthClientDocument.getTokenAuthentication().getExpirationDateAuth());
			tokenVO.setExpiresIn(oauthClientDocument.getTokenAuthentication().getExpiresIn());
			tokenVO.setScope(oauthClientDocument.getTokenAuthentication().getScopes());
			tokenVO.setTokenType(oauthClientDocument.getTokenAuthentication().getTokenTypeAuth());
			tokenVO.setTokenValue(oauthClientDocument.getTokenAuthentication().getAccessToken());
			if(tokenVO.getExpiration().before(new Date())) {
				tokenVO.setExpired(true);
			}
			if(oauthClientDocument.getTokenAuthentication() != null && oauthClientDocument.getTokenAuthentication().getRefreshTokenAuth() != null) {
				tokenVO.setRefreshTokenVO(new RefreshTokenVO(oauthClientDocument.getTokenAuthentication().getRefreshTokenAuth()));
			}			
			return tokenVO;
		}			
		return null;
	}
	
	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {		
		LOGGER.info(" *--- Reading authentication OAuth2AccessToken with {} ---* ", token);
		OauthClientDetailsDocument oauthClientDocument = oauthClientDetailsRepository.findOauthClientByTokenAuthenticationAccessToken(token.getValue());
		if(oauthClientDocument != null && token.getTokenType().equalsIgnoreCase(oauthClientDocument.getTokenType())) {
			Authentication authentication = new UsernamePasswordAuthenticationToken(oauthClientDocument, oauthClientDocument.getAuthenticationToken(), getAuthorities(oauthClientDocument.getAuthorities()));
			OAuth2Request oauth2Request = new OAuth2Request(
					new HashMap<String, String>()					// requestParameters
                    , oauthClientDocument.getClientId()					// clientId
                    , getAuthorities(oauthClientDocument.getAuthorities())					// authorities
                    , oauthClientDocument.isAutoApprove()									// approved
                    , oauthClientDocument.getScope() // scope
                    , oauthClientDocument.getResourceIds() // resourcesIds
                    , (oauthClientDocument.getRedirectUris() != null && !oauthClientDocument.getRedirectUris().isEmpty()) ? oauthClientDocument.getRedirectUris().iterator().next() : "/" 
                    , new HashSet<>()								// responseTypes
                    , new HashMap<>());							// extensionProperties
			return new OAuth2Authentication(oauth2Request, authentication);
		}
		return null;
	}
	
	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {		
		LOGGER.info(" *--- Getting accessToken OAuth2AccessToken by ClientID [{}] ---* ", authentication.getOAuth2Request().getClientId());
		OauthClientDetailsDocument oauthClientDocument = oauthClientDetailsRepository.findOauthClientByClientId(authentication.getOAuth2Request().getClientId());
		if(null != oauthClientDocument && oauthClientDocument.getTokenAuthentication() != null) {
			TokenVO tokenVO = new TokenVO();
			tokenVO.setAdditionalInformation(oauthClientDocument.getTokenAuthentication().getAdditionalInformation());
			tokenVO.setExpirationDate(oauthClientDocument.getTokenAuthentication().getExpirationDateAuth());
			tokenVO.setExpiresIn(oauthClientDocument.getTokenAuthentication().getExpiresIn());
			tokenVO.setScope(oauthClientDocument.getTokenAuthentication().getScopes());
			tokenVO.setTokenType(oauthClientDocument.getTokenAuthentication().getTokenTypeAuth());
			tokenVO.setTokenValue(oauthClientDocument.getTokenAuthentication().getAccessToken());
			if(tokenVO.getExpiration().before(new Date())) {
				tokenVO.setExpired(true);
			}
			if(oauthClientDocument.getTokenAuthentication() != null && oauthClientDocument.getTokenAuthentication().getRefreshTokenAuth() != null) {
				tokenVO.setRefreshTokenVO(new RefreshTokenVO(oauthClientDocument.getTokenAuthentication().getRefreshTokenAuth()));
			}			
			return tokenVO;
		}			
		return null;
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		LOGGER.info(" *--- Removing OAuth2AccessToken by {}", token.getValue());
		OauthClientDetailsDocument oauthClientDocument = oauthClientDetailsRepository.findOauthClientByTokenAuthenticationAccessToken(token.getValue());
		oauthClientDocument.setTokenAuthentication(null);
		oauthClientDetailsRepository.save(oauthClientDocument);
	}
	
	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		LOGGER.info(" *--- Removing Refresh OAuth2AccessToken by {}", token.getValue());
		OauthClientDetailsDocument oauthClientDocument = oauthClientDetailsRepository.findOauthClientByTokenAuthenticationRefreshTokenAuth(token.getValue());
		if(oauthClientDocument != null && oauthClientDocument.getTokenAuthentication() != null) {
			oauthClientDocument.getTokenAuthentication().setRefreshTokenAuth(null);
			oauthClientDetailsRepository.save(oauthClientDocument);			
		}
	}
	
	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		LOGGER.info(" *--- Creating storeRefreshToken by OAuth2RefreshToken [{}] ---*", refreshToken.getValue());
		OauthClientDetailsDocument oauthClientDocument = oauthClientDetailsRepository.findOauthClientByClientId(authentication.getOAuth2Request().getClientId());
		if(null != oauthClientDocument && oauthClientDocument.getTokenAuthentication() != null) {
			oauthClientDocument.getTokenAuthentication().setRefreshTokenAuth(refreshToken.getValue());
			oauthClientDetailsRepository.save(oauthClientDocument);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public OAuth2Authentication readAuthentication(String token) {
		LOGGER.info("\n\n\t\t  ***** Entro a readAuthentication ***** ");		
		return null;
	}	

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {		
		LOGGER.info("\n\n\t\t  ***** Entro a readRefreshToken ***** ");		
		return null;
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {		
		LOGGER.info("\n\n\t\t  ***** Entro a readAuthenticationForRefreshToken ***** ");
		return null;
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		LOGGER.info("\n\n\t\t  ***** Entro a removeAccessTokenUsingRefreshToken ***** ");
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		LOGGER.info("\n\n\t\t  ***** Entro a findTokensByClientIdAndUserName ***** ");
		return null;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		LOGGER.info("\n\n\t\t  ***** Entro a findTokensByClientId ***** ");
		return null;
	}	
	
	@SuppressWarnings("serial")
	private Collection<GrantedAuthority> getAuthorities(Set<String> roles) {
		List<GrantedAuthority> grantedAuthority = new ArrayList<>();
		for(String s: roles) {
			grantedAuthority.add(new GrantedAuthority() {				
				@Override
				public String getAuthority() {					
					return s;
				}
			});
		}
		return grantedAuthority;
	}
}
