/**
 * 
 */
package mx.budgie.billers.accounts.vo;

import java.io.Serializable;
import java.util.Set;

import mx.budgie.billers.accounts.mongo.documents.AccountStatus;

/**
 * @author bruno.rivera
 *
 */
public class AccountRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nickname;
	private String email;
	private String password;
	private String repassword;
	private Set<String> roles;
	private GeolocalizationVO registerLocation;
	private String registrationDevice;
	private String phoneNumber;
	private AccountStatus status;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	public GeolocalizationVO getRegisterLocation() {
		return registerLocation;
	}
	public void setRegisterLocation(GeolocalizationVO registerLocation) {
		this.registerLocation = registerLocation;
	}
	
	public String getRegistrationDevice() {
		return registrationDevice;
	}
	public void setRegistrationDevice(String registrationDevice) {
		this.registrationDevice = registrationDevice;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	
	public AccountStatus getStatus() {
		return status;
	}
	public void setStatus(AccountStatus accountStatus) {
		this.status = accountStatus;
	}
	
	@Override
	public String toString() {
		return "AccountRequest [nickname=" + nickname + ", email=" + email + ", roles=" + roles + ", registerLocation="
				+ registerLocation + ", registrationDevice=" + registrationDevice + ", phoneNumber=" + phoneNumber
				+ ", accountStatus=" + status + "]";
	}
	
}
