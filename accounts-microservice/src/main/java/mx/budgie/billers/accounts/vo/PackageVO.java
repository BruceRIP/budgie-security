/**
 * 
 */
package mx.budgie.billers.accounts.vo;

import java.io.Serializable;

/**
 * @author brucewayne
 *
 */
public class PackageVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idPackage;
	private String namePackage;
	private Integer totalFreeBills;
	private Integer totalBills;	
	private Integer totalActiveSessions;
	private Integer totalActiveDays;
	private Integer totalRegisteredCustomer;
	private boolean onSale;
	private String offerDescription;
	private boolean active;
	private Double price;
	private String templateBill;
	private String clientManagement;
	private String personalSupport;
	private String services;
	private String timeStorage;
	
	private String addendas;
	private String tutorials;
	private String scheduledDelivery;
	private String statisticsReports;
	
	public Integer getIdPackage() {
		return idPackage;
	}
	public void setIdPackage(Integer idPackage) {
		this.idPackage = idPackage;
	}
	public String getNamePackage() {
		return namePackage;
	}
	public void setNamePackage(String namePackage) {
		this.namePackage = namePackage;
	}
	public Integer getTotalFreeBills() {
		return totalFreeBills;
	}
	public void setTotalFreeBills(Integer totalFreeBills) {
		this.totalFreeBills = totalFreeBills;
	}
	public Integer getTotalBills() {
		return totalBills;
	}
	public void setTotalBills(Integer totalBills) {
		this.totalBills = totalBills;
	}
	public Integer getTotalActiveSessions() {
		return totalActiveSessions;
	}
	public void setTotalActiveSessions(Integer totalActiveSessions) {
		this.totalActiveSessions = totalActiveSessions;
	}
	public Integer getTotalActiveDays() {
		return totalActiveDays;
	}
	public void setTotalActiveDays(Integer totalActiveDays) {
		this.totalActiveDays = totalActiveDays;
	}
	public boolean isOnSale() {
		return onSale;
	}
	public void setOnSale(boolean onSale) {
		this.onSale = onSale;
	}
	
	public String getOfferDescription() {
		return offerDescription;
	}
	public void setOfferDescription(String offerDescription) {
		this.offerDescription = offerDescription;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getTemplateBill() {
		return templateBill;
	}
	public void setTemplateBill(String templateBill) {
		this.templateBill = templateBill;
	}
	public String getClientManagement() {
		return clientManagement;
	}
	public void setClientManagement(String clientManagement) {
		this.clientManagement = clientManagement;
	}
	public String getPersonalSupport() {
		return personalSupport;
	}
	public void setPersonalSupport(String personalSupport) {
		this.personalSupport = personalSupport;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getTimeStorage() {
		return timeStorage;
	}
	public void setTimeStorage(String timeStorage) {
		this.timeStorage = timeStorage;
	} 
	public String getAddendas() {
		return addendas;
	}
	public void setAddendas(String addendas) {
		this.addendas = addendas;
	}
	public String getTutorials() {
		return tutorials;
	}
	public void setTutorials(String tutorials) {
		this.tutorials = tutorials;
	}
	public String getScheduledDelivery() {
		return scheduledDelivery;
	}
	public void setScheduledDelivery(String scheduledDelivery) {
		this.scheduledDelivery = scheduledDelivery;
	}
	public String getStatisticsReports() {
		return statisticsReports;
	}
	public void setStatisticsReports(String statisticsReports) {
		this.statisticsReports = statisticsReports;
	}
	public Integer getTotalRegisteredCustomer() {
		return totalRegisteredCustomer;
	}
	public void setTotalRegisteredCustomer(Integer totalRegisteredCustomer) {
		this.totalRegisteredCustomer = totalRegisteredCustomer;
	}		
}