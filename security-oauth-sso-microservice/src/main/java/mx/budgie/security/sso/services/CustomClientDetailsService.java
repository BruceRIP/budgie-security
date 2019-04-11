/**
 * 
 */
package mx.budgie.security.sso.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import mx.budgie.billers.accounts.mongo.constants.RepositoryConstants;
import mx.budgie.billers.accounts.mongo.documents.OauthClientDetailsDocument;
import mx.budgie.billers.accounts.mongo.repositories.ClientAuthenticationRepository;
import mx.budgie.billers.accounts.mongo.repositories.OauthClientDetailsRepository;
import mx.budgie.security.sso.builder.ClientBuilder;
import mx.budgie.security.sso.constants.SecurityConstants;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 25, 2017
 */
@Service(SecurityConstants.SERVICE_CUSTOM_CLIENT_DETAIL)
public class CustomClientDetailsService implements ClientDetailsService{

	private static final Logger LOGGER = LogManager.getLogger(CustomClientDetailsService.class);
	
	@Autowired
	@Qualifier(RepositoryConstants.MONGO_BILLER_CLIENT_AUTH_REPOSITORY)
	private ClientAuthenticationRepository authenticationRepository;
	
	@Autowired
	private ClientBuilder clientBuilder;
	@Autowired
	private OauthClientDetailsRepository oauthClientDetailsRepository;
	
	@Override
	public ClientDetails loadClientByClientId(String clientId) {
		OauthClientDetailsDocument oauthClientDocument = oauthClientDetailsRepository.findOauthClientByClientId(clientId);
		if(null != oauthClientDocument){
			LOGGER.info(" *--- ClientId was found  ---* ");
			return clientBuilder.buildDocumentFromSource(oauthClientDocument);			
		}
		throw new ClientRegistrationException(" --- ClientId not found --- ");
	}

}
