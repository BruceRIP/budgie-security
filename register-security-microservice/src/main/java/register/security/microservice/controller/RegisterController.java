/**
 * 
 */
package register.security.microservice.controller;

import static register.security.microservice.constants.RegisterSecurityConstants.PATH_BASE;
import static register.security.microservice.constants.RegisterSecurityConstants.PATH_CREATE_ACCOUNT;
import static register.security.microservice.constants.RegisterSecurityConstants.PATH_CREATE_ADMIN_CLIENT;
import static register.security.microservice.constants.RegisterSecurityConstants.PATH_CREATE_CLIENT;
import static register.security.microservice.constants.RegisterSecurityConstants.PATH_RESET_ACCOUNT;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import mx.budgie.commons.client.EndpointClient;
import mx.budgie.commons.email.EmailRequest;
import mx.budgie.commons.email.SendEmail;
import mx.budgie.commons.exception.EndpointException;
import mx.budgie.commons.utils.EmailTemplateType;
import register.security.microservice.loggers.LoggerTransaction;
import register.security.microservice.model.AccessTokenResponse;
import register.security.microservice.model.AccountReponse;
import register.security.microservice.model.AccountRequest;
import register.security.microservice.model.ClientResponse;
import register.security.microservice.model.ResponseMessage;

/**
 * @author bruno.rivera
 *
 */
@RequestMapping(value = PATH_BASE)
@RestController
@Log4j2
@RefreshScope
@Api(value = PATH_BASE)
public class RegisterController {
		
	@Autowired
	private SendEmail sendEmail;
	@Value("${server.port}")
	private String port;
	@Value("${accounts.biller.id.recover}")
	private String urlGetAccount;
	@Value("${accounts.account.create}")
	private String urlAccountCreate;
	@Value("${accounts.account.password.reset}")
	private String urlAccountResetPassword;
	@Value("${accounts.client.create}")
	private String urlCreateClient;
	@Value("${accounts.client.delete}")
	private String urlDeleteClient;
	@Value("${accounts.client.token.type}")
	private String refreshToken;
	@Value("${oauth.access.token.create}")
	private String urlCreateAccessToken;
	@Value("${reporter.send.email}")
	private String urlSendEmail;
	@Value("${reporter.send.email.from}")
	private String emailFrom;
	@Value("${reporter.send.email.subject}")
	private String emailSubject;
	@Value("${register.email.link.home}")
	private String linkHome;	
	@Value("${accounts.account.activate.url}")
	private String urlActivateAccount;		
	private static final String INSTANCE_NAME = "REGISTER-SECURITY";
	
	@ApiOperation(value = "Create account", notes = "Create an security account. It sends an email with access credentials")
	@PostMapping(path = PATH_CREATE_ACCOUNT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> createAccount(@RequestHeader("transactionId") final Long transactionId
										, @RequestHeader("Authorization") final String authorizationToken
										, @RequestBody final AccountRequest account){
		// ------------------------------------------------------------------
			final Calendar startTime = Calendar.getInstance();
			boolean status = true;
			String description = "SUCCESSFUL";
		// ------------------------------------------------------------------
		Map<String, String> headers = new HashMap<>();	
		headers.put("transactionId", String.valueOf(transactionId));
		headers.put("Content-Type", "application/json");		
		try {
			ThreadContext.push(Long.toString(transactionId));
			ResponseEntity<AccountReponse>	accountResponse = new EndpointClient(String.format("%s", urlAccountCreate))
													.putHeaders(headers)
													.putAuthorizationToken(authorizationToken)
													.requestBody(account)
													.callPOST(AccountReponse.class);
			if(!accountResponse.getStatusCode().equals(HttpStatus.CREATED)) {
				log.error("oAuth Server error: {}", accountResponse.getBody().getDescription());
				return new ResponseEntity<>(new ResponseMessage(accountResponse.getStatusCodeValue(), accountResponse.getBody().getDescription()), accountResponse.getStatusCode());
			}
			
			EmailRequest emailRequest = new EmailRequest();
			emailRequest.setHeaders(headers);
			emailRequest.setAuthorizationToken(authorizationToken);
			emailRequest.setFrom(emailFrom);
			emailRequest.setTo(accountResponse.getBody().getEmail());
			emailRequest.setSubject(emailSubject);
			emailRequest.setTemplateType(EmailTemplateType.ACTIVATE_ACCOUNT);
			Map<String, String> custom = new LinkedHashMap<>();
			custom.put("nickname", accountResponse.getBody().getNickname());			
			custom.put("link_home", linkHome);
			custom.put("link_activate_account", urlActivateAccount + "?code=" + accountResponse.getBody().getCode() + "&billerID=" + accountResponse.getBody().getBillerID());
			emailRequest.setCustom(custom);
			sendEmail.sendEmailRequest(urlSendEmail, emailRequest);			
			return new ResponseEntity<>(accountResponse.getBody(), HttpStatus.CREATED);
		} catch (EndpointException e) {
			log.error("Create account error {}", e);
			description = e.getMessage();
			status = false;
			return new ResponseEntity<>(new ResponseMessage(400, HttpStatus.BAD_REQUEST.getReasonPhrase()), HttpStatus.BAD_REQUEST);
		}finally {
			LoggerTransaction.printTransactionalLog(INSTANCE_NAME, port, startTime, Calendar.getInstance(), transactionId, "ACCOUNT_CREATE", status, description);
			ThreadContext.clearStack();
		}
	}
	
	@ApiOperation(value = "Reset password", notes = "Reset password when the user forgot or wants to change it. Send an email with activation code")
	@PostMapping(path = PATH_RESET_ACCOUNT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> resetPassowrd(@RequestHeader("transactionId") final Long transactionId
										, @RequestHeader("Authorization") final String authorizationToken
										, @RequestBody final AccountRequest accountRequest){
		// ------------------------------------------------------------------
		final Calendar startTime = Calendar.getInstance();
		boolean status = true;
		String description = "SUCCESSFUL";
		// ------------------------------------------------------------------
		Map<String, String> headers = new HashMap<>();
		headers.put("transactionId", String.valueOf(transactionId));	
		headers.put("Content-Type", "application/json");	
		try {
			ThreadContext.push(Long.toString(transactionId));
			ResponseEntity<AccountReponse> accountResponse = new EndpointClient(String.format("%s", urlAccountResetPassword))
																.putAuthorizationToken(authorizationToken)
																.putHeaders(headers)
																.requestBody(accountRequest)
																.callPOST(AccountReponse.class);
			if (!accountResponse.getStatusCode().equals(HttpStatus.OK)) {
				log.error("oAuth Server error: {}", accountResponse.getBody().getDescription());
				return new ResponseEntity<>(new ResponseMessage(accountResponse.getStatusCodeValue(), accountResponse.getBody().getDescription()), accountResponse.getStatusCode());
			}
			EmailRequest emailRequest = new EmailRequest();
			emailRequest.setHeaders(headers);
			emailRequest.setAuthorizationToken(authorizationToken);
			emailRequest.setFrom(emailFrom);
			emailRequest.setTo(accountResponse.getBody().getEmail());
			emailRequest.setSubject(emailSubject);
			emailRequest.setTemplateType(EmailTemplateType.ACTIVATE_ACCOUNT);
			Map<String, String> custom = new LinkedHashMap<>();
			custom.put("nickname", accountResponse.getBody().getNickname());			
			custom.put("link_home", linkHome);
			custom.put("link_activate_account", urlActivateAccount + "?code=" + accountResponse.getBody().getActivationCode() + "&billerID=" + accountResponse.getBody().getBillerID());
			emailRequest.setCustom(custom);
			sendEmail.sendEmailRequest(urlSendEmail, emailRequest);	
			return new ResponseEntity<>(accountResponse.getBody(), HttpStatus.OK);			
		} catch (EndpointException e) {
			log.error("Reset password error {}", e);
			description = e.getMessage();
			status = false;
			return new ResponseEntity<>(new ResponseMessage(400, e.getMessage()), e.getCode());
		} finally {
			LoggerTransaction.printTransactionalLog(INSTANCE_NAME, port, startTime, Calendar.getInstance(), transactionId, "ACCOUNT_RESET_PASSWORD", status, description);
			ThreadContext.clearStack();
		}
	}
	@ApiOperation(value = "Create administration client", notes = "Create client for administration purpose")
	@PostMapping(path=PATH_CREATE_ADMIN_CLIENT)
	public @ResponseBody ResponseEntity<?> createAdminClientCredentials(@RequestParam("applicationName") final String applicationName			
			, @RequestParam(required = false, name = "tokenType") String tokenType
			, @RequestHeader("transactionId") final Long transactionId
			, @RequestHeader("Authorization") final String authorization){
		// ------------------------------------------------------------------
			final Calendar startTime = Calendar.getInstance();
			boolean status = true;
			String description = "SUCCESSFUL";
		// ------------------------------------------------------------------
		if(authorization == null || authorization.isEmpty()) {
			log.error("Authorization header is missing");
			return new ResponseEntity<>(new ResponseMessage(400, HttpStatus.BAD_REQUEST.getReasonPhrase()), HttpStatus.BAD_REQUEST);
		}
		String clientId = null;
		Map<String, String> headers = new HashMap<>();
		headers.put("transactionId", String.valueOf(transactionId));
		headers.put("Content-Type", "application/json");
		try {
			ThreadContext.push(Long.toString(transactionId));
			ResponseEntity<ClientResponse> clientResponse = new EndpointClient(String.format("%s", urlCreateClient))
															.putHeaders(headers)
															.requestParameters(applicationName, tokenType)
															.callPOST(ClientResponse.class);
			if(!clientResponse.getStatusCode().equals(HttpStatus.OK)) {
				log.error("Accounts Create Client error: {}", clientResponse.getBody());
				return new ResponseEntity<>(new ResponseMessage(clientResponse.getStatusCodeValue(), clientResponse.getBody().toString()), clientResponse.getStatusCode());
			}
			clientId = clientResponse.getBody().getClientId();
			ResponseEntity<AccessTokenResponse> accessTokenResponse = new EndpointClient(String.format("%s", urlCreateAccessToken))
																			.putBasicAuthorization(clientResponse.getBody().getClientAccessToken())
																			.putHeaders(headers)
																			.requestParameters(clientResponse.getBody().getClientId(), clientResponse.getBody().getClientSecret())
																			.callPOST(AccessTokenResponse.class);
			if(!accessTokenResponse.getStatusCode().equals(HttpStatus.OK)) {
				log.error("oAuth Server error: {}", accessTokenResponse.getBody());
				return new ResponseEntity<>(new ResponseMessage(accessTokenResponse.getStatusCodeValue(), accessTokenResponse.getBody().toString()), accessTokenResponse.getStatusCode());
			}
			clientResponse.getBody().setAccessToken(accessTokenResponse.getBody().getAccessToken());
			clientResponse.getBody().setExpiresIn(accessTokenResponse.getBody().getExpiresIn());
			clientResponse.getBody().setScope(accessTokenResponse.getBody().getScope());
			clientResponse.getBody().setTokenType(accessTokenResponse.getBody().getTokenType());
			return new ResponseEntity<>(clientResponse.getBody(), HttpStatus.CREATED);
		} catch (EndpointException e) {
			log.error("Create administration client error {}", e);
			removeClient(clientId, headers);
			description = e.getMessage();
			status = false;
			return new ResponseEntity<>(new ResponseMessage(400, HttpStatus.BAD_REQUEST.getReasonPhrase()), HttpStatus.BAD_REQUEST);
		}finally {
			LoggerTransaction.printTransactionalLog(INSTANCE_NAME, port, startTime, Calendar.getInstance(),
					transactionId, "CLIENT_ADMIN_CREATE", status, description);
			ThreadContext.clearStack();
		}
	}
	
	/**
	 * Maneja el flujo de creacion de cliente, enviando correo con credenciales de accesso
	 * @param applicationName
	 * @param tokenType
	 * @param transactionId
	 * @return
	 */
	@ApiOperation(value = "Create clients", notes = "Get an account and create client with accountID. It sends an email with access credentials")
	@PostMapping(path=PATH_CREATE_CLIENT)
	public @ResponseBody ResponseEntity<?> createClientCredentials(@PathVariable final String billerID
			, @RequestParam("applicationName") final String applicationName			
			, @RequestParam(required = false, name = "tokenType") String tokenType
			, @RequestHeader("transactionId") final Long transactionId
			, @RequestHeader("Authorization") final String authorizationToken){
		// ------------------------------------------------------------------
		final Calendar startTime = Calendar.getInstance();
		boolean status = true;
		String description = "SUCCESSFUL";
		// ------------------------------------------------------------------
		String clientId = null;
		Map<String, String> headers = new HashMap<>();	
		headers.put("transactionId", String.valueOf(transactionId));
		headers.put("Content-Type", "application/json");
		try {
			ThreadContext.push(Long.toString(transactionId));
			ResponseEntity<AccountReponse> accountsResponse = new EndpointClient(String.format("%s", urlGetAccount))
																.putAuthorizationToken(authorizationToken)
																.putHeaders(headers)
																.requestParameters(billerID)
																.callPOST(AccountReponse.class);
			if(!accountsResponse.getStatusCode().equals(HttpStatus.OK)) {
				log.error("Accounts Get Account error: {}", accountsResponse.getBody());
				return new ResponseEntity<>(new ResponseMessage(accountsResponse.getStatusCodeValue(), accountsResponse.getBody().toString()), accountsResponse.getStatusCode());
			}
			
			ResponseEntity<ClientResponse> clientResponse = new EndpointClient(String.format("%s", urlCreateClient))
																.putHeaders(headers)
																.requestParameters(applicationName, tokenType)
																.callPOST(ClientResponse.class);
			if(!clientResponse.getStatusCode().equals(HttpStatus.OK)) {
				log.error("Accounts Create Client error: {}", clientResponse.getBody());
				throw new EndpointException(clientResponse.getBody().toString());
			}
			clientId = clientResponse.getBody().getClientId(); 
			
			ResponseEntity<AccessTokenResponse> accessTokenResponse = new EndpointClient(String.format("%s", urlCreateAccessToken))
										.putBasicAuthorization(clientResponse.getBody().getClientAccessToken())
										.requestParameters(clientResponse.getBody().getClientId(), clientResponse.getBody().getClientSecret())
										.callPOST(AccessTokenResponse.class);
			if(!accessTokenResponse.getStatusCode().equals(HttpStatus.OK)) {
				log.error("oAuth Server error: {}", accessTokenResponse.getBody());
				throw new EndpointException(accessTokenResponse.getBody().toString());
			}			

//			SendEmailRequest emailRequest = new SendEmailRequest();
//			emailRequest.setFrom(emailFrom);
//			emailRequest.setTo(accountsResponse.getBody().getEmail());
//			emailRequest.setSubject(emailSubject);
//			emailRequest.setTemplateType("CLIENT_CREDENTIALS");
//			Map<String, String> custom = new LinkedHashMap<>();
//			custom.put("nickname", accountsResponse.getBody().getNickname());
//			custom.put("email", accountsResponse.getBody().getEmail());
//			custom.put("link_home", linkHome);
//			custom.put("clientId", clientResponse.getBody().getClientId());
//			custom.put("clientSecret", clientResponse.getBody().getClientSecret());
//			custom.put("accessToken", null);
//			custom.put("token_type", null);
//			custom.put("expires_in", null);
//			custom.put("scope", null);
//			emailRequest.setCustom(custom);
//			clientId = null;
//			ResponseEntity<String> sendEmailResponse = new EndpointClient(String.format("%s", urlSendEmail))
//															.putHeaders(headers)
//															.putBearerAccessToken(accessTokenResponse.getBody().getAccessToken())
//															.requestBody(emailRequest)
//															.callPOST(String.class);
//			log.info("Email Response: ", sendEmailResponse.getBody());
			return new ResponseEntity<>(new ResponseMessage(201, HttpStatus.CREATED.getReasonPhrase()), HttpStatus.CREATED);
		} catch (EndpointException e) {
			log.error("Create client error {}", e);
			removeClient(clientId, headers);
			description = e.getMessage();
			status = false;
			return new ResponseEntity<>(new ResponseMessage(400, HttpStatus.BAD_REQUEST.getReasonPhrase()), HttpStatus.BAD_REQUEST);
		}finally {
			LoggerTransaction.printTransactionalLog(INSTANCE_NAME, port, startTime, Calendar.getInstance(),
					transactionId, "CLIENT_CREATE", status, description);
			ThreadContext.clearStack();
		}
	}		
	
	public void removeClient(final String clientId, final Map<String, String> headers) {
		if(clientId != null) {
			headers.remove("Content-Type");
			try {
				new EndpointClient(String.format("%s", urlDeleteClient))
						.putHeaders(headers)
						.requestParameters(clientId)
						.callDELETE(String.class);
			} catch (EndpointException e1) {
				log.warn(e1.getMessage());
			}
		}
	}
	
}
