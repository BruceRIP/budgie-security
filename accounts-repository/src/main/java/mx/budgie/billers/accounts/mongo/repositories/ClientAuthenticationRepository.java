/**
 * 
 */
package mx.budgie.billers.accounts.mongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.budgie.billers.accounts.mongo.constants.RepositoryConstants;
import mx.budgie.billers.accounts.mongo.documents.ClientAuthenticationDocument;

/**
 * @author bruno-rivera
 *
 */
@Repository(RepositoryConstants.MONGO_BILLER_CLIENT_AUTH_REPOSITORY)
public interface ClientAuthenticationRepository extends MongoRepository<ClientAuthenticationDocument, Long>{

	@Override
	public <S extends ClientAuthenticationDocument> S save(S entity);
	
	public ClientAuthenticationDocument findByClientIdAndClientSecret(final String clientId, final String clientSecret);
	
	public ClientAuthenticationDocument findByClientId(final String clientId);
	
	public ClientAuthenticationDocument findByClientToken(final String clientToken);
	
	public ClientAuthenticationDocument findByName(final String name);
	
	public ClientAuthenticationDocument findByNameAndClientToken(final String name, final String clientToken);
	
	public void deleteClientByClientId(final String clientId);
		
}
