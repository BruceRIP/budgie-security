/**
 * 
 */
package mx.budgie.billers.accounts.service;

import java.util.Date;

import mx.budgie.billers.accounts.mongo.documents.AdministratorAccount;
import mx.budgie.billers.accounts.vo.AccountAdminRequestVO;

/**
 * @author brucewayne
 *
 */
public interface AccountManagerService {

	public AccountAdminRequestVO findAccountManagerByBillerId(final String billerId);
	
//	public AdministratorAccount modifyAccountManager(String billerId, final AccountAdminRequestVO accountAdminRequest);
	
	public AdministratorAccount modifyAccountManagerFreeBills(final String billerId, final int totalFreeBillsEmitted);
	
	public AdministratorAccount modifyAccountManagerTotalBills(final String billerId, final int totalBillsEmitted);
	
	public AdministratorAccount modifyAccountManagerActiveSessions(final String billerId, final int totalActiveSessions);
	
	public AdministratorAccount modifyAccountManagerRegisteredCustomer(final String billerId, final int totalRegisteredCustomers);
	
	public AdministratorAccount modifyAccountManagerExpirationDate(final String billerId, final Date packageExpirationDate);
	
	public AdministratorAccount modifyAccountManagerPackageName(final String billerId, final String packageName);
	
	public AccountAdminRequestVO manageAccountPlanByBillerID(final String billerId);
	
	public AdministratorAccount manageAccountLogin(final String billerId, boolean login);
	
	public AdministratorAccount manageAccountLogout(final String billerId, boolean logout, final String sessionID);
	
	public void removeAccountManager(final String billerID) ;
	
}

