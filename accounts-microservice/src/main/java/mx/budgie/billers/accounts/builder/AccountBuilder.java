/**
 * 
 */
package mx.budgie.billers.accounts.builder;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import mx.budgie.billers.accounts.constants.AccountsConstants;
import mx.budgie.billers.accounts.mongo.documents.AccountAuthorizationDocument;
import mx.budgie.billers.accounts.mongo.documents.AccountStatus;
import mx.budgie.billers.accounts.mongo.documents.GeolocalizationDocument;
import mx.budgie.billers.accounts.mongo.documents.TokenAuthenticationDocument;
import mx.budgie.billers.accounts.mongo.utils.AESCrypt;
import mx.budgie.billers.accounts.mongo.utils.DigestAlgorithms;
import mx.budgie.billers.accounts.vo.AccountRequest;
import mx.budgie.billers.accounts.vo.AccountVO;
import mx.budgie.billers.accounts.vo.GeolocalizationVO;
import mx.budgie.billers.accounts.vo.TokenAuthenticationVO;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 26, 2017
 */
@Component
public class AccountBuilder extends AbstractBuilder<AccountVO, AccountAuthorizationDocument>{

	@Value(AccountsConstants.ACCOUNTS_FORMAT_DATE)
	private String formatDate;
	@Value(AccountsConstants.ACCOUNTS_KEY_DEFAULT)
	private String keyDefault;
	
	private AccountAuthorizationDocument billerAccount;	
	
	@Override
	public AccountAuthorizationDocument createDocument() {
		billerAccount = new AccountAuthorizationDocument();
		return billerAccount;
	}
	
	@Override
	public AccountAuthorizationDocument buildDocumentFromSource(final AccountVO source) {
		createDocument();				
		billerAccount.setId(source.getId());
		billerAccount.setBillerID(source.getBillerID());
		billerAccount.setEmail(source.getEmail());
		billerAccount.setNickname(source.getNickname());
		billerAccount.setPassword(AESCrypt.buildPassword(source.getPassword()));
		billerAccount.setPhoneNumber(source.getPhoneNumber());
		billerAccount.setRegistrationDevice(source.getRegistrationDevice());		
		billerAccount.setAccountStatus(source.getAccountStatus());
		billerAccount.setLastAccess(Date.from(Instant.now()));
		billerAccount.setRegisterDate(source.getRegisterDate());				
		billerAccount.setRoles(source.getRoles());
		billerAccount.setPackageExpirationDate(source.getExpirationPackageDate());
		billerAccount.setPurchasedPackage(source.getPurchasedPackage());
		billerAccount.setTotalBills(source.getTotalBills());
		billerAccount.setTotalFreeBills(source.getTotalFreeBills());
		billerAccount.setTotalRegisteredCustomer(source.getTotalRegisteredCustomer());
		billerAccount.setTotalActiveSession(source.getTotalActiveSession());
		billerAccount.setAccountStatus(source.getAccountStatus());
		billerAccount.setDatePurchasedPackage(source.getPurchasedPackageDate());
		if(source.getRoles() != null) {
			billerAccount.setRoles(source.getRoles());
		}
		if(source.getRegisterLocation() != null){
			billerAccount.setRegisterLocation(new GeolocalizationDocument(source.getRegisterLocation().getLatitude(), source.getRegisterLocation().getLongitude()));			
		}										
		return billerAccount;
	}
	
	@Override
	public AccountVO buildSourceFromDocument(AccountAuthorizationDocument document) {
		AccountVO account = new AccountVO();
			account.setId(document.getId());
			account.setBillerID(document.getBillerID());
			account.setEmail(document.getEmail());			
			account.setNickname(document.getNickname());
			account.setPassword(document.getPassword());
			account.setPhoneNumber(document.getPhoneNumber());			
			if(document.getRegisterLocation() != null) {
				account.setRegisterLocation(new GeolocalizationVO(document.getRegisterLocation().getLatitude(), document.getRegisterLocation().getLongitude()));				
			}
			account.setRoles(document.getRoles());
			account.setRegistrationDevice(document.getRegistrationDevice());
			account.setAccountStatus(document.getAccountStatus());
			account.setPurchasedPackage(document.getPurchasedPackage());
			account.setTotalBills(document.getTotalBills());
			account.setTotalFreeBills(document.getTotalFreeBills());
			account.setTotalRegisteredCustomer(document.getTotalRegisteredCustomer());
			account.setTotalActiveSession(document.getTotalActiveSession());
			account.setExpirationPackageDate(document.getPackageExpirationDate());	
			account.setPurchasedPackageDate(document.getDatePurchasedPackage());
			account.setActivationCode(document.getActivationCode());
			account.setTemporaryPassword(document.getPassword());
			account.setRoles(document.getRoles());
		return account;
	}
	
	public AccountAuthorizationDocument createDocumentFromSource(final AccountRequest source, final String clientAuthentication) {
		createDocument();
		billerAccount.setBillerID(AESCrypt.buildHashValue(AESCrypt.getUniqueID(null), DigestAlgorithms.SHA_256));
		billerAccount.setEmail(source.getEmail());
		billerAccount.setNickname(source.getNickname());
		billerAccount.setPassword(source.getPassword() != null ? AESCrypt.buildPassword(source.getPassword()) : null);
		billerAccount.setPhoneNumber(source.getPhoneNumber());
		billerAccount.setRegistrationDevice(source.getRegistrationDevice());
		billerAccount.setAccountStatus(AccountStatus.REGISTER);
		billerAccount.setLastAccess(Date.from(Instant.now()));
		billerAccount.setRegisterDate(Date.from(Instant.now()));
		billerAccount.setTokenClientIdUsed(clientAuthentication.replaceFirst("bearer" + " ", ""));
		Set<String> roles = new HashSet<>();
		roles.add("USER");
		billerAccount.setRoles(roles);
		if(source.getRegisterLocation() != null){
			billerAccount.setRegisterLocation(new GeolocalizationDocument(source.getRegisterLocation().getLatitude(), source.getRegisterLocation().getLongitude()));
		}
		return billerAccount;			
	}
	
	public TokenAuthenticationVO createTokenAuthenticationVO(TokenAuthenticationDocument document) {
		if(document != null) {
			TokenAuthenticationVO vo = new TokenAuthenticationVO();
			vo.setAdditionalInformation(document.getAdditionalInformation());
			vo.setExpirationDateAuth(document.getExpirationDateAuth());
			vo.setExpiresIn(document.getExpiresIn());
			vo.setRefreshTokenAuth(document.getRefreshTokenAuth());
			vo.setScopes(document.getScopes());
			vo.setTokenAuth(document.getTokenAuth());
			vo.setTokenTypeAuth(document.getTokenTypeAuth());
			return vo;
		}
		return null;
	}
	
	public TokenAuthenticationDocument createTokenAuthenticationDocument(TokenAuthenticationVO vo) {
		if(vo != null) {
			TokenAuthenticationDocument document = new TokenAuthenticationDocument();
			document.setAdditionalInformation(vo.getAdditionalInformation());
			document.setExpirationDateAuth(vo.getExpirationDateAuth());
			document.setExpiresIn(vo.getExpiresIn());
			document.setRefreshTokenAuth(vo.getRefreshTokenAuth());
			document.setScopes(vo.getScopes());
			document.setTokenAuth(vo.getTokenAuth());
			document.setTokenTypeAuth(vo.getTokenTypeAuth());
			return document;
		}
		return null;
	}
		
}
