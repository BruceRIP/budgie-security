/**
 * 
 */
package mx.budgie.billers.packages.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import mx.budgie.billers.packages.mongo.documents.PackageDocument;

/**
 * @author brucewayne
 *
 */
public interface PackageRepository extends MongoRepository<PackageDocument, Integer>{

	@Query(value="{ 'idPackage' :  ?0}", fields="{'idPackage':1,'offerDescription':1, 'namePackage' : 1, 'totalFreeBills' : 1, 'totalBills': 1, 'totalActiveSessions': 1, 'totalActiveDays': 1, 'onSale': 1, 'kindOffer': 1, 'active': 1, 'price':1, 'templateBill':1, 'clientManagement':1, 'personalSupport':1, 'services':1, 'timeStorage':1, 'tutorials':1, 'scheduledDelivery':1, 'statisticsReports':1, 'addenas':1, 'totalRegisteredCustomer':1}")
	public  PackageDocument findByIdPackage(Integer id);
}
