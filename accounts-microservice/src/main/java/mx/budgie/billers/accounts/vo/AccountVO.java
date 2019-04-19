/**
 * 
 */
package mx.budgie.billers.accounts.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import mx.budgie.billers.accounts.mongo.documents.AccountStatus;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 25, 2017
 */
@JsonInclude(Include.NON_NULL)
public class AccountVO extends AccountRequestVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String billerID;
	private Set<String> roles;
	private String accessToken;
	private AccountStatus accountStatus;
	private Date registerDate;
	private Date lastAccess;	
	private String view;
	private String purchasedPackage;	
	private int totalBills;
	private int totalFreeBills;
	private int totalRegisteredCustomer;
	private int totalActiveSession;
	private Date expirationPackageDate;
	private Date purchasedPackageDate;
	private String activationCode;
	private String temporaryPassword;

	public AccountVO() {

	}	

	public AccountVO(String billerID, String accessToken, String nickname, String email, String password, String view, String purchasedPackage
			, int totalBills,int totalFreeBills, int totalRegisteredCustomer, int totalActiveSession, Date expirationPackageDate, Date purchasedPackageDate) {
		this.billerID = billerID;
		this.accessToken = accessToken;		
		this.setNickname(nickname);
		this.setEmail(email);
		this.view = view;
		this.purchasedPackage = purchasedPackage;
		this.totalBills = totalBills;
		this.totalFreeBills = totalFreeBills;
		this.totalRegisteredCustomer = totalRegisteredCustomer;
		this.totalActiveSession = totalActiveSession;
		this.expirationPackageDate = expirationPackageDate;
		this.purchasedPackageDate = purchasedPackageDate;
		this.temporaryPassword = password;
	}

	public AccountVO(String billerID, String nickname, String email) {
		super();
		this.billerID = billerID;
		this.setNickname(nickname);
		this.setEmail(email);
	}

	public AccountVO(String billerID, String nickname, String email, AccountStatus accountStatus) {
		super();
		this.billerID = billerID;
		this.setNickname(nickname);
		this.setEmail(email);
		this.setAccountStatus(accountStatus);
	}
	
	public AccountVO(String billerID, String nickname, String email, AccountStatus accountStatus, Set<String> roles) {
		super();
		this.billerID = billerID;
		this.setNickname(nickname);
		this.setEmail(email);
		this.setAccountStatus(accountStatus);
		this.roles = roles;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getPurchasedPackageDate() {
		return purchasedPackageDate;
	}

	public void setPurchasedPackageDate(Date packagePurchasedDate) {
		this.purchasedPackageDate = packagePurchasedDate;
	}
	public String getBillerID() {
		return billerID;
	}

	public void setBillerID(String billerID) {
		this.billerID = billerID;
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

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}

	public String getView() {
		return view;
	}

	public void setView(String tokenView) {
		this.view = tokenView;
	}

	public String getPurchasedPackage() {
		return purchasedPackage;
	}

	public void setPurchasedPackage(String purchasedPackage) {
		this.purchasedPackage = purchasedPackage;
	}

	public int getTotalBills() {
		return totalBills;
	}

	public void setTotalBills(int totalBills) {
		this.totalBills = totalBills;
	}

	public int getTotalActiveSession() {
		return totalActiveSession;
	}

	public void setTotalActiveSession(int totalActiveSession) {
		this.totalActiveSession = totalActiveSession;
	}

	public Date getExpirationPackageDate() {
		return expirationPackageDate;
	}

	public void setExpirationPackageDate(Date expirationPackageDate) {
		this.expirationPackageDate = expirationPackageDate;
	}

	public int getTotalFreeBills() {
		return totalFreeBills;
	}

	public void setTotalFreeBills(int totalFreeBills) {
		this.totalFreeBills = totalFreeBills;
	}

	public int getTotalRegisteredCustomer() {
		return totalRegisteredCustomer;
	}

	public void setTotalRegisteredCustomer(int totalRegisteredCustomer) {
		this.totalRegisteredCustomer = totalRegisteredCustomer;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activateAccount) {
		this.activationCode = activateAccount;
	}

	public String getTemporaryPassword() {
		return temporaryPassword;
	}

	public void setTemporaryPassword(String temporaryPassword) {
		this.temporaryPassword = temporaryPassword;
	}

}
