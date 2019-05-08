/**
 * 
 */
package mx.budgie.billers.accounts.mongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.budgie.billers.accounts.mongo.documents.OauthClientDetailsDocument;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 25, 2017
 */
@Repository
public interface OauthClientDetailsRepository extends MongoRepository<OauthClientDetailsDocument, Integer>{

	public OauthClientDetailsDocument findOauthClientByName(final String name);
	
	public OauthClientDetailsDocument findOauthClientByClientId(final String clientId);
	
	public OauthClientDetailsDocument findOauthClientByClientIdAndClientSecret(final String clientId, final String clientSecret);
	
	public OauthClientDetailsDocument findOauthClientByAuthenticationToken(final String authenticationToken);
	
	public OauthClientDetailsDocument findOauthClientByTokenAuthenticationAccessToken(final String tokenAuthentication);
	
	public OauthClientDetailsDocument findOauthClientByTokenAuthenticationRefreshTokenAuth(final String refreshToken);
	
}
