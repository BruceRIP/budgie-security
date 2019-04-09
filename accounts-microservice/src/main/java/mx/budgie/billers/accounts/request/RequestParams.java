/**
 * 
 */
package mx.budgie.billers.accounts.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import mx.budgie.billers.accounts.mongo.documents.AccountStatus;

/**
 * @author brucewayne
 *
 */
public class RequestParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull(message="BillerId can´t be null")
	private String billerID;	
	private String nickname ;
	private String email;
	private String password;
	private String newPassword;
	private AccountStatus accountStatus;
	
	
	
	public AccountStatus getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getBillerID() {
		return billerID;
	}
	public void setBillerID(String billerID) {
		this.billerID = billerID;
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
