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
import mx.budgie.billers.accounts.request.RequestParams;
import mx.budgie.billers.accounts.request.UpdateRolesParams;
import mx.budgie.billers.accounts.response.ResponseMessage;
import mx.budgie.billers.accounts.service.AccountManagerService;
import mx.budgie.billers.accounts.service.AccountService;
import mx.budgie.billers.accounts.service.ClientAuthService;
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
						HttpStatus.OK);
			}
			LOGGER.info("Validating that the account was not previously created with email [{}]", account.getEmail());
			if (null != accountService.findAccountByEmail(account.getEmail())) {
				LOGGER.error("There is an account associated with that email: [{}]", account.getEmail());
				status = false;
				description = accountsDesc03;
				return new ResponseEntity<>(
						buildResponseMessage(Integer.valueOf(accountsCode03), accountsMSG03, accountsDesc03),
						HttpStatus.OK);
			}
			LOGGER.info("Validating that the account was not previously created with nickname [{}]",account.getNickname());
			AccountVO auxAcc = accountService.findAccountByNickname(account.getNickname());
			if (null != auxAcc && account.getPassword() != null && account.getPassword().equals(auxAcc.getPassword()) && needValidateNickname) {
				status = false;
				description = accountsDesc02;
				LOGGER.error("There is an account associated with that nickname: [{}]", account.getNickname());
				return new ResponseEntity<>(
						buildResponseMessage(Integer.valueOf(accountsCode02), accountsMSG02, accountsDesc02),
						HttpStatus.OK);
			}			
			
			PackageVO packageVO = null;
			try {
				ResponseEntity<PackageVO> responsePackage = new EndpointClient(String.format("%s", packageEndpoint))
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
						HttpStatus.OK);
			}
			LOGGER.info("Accout was created successfully");
			AccountVO responseAccount = buildAccountResponse(accountVO);
			responseAccount.setActivationCode(accountVO.getActivationCode());
			return new ResponseEntity<>(responseAccount, HttpStatus.OK);
		} finally {
			LoggerTransaction.printTransactionalLog(instanceName, port, startTime, Calendar.getInstance(),
					transactionId, "ACCOUNTS_CREATE", status, description);
			ThreadContext.clearStack();
		}
	}

	@ApiOperation(value = "Get a biller account with billerId", notes = "It is necessary to provide the billerId in the path variable")
	@GetMapping(value = AccountPaths.ACCOUNT_RECOVER_BY_ID, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseMessage recoverAccount(final @PathVariable("billerID") String billerID,
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
				return buildResponseMessage(Integer.valueOf(accountsCode06), accountsMSG06, accountsDesc06);
			}
			if (!AccountStatus.ACTIVE.equals(account.getAccountStatus())) {
				status = false;
				description = accountsDesc13;
				return buildResponseMessage(Integer.valueOf(accountsCode13), accountsMSG13, accountsDesc13);
			}
			LOGGER.info("Account [{}] was found ", billerID);
			return buildAccountResponse(account);
		} finally {
			LoggerTransaction.printTransactionalLog(instanceName, port, startTime, Calendar.getInstance(),
					transactionId, "ACCOUNTS_RECOVER", status, description);
			ThreadContext.clearStack();
		}
	}

	@ApiOperation(value = "Get a biller account from a nickname or email", notes = "It is necessary to provide the nickname or email in the request paramr. Ej: ?nickname=BruceRip")
	@GetMapping(value = AccountPaths.ACCOUNT_RECOVER_BY_NICKNAME, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseMessage recoverAccountByNickname(final @RequestParam("nickname") String nickname,
			final @RequestHeader("transactionId") long transactionId) {
		AccountVO account = null;
		// ------------------------------------------------------------------
		final Calendar startTime = Calendar.getInstance();
		boolean status = true;
		String description = AccountsConstants.SUCCESSFUL;
		// ------------------------------------------------------------------
		try {
			ThreadContext.push(Long.toString(transactionId));
			LOGGER.info("Looking for a account by Nickname or Email {}", nickname);
			account = accountService.findAccountByNickname(nickname);
			if (null == account) {
				account = accountService.findAccountByEmail(nickname);
				if (null == account) {
					LOGGER.info("BillerID not found");
					LOGGER.error("Accout with nickname '" + nickname + "' not found.");
					status = false;
					description = accountsDesc06;
					return buildResponseMessage(Integer.valueOf(accountsCode06), accountsMSG06, accountsDesc06);
				}
			}
			if (!AccountStatus.ACTIVE.equals(account.getAccountStatus())) {
				status = false;
				description = accountsDesc13;
				return buildResponseMessage(Integer.valueOf(accountsCode13), accountsMSG13, accountsDesc13);
			}
			LOGGER.info("Account {} was found ", nickname);
			return buildAccountResponse(account);
		} finally {
			LoggerTransaction.printTransactionalLog(instanceName, port, startTime, Calendar.getInstance(),
					transactionId, "ACCOUNTS_RECOVER", status, description);
			ThreadContext.clearStack();
		}
	}

	@ApiOperation(value = "Update a biller account", notes = "It is necessary to provide all parameters that need update")
	@PutMapping(value = AccountPaths.ACCOUNT_UPDATE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseMessage updateAccount(@RequestBody final RequestParams params,
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
				return buildResponseMessage(Integer.valueOf(accountsCode06), accountsMSG06, accountsDesc06);
			}
			LOGGER.info("{} account has status of {}", accountVO.getEmail(), accountVO.getAccountStatus());
			if (!AccountStatus.REGISTER.equals(accountVO.getAccountStatus()) && params.getPassword() != null && !accountVO.getPassword().equals(params.getPassword()) ) {
				status = false;
				description = accountsDesc07;
				return buildResponseMessage(Integer.valueOf(accountsCode07), accountsMSG07, accountsDesc07);
			}
			if (params.getNewPassword() != null && !params.getNewPassword().isEmpty()) {
				accountVO.setPassword(params.getNewPassword());
			}
			if (AccountStatus.REGISTER.equals(accountVO.getAccountStatus())) {
				accountVO.setAccountStatus(AccountStatus.ACTIVE);
			}	
			if (params.getAccountStatus() != null) {
				accountVO.setAccountStatus(params.getAccountStatus());
			}
			if (params.getNickname() != null && !params.getNickname().isEmpty()) {
				LOGGER.info("Changing nickname to {} to {}", accountVO.getNickname(), params.getNickname());
				String nickname = params.getNickname();
				if (null != accountService.findAccountByNickname(nickname)) {
					status = false;
					description = accountsDesc02;
					return buildResponseMessage(Integer.valueOf(accountsCode02), accountsMSG02, accountsDesc02);
				}
				accountVO.setNickname(nickname);
			}
			if (params.getEmail() != null && !params.getEmail().isEmpty()) {
				LOGGER.info("Changing email to {} to {}", accountVO.getEmail(), params.getEmail());
				String email = params.getEmail();
				if (null != accountService.findAccountByEmail(email)) {
					status = false;
					description = accountsDesc03;
					return buildResponseMessage(Integer.valueOf(accountsCode03), accountsMSG03, accountsDesc03);
				}
				accountVO.setEmail(params.getEmail());
			}
			accountVO = accountService.updateAccount(accountVO);
			if (null == accountVO) {
				LOGGER.error("There was an error while updating the account");
				status = false;
				description = accountsDesc666;
				return buildResponseMessage(Integer.valueOf(accountsCode666), accountsMSG666, accountsDesc666);
			}
			LOGGER.info("Accout was updated successfully");
			return new AccountVO(accountVO.getBillerID(), accountVO.getNickname(), accountVO.getEmail(),
					accountVO.getAccountStatus());
		} finally {
			LoggerTransaction.printTransactionalLog(instanceName, port, startTime, Calendar.getInstance(),
					transactionId, "ACCOUNTS_UPDATE", status, description);
			ThreadContext.clearStack();
		}
	}
	
	@ApiOperation(value = "Update a biller account", notes = "It is necessary to provide all parameters that need update")
	@RequestMapping(value = AccountPaths.ACCOUNT_UPDATE_ROLES, method= {RequestMethod.PUT, RequestMethod.DELETE}, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseMessage updateRolesAccount(final HttpServletRequest request, @RequestBody final UpdateRolesParams params, @RequestParam(required=false) final String activate,
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
				return buildResponseMessage(Integer.valueOf(accountsCode06), accountsMSG06, accountsDesc06);
			}						
			accountVO = accountService.updateRoles(params, "DELETE".equals(request.getMethod()));
			if (null == accountVO) {
				LOGGER.error("There was an error while updating the account");
				status = false;
				description = accountsDesc666;
				return buildResponseMessage(Integer.valueOf(accountsCode666), accountsMSG666, accountsDesc666);
			}
			LOGGER.info("Accout was updated successfully");
			return new AccountVO(accountVO.getBillerID(), accountVO.getNickname(), accountVO.getEmail(),accountVO.getAccountStatus(), accountVO.getRoles());
		} finally {
			LoggerTransaction.printTransactionalLog(instanceName, port, startTime, Calendar.getInstance(),
					transactionId, "ACCOUNTS_UPDATE", status, description);
			ThreadContext.clearStack();
		}
	}
	
	@ApiOperation(value = "Delete a biller account", notes = "It is necessary to provide all parameters included the password")
	@DeleteMapping(value = AccountPaths.ACCOUNT_DELETE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseMessage deleteAccount(@RequestBody final RequestParams params,
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
				return buildResponseMessage(Integer.valueOf(accountsCode06), accountsMSG06, accountsDesc06);
			}
			if (!accountVO.getPassword().equals(params.getPassword())) {
				LOGGER.error("Password not match, we can´t delete account");
				status = false;
				description = accountsDesc07;
				return buildResponseMessage(Integer.valueOf(accountsCode07), accountsMSG07, accountsDesc07);
			}
			LOGGER.info("Accout was deleted successfully");
			accountService.deleteAccount(accountVO.getEmail());
			accountManagerService.removeAccountManager(accountVO.getBillerID());
			return buildResponseMessage(Integer.valueOf(accountsCodeSuccess), accountsMSGSuccess, accountsDescSuccess);
		} finally {
			LoggerTransaction.printTransactionalLog(instanceName, port, startTime, Calendar.getInstance(),
					transactionId, "ACCOUNTS_DELETE", status, description);
			ThreadContext.clearStack();
		}
	}
	
	@ApiOperation(value = "Activate a biller account", notes = "It is necessary to provide activate param")
	@PostMapping(value = AccountPaths.ACCOUNT_ACTIVATE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseMessage activateAccount(@RequestBody Map<String, String> data, @RequestParam final String code,
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
				return buildResponseMessage(Integer.valueOf(accountsCode06), accountsMSG06, accountsDesc06);
			}			
			LOGGER.info("Trying to activate account");
			accountVO = accountService.findAccountToActivate(code);
			if(accountVO == null || !accountVO.getBillerID().equals(data.get("billerID")) || !accountVO.getTemporaryPassword().equals(data.get("temporaryPassword"))) {
				LOGGER.error("Accout not found to activate");
				status = false;
				description = accountsDesc06;
				return buildResponseMessage(Integer.valueOf(accountsCode06), accountsMSG06, accountsDesc06);
			}			
			if (AccountStatus.REGISTER.equals(accountVO.getAccountStatus())) {
				accountVO.setAccountStatus(AccountStatus.ACTIVE);
			}					
			accountVO = accountService.updateAccount(accountVO);
			if (null == accountVO) {
				LOGGER.error("There was an error while updating the account");
				status = false;
				description = accountsDesc666;
				return buildResponseMessage(Integer.valueOf(accountsCode666), accountsMSG666, accountsDesc666);
			}
			LOGGER.info("Accout was updated successfully");
			return new AccountVO(accountVO.getBillerID(), accountVO.getNickname(), accountVO.getEmail(),
					accountVO.getAccountStatus());
		} finally {
			LoggerTransaction.printTransactionalLog(instanceName, port, startTime, Calendar.getInstance(),
					transactionId, "ACCOUNTS_ACTIVATE", status, description);
			ThreadContext.clearStack();
		}
	}

	private String validateAuthenticationHeader(final String authentication) throws AccessDeniedException {
		LOGGER.info("Validating authentication header [{}]", authentication);
		if (!isHttpBasicAuthEnabled) {
			ClientAuthenticationVO client = clientAuthService.findClientByClientId(authentication);
			if (client == null) {
				throw new AccessDeniedException("Access is denied. Authentication is null or empty");
			}
			return client.getClientId();
		} else {
			try {
				if (authentication == null || authentication.isEmpty()) {
					throw new AccessDeniedException("Access is denied. Authentication is null or empty");
				}
				final String encUserPassword = authentication.replaceFirst("Basic" + " ", "");
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
		}
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
