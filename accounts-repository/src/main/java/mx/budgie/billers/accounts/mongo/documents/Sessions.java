package mx.budgie.billers.accounts.mongo.documents;

import java.io.Serializable;
import java.util.Date;

public class Sessions implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idSession;
	private Boolean activeSession;
    private Date loginSessionDate;
    private Date logoutSessionDate;
    
    public Sessions() {
    		
    }
    
    public Sessions(String idSession, Boolean activeSessions, Date loginSessionDate) {
		super();
		this.idSession = idSession;
		this.activeSession = activeSessions;
		this.loginSessionDate = loginSessionDate;
	}
	public Date getLoginSessionDate() {
		return loginSessionDate;
	}
	public void setLoginSessionDate(Date loginSessionDate) {
		this.loginSessionDate = loginSessionDate;
	}
	public Date getLogoutSessionDate() {
		return logoutSessionDate;
	}
	public void setLogoutSessionDate(Date logoutSessionDate) {
		this.logoutSessionDate = logoutSessionDate;
	}
	public Boolean getActiveSessions() {
		return activeSession;
	}
	public void setActiveSessions(Boolean activeSessions) {
		this.activeSession = activeSessions;
	}
	public String getIdSession() {
		return idSession;
	}
	public void setIdSession(String idSession) {
		this.idSession = idSession;
	}

	
} 
