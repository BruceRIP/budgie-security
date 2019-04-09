/**
 * 
 */
package mx.budgie.security.vo;

import java.util.Set;

import mx.budgie.billers.accounts.mongo.documents.AccountStatus;
import mx.budgie.security.messages.ResponseMessage;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 25, 2017
 */
public class AccountVO extends ResponseMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nickname;
	private String password;
	private String email;
	private String phoneNumber;
	private GeolocalizationVO registerLocation;
	private Set<String> roles;
	private String accessToken;
	private String registrationDevice;
	private AccountStatus accountStatus;
	private String registerDate;
    private String lastAccess;
    private String clientAuthentication;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public GeolocalizationVO getRegisterLocation() {
		return registerLocation;
	}

	public void setRegisterLocation(GeolocalizationVO registerLocation) {
		this.registerLocation = registerLocation;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRegistrationDevice() {
		return registrationDevice;
	}

	public void setRegistrationDevice(String registrationDevice) {
		this.registrationDevice = registrationDevice;
	}	

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(String lastAccess) {
		this.lastAccess = lastAccess;
	}

	public String getClientAuthentication() {
		return clientAuthentication;
	}

	public void setClientAuthentication(String clientAuthentication) {
		this.clientAuthentication = clientAuthentication;
	}
	
}
