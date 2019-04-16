/**
 * 
 */
package mx.budgie.billers.accounts.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import mx.budgie.billers.accounts.response.ResponseMessage;

/**
 * @author bruno.rivera
 *
 */
@JsonInclude(Include.NON_NULL)
public class AccountRequestVO extends ResponseMessage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotEmpty
	@NotNull(message = "Nickname cannot be null")
	private String nickname;
	@NotEmpty
	@NotNull(message = "Email cannot be null")
	@Email(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$", message = "Email format not allowed")
	private String email;
//	@NotEmpty
//	@NotNull(message = "Password cannot be null")
	private String password;
	private String phoneNumber;
	@JsonIgnore
	//	@NotNull(message = "RegisterLocation cannot be null")
	private GeolocalizationVO registerLocation = new GeolocalizationVO(); // Required when account is create
	@JsonIgnore
//	@NotEmpty
//	@NotNull(message = "Device cannot be null")
	private String registrationDevice = "SecurityApp"; // Required when account is create	
	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @return the registerLocation
	 */
	public GeolocalizationVO getRegisterLocation() {
		return registerLocation;
	}
	/**
	 * @param registerLocation the registerLocation to set
	 */
	public void setRegisterLocation(GeolocalizationVO registerLocation) {
		this.registerLocation = registerLocation;
	}
	/**
	 * @return the registrationDevice
	 */
	public String getRegistrationDevice() {
		return registrationDevice;
	}
	/**
	 * @param registrationDevice the registrationDevice to set
	 */
	public void setRegistrationDevice(String registrationDevice) {
		this.registrationDevice = registrationDevice;
	}	
	
}
