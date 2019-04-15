package mx.budgie.billers.accounts.mongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.budgie.billers.accounts.mongo.constants.RepositoryConstants;
import mx.budgie.billers.accounts.mongo.documents.AccountAuthorizationDocument;

/**
 * Created by brucewayne on 6/25/17.
 */
@Repository(RepositoryConstants.MONGO_BILLER_ACCOUNT_REPOSITORY)
public interface AccountsRepository extends MongoRepository<AccountAuthorizationDocument, Long>{

	public abstract AccountAuthorizationDocument findByBillerID(final String billerID);
	
	public abstract AccountAuthorizationDocument findByNickname(final String nickname);
	
	public abstract AccountAuthorizationDocument findByEmail(final String email);
	
	@Override
	public <S extends AccountAuthorizationDocument> S save(S entity);
	
	public abstract void deleteByEmail(final String email);
	
	public abstract AccountAuthorizationDocument findByActivationCode(final String accountHash);
}
