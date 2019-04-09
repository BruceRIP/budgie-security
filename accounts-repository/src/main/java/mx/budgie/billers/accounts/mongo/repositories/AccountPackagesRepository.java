package mx.budgie.billers.accounts.mongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import mx.budgie.billers.accounts.mongo.constants.RepositoryConstants;
import mx.budgie.billers.accounts.mongo.documents.AccountPackagesDocument;

/**
 * Created by brucewayne on 6/25/17.
 */
@Repository(RepositoryConstants.MONGO_BILLER_ACCOUNT_PKG_REPOSITORY)
public interface AccountPackagesRepository extends MongoRepository<AccountPackagesDocument, Integer>{

	@Query(value="{ 'packageId' :  ?0}", fields="{'packageId':1, 'packageName' : 1, 'totalBills' : 1, 'totalActiveSession': 1, 'expirationDate': 1}")
	public abstract AccountPackagesDocument findPackageByPackageId(final Integer packageId);
	
	@Override
	public <S extends AccountPackagesDocument> S save(S entity);
	
}
