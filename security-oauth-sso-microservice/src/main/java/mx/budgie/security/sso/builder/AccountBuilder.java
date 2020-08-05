/**
 * 
 */
package mx.budgie.security.sso.builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import mx.budgie.billers.accounts.mongo.documents.AccountAuthorizationDocument;
import mx.budgie.security.sso.vo.AccountVO;
import mx.budgie.security.sso.vo.GeolocalizationVO;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 26, 2017
 */
@Component
public class AccountBuilder extends AbstractBuilder<AccountVO, AccountAuthorizationDocument>{

	private static final Logger LOGGER = LogManager.getLogger(AccountBuilder.class);
	
	@Override
	public AccountAuthorizationDocument createObject() {		
		return null;
	}
	
	@Override
	public AccountVO buildSourceFromDocument(AccountAuthorizationDocument document) {
		LOGGER.info("Building object");
		AccountVO account = new AccountVO();
		account.setEmail(document.getEmail());
		account.setNickname(document.getNickname());
		account.setPassword(document.getPassword());
		account.setPhoneNumber(document.getPhoneNumber());
		if(document.getRegisterLocation() != null) {
			account.setRegisterLocation(new GeolocalizationVO(document.getRegisterLocation().getLatitude(),document.getRegisterLocation().getLongitude()));			
		}
		account.setRoles(document.getRoles());
		account.setRegistrationDevice(document.getRegistrationDevice());
		account.setAccountStatus(document.getAccountStatus());
//		account.setAccessToken(document.getAccessToken());
//		if(document.getTokensAuthentication() != null){
//			account.setClientAuthentication((String)document.getTokensAuthentication().getAdditionalInformation().get("clientAuthentication"));
//		}
		return account;
	}
}
