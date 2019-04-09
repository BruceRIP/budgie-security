/**
 * 
 */
package mx.budgie.billers.accounts.mongo.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author bruce
 *
 */
@Document(collection = "AccountPackages")
public class AccountPackagesDocument {

	@Id
	private Integer packageId;
	private String packageName;
	private int totalBills;
	private int totalActiveSession;
	private int expirationDate;

	public AccountPackagesDocument() {
		super();
	}

	public AccountPackagesDocument(Integer packageId, String packageName, int totalBills, int totalActiveSession,
			int expirationDate) {
		super();
		this.packageId = packageId;
		this.packageName = packageName;
		this.totalBills = totalBills;
		this.totalActiveSession = totalActiveSession;
		this.expirationDate = expirationDate;
	}

	public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
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

	public int getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(int expirationDate) {
		this.expirationDate = expirationDate;
	}

}
