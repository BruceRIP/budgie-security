/**
 * 
 */
package mx.budgie.billers.accounts.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import mx.budgie.billers.accounts.mongo.documents.AccountAdministratorVO;
import mx.budgie.billers.accounts.mongo.documents.AdministratorAccount;

/**
 * @author brucewayne
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountAdminRequestVO extends AdministratorAccount{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String billerID;	
    private boolean isLogin;
    private boolean isLogout;    
    private boolean allowActiveSession = true;
   	private boolean allowFreeBill = true;
   	private boolean allowNormalBill = true;
   	private boolean allowRegisterCustomer = true;
   	private boolean allowUseFunctionalities = true;
   	private boolean allowEmitBills = true;
    
   	public AccountAdminRequestVO() {
		super();
	}
   	
	public AccountAdminRequestVO(AccountAdministratorVO vo) {
		super();
		this.setPurchasedPackage(vo.getPurchasedPackage());
		this.setTotalFreeBillsEmitted(vo.getTotalFreeBillsEmitted());
		this.setTotalBillsEmitted(vo.getTotalBillsEmitted());
		this.setTotalActiveSessions(vo.getTotalActiveSessions());
		this.setTotalRegisteredCustomers(vo.getTotalRegisteredCustomers());
		this.setPackageExpirationDate(vo.getPackageExpirationDate());
		this.setSessionID(vo.getSessionID());
	}
	public String getBillerID() {
		return billerID;
	}
	public void setBillerID(String billerID) {
		this.billerID = billerID;
	}
	
	public boolean isLogin() {
		return isLogin;
	}
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	public boolean isLogout() {
		return isLogout;
	}
	public void setLogout(boolean isLogout) {
		this.isLogout = isLogout;
	}
	
	public boolean isAllowActiveSession() {
		return allowActiveSession;
	}
	public void setAllowActiveSession(boolean allowActiveSession) {
		this.allowActiveSession = allowActiveSession;
	}
	public boolean isAllowFreeBill() {
		return allowFreeBill;
	}
	public void setAllowFreeBill(boolean allowFreeBill) {
		this.allowFreeBill = allowFreeBill;
	}
	public boolean isAllowNormalBill() {
		return allowNormalBill;
	}
	public void setAllowNormalBill(boolean allowNormalBill) {
		this.allowNormalBill = allowNormalBill;
	}
	public boolean isAllowRegisterCustomer() {
		return allowRegisterCustomer;
	}
	public void setAllowRegisterCustomer(boolean allowRegisterCustomer) {
		this.allowRegisterCustomer = allowRegisterCustomer;
	}
	public boolean isAllowUseFunctionalities() {
		return allowUseFunctionalities;
	}
	public void setAllowUseFunctionalities(boolean allowUseFunctionalities) {
		this.allowUseFunctionalities = allowUseFunctionalities;
	}
	public boolean isAllowEmitBills() {
		return allowEmitBills;
	}
	public void setAllowEmitBills(boolean allowEmitBills) {
		this.allowEmitBills = allowEmitBills;
	}	
    
}
