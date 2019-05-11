/**
 * 
 */
package mx.budgie.billers.accounts.request;

import java.io.Serializable;

import mx.budgie.billers.accounts.mongo.documents.AccountStatus;

/**
 * @author bruno.rivera
 *
 */
public class AccountUpdateParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String billerID;
	private String password;
	private String nickname;
	private AccountStatus status;
	public String getBillerID() {
		return billerID;
	}
	public void setBillerID(String billerID) {
		this.billerID = billerID;
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
	public AccountStatus getStatus() {
		return status;
	}
	public void setStatus(AccountStatus status) {
		this.status = status;
	}
	
	
}
