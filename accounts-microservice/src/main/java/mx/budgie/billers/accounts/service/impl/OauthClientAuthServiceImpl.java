/**
 * 
 */
package mx.budgie.billers.accounts.service.impl;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import mx.budgie.billers.accounts.builder.ClientAuthenticationBuilder;
import mx.budgie.billers.accounts.constants.AccountsConstants;
import mx.budgie.billers.accounts.mongo.dao.SequenceDao;
import mx.budgie.billers.accounts.mongo.documents.OauthClientDetailsDocument;
import mx.budgie.billers.accounts.mongo.repositories.OauthClientDetailsRepository;
import mx.budgie.billers.accounts.mongo.utils.AESCrypt;
import mx.budgie.billers.accounts.mongo.utils.DigestAlgorithms;
import mx.budgie.billers.accounts.service.ClientAuthService;
import mx.budgie.billers.accounts.vo.ClientAuthenticationVO;
import mx.budgie.billers.accounts.vo.TokensResponse;

/**
 * @author bruno.rivera
 *
 */
@Service(AccountsConstants.SERVICE_OAUTH_CLIENT_DETAIL)
public class OauthClientAuthServiceImpl implements ClientAuthService{

	private static final Logger LOGGER = LogManager.getLogger(OauthClientAuthServiceImpl.class);
			
	@Autowired
	private OauthClientDetailsRepository oauthClientDetailsRepository;
	@Autowired
	private ClientAuthenticationBuilder clientAuthenticationBuilder;
	@Autowired
	private SequenceDao sequenceDao;	
	@Value(AccountsConstants.ACCOUNTS_KEY_DEFAULT)
	private String keyDefault;
	@Value(AccountsConstants.ACCOUNTS_DIGEST_DEFAULT)
	private String defaultDigest;
	
	@Override
	public TokensResponse saveClient(final String clientName) {
		final TokensResponse tokens = new TokensResponse();
		final String defaultKey = keyDefault;
		OauthClientDetailsDocument oauthClientDocument = oauthClientDetailsRepository.findOauthClientByName(clientName);
		if(oauthClientDocument != null) {
			LOGGER.info("Client was found");
			oauthClientDocument.setName(clientName);
			oauthClientDocument.setClientId(AESCrypt.buildPassword(clientName, DigestAlgorithms.getDigestAlgorithm(defaultDigest)));
			oauthClientDocument.setClientSecret(BCrypt.hashpw(clientName +  Calendar.getInstance().getTimeInMillis(), BCrypt.gensalt()));
			oauthClientDocument.setAuthenticationToken(AESCrypt.encryptKeyAndEncodeBase64(oauthClientDocument.getClientId() + ":" + oauthClientDocument.getClientSecret(), defaultKey));
		}else {
			LOGGER.info("Client NOT found");
			oauthClientDocument = new OauthClientDetailsDocument();
			oauthClientDocument.setName(clientName);
			oauthClientDocument.setClientId(AESCrypt.buildPassword(clientName, DigestAlgorithms.getDigestAlgorithm(defaultDigest)));
			oauthClientDocument.setClientSecret(BCrypt.hashpw(clientName +  Calendar.getInstance().getTimeInMillis(), BCrypt.gensalt()));
			oauthClientDocument.setAuthenticationToken(AESCrypt.encryptKeyAndEncodeBase64(oauthClientDocument.getClientId() + ":" + oauthClientDocument.getClientSecret(), defaultKey));		
			long incre = sequenceDao.getClientAuthSequenceNext();
			oauthClientDocument.setId(incre);
			Calendar cal = Calendar.getInstance();
			cal.setTime(Date.from(Instant.now()));
			cal.add(Calendar.DAY_OF_MONTH, 30);
			oauthClientDocument.setExpirationDate(cal.getTime());
			oauthClientDocument.setExpiresIn(30);
			oauthClientDocument.setTokenType("bearer");
			Set<String> resources = new HashSet<>();
			resources.add("billers");
			oauthClientDocument.setResourceIds(resources);
			Set<String> scopes = new HashSet<>();
			scopes.add("read");
			scopes.add("write");
			oauthClientDocument.setScope(scopes);
			Set<String> grant = new HashSet<>();
			grant.add("password");
			grant.add("client_credentials");
//			grant.add("refresh_token");
			oauthClientDocument.setAuthorizationGrantTypes(grant);
			Set<String> redirectUri = new HashSet<>();
			redirectUri.add("/error");
			redirectUri.add("/denied");
			oauthClientDocument.setRedirectUris(redirectUri);
			Set<String> authorities = new HashSet<>();
			authorities.add("ROLE_USER");
			authorities.add("ROLE_CLIENT");
			oauthClientDocument.setAuthorities(authorities);
			oauthClientDocument.setAccessTokenValidity(10000);
			oauthClientDocument.setRefreshTokenValidity(60000);
			oauthClientDocument.setAutoApprove(true);
			oauthClientDetailsRepository.save(oauthClientDocument);
		}
		tokens.setAccessToken(oauthClientDocument.getAuthenticationToken());
		tokens.setClientId(oauthClientDocument.getClientId());
		tokens.setClientSecret(oauthClientDocument.getClientSecret());
		tokens.setKey(defaultKey);
		LOGGER.info("Save client is successful");
		return tokens;
	}

	@Override
	public ClientAuthenticationVO findClientByClientId(final String clientId) {
		OauthClientDetailsDocument oauthClientDocument = oauthClientDetailsRepository.findOauthClientByClientId(clientId);
		if(oauthClientDocument != null) {
			ClientAuthenticationVO vo = new ClientAuthenticationVO();
			vo.setClientId(oauthClientDocument.getClientId());
			vo.setClientSecret(oauthClientDocument.getClientSecret());
			vo.setAccessTokenValidity(oauthClientDocument.getAccessTokenValidity());
			vo.setAdditionalInformation(oauthClientDocument.getAdditionalInformation());
			vo.setAuthorities(oauthClientDocument.getAuthorities());
			vo.setAuthorizationGrantTypes(oauthClientDocument.getAuthorizationGrantTypes());
			vo.setRefreshTokenValidity(oauthClientDocument.getRefreshTokenValidity());
			vo.setRedirectUris(oauthClientDocument.getRedirectUris());
			vo.setResourceIds(oauthClientDocument.getResourceIds());
			vo.setScope(oauthClientDocument.getScope());
			return vo;
		}
		return null;
	}

	@Override
	public boolean deleteClientByName(final String clientName) {
		OauthClientDetailsDocument oauthClientDocument = oauthClientDetailsRepository.findOauthClientByName(clientName);
		if(oauthClientDocument == null) {
			oauthClientDocument = oauthClientDetailsRepository.findOauthClientByClientId(clientName);
			if(oauthClientDocument != null) {
				oauthClientDetailsRepository.delete(oauthClientDocument);
				return true;
			}
		}
		return false;
	}

	@Override
	public ClientAuthenticationVO updateClient(ClientAuthenticationVO clientVO, final boolean delete) {
		OauthClientDetailsDocument oauthClientDocument = oauthClientDetailsRepository.findOauthClientByClientId(clientVO.getClientId());
		if(oauthClientDocument != null) {
			oauthClientDocument = clientAuthenticationBuilder.buildDocumentFromSource(oauthClientDocument, clientVO, delete);
			oauthClientDetailsRepository.save(oauthClientDocument);
			return clientAuthenticationBuilder.buildSourceFromDocument(oauthClientDocument);
		}
		return null;
	}

	@Override
	public boolean validateAuthenticationClient(String clientId, String password) {
		OauthClientDetailsDocument oauthClientDocument = oauthClientDetailsRepository.findOauthClientByClientId(clientId);
		return (oauthClientDocument != null && password.equalsIgnoreCase(oauthClientDocument.getAuthenticationToken()));
	}

}
