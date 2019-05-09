/**
 * 
 */
package register.security.microservice.model;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bruno.rivera
 *
 */
@Getter
@Setter
public class SendEmailRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String from;
	private String to;
	private String subject;
	private String templateType;
	private Map<String, String> custom;
}
