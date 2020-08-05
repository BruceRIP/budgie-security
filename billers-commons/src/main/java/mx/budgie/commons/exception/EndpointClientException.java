/**
 * 
 */
package mx.budgie.commons.exception;

import org.springframework.http.HttpStatus;

import mx.budgie.commons.utils.ClientCode;

/**
 * @author bruno.rivera
 *
 */
public class EndpointClientException extends RuntimeException{

	private HttpStatus code;
	private String message;
	private String addtionalInformation;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EndpointClientException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public EndpointClientException(String msg) {
		super(msg);
	}
	public EndpointClientException(ClientCode clientCode) {
		super(clientCode.getMessage());
	}

	public EndpointClientException(HttpStatus code, String message, String addtionalInformation) {
		super(message);
		this.code = code;
		this.message = message;
		this.addtionalInformation = addtionalInformation;
	}
	
	public EndpointClientException(HttpStatus code, String message) {
		super(message);
		this.code = code;
		this.message = message;		
	}

	public HttpStatus getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getAddtionalInformation() {
		return addtionalInformation;
	}
	
	
}
