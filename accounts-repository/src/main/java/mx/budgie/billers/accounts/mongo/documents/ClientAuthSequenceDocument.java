/**
 * 
 */
package mx.budgie.billers.accounts.mongo.documents;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author brucewayne
 *
 */
@Document(collection="ClientsAuthenticationSequence")
public class ClientAuthSequenceDocument implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	private long sequence;	
	
	public ClientAuthSequenceDocument(long sequence) {
		super();
		this.sequence = sequence;
	}
	
	public long getSequence() {
		return sequence;
	}
	public void setSequence(long sequence) {
		this.sequence = sequence;
	}
	
	
}
