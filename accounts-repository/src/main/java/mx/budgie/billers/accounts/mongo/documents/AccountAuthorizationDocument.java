package mx.budgie.billers.accounts.mongo.documents;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by brucewayne on 6/25/17.
 */
@Document(collection = "AccountsAuthorization")
public class AccountAuthorizationDocument implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    private String billerID;
    private String nickname;
    private String password;
    private String email;
    private String phoneNumber;
    private GeolocalizationDocument registerLocation;
    private Set<String> roles;
    private String registrationDevice;
    private String purchasedPackage;
    private boolean activeSession;
    private int totalBills;
    private int totalFreeBills;
    private int totalRegisteredCustomer;
    private int totalActiveSession;
    private Date packageExpirationDate;
    private AccountStatus accountStatus;
    private Date registerDate;
    private Date lastAccess;    
    private Date datePurchasedPackage;

    public AccountAuthorizationDocument() {
    }    
    
	public AccountAuthorizationDocument(String billerID, String nickname, String password, String email,
			String phoneNumber, GeolocalizationDocument registerLocation, Set<String> roles, String accessToken,
			String registrationDevice, String purchasedPackage, boolean activeSession, int totalBills,
			int totalActiveSession, AccountStatus accountStatus, Date registerDate, Date lastAccess,
			TokenAuthenticationDocument tokensAuthentication, Date datePurchasedPackage) {
		super();
		this.billerID = billerID;
		this.nickname = nickname;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.registerLocation = registerLocation;
		this.roles = roles;
		this.registrationDevice = registrationDevice;
		this.purchasedPackage = purchasedPackage;
		this.activeSession = activeSession;
		this.totalBills = totalBills;
		this.totalActiveSession = totalActiveSession;
		this.accountStatus = accountStatus;
		this.registerDate = registerDate;
		this.lastAccess = lastAccess;
		this.datePurchasedPackage = datePurchasedPackage;
	}

	public Date getDatePurchasedPackage() {
		return datePurchasedPackage;
	}

	public void setDatePurchasedPackage(Date datePurchasedPackage) {
		this.datePurchasedPackage = datePurchasedPackage;
	}

	public String getBillerID() {
        return billerID;
    }

    public void setBillerID(String id) {
        this.billerID = id;
    }

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

    public GeolocalizationDocument getRegisterLocation() {
		return registerLocation;
	}

	public void setRegisterLocation(GeolocalizationDocument registerLocation) {
		this.registerLocation = registerLocation;
	}

	public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getRegistrationDevice() {
        return registrationDevice;
    }

    public void setRegistrationDevice(String registrationDevice) {
        this.registrationDevice = registrationDevice;
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

	public String getPurchasedPackage() {
		return purchasedPackage;
	}

	public void setPurchasedPackage(String purchasedPackage) {
		this.purchasedPackage = purchasedPackage;
	}

	public boolean isActiveSession() {
		return activeSession;
	}

	public void setActiveSession(boolean activeSession) {
		this.activeSession = activeSession;
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

	public Date getPackageExpirationDate() {
		return packageExpirationDate;
	}

	public void setPackageExpirationDate(Date packageExpirationDate) {
		this.packageExpirationDate = packageExpirationDate;
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

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}	

}
