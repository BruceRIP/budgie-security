/**
 * 
 */
package mx.budgie.billers.accounts.service.impl;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import mx.budgie.billers.accounts.builder.AccountBuilder;
import mx.budgie.billers.accounts.constants.AccountsConstants;
import mx.budgie.billers.accounts.mongo.constants.RepositoryConstants;
import mx.budgie.billers.accounts.mongo.dao.SequenceDao;
import mx.budgie.billers.accounts.mongo.documents.AccountAdministratorVO;
import mx.budgie.billers.accounts.mongo.documents.AccountAuthorizationDocument;
import mx.budgie.billers.accounts.mongo.documents.AccountStatus;
import mx.budgie.billers.accounts.mongo.repositories.AccountAdministratorRepository;
import mx.budgie.billers.accounts.mongo.repositories.AccountPackagesRepository;
import mx.budgie.billers.accounts.mongo.repositories.AccountsRepository;
import mx.budgie.billers.accounts.mongo.utils.AESCrypt;
import mx.budgie.billers.accounts.mongo.utils.DigestAlgorithms;
import mx.budgie.billers.accounts.request.UpdateRolesParams;
import mx.budgie.billers.accounts.service.AccountService;
import mx.budgie.billers.accounts.vo.AccountRequestVO;
import mx.budgie.billers.accounts.vo.AccountVO;
import mx.budgie.billers.accounts.vo.PackageVO;
import mx.budgie.commons.utils.CommonsUtil;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 26, 2017
 */
@Service(AccountsConstants.SERVICE_ACCOUNT)
public class AccountServiceImpl implements AccountService{

	private static final Logger LOGGER = LogManager.getLogger(AccountServiceImpl.class);	
	@Autowired
	private AccountBuilder accountBuilder;
	@Autowired
	@Qualifier(RepositoryConstants.MONGO_BILLER_ACCOUNT_REPOSITORY)
	private AccountsRepository accountRepository; 
	@Autowired
	@Qualifier(RepositoryConstants.MONGO_BILLER_ACCOUNT_PKG_REPOSITORY)
	private AccountPackagesRepository accountPackagesRepository; 
	@Autowired
	private SequenceDao sequenceDao;	
	@Value(AccountsConstants.ACCOUNT_DEFAULT_PACKAGE)
	private String defaultPackage;
	@Autowired
	private AccountAdministratorRepository accountAdminRepository;
	
	@Override
	public AccountVO createAccount(final AccountRequestVO account, final String clientAuthentication, PackageVO packageVO) {
		Assert.notNull(accountRepository, "Account object can't be null");			
		AccountAuthorizationDocument accountDocument = accountBuilder.createDocumentFromSource(account, clientAuthentication);				
		Calendar cal = Calendar.getInstance();
		AccountAdministratorVO accountAdmin = new AccountAdministratorVO();;
		if(null != packageVO) {
			LOGGER.info("Creationg account plan with [{}]", packageVO.getNamePackage());
			cal.add(Calendar.DAY_OF_YEAR, packageVO.getTotalActiveDays());
			accountDocument.setPurchasedPackage(packageVO.getNamePackage());
			accountDocument.setTotalActiveSession(packageVO.getTotalActiveSessions());
			accountDocument.setTotalBills(packageVO.getTotalBills());
			accountDocument.setTotalFreeBills(packageVO.getTotalFreeBills());			
			accountDocument.setTotalRegisteredCustomer(packageVO.getTotalRegisteredCustomer());
			accountDocument.setPackageExpirationDate(cal.getTime());
			accountDocument.setDatePurchasedPackage(Date.from(Instant.now()));
			
			accountAdmin.setPurchasedPackage(packageVO.getNamePackage());
			accountAdmin.setTotalBillsEmitted(0);
			accountAdmin.setTotalFreeBillsEmitted(0);
			accountAdmin.setTotalRegisteredCustomers(0);
			accountAdmin.setPackageExpirationDate(cal.getTime());
			accountAdmin.setDatePurchasedPackage(Date.from(Instant.now()));
			accountAdmin.setTotalActiveSessions(0);
		}else {
			LOGGER.info("Creationg account plan from hardcode");
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_YEAR, 30);
			accountDocument.setPurchasedPackage("PAQUETE BÁSICO");
			accountDocument.setTotalActiveSession(1);
			accountDocument.setTotalBills(0);
			accountDocument.setTotalFreeBills(5);
			accountDocument.setTotalRegisteredCustomer(2);
			accountDocument.setPackageExpirationDate(cal.getTime());
			accountDocument.setDatePurchasedPackage(Date.from(Instant.now()));
			
			accountAdmin.setPurchasedPackage("PAQUETE BÁSICO");
			accountAdmin.setTotalActiveSessions(0);
			accountAdmin.setTotalBillsEmitted(0);
			accountAdmin.setTotalFreeBillsEmitted(0);
			accountAdmin.setTotalRegisteredCustomers(0);
			accountAdmin.setPackageExpirationDate(cal.getTime());
			accountAdmin.setDatePurchasedPackage(Date.from(Instant.now()));
		}
		Long incre = sequenceDao.getAccountSequenceNext();
		String billerID = CommonsUtil.generateID();
		accountDocument.setId(incre);
		accountDocument.setBillerID(billerID);
		accountDocument.setPassword(AESCrypt.buildPassword(billerID, DigestAlgorithms.SHA_256));
		accountAdmin.setId(incre);
		accountAdmin.setBillerID(billerID);		
		accountDocument.setActivationCode(AESCrypt.buildHashValue(accountAdmin.toString()));
		LOGGER.info("Creating account for [: '" + billerID + "'] and Nickname ['" + account.getNickname() + "']");
		AccountAuthorizationDocument saveDocument = accountRepository.save(accountDocument);
		accountAdminRepository.save(accountAdmin);
		if(null != saveDocument){
			return accountBuilder.buildSourceFromDocument(saveDocument);
		}
		return null;
	}

	@Override
	public AccountVO findAccountByBillerID(final String billerID) {
		Assert.notNull(accountRepository, "Account object can't be null");
		LOGGER.info("Looking for user account by billerID: [{}]", billerID);
		AccountAuthorizationDocument accountDocument = accountRepository.findByBillerID(billerID);
		if(null != accountDocument){
			LOGGER.info("Building account from document");
			return accountBuilder.buildSourceFromDocument(accountDocument);
		}
		LOGGER.warn("Account not found");
		return null;
	}
	
	@Override
	public AccountVO findAccountByEmail(final String email){
		Assert.notNull(accountRepository, "Account object can't be null");
		LOGGER.info("Looking for user account by email: [{}]", email);
		AccountAuthorizationDocument accountDocument = accountRepository.findByEmail(email);		
		if(null != accountDocument){
			LOGGER.info("Building account from document");
			return accountBuilder.buildSourceFromDocument(accountDocument);
		}
		LOGGER.warn("Account not found");
		return null;
	}
	
	@Override
	public AccountVO findAccountByNickname(final String nickname){
		Assert.notNull(accountRepository, "Account object can't be null");
		LOGGER.info("Looking for user account by nickname: '" + nickname + "'");
		AccountAuthorizationDocument accountDocument = accountRepository.findByNickname(nickname);
		if(null != accountDocument){
			LOGGER.info("Building account from document");
			return accountBuilder.buildSourceFromDocument(accountDocument);
		}
		LOGGER.warn("Account not found");
		return null;
	}

	@Override
	public AccountVO updateAccount(final AccountVO account) {
		AccountAuthorizationDocument document = accountRepository.findByBillerID(account.getBillerID());		
		if(null != document){
			if(!account.getPassword().equals(document.getPassword())) {
				document.setPassword(account.getPassword());
			}
			if(!account.getAccountStatus().equals(document.getAccountStatus())) {
				document.setAccountStatus(account.getAccountStatus());				
			}
			if(document.getActivationCode() != null && !document.getActivationCode().equals(account.getActivationCode())) {
				document.setActivationCode(account.getActivationCode());
			}
			AccountAuthorizationDocument doc = accountRepository.save(document);
			return accountBuilder.buildSourceFromDocument(doc);
		}
		return null;
	}

	@Override
	public void deleteAccount(final String email) {
		LOGGER.info("Removing account by email [{}]", email);
		accountRepository.deleteByEmail(email);
	}

	@Override
	public AccountVO findAccountToActivate(final String accountReference) {
		AccountAuthorizationDocument documet = accountRepository.findByActivationCode(accountReference);
		if(documet != null) {
			return accountBuilder.buildSourceFromDocument(documet);
		}
		return null;
	}

	@Override
	public AccountVO updateRoles(UpdateRolesParams params, boolean deleted) {
		AccountAuthorizationDocument document = accountRepository.findByBillerID(params.getBillerID());		
		if(null != document){
			Iterator<String> roles = params.getRoles().iterator();
			if(deleted) {				
				document.getRoles().removeIf(x -> params.getRoles().contains(x));
			}else {
				if(document.getRoles() == null) {
					document.setRoles(new HashSet<>());
				}
				while(roles.hasNext()) {
					document.getRoles().add(roles.next());
				}
			}
			AccountAuthorizationDocument doc = accountRepository.save(document);
			return accountBuilder.buildSourceFromDocument(doc);
		}
		return null;
	}

	@Override
	public AccountVO resendActivationCode(AccountVO account) {
		AccountAuthorizationDocument document = accountRepository.findByBillerID(account.getBillerID());
		if(document != null) {
			document.setActivationCode(AESCrypt.buildHashValue(account.toString()));
			document.setAccountStatus(AccountStatus.TO_CONFIRM);
			AccountAuthorizationDocument doc = accountRepository.save(document);
			return accountBuilder.buildSourceFromDocument(doc);
		}
		return null;
	}
		
}
