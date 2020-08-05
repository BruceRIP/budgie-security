/**
 * 
 */
package mx.budgie.billers.accounts.mongo.documents;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author brucewayne
 *
 */
@Document(collection = "AccountAdministrator")
public class AccountAdministratorVO extends AdministratorAccount{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	private String billerID;	
    @JsonIgnore
    private List<Sessions> sessions = new ArrayList<>();
    	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Sessions> getSessions() {
		return sessions;
	}
	public void setSessions(List<Sessions> sessions) {
		this.sessions = sessions;
	}
	public String getBillerID() {
		return billerID;
	}
	public void setBillerID(String billerID) {
		this.billerID = billerID;
	}

	
}