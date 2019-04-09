/**
 * 
 */
package mx.budgie.billers.accounts.controller;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import mx.budgie.billers.accounts.constants.AccountPaths;
import mx.budgie.billers.accounts.constants.AccountsConstants;
import mx.budgie.billers.accounts.loggers.LoggerTransaction;
import mx.budgie.billers.accounts.response.ResponseMessage;
import mx.budgie.billers.accounts.service.ClientAuthService;
import mx.budgie.billers.accounts.vo.ClientAuthVO;
import mx.budgie.billers.accounts.vo.TokensResponse;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 27, 2017
 */
@RestController
@RequestMapping(value = AccountPaths.CLIENT_BASED_PATH)
@RefreshScope
@Api(value = AccountPaths.CLIENT_BASED_PATH)
public class ClientAuthenticationController {

	private static final Logger LOGGER = LogManager.getLogger(ClientAuthenticationController.class);	
	@Autowired
	@Qualifier(value = AccountsConstants.SERVICE_OAUTH_CLIENT_DETAIL)
	private ClientAuthService oauthClientAuthService;
	
	@Value(AccountsConstants.ACCOUNTS_SERVER_PORT)
	private String port;
	@Value(AccountsConstants.ACCOUNTS_INSTANCE_NAME)
	private String instanceName;
	@Value(AccountsConstants.ACCOUNTS_CODE_03)
	private String accountsCode03;
	@Value(AccountsConstants.ACCOUNTS_MSG_CODE_03)
	private String accountsMSG03;
	@Value(AccountsConstants.ACCOUNTS_DES_CODE_03)
	private String accountsDesc03;
	@Value(AccountsConstants.ACCOUNTS_CODE_04)
	private String accountsCode04;
	@Value(AccountsConstants.ACCOUNTS_MSG_CODE_04)
	private String accountsMSG04;
	@Value(AccountsConstants.ACCOUNTS_DES_CODE_04)
	private String accountsDesc04;
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
	
	@ApiOperation(value = "Create authentication tokens for a client that will consume API", notes = "It must be present in all transactions")
	@PostMapping(value= AccountPaths.CLIENT_CREATE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseMessage createClient(@RequestParam("clientName") String clientName, final @RequestHeader("transactionId") long transactionId){
		//------------------------------------------------------
		Calendar startTime = Calendar.getInstance();
		boolean flag = true;
		String message = AccountsConstants.SUCCESSFUL;
		//------------------------------------------------------
		try{
			ThreadContext.push(Long.toString(transactionId));
			LOGGER.info("Creating client '{}' ", clientName);
			TokensResponse tokenResonse = null;			
			tokenResonse = oauthClientAuthService.saveClient(clientName);
			if(null != tokenResonse){
				LOGGER.info("Client was created successfully for '{}'", clientName);
				return tokenResonse;
			}
			return new ResponseMessage(Integer.valueOf(accountsCode666), accountsMSG666);
		}finally{
			LoggerTransaction.printTransactionalLog(instanceName, port, startTime, Calendar.getInstance(), transactionId, "CLIENT_CREATE", flag, message);
			ThreadContext.clearStack();
		}		
	}
	
	@ApiOperation(value = "Get tokens of a client", notes = "it is necessary to provide the client name")
	@GetMapping(value=AccountPaths.CLIENT_RECOVER_BY_NAME, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseMessage recoverClient(final @PathVariable String clientId, final @RequestHeader("transactionId") long transactionId){
		//------------------------------------------------------
		Calendar startTime = Calendar.getInstance();
		boolean flag = true;
		String message = AccountsConstants.SUCCESSFUL;
		//------------------------------------------------------
		try{			
			ThreadContext.push(Long.toString(transactionId));									
			LOGGER.info("Looking for client with Name '{}'", clientId);
			ClientAuthVO client = oauthClientAuthService.findClientByClientId(clientId);
			if(null != client){
				LOGGER.info("Client was found with email '{}'", client.getClientEmail());
				return client;
			}
		return new ResponseMessage(Integer.valueOf(accountsCode04), accountsMSG04);
		}finally{
			LoggerTransaction.printTransactionalLog(instanceName, port, startTime, Calendar.getInstance(), transactionId, "CLIENT_RECOVER", flag, message);
			ThreadContext.clearStack();
		}		
	}
	
	@ApiOperation(value = "Delete tokens of a client", notes = "it is necessary to provide the client name")
	@DeleteMapping(value=AccountPaths.CLIENT_DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseMessage deleteClient(final @PathVariable String clientName, final @RequestHeader("transactionId") long transactionId){
		//------------------------------------------------------
		Calendar startTime = Calendar.getInstance();
		boolean flag = true;
		String message = AccountsConstants.SUCCESSFUL;
		//------------------------------------------------------
		try{			
			ThreadContext.push(Long.toString(transactionId));											
			LOGGER.info("Removing client '{}'", clientName);				
			if(oauthClientAuthService.deleteClientByName(clientName)){
				LOGGER.info("Client '{}' was removed successfully", clientName);
				return new ResponseMessage(Integer.valueOf(accountsCodeSuccess), accountsMSGSuccess);
			}			
		return new ResponseMessage(Integer.valueOf(accountsCode04),accountsMSG04);
		}finally{
			LoggerTransaction.printTransactionalLog(instanceName, port, startTime, Calendar.getInstance(), transactionId, "CLIENT_DELETE", flag, message);
			ThreadContext.clearStack();
		}		
	}
	
	@ApiOperation(value = "Update tokens of a client", notes = "it is necessary to provide the client information")
	@PutMapping(value= AccountPaths.CLIENT_UPDATE, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseMessage updateClient(final @RequestBody ClientAuthVO client, final @RequestHeader("transactionId") long transactionId){
		//------------------------------------------------------
		Calendar startTime = Calendar.getInstance();
		boolean flag = true;
		String message = AccountsConstants.SUCCESSFUL;
		//------------------------------------------------------
		try{			
			ThreadContext.push(Long.toString(transactionId));			
			LOGGER.info("Updating client '{}' ", client.getClientId());
//			ClientAuthVO clientVO = clientAuthService.updateClient(client);
//			if(null != clientVO){
//				return clientVO;
//			}
			return new ResponseMessage(Integer.valueOf(accountsCode666), accountsMSG666);
		}finally{
			LoggerTransaction.printTransactionalLog(instanceName, port, startTime, Calendar.getInstance(), transactionId, "CLIENT_UPDATE", flag, message);
			ThreadContext.clearStack();
		}		
	}
}
