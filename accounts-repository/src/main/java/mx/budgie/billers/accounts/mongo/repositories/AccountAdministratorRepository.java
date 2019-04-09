/**
 * 
 */
package mx.budgie.billers.accounts.mongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import mx.budgie.billers.accounts.mongo.documents.AccountAdministratorVO;

/**
 * @author brucewayne
 *
 */
@Repository
public interface AccountAdministratorRepository extends MongoRepository<AccountAdministratorVO, String>{

	@Query(value = "{'billerID': ?0}", fields="{'datePurchasedPackage':1, 'purchasedPackage' : 1,'sessions':1, 'totalFreeBillsEmitted' : 1,'totalBillsEmitted' : 1,'totalActiveSessions' : 1,'totalRegisteredCustomers' : 1,'packageExpirationDate' : 1}")
	public AccountAdministratorVO findAdministratorVOByBillerID(final String billerID);
		
}
