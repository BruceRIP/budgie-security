/**
 * 
 */
package mx.budgie.billers.accounts.mongo.documents;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author brucewayne
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdministratorAccount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String purchasedPackage;
	private Date datePurchasedPackage;
	private Integer totalFreeBillsEmitted;
	private Integer totalBillsEmitted;    
	private Integer totalActiveSessions;
	private Integer totalRegisteredCustomers;
    private Date packageExpirationDate;
    private String sessionID;
    
	
	public Date getDatePurchasedPackage() {
		return datePurchasedPackage;
	}
	public void setDatePurchasedPackage(Date datePurchasedPackage) {
		this.datePurchasedPackage = datePurchasedPackage;
	}
	public String getPurchasedPackage() {
		return purchasedPackage;
	}
	public void setPurchasedPackage(String purchasedPackage) {
		this.purchasedPackage = purchasedPackage;
	}
	public Integer getTotalFreeBillsEmitted() {
		return totalFreeBillsEmitted;
	}
	public void setTotalFreeBillsEmitted(Integer totalFreeBillsEmitted) {
		this.totalFreeBillsEmitted = totalFreeBillsEmitted;
	}
	public Integer getTotalBillsEmitted() {
		return totalBillsEmitted;
	}
	public void setTotalBillsEmitted(Integer totalBillsEmitted) {
		this.totalBillsEmitted = totalBillsEmitted;
	}
	public Integer getTotalActiveSessions() {
		return totalActiveSessions;
	}
	public void setTotalActiveSessions(Integer totalActiveSessions) {
		this.totalActiveSessions = totalActiveSessions;
	}
	public Integer getTotalRegisteredCustomers() {
		return totalRegisteredCustomers;
	}
	public void setTotalRegisteredCustomers(Integer totalRegisteredCustomers) {
		this.totalRegisteredCustomers = totalRegisteredCustomers;
	}
	public Date getPackageExpirationDate() {
		return packageExpirationDate;
	}
	public void setPackageExpirationDate(Date packageExpirationDate) {
		this.packageExpirationDate = packageExpirationDate;
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
    
    
}
