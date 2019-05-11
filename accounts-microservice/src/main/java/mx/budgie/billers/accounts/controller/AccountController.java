/**
 * 
 */
package mx.budgie.billers.accounts.controller;

import java.nio.file.AccessDeniedException;
import java.util.Calendar;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import mx.budgie.billers.accounts.constants.AccountPaths;
import mx.budgie.billers.accounts.constants.AccountsConstants;
import mx.budgie.billers.accounts.loggers.LoggerTransaction;
import mx.budgie.billers.accounts.mongo.documents.AccountStatus;
import mx.budgie.billers.accounts.request.AccountUpdateParams;
import mx.budgie.billers.accounts.request.RequestParams;
import mx.budgie.billers.accounts.request.UpdateRolesParams;
import mx.budgie.billers.accounts.response.ResponseMessage;
import mx.budgie.billers.accounts.service.AccountManagerService;
import mx.budgie.billers.accounts.service.AccountService;
import mx.budgie.billers.accounts.service.ClientAuthService;
import mx.budgie.billers.accounts.vo.AccountEmailRequest;
import mx.budgie.billers.accounts.vo.AccountRequestVO;
import mx.budgie.billers.accounts.vo.AccountVO;
import mx.budgie.billers.accounts.vo.ClientAuthenticationVO;
import mx.budgie.billers.accounts.vo.PackageVO;
import mx.budgie.commons.client.EndpointClient;
import mx.budgie.commons.exception.EndpointException;
import mx.budgie.commons.utils.CommonsUtil;

/**
 * @company Budgie Software Technologies
 * @author brucewayne
 * @date Jun 25, 2017
 * @description Allowed CRUD operations to Account Microservice
 */
@RestController
@RequestMapping(value = AccountPaths.ACCOUNT_BASED_PATH)
@RefreshScope
@Api(value = AccountPaths.ACCOUNT_BASED_PATH)
public class AccountController {

	private static final Logger LOGGER = LogManager.getLogger(AccountController.class);
	@Autowired
	private ClientAuthService clientAuthService;
	@Autowired
	@Qualifier(value = AccountsConstants.SERVICE_OAUTH_CLIENT_DETAIL)
	private ClientAuthService oauthClientAuthService;
	@Autowired
	@Qualifier(AccountsConstants.SERVICE_ACCOUNT)
	private AccountService accountService;
	@Autowired
	private AccountManagerService accountManagerService;
	@Value(AccountsConstants.ACCOUNTS_SERVER_PORT)
	private String port;
	@Value(AccountsConstants.ACCOUNTS_CODE_400)
	private String accountsCode400;
	@Value(AccountsConstants.ACCOUNTS_MSG_BAD_REQUEST)
	private String accountsDesc400;
	@Value(AccountsConstants.ACCOUNTS_CODE_01)
	private String accountsCode01;
	@Value(AccountsConstants.ACCOUNTS_MSG_CODE_01)
	private String accountsMSG01;
	@Value(AccountsConstants.ACCOUNTS_DES_CODE_01)
	private String accountsDesc01;
	@Value(AccountsConstants.ACCOUNTS_CODE_02)
	private String accountsCode02;
	@Value(AccountsConstants.ACCOUNTS_MSG_CODE_02)
	private String accountsMSG02;
	@Value(AccountsConstants.ACCOUNTS_DES_CODE_02)
	private String accountsDesc02;
	@Value(AccountsConstants.ACCOUNTS_CODE_03)
	private String accountsCode03;
	@Value(AccountsConstants.ACCOUNTS_MSG_CODE_03)
	private String accountsMSG03;
	@Value(AccountsConstants.ACCOUNTS_DES_CODE_03)
	private String accountsDesc03;
	@Value(AccountsConstants.ACCOUNTS_CODE_06)
	private String accountsCode06;
	@Value(AccountsConstants.ACCOUNTS_MSG_CODE_06)
	private String accountsMSG06;
	@Value(AccountsConstants.ACCOUNTS_DES_CODE_06)
	private String accountsDesc06;
	@Value(AccountsConstants.ACCOUNTS_CODE_07)
	private String accountsCode07;
	@Value(AccountsConstants.ACCOUNTS_MSG_CODE_07)
	private String accountsMSG07;
	@Value(AccountsConstants.ACCOUNTS_DES_CODE_07)
	private String accountsDesc07;
	@Value(AccountsConstants.ACCOUNTS_CODE_13)
	private String accountsCode13;
	@Value(AccountsConstants.ACCOUNTS_MSG_CODE_13)
	private String accountsMSG13;
	@Value(AccountsConstants.ACCOUNTS_DES_CODE_13)
	private String accountsDesc13;
	@Value(AccountsConstants.ACCOUNTS_CODE_666)
	private String accountsCode666;
	@Value(AccountsConstants.ACCOUNTS_MSG_CODE_666)
	private String accountsMSG666;
	@Value(AccountsConstants.ACCOUNTS_DES_CODE_666)
	private String accountsDesc666;
	@Value(AccountsConstants.ACCOUNTS_CODE_SUCESS)
	private String accountsCodeSuccess;
	@Value(AccountsConstants.ACCOUNTS_MSG_SUCESS)
	private String accountsMSGSuccess;
	@Value(AccountsConstants.ACCOUNTS_DES_SUCESS)
	private String accountsDescSuccess;
	@Value(AccountsConstants.ACCOUNTS_INSTANCE_NAME)
	private String instanceName;
	@Value(AccountsConstants.ACCOUNTS_KEY_DEFAULT)
	private String keyDefault;
	@Value("${package.recover.by.id}")
	private String packageEndpoint;
	@Value(AccountsConstants.ACCOUNT_DEFAULT_PACKAGE)
	private String defaultPackage;
	@Value(AccountsConstants.ACCOUNT_HTTP_BASIC_AUTH_ENABLED)
	private boolean isHttpBasicAuthEnabled;
	@Value(AccountsConstants.ACCOUNT_NEED_VALIDATE_NICKNAME)
	private boolean needValidateNickname;
	@Value(AccountsConstants.ACCOUNT_NEED_VALIDATE_AUTHORIZATION)
	private boolean needValidateAuthorization;
	@Value(AccountsConstants.ACCOUNT_NEED_VALIDATE_CLIENT_ID)
	private boolean needValidateClientId;
	
	@ApiOperation(value = "Create account for a Biller Customer", notes = "It is necessary to provide the authentication token that was generated for a client")
	@PostMapping(value = AccountPaths.ACCOUNT_CREATE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<ResponseMessage> createAccount(
			@RequestHeader("Authorization") final String authentication,
			@RequestHeader("transactionId") final long transactionId, @RequestBody @Valid AccountRequestVO account) {
		// ------------------------------------------------------------------
		final Calendar startTime = Calendar.getInstance();
		boolean status = true;
		String description = AccountsConstants.SUCCESSFUL;
		// ------------------------------------------------------------------
		try {
			ThreadContext.push(Long.toString(transactionId));
			LOGGER.info("Starting account creation process");
			String clientAuthentication = null;
			try {
				clientAuthentication = validateAuthenticationHeader(authentication);
			} catch (AccessDeniedException ex) {
				LOGGER.error("Access denied. API Token is wrong: " + ex);
				status = false;
				description = ex.getMessage();
				return new ResponseEntity<>(
						buildResponseMessage(Integer.valueOf(accountsCode400), accountsDesc400, accountsDesc400),
						HttpStatus.NOT_ACCEPTABLE);
			}
			LOGGER.info("Validating that the account was not previously created with email [{}]", account.getEmail());
			if (null != accountService.findAccountByEmail(account.getEmail())) {
				LOGGER.error("There is an account associated with that email: [{}]", account.getEmail());
				status = false;
				description = accountsDesc03;
				return new ResponseEntity<>(
						buildResponseMessage(Integer.valueOf(accountsCode03), accountsMSG03, accountsDesc03),
						HttpStatus.NOT_ACCEPTABLE);
			}
			LOGGER.info("Validating that the account was not previously created with nickname [{}]",account.getNickname());
			AccountVO auxAcc = accountService.findAccountByNickname(account.getNickname());
			if (null != auxAcc && account.getPassword() != null && account.getPassword().equals(auxAcc.getPassword()) && needValidateNickname) {
				status = false;
				description = accountsDesc02;
				LOGGER.error("There is an account associated with that nickname: [{}]", account.getNickname());
				return new ResponseEntity<>(
						buildResponseMessage(Integer.valueOf(accountsCode02), accountsMSG02, accountsDesc02),
						HttpStatus.NOT_ACCEPTABLE);
			}			
			
			PackageVO packageVO = null;
			try {
				ResponseEntity<PackageVO> responsePackage = new EndpointClient(String.format("%s", packageEndpoint))
														.putAuthorizationToken(authentication)
														.putHeaders(CommonsUtil.createHead())
														.requestParameters(defaultPackage)
														.callGET(PackageVO.class);	
				packageVO = responsePackage.getBody();
			} catch(EndpointException e) {
				LOGGER.error("Endpoint package is failed: {}", e);
			}
			
			LOGGER.info("Creating account for Biller User: [{}]", account.getEmail());
			AccountVO accountVO = accountService.createAccount(account, clientAuthentication, packageVO);
			if (null == accountVO) {
				LOGGER.error("Error while create account");
				status = false;
				description = accountsDesc01;
				return new ResponseEntity<>(
						buildResponseMessage(Integer.valueOf(accountsCode01), accountsMSG01, accountsDesc01),
						HttpStatus.NOT_ACCEPTABLE);
			}
			LOGGER.info("Accout was created successfully");
			AccountVO responseAccount = buildAccountResponse(accountVO);
			responseAccount.setActivationCode(accountVO.getActivationCode());
			return new ResponseEntity<>(responseAccount, HttpStatus.CREATED);
		} finally {
			LoggerTransaction.printTransactionalLog(instanceName, port, startTime, Calendar.getInstance(),
					transactionId, "ACCOUNTS_CREATE", status, description);
			ThreadContext.clearStack();
		}
	}

	@ApiOperation(value = "Login a biller account with email", notes = "It is necessary to provide the email and password")
	@PostMapping(value = AccountPaths.ACCOUNT_LOGIN, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> loginAccount(final @RequestHeader("transactionId") long transactionId
			, @RequestBody AccountRequestVO accountRequest) {
		AccountVO account = null;
		// ------------------------------------------------------------------
		final Calendar startTime = Calendar.getInstance();
		boolean status = true;
		String description = AccountsConstants.SUCCESSFUL;
		// ------------------------------------------------------------------
		try {
			ThreadContext.push(Long.toString(transactionId));
			LOGGER.info("Starting account recover process by ID");
			LOGGER.info("Looking for a Account by email {}", accountRequest.getEmail());
			account = accountService.findAccountByEmail(accountRequest.getEmail());
			if (null == account) {
				LOGGER.warn("Accout with email '{}' not found.", accountRequest.getEmail());
				status = false;
				description = accountsDesc06;
				return new ResponseEntity<>(buildResponseMessage(Integer.valueOf(accountsCode06), accountsMSG06, accountsDesc06), HttpStatus.NOT_ACCEPTABLE);
			}
			if (!AccountStatus.ACTIVE.equals(account.getAccountStatus())) {
				LOGGER.warn("Accout with email '{}' is inactive", accountRequest.getEmail());
				status = false;
				description = accountsDesc13;
				return new ResponseEntity<>(buildResponseMessage(Integer.valueOf(accountsCode13), accountsMSG13, accountsDesc13), HttpStatus.NOT_ACCEPTABLE);
			}
			if (!account.getPassword().equals(accountRequest.getPassword())) {
				LOGGER.warn("Accout with email '{}' not match for password", accountRequest.getEmail());
				status = false;
				description = accountsDesc13;
				return new ResponseEntity<>(buildResponseMessage(Integer.valueOf(accountsCode06), accountsMSG06, accountsDesc06), HttpStatus.NOT_ACCEPTABLE);
			}
			LOGGER.info("Account [{}] was found ", accountRequest.getEmail());
			return new ResponseEntity<>(new AccountVO(account.getBillerID(), account.getNickname(), account.getEmail(),account.getAccountStatus()), HttpStatus.OK);
		} finally {
			LoggerTransaction.printTransactionalLog(instanceName, port, startTime, Calendar.getInstance(),
					transactionId, "ACCOUNTS_RECOVER", status, description);
			ThreadContext.clearStack();
		}
	}
	
	@ApiOperation(value = "Activate a biller account", notes = "It is necessary to provide activate param")
	@PostMapping(value = AccountPaths.ACCOUNT_ACTIVATE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> activateAccount(@RequestBody Map<String, String> data, @RequestParam final String code,
			final @RequestHeader("transactionId") long transactionId) {
		AccountVO accountVO = null;
		// ------------------------------------------------------------------
		final Calendar startTime = Calendar.getInstance();
		boolean status = true;
		String description = AccountsConstants.SUCCESSFUL;
		// ------------------------------------------------------------------
		try {
			ThreadContext.push(Long.toString(transactionId));
			if(data == null || code == null || code.isEmpty()) {
				LOGGER.error("Accout not found to activate");
				status = false;
				description = accountsDesc06;
				return new ResponseEntity<>(buildResponseMessage(Integer.valueOf(accountsCode06), accountsMSG06, accountsDesc06), HttpStatus.NOT_ACCEPTABLE);
			}			
			LOGGER.info("Trying to activate account");
			accountVO = accountService.findAccountToActivate(code);
			if(accountVO == null || !accountVO.getBillerID().equals(data.get("billerID")) || data.get("password") == null || data.get("repassword") == null) {
				LOGGER.error("Accout not found to activate");
				status = false;
				description = accountsDesc06;
				return new ResponseEntity<>(buildResponseMessage(Integer.valueOf(accountsCode06), accountsMSG06, accountsDesc06), HttpStatus.NOT_ACCEPTABLE);
			}
			if(!data.get("password").equals(data.get("repassword"))) {
				LOGGER.error("Password not match");
				status = false;
				description = accountsDesc06;
				return new ResponseEntity<>(buildResponseMessage(Integer.valueOf(accountsCode06), accountsMSG06, accountsDesc06), HttpStatus.NOT_ACCEPTABLE);
			}		
			accountVO.setAccountStatus(AccountStatus.ACTIVE);			
			accountVO.setPassword(data.get("password"));
			accountVO.setActivationCode(null);
			accountVO = accountService.updateAccount(accountVO);
			if (null == accountVO) {
				LOGGER.error("There was an error while updating the account");
				status = false;
				description = accountsDesc666;
				return new ResponseEntity<>(buildResponseMessage(Integer.valueOf(accountsCode666), accountsMSG666, accountsDesc666), HttpStatus.NOT_ACCEPTABLE);
			}
			LOGGER.info("Accout was updated successfully");
			return new ResponseEntity<>(new AccountVO(accountVO.getBillerID(), accountVO.getNickname(), accountVO.getEmail(),accountVO.getAccountStatus()), HttpStatus.OK);
		} finally {
			LoggerTransaction.printTransactionalLog(instanceName, port, startTime, Calendar.getInstance(),
					transactionId, "ACCOUNTS_ACTIVATE", status, description);
			ThreadContext.clearStack();
		}
	}
	
	@ApiOperation(value = "Get a biller account with billerId", notes = "It is necessary to provide the billerId in the path variable")
	@GetMapping(value = AccountPaths.ACCOUNT_RECOVER_BY_ID, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> recoverAccount(final @PathVariable("billerID") String billerID,
			final @RequestHeader("transactionId") long transactionId) {
		AccountVO account = null;
		// ------------------------------------------------------------------
		final Calendar startTime = Calendar.getInstance();
		boolean status = true;
		String description = AccountsConstants.SUCCESSFUL;
		// ------------------------------------------------------------------
		try {
			ThreadContext.push(Long.toString(transactionId));
			LOGGER.info("Starting account recover process by ID");
			LOGGER.info("Looking for a Account by billerID {}", billerID);
			account = accountService.findAccountByBillerID(billerID);
			if (null == account) {
				LOGGER.warn("Accout with billerID '{}' not found.", billerID);
				status = false;
				description = accountsDesc06;
				return new ResponseEntity<>(buildResponseMessage(Integer.valueOf(accountsCode06), accountsMSG06, accountsDesc06), HttpStatus.NOT_ACCEPTABLE);
			}
			if (!AccountStatus.ACTIVE.equals(account.getAccountStatus())) {
				status = false;
				description = accountsDesc13;
				return new ResponseEntity<>(buildResponseMessage(Integer.valueOf(accountsCode13), accountsMSG13 + account.getAccountStatus().name(), accountsDesc13), HttpStatus.NOT_ACCEPTABLE);
			}
			LOGGER.info("Account [{}] was found ", billerID);
			return new ResponseEntity<>(buildAccountResponse(account), HttpStatus.OK);
		} finally {
			LoggerTransaction.printTransactionalLog(instanceName, port, startTime, Calendar.getInstance(),
					transactionId, "ACCOUNTS_RECOVER", status, description);
			ThreadContext.clearStack();
		}
	}
	
	@ApiOperation(value = "Resend an activation code", notes = "Resend activation code to reset or recover password")
	@PostMapping(value = AccountPaths.ACCOUNT_RESET_OR_RECOVER_PASSWORD, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> resetOrRecoverPassword(final @RequestBody(required = true) AccountEmailRequest accountEmail,
			final @RequestHeader("transactionId") long transactionId) {
		AccountVO account = null;
		// ------------------------------------------------------------------
		final Calendar startTime = Calendar.getInstance();
		boolean status = true;
		String description = AccountsConstants.SUCCESSFUL;
		// ------------------------------------------------------------------
		try {
			ThreadContext.push(Long.toString(transactionId));
			LOGGER.info("Starting account recover process by email");
			LOGGER.info("Looking for a Account by email {}", accountEmail.getEmail());
			account = accountService.findAccountByEmail(accountEmail.getEmail());
			if (null == account) {
				LOGGER.warn("Accout with email '{}' not found.", accountEmail.getEmail());
				status = false;
				description = accountsDesc06;
				return new ResponseEntity<>(buildResponseMessage(Integer.valueOf(accountsCode06), accountsMSG06, accountsDesc06), HttpStatus.NOT_ACCEPTABLE);
			}					
			LOGGER.info("Account [{}] was found ", accountEmail.getEmail());
			AccountVO responseAccount = accountService.resendActivationCode(account);
			LOGGER.info("Activation code was created");			
			return new ResponseEntity<>(new AccountVO(responseAccount.getBillerID(), responseAccount.getNickname(), responseAccount.getEmail(), responseAccount.getAccountStatus(), responseAccount.getActivationCode()), HttpStatus.OK);
		} finally {
			LoggerTransaction.printTransactionalLog(instanceName, port, startTime, Calendar.getInstance(), transactionId, "ACCOUNTS_RESET_RECOVER_PASSWORD", status, description);
			ThreadContext.clearStack();
		}
	}

	@ApiOperation(value = "Get a biller account from activation code and billerID", notes = "It is necessary to provide the activation code and billerID in the request paramr.")
	@GetMapping(value = AccountPaths.ACCOUNT_RECOVER_BY_ACTIVATION_CODE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> recoverAccountByActivationCode(final @RequestParam("code") String code,
			final @RequestParam("billerID") String billerID,
			final @RequestHeader("transactionId") long transactionId) {
		AccountVO accountVO = null;
		// ------------------------------------------------------------------
			final Calendar startTime = Calendar.getInstance();
			boolean status = true;
			String description = AccountsConstants.SUCCESSFUL;
		// ------------------------------------------------------------------
		try {
			ThreadContext.push(Long.toString(transactionId));
			if(code == null || code.isEmpty() || billerID == null || billerID.isEmpty()) {
				LOGGER.error("Accout not found to activate");
				status = false;
				description = accountsDesc06;
				return new ResponseEntity<>(buildResponseMessage(Integer.valueOf(accountsCode06), accountsMSG06, accountsDesc06), HttpStatus.NOT_ACCEPTABLE);
			}			
			LOGGER.info("Trying to get account with activation code");
			accountVO = accountService.findAccountToActivate(code);
			if(accountVO == null || !accountVO.getBillerID().equals(billerID)) {
				LOGGER.error("Accout not found to activate");
				status = false;
				description = accountsDesc06;
				return new ResponseEntity<>(buildResponseMessage(Integer.valueOf(accountsCode06), accountsMSG06, accountsDesc06), HttpStatus.NOT_ACCEPTABLE);
			}
			LOGGER.info("Account {} was found email:{}", accountVO.getNickname(), accountVO.getEmail());
			return new ResponseEntity<>(buildAccountResponse(accountVO), HttpStatus.OK);
		} finally {
			LoggerTransaction.printTransactionalLog(instanceName, port, startTime, Calendar.getInstance(),
					transactionId, "ACCOUNTS_RECOVER_ACTIVATION_CODE", status, description);
			ThreadContext.clearStack();
		}
	}
	
	@ApiOperation(value = "Update a biller account", notes = "It is necessary to provide all parameters that need update")
	@PutMapping(value = AccountPaths.ACCOUNT_UPDATE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> updateAccount(@RequestBody final AccountUpdateParams params,
			final @RequestHeader("transactionId") long transactionId) {
		AccountVO accountVO = null;
		// ------------------------------------------------------------------
		final Calendar startTime = Calendar.getInstance();
		boolean status = true;
		String description = AccountsConstants.SUCCESSFUL;
		// ------------------------------------------------------------------
		try {
			ThreadContext.push(Long.toString(transactionId));
			LOGGER.info("Looking for account by billerID {} to update", params.getBillerID());
			accountVO = accountService.findAccountByBillerID(params.getBillerID());
			if (null == accountVO) {
				LOGGER.error("Accout with billerID {} not found.", params.getBillerID());
				status = false;
				description = accountsDesc06;
				return new ResponseEntity<>(buildResponseMessage(Integer.valueOf(accountsCode06), accountsMSG06, accountsDesc06), HttpStatus.NOT_ACCEPTABLE);
			}
			LOGGER.info("{} account has status of {}", accountVO.getEmail(), accountVO.getAccountStatus());
			if (params.getPassword() != null && !accountVO.getPassword().equals(params.getPassword()) ) {
				status = false;
				description = accountsDesc07;
				return new ResponseEntity<>(buildResponseMessage(Integer.valueOf(accountsCode07), accountsMSG07, accountsDesc07), HttpStatus.NOT_ACCEPTABLE);
			}			
			if (params.getStatus() != null) {
				LOGGER.info("Changing status by {} to {}", accountVO.getAccountStatus(), params.getStatus());
				accountVO.setAccountStatus(params.getStatus());
			}
			if (params.getNickname() != null && !params.getNickname().isEmpty()) {
				LOGGER.info("Changing nickname by {} to {}", accountVO.getNickname(), params.getNickname());				
				accountVO.setNickname(params.getNickname());
			}			
			accountVO = accountService.updateAccount(accountVO);
			if (null == accountVO) {
				LOGGER.error("There was an error while updating the account");
				status = false;
				description = accountsDesc666;
				return new ResponseEntity<>(buildResponseMessage(Integer.valueOf(accountsCode666), accountsMSG666, accountsDesc666), HttpStatus.NOT_ACCEPTABLE);
			}
			LOGGER.info("Accout was updated successfully");
			return new ResponseEntity<>(new AccountVO(accountVO.getBillerID(), accountVO.getNickname(), accountVO.getEmail(),
					accountVO.getAccountStatus()), HttpStatus.OK);
		} finally {
			LoggerTransaction.printTransactionalLog(instanceName, port, startTime, Calendar.getInstance(),
					transactionId, "ACCOUNTS_UPDATE", status, description);
			ThreadContext.clearStack();
		}
	}
	
	@ApiOperation(value = "Update a biller account", notes = "It is necessary to provide all parameters that need update")
	@RequestMapping(value = AccountPaths.ACCOUNT_UPDATE_ROLES, method= {RequestMethod.PUT, RequestMethod.DELETE}, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> updateRolesAccount(final HttpServletRequest request, @RequestBody final UpdateRolesParams params, @RequestParam(required=false) final String activate,
			final @RequestHeader("transactionId") long transactionId) {
		AccountVO accountVO = null;
		// ------------------------------------------------------------------
		final Calendar startTime = Calendar.getInstance();
		boolean status = true;
		String description = AccountsConstants.SUCCESSFUL;
		// ------------------------------------------------------------------
		try {
			ThreadContext.push(Long.toString(transactionId));			
			LOGGER.info("Looking for account by billerID {} to update", params.getBillerID());
			accountVO = accountService.findAccountByBillerID(params.getBillerID());
			if (null == accountVO) {
				LOGGER.error("Accout with billerID {} not found.", params.getBillerID());
				status = false;
				description = accountsDesc06;
				return new ResponseEntity<>(buildResponseMessage(Integer.valueOf(accountsCode06), accountsMSG06, accountsDesc06), HttpStatus.NOT_ACCEPTABLE);
			}						
			accountVO = accountService.updateRoles(params, "DELETE".equals(request.getMethod()));
			if (null == accountVO) {
				LOGGER.error("There was an error while updating the account");
				status = false;
				description = accountsDesc666;
				return new ResponseEntity<>(buildResponseMessage(Integer.valueOf(accountsCode666), accountsMSG666, accountsDesc666), HttpStatus.NOT_ACCEPTABLE);
			}
			LOGGER.info("Accout was updated successfully");
			return new ResponseEntity<>(new AccountVO(accountVO.getBillerID(), accountVO.getNickname(), accountVO.getEmail(),accountVO.getAccountStatus(), accountVO.getRoles()), HttpStatus.OK);
		} finally {
			LoggerTransaction.printTransactionalLog(instanceName, port, startTime, Calendar.getInstance(),
					transactionId, "ACCOUNTS_UPDATE", status, description);
			ThreadContext.clearStack();
		}
	}
	
	@ApiOperation(value = "Delete a biller account", notes = "It is necessary to provide all parameters included the password")
	@DeleteMapping(value = AccountPaths.ACCOUNT_DELETE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> deleteAccount(@RequestBody final RequestParams params,
			final @RequestHeader("transactionId") long transactionId) {
		AccountVO accountVO = null;
		// ------------------------------------------------------------------
		final Calendar startTime = Calendar.getInstance();
		boolean status = true;
		String description = AccountsConstants.SUCCESSFUL;
		// ------------------------------------------------------------------
		try {
			ThreadContext.push(Long.toString(transactionId));
			LOGGER.info("Looking for account object by billerID {}", params.getBillerID());
			accountVO = accountService.findAccountByBillerID(params.getBillerID());
			if (null == accountVO) {
				LOGGER.error("Accout with billerID '" + params.getBillerID() + "' not found.");
				status = false;
				description = accountsDesc06;
				return new ResponseEntity<>(buildResponseMessage(Integer.valueOf(accountsCode06), accountsMSG06, accountsDesc06), HttpStatus.NOT_ACCEPTABLE);
			}
			if (!accountVO.getPassword().equals(params.getPassword())) {
				LOGGER.error("Password not match, we can´t delete account");
				status = false;
				description = accountsDesc07;
				return new ResponseEntity<>(buildResponseMessage(Integer.valueOf(accountsCode07), accountsMSG07, accountsDesc07), HttpStatus.NOT_ACCEPTABLE);
			}
			LOGGER.info("Accout was deleted successfully");
			accountService.deleteAccount(accountVO.getEmail());
			accountManagerService.removeAccountManager(accountVO.getBillerID());
			return new ResponseEntity<>(buildResponseMessage(Integer.valueOf(accountsCodeSuccess), accountsMSGSuccess, accountsDescSuccess), HttpStatus.OK);
		} finally {
			LoggerTransaction.printTransactionalLog(instanceName, port, startTime, Calendar.getInstance(),
					transactionId, "ACCOUNTS_DELETE", status, description);
			ThreadContext.clearStack();
		}
	}

	private String validateAuthenticationHeader(final String authorization) throws AccessDeniedException {
		LOGGER.info("Validating authentication header [{}]", authorization);
		if (!isHttpBasicAuthEnabled) {
			ClientAuthenticationVO client = clientAuthService.findClientByClientId(authorization);
			if (client == null) {
				throw new AccessDeniedException("Access is denied. Authentication is null or empty");
			}
			return client.getClientId();
		} 
		if(needValidateAuthorization) {
			try {
				if (authorization == null || authorization.isEmpty()) {
					throw new AccessDeniedException("Access is denied. Authentication is null or empty");
				}
				final String encUserPassword = authorization.replaceFirst("Basic" + " ", "");
				LOGGER.info("Decoding basic authentication");
				final String decodingBase64 = new String(Base64.decodeBase64(encUserPassword.getBytes()));
				LOGGER.info("Decrypting basic authentication header");
				LOGGER.info("Customer is trying to call create account resource: ' " + decodingBase64 + " '");
				final StringTokenizer tokenizer = new StringTokenizer(decodingBase64, ":");
				final String[] credentials = new String[2];
				credentials[0] = tokenizer.nextToken();
				credentials[1] = tokenizer.nextToken();
				final boolean foundClient = oauthClientAuthService.validateAuthenticationClient(credentials[0],
						encUserPassword);
				if (!foundClient) {
					throw new AccessDeniedException("Client not found. Verify your credentials");
				} else {
					return credentials[0];
				}
			} catch (Exception ex) {
				throw new AccessDeniedException("Access is denied. Authentication is null or empty");
			}
		}else if(needValidateClientId){
			final String token = authorization.replaceFirst("bearer" + " ", "");
			String clientId = oauthClientAuthService.findClientIdByToken(token);
			if(clientId == null) {
				throw new AccessDeniedException("Access is denied. ClientId doesn´t exist");
			}
			return clientId;
		}		
		return authorization;
	}

	private ResponseMessage buildResponseMessage(final Integer code, final String message, final String description) {
		LOGGER.info("Response Message: code = {} : message = {} : http status = {}", code, message, description);
		return new ResponseMessage(code, message, description);
	}

	private AccountVO buildAccountResponse(final AccountVO accountVO) {
		return new AccountVO(accountVO.getBillerID(), accountVO.getAccessToken(), accountVO.getNickname(),
				accountVO.getEmail(), accountVO.getTemporaryPassword(), accountVO.getView(), accountVO.getPurchasedPackage(), accountVO.getTotalBills(),
				accountVO.getTotalFreeBills(), accountVO.getTotalRegisteredCustomer(),
				accountVO.getTotalActiveSession(), accountVO.getExpirationPackageDate(),
				accountVO.getPurchasedPackageDate());
	}
}
