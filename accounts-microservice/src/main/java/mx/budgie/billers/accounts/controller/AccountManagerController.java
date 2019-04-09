/**
 * 
 */
package mx.budgie.billers.accounts.controller;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import mx.budgie.billers.accounts.constants.AccountPaths;
import mx.budgie.billers.accounts.constants.AccountsConstants;
import mx.budgie.billers.accounts.loggers.LoggerTransaction;
import mx.budgie.billers.accounts.mongo.documents.AccountAdministratorVO;
import mx.budgie.billers.accounts.mongo.documents.AdministratorAccount;
import mx.budgie.billers.accounts.response.ResponseMessage;
import mx.budgie.billers.accounts.service.AccountManagerService;
import mx.budgie.billers.accounts.vo.AccountAdminRequestVO;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author brucewayne
 *
 */
@RestController
@RequestMapping(value = AccountPaths.ACCOUNT_BASED_PATH)
@RefreshScope
@Api(value = AccountPaths.ACCOUNT_BASED_PATH)
public class AccountManagerController {
	
	private static final Logger LOGGER = LogManager.getLogger(AccountManagerController.class);	
	@Autowired
	private AccountManagerService accountManagerService;
	@Value(AccountsConstants.ACCOUNTS_CODE_SUCESS)
	private String accountsCodeSuccess;
	@Value(AccountsConstants.ACCOUNTS_MSG_SUCESS)
	private String accountsMSGSuccess;
	@Value(AccountsConstants.ACCOUNTS_DES_SUCESS)
	private String accountsDescSuccess;	
	@Value("${server.port}")
	private String port;	
	private static final String SERVICE_NAME = "ACCOUNT_MANAGER";
	
	@ApiOperation(value = "Get account package information", notes = "Get all account package informacion by billerID")
	@GetMapping(value = AccountPaths.ACCOUNT_MANAGER_GET)
	public ResponseEntity<?> recoverAccountManager(final @PathVariable("billerID") String billerID, final @RequestHeader("transactionId") long transactionId){
		//------------------------------------------------------------------
		final Calendar startTime = Calendar.getInstance();		
		boolean status = true;
		String description = AccountsConstants.SUCCESSFUL;
		ThreadContext.push(Long.toString(transactionId));
		//------------------------------------------------------------------
		try {
			LOGGER.info("getting account package information by billerId {}", billerID);
			AccountAdminRequestVO accountResponse = accountManagerService.findAccountManagerByBillerId(billerID);
			LOGGER.info("Account package information was successfully");				
			return new ResponseEntity<>(accountResponse, HttpStatus.OK);
		}finally {
			LoggerTransaction.printTransactionalLog(SERVICE_NAME, port, startTime, Calendar.getInstance(), transactionId, "ACCOUNTS_MANAGER_GET", status, description);
			ThreadContext.clearStack();
		}
	}
	
	@ApiOperation(value = "Modify account package information", notes = "Manage the plan of the account. Total invoices issued, total open/closed sessions, total customer record. Used in web pages")
	@PutMapping(value = AccountPaths.ACCOUNT_MANAGER_UPDATE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> upadateAccountManager(final @PathVariable String billerID, final @RequestHeader("transactionId") long transactionId, @RequestBody AccountAdminRequestVO accountAdmin){
		//------------------------------------------------------------------
		final Calendar startTime = Calendar.getInstance();		
		boolean status = true;
		String description = AccountsConstants.SUCCESSFUL;
		ThreadContext.push(Long.toString(transactionId));
		//------------------------------------------------------------------
		try {
			LOGGER.info("Updating account package information by billerId {}", billerID);
			AdministratorAccount adminResponse = null;
			
			if(accountAdmin.isLogin()) {
				adminResponse = accountManagerService.manageAccountLogin(billerID, accountAdmin.isLogin());
			}else if(accountAdmin.isLogout()) {
				adminResponse = accountManagerService.manageAccountLogout(billerID, accountAdmin.isLogout(), accountAdmin.getSessionID());
			}else if(accountAdmin.getTotalActiveSessions() != null) {
				adminResponse = accountManagerService.modifyAccountManagerActiveSessions(billerID, accountAdmin.getTotalActiveSessions());
			}else if(accountAdmin.getTotalRegisteredCustomers() != null) {
				adminResponse = accountManagerService.modifyAccountManagerRegisteredCustomer(billerID, accountAdmin.getTotalRegisteredCustomers());
			}else if(accountAdmin.getTotalFreeBillsEmitted() != null) {
				adminResponse = accountManagerService.modifyAccountManagerFreeBills(billerID, accountAdmin.getTotalFreeBillsEmitted());
			}else if(accountAdmin.getTotalBillsEmitted() != null) {
				adminResponse = accountManagerService.modifyAccountManagerTotalBills(billerID, accountAdmin.getTotalBillsEmitted());
			}
			LOGGER.info("Manage account was successfully");
			if(adminResponse instanceof AccountAdminRequestVO) {
				LOGGER.info("Account package information wil be send");
				return new ResponseEntity<>((AccountAdminRequestVO)adminResponse, HttpStatus.OK);
			}else {
				LOGGER.info("Account package information update was successfully");
				return new ResponseEntity<>((AccountAdministratorVO)adminResponse, HttpStatus.OK);
			}				
		}finally {
			LoggerTransaction.printTransactionalLog(SERVICE_NAME, port, startTime, Calendar.getInstance(), transactionId, "ACCOUNTS_MANAGER_UPDATE", status, description);
			ThreadContext.clearStack();
		}
	}
	
	@ApiIgnore
	@ApiOperation(value = "Modify account package information all in one", notes = "It managed information about account package by BillerID")
	@PostMapping(value = AccountPaths.ACCOUNT_MANAGER_MANAGED, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> manageAccount(final @PathVariable String billerID, final @RequestHeader("transactionId") long transactionId, @RequestBody AccountAdminRequestVO accountAdmin){
		//------------------------------------------------------------------
		final Calendar startTime = Calendar.getInstance();		
		boolean status = true;
		String description = AccountsConstants.SUCCESSFUL;
		ThreadContext.push(Long.toString(transactionId));
		//------------------------------------------------------------------
		try {
			LOGGER.info("Checking account to manage plan");
			AccountAdminRequestVO adminResponse = accountManagerService.manageAccountPlanByBillerID(billerID);		
			return new ResponseEntity<>(adminResponse, HttpStatus.OK);
		}finally {
			LoggerTransaction.printTransactionalLog(SERVICE_NAME, port, startTime, Calendar.getInstance(), transactionId, "ACCOUNTS_MANAGER_MANAGE", status, description);
			ThreadContext.clearStack();
		}
	}
	
	@ApiOperation(value = "Remove the account management record")
	@DeleteMapping(value = AccountPaths.ACCOUNT_MANAGER_MANAGED_REMOVE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> removeAccountManager(final @PathVariable String billerID, final @RequestHeader("transactionId") long transactionId){
		//------------------------------------------------------------------
		final Calendar startTime = Calendar.getInstance();		
		boolean status = true;
		String description = AccountsConstants.SUCCESSFUL;
		ThreadContext.push(Long.toString(transactionId));
		//------------------------------------------------------------------
		try {
			LOGGER.info("Removing account to manage plan");
			accountManagerService.removeAccountManager(billerID);		
			return new ResponseEntity<>(new ResponseMessage(200, "SUCCESS"), HttpStatus.OK);
		}finally {
			LoggerTransaction.printTransactionalLog(SERVICE_NAME, port, startTime, Calendar.getInstance(), transactionId, "ACCOUNTS_MANAGER_MANAGE", status, description);
			ThreadContext.clearStack();
		}
	}
}
