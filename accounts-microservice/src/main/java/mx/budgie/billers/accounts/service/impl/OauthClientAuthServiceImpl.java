/**
 * 
 */
package mx.budgie.billers.accounts.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
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
	public TokensResponse saveClient(final String billerID, final String applicationName, final String tokenType) {
		final TokensResponse tokens = new TokensResponse();
		final String defaultKey = keyDefault;
		OauthClientDetailsDocument oauthClientDocument = oauthClientDetailsRepository.findOauthClientByBillerIDAndName(billerID, applicationName);
		if(oauthClientDocument != null) {
			LOGGER.info("Client was found");
			oauthClientDocument.setName(applicationName);
			oauthClientDocument.setClientId(AESCrypt.buildPassword(applicationName, DigestAlgorithms.getDigestAlgorithm(defaultDigest)));
			oauthClientDocument.setClientSecret(BCrypt.hashpw(applicationName +  Calendar.getInstance().getTimeInMillis(), BCrypt.gensalt()));
			oauthClientDocument.setAuthenticationToken(AESCrypt.encryptKeyAndEncodeBase64(oauthClientDocument.getClientId() + ":" + oauthClientDocument.getClientSecret(), defaultKey));
		}else {
			LOGGER.info("Client NOT found");
			oauthClientDocument = new OauthClientDetailsDocument();
			oauthClientDocument.setBillerID(billerID);
			oauthClientDocument.setName(applicationName);
			oauthClientDocument.setClientId(AESCrypt.buildPassword(applicationName, DigestAlgorithms.getDigestAlgorithm(defaultDigest)));
			oauthClientDocument.setClientSecret(BCrypt.hashpw(applicationName +  Calendar.getInstance().getTimeInMillis(), BCrypt.gensalt()));
			oauthClientDocument.setAuthenticationToken(AESCrypt.encryptKeyAndEncodeBase64(oauthClientDocument.getClientId() + ":" + oauthClientDocument.getClientSecret(), defaultKey));		
			long incre = sequenceDao.getClientAuthSequenceNext();
			oauthClientDocument.setId(incre);
			Calendar cal = Calendar.getInstance();
			cal.setTime(Date.from(Instant.now()));
			cal.add(Calendar.DAY_OF_MONTH, 30);
			oauthClientDocument.setExpirationDate(cal.getTime());
			oauthClientDocument.setExpiresIn(30);
			oauthClientDocument.setTokenType("bearer");
			Set<String> resourcesId = new HashSet<>();
			resourcesId.add("auth-security");
			resourcesId.add(applicationName);
			oauthClientDocument.setResourceIds(resourcesId);
			Set<String> scope = new HashSet<>();
			scope.add("read");
			scope.add("write");
			oauthClientDocument.setScope(scope);
			Set<String> grant = new HashSet<>();
			grant.add("client_credentials");			
			if(tokenType != null) {
				grant.add(tokenType);
			}
			oauthClientDocument.setAuthorizationGrantTypes(grant);
			oauthClientDocument.setRedirectUris(new HashSet<>());
			Set<String> authorities = new HashSet<>();
			authorities.add("ROLE_CLIENT_APP");
			oauthClientDocument.setAuthorities(authorities);
			oauthClientDocument.setAccessTokenValidity(86400);
			oauthClientDocument.setRefreshTokenValidity(43200);
			oauthClientDocument.setAutoApprove(false);
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
			vo.setAccessToken(oauthClientDocument.getAuthenticationToken());
			vo.setAccessTokenValidity(oauthClientDocument.getAccessTokenValidity());
			vo.setAdditionalInformation(oauthClientDocument.getAdditionalInformation());
			vo.setAuthorities(oauthClientDocument.getAuthorities());
			vo.setAuthorizationGrantTypes(oauthClientDocument.getAuthorizationGrantTypes());
			vo.setRefreshTokenValidity(oauthClientDocument.getRefreshTokenValidity());
			vo.setRedirectUris(oauthClientDocument.getRedirectUris());
			vo.setResourceIds(oauthClientDocument.getResourceIds());
			vo.setScope(oauthClientDocument.getScope());
			if(oauthClientDocument.getTokenAuthentication() != null){
				vo.setAdditionalInformation(new LinkedHashMap<>());
				vo.getAdditionalInformation().put("access_token", oauthClientDocument.getTokenAuthentication().getAccessToken());
				vo.getAdditionalInformation().put("refresh_token", oauthClientDocument.getTokenAuthentication().getRefreshTokenAuth());
				vo.getAdditionalInformation().put("token_type", oauthClientDocument.getTokenAuthentication().getTokenTypeAuth());
				vo.getAdditionalInformation().put("expiresIn", oauthClientDocument.getTokenAuthentication().getExpiresIn());
				vo.getAdditionalInformation().put("scopes", oauthClientDocument.getTokenAuthentication().getScopes());
			}
			return vo;
		}
		return null;
	}

	@Override
	public boolean deleteClientByName(final String billerID, final String clientName) {
		OauthClientDetailsDocument oauthClientDocument = oauthClientDetailsRepository.findOauthClientByBillerIDAndName(billerID, clientName);
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
	public ClientAuthenticationVO updateClient(final String billerID, final ClientAuthenticationVO clientVO, final boolean delete) {
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

	@Override
	public String findClientIdByToken(String token) {
		OauthClientDetailsDocument oauthClientDocument = oauthClientDetailsRepository.findOauthClientByAuthenticationToken(token);
		if(oauthClientDocument != null) {
			return oauthClientDocument.getClientId();
		}
		return null;
	}

	@Override
	public List<ClientAuthenticationVO> findClientByBillerID(String billerID) {
		List<OauthClientDetailsDocument> oauthClientDocumentList = oauthClientDetailsRepository.findOauthClientByBillerID(billerID);
		List<ClientAuthenticationVO> clientsList = new ArrayList<>();
		if(oauthClientDocumentList != null && !oauthClientDocumentList.isEmpty()) {
			for(OauthClientDetailsDocument oauthClientDocument: oauthClientDocumentList) {
				ClientAuthenticationVO vo = new ClientAuthenticationVO();
				vo.setApplicationName(oauthClientDocument.getName());
				vo.setClientId(oauthClientDocument.getClientId());
				vo.setClientSecret(oauthClientDocument.getClientSecret());
				vo.setAccessToken(oauthClientDocument.getAuthenticationToken());
				vo.setAdditionalInformation(oauthClientDocument.getAdditionalInformation());
				vo.setAccessTokenValidity(oauthClientDocument.getAccessTokenValidity());
				vo.setAuthorities(oauthClientDocument.getAuthorities());
				vo.setAuthorizationGrantTypes(oauthClientDocument.getAuthorizationGrantTypes());
				vo.setRefreshTokenValidity(oauthClientDocument.getRefreshTokenValidity());
				vo.setRedirectUris(oauthClientDocument.getRedirectUris());
				vo.setResourceIds(oauthClientDocument.getResourceIds());
				vo.setScope(oauthClientDocument.getScope());
				if(oauthClientDocument.getTokenAuthentication() != null){
					vo.setAdditionalInformation(new LinkedHashMap<>());
					vo.getAdditionalInformation().put("access_token", oauthClientDocument.getTokenAuthentication().getAccessToken());
					vo.getAdditionalInformation().put("refresh_token", oauthClientDocument.getTokenAuthentication().getRefreshTokenAuth());
					vo.getAdditionalInformation().put("token_type", oauthClientDocument.getTokenAuthentication().getTokenTypeAuth());
					vo.getAdditionalInformation().put("expiresIn", oauthClientDocument.getTokenAuthentication().getExpiresIn());
					vo.getAdditionalInformation().put("scopes", oauthClientDocument.getTokenAuthentication().getScopes());
				}		
				clientsList.add(vo);
			}
		}
		return clientsList;
	}

}
