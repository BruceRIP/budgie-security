/**
 * 
 */
package mx.budgie.billers.accounts.service.impl;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import mx.budgie.billers.accounts.constants.AccountsConstants;
import mx.budgie.billers.accounts.mongo.documents.AccountAdministratorVO;
import mx.budgie.billers.accounts.mongo.documents.AdministratorAccount;
import mx.budgie.billers.accounts.mongo.documents.Sessions;
import mx.budgie.billers.accounts.service.AccountManagerService;
import mx.budgie.billers.accounts.service.AccountService;
import mx.budgie.billers.accounts.vo.AccountAdminRequestVO;
import mx.budgie.billers.accounts.vo.AccountVO;

/**
 * @author brucewayne
 *
 */
@Service
public class AccountManagerServiceImpl implements AccountManagerService{

	private static final Logger LOGGER = LogManager.getLogger(AccountManagerServiceImpl.class);	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	@Qualifier(AccountsConstants.SERVICE_ACCOUNT)
	private AccountService accountService;
	@Value("${billers.format.date}")
	private String formatDate;
	
	@Override
	public AccountAdminRequestVO findAccountManagerByBillerId(String billerId) {
		LOGGER.info("Getting administrator by billerID {}", billerId);
		return manageAccountPlanByBillerID(billerId);
	}
	
	@Override
	public AdministratorAccount modifyAccountManagerFreeBills(String billerId, int totalFreeBillsEmitted) {		
		LOGGER.info("updating totalFreeBillsEmitted for account by billerID {}", billerId);
		Query query = new Query(Criteria.where("_id").is(billerId));
		AccountAdministratorVO accountAdmin = mongoTemplate.findOne(query, AccountAdministratorVO.class);						
		LOGGER.info("totalFreeBillsEmitted will be updated {}", totalFreeBillsEmitted);
		int freeBillsEmitted = 0;
		if(totalFreeBillsEmitted >= 0) {
			freeBillsEmitted = accountAdmin.getTotalFreeBillsEmitted() + totalFreeBillsEmitted;
		}else {
			int convert = (totalFreeBillsEmitted) * (-1);
			freeBillsEmitted = accountAdmin.getTotalFreeBillsEmitted() - convert;
		}
		accountAdmin.setTotalFreeBillsEmitted(freeBillsEmitted);
		LOGGER.info("User has totalFreeBillsEmitted {}", freeBillsEmitted);
		updateAccountAdministrator(billerId, accountAdmin);
		return manageAccountPlanByBillerID(billerId);
	}

	@Override
	public AdministratorAccount modifyAccountManagerTotalBills(String billerId, int totalBillsEmitted) {		
		LOGGER.info("updating totalBillsEmitted for account by billerID {}", billerId);
		Query query = new Query(Criteria.where("_id").is(billerId));
		AccountAdministratorVO accountAdmin = mongoTemplate.findOne(query, AccountAdministratorVO.class);				
		LOGGER.info("totalBillsEmitted will be updated {}", totalBillsEmitted);
		int billsEmitted = 0;
		if(totalBillsEmitted >= 0) {
			billsEmitted = accountAdmin.getTotalBillsEmitted() + totalBillsEmitted;
		}else {
			int convert = (totalBillsEmitted) * (-1);
			billsEmitted = accountAdmin.getTotalBillsEmitted() - convert;
		}				
		accountAdmin.setTotalBillsEmitted(billsEmitted);
		LOGGER.info("User has totalBillsEmitted {}", billsEmitted);
		updateAccountAdministrator(billerId, accountAdmin);
		return manageAccountPlanByBillerID(billerId);
	}

	@Override
	public AdministratorAccount modifyAccountManagerActiveSessions(String billerId, int totalActiveSessions) {
		LOGGER.info("updating totalActiveSessions for account by billerID {}", billerId);
		Query query = new Query(Criteria.where("_id").is(billerId));
		AccountAdministratorVO accountAdmin = mongoTemplate.findOne(query, AccountAdministratorVO.class);				
		LOGGER.info("totalActiveSessions will be updated {}", totalActiveSessions);
		int activeSessions = accountAdmin.getTotalActiveSessions() + totalActiveSessions;
		accountAdmin.setTotalActiveSessions(activeSessions);
		LOGGER.info("User has totalActiveSessions {}", activeSessions);
		updateAccountAdministrator(billerId, accountAdmin);
		return manageAccountPlanByBillerID(billerId);
	}

	@Override
	public AdministratorAccount modifyAccountManagerRegisteredCustomer(String billerId, int totalRegisteredCustomers) {
		LOGGER.info("updating totalRegisteredCustomers for account by billerID {}", billerId);
		Query query = new Query(Criteria.where("_id").is(billerId));
		AccountAdministratorVO accountAdmin = mongoTemplate.findOne(query, AccountAdministratorVO.class);				
		LOGGER.info("totalRegisteredCustomers will be updated {}", totalRegisteredCustomers);
		int registered = accountAdmin.getTotalRegisteredCustomers() + totalRegisteredCustomers;
		accountAdmin.setTotalRegisteredCustomers(registered);
		LOGGER.info("User has totalRegisteredCustomers {}", registered);
		updateAccountAdministrator(billerId, accountAdmin);
		return manageAccountPlanByBillerID(billerId);
	}

	@Override
	public AdministratorAccount modifyAccountManagerExpirationDate(String billerId, Date packageExpirationDate) {
		LOGGER.info("updating packageExpirationDate for account by billerID {}", billerId);
		Query query = new Query(Criteria.where("_id").is(billerId));
		AccountAdministratorVO accountAdmin = mongoTemplate.findOne(query, AccountAdministratorVO.class);				
		LOGGER.info("packageExpirationDate will be updated {}", packageExpirationDate);
		accountAdmin.setPackageExpirationDate(packageExpirationDate);
		updateAccountAdministrator(billerId, accountAdmin);
		return manageAccountPlanByBillerID(billerId);
	}
	

	@Override
	public AdministratorAccount modifyAccountManagerPackageName(String billerId, String packageName) {
		LOGGER.info("updating PackageName for account by billerID {}", billerId);
		Query query = new Query(Criteria.where("_id").is(billerId));
		AccountAdministratorVO accountAdmin = mongoTemplate.findOne(query, AccountAdministratorVO.class);	
		accountAdmin.setPurchasedPackage(packageName);		
		updateAccountAdministrator(billerId, accountAdmin);
		return manageAccountPlanByBillerID(billerId);
	}
	
	@Override
	public AdministratorAccount manageAccountLogin(String billerId, boolean login) {
		LOGGER.info("updating Login for account by billerID {}", billerId);
		AccountAdminRequestVO adminRequestVO = manageAccountPlanByBillerID(billerId);
		Query query = new Query(Criteria.where("_id").is(billerId));
		AccountAdministratorVO accountAdmin = mongoTemplate.findOne(query, AccountAdministratorVO.class);	
		String sessionID = null;
		int activeSessions = 0;		
		LOGGER.info("login date will be updated");
		sessionID = UUID.randomUUID().toString();
		if(accountAdmin.getSessions().isEmpty()) {
			accountAdmin.getSessions().add(new Sessions(sessionID, Boolean.TRUE, Date.from(Instant.now())));
			activeSessions++;
		}else {					
			for(int i=0; i<accountAdmin.getSessions().size(); i++) {
				activeSessions++;
				if(accountAdmin.getSessions().get(i).getIdSession() != null && accountAdmin.getSessions().get(i).getIdSession().isEmpty()) {
					accountAdmin.getSessions().get(i).setLoginSessionDate(Date.from(Instant.now()));			
					accountAdmin.getSessions().get(i).setActiveSessions(Boolean.TRUE);
					accountAdmin.getSessions().get(i).setIdSession(sessionID);
					accountAdmin.getSessions().get(i).setLogoutSessionDate(null);
					break;
				}if(accountAdmin.getSessions().get(i).getIdSession() != null && !accountAdmin.getSessions().get(i).getIdSession().isEmpty()) {
					if(accountAdmin.getSessions().size() < adminRequestVO.getTotalActiveSessions()) {
						accountAdmin.getSessions().add(new Sessions(sessionID, Boolean.TRUE, Date.from(Instant.now())));
						break;
					}
				}
			}				
		}
		accountAdmin.setTotalActiveSessions(activeSessions);
		updateAccountAdministrator(billerId, accountAdmin);
		adminRequestVO.setSessionID(sessionID);
		return adminRequestVO;
	}

	@Override
	public AdministratorAccount manageAccountLogout(String billerId, boolean logout, final String sessionID) {
		LOGGER.info("updating Logout for account by billerID {}", billerId);		
		Query query = new Query(Criteria.where("_id").is(billerId));
		AccountAdministratorVO accountAdmin = mongoTemplate.findOne(query, AccountAdministratorVO.class);	
		int activeSessions = 0;		
		LOGGER.info("logout date will be updated");
		activeSessions = accountAdmin.getSessions().size();
		for(int i=activeSessions-1; i>=0; i--) {
			if(accountAdmin.getSessions().get(i).getIdSession().isEmpty()) {
				activeSessions--;
			}else if(accountAdmin.getSessions().get(i).getIdSession().equals(sessionID)) {
				accountAdmin.getSessions().get(i).setIdSession("");
				accountAdmin.getSessions().get(i).setActiveSessions(Boolean.FALSE);
				accountAdmin.getSessions().get(i).setLogoutSessionDate(Date.from(Instant.now()));
				activeSessions--;					
			} 
		}
		accountAdmin.setTotalActiveSessions(activeSessions);		
		return updateAccountAdministrator(billerId, accountAdmin);
	}

	@Override
	public AccountAdminRequestVO manageAccountPlanByBillerID(final String billerId) {
		Query query = new Query(Criteria.where("_id").is(billerId));
		AccountAdministratorVO accountAdministrator = mongoTemplate.findOne(query, AccountAdministratorVO.class);
		AccountVO accountVO = accountService.findAccountByBillerID(billerId);		
		AccountAdminRequestVO accountAdminRequestVO = new AccountAdminRequestVO();
		accountAdminRequestVO.setTotalActiveSessions(accountVO.getTotalActiveSession());
		if(new Date().after(accountAdministrator.getPackageExpirationDate())) {
			accountAdminRequestVO.setAllowUseFunctionalities(false);
		}		
		if(accountAdministrator.getTotalActiveSessions() != null && accountAdministrator.getTotalActiveSessions() >= accountVO.getTotalActiveSession()) {
			accountAdminRequestVO.setAllowActiveSession(false);
		}
		if(accountAdministrator.getTotalFreeBillsEmitted() != null && accountAdministrator.getTotalFreeBillsEmitted() >= accountVO.getTotalFreeBills()) {
			accountAdminRequestVO.setAllowFreeBill(false);
		}
		if(accountAdministrator.getTotalBillsEmitted() != null && accountAdministrator.getTotalBillsEmitted() >= accountVO.getTotalBills()) {
			accountAdminRequestVO.setAllowEmitBills(false);
		}
		if(accountAdministrator.getTotalRegisteredCustomers() != null && accountAdministrator.getTotalRegisteredCustomers() >= accountVO.getTotalRegisteredCustomer()) {
			accountAdminRequestVO.setAllowRegisterCustomer(false);
		}		
		return accountAdminRequestVO;
	}	
	
	private AdministratorAccount updateAccountAdministrator(final String billerID, final AccountAdministratorVO accountAdministratorVO) {		
		mongoTemplate.save(accountAdministratorVO);
		LOGGER.info("Update process was successfully");
		Query query = new Query(Criteria.where("_id").is(billerID));
		return mongoTemplate.findOne(query, AccountAdministratorVO.class);
	}

	@Override
	public void removeAccountManager(String billerID) {
		LOGGER.info("Removing account manage by billerID {}", billerID);
		Query query = new Query(Criteria.where("_id").is(billerID));
		mongoTemplate.remove(query, AccountAdministratorVO.class);
	}
}
