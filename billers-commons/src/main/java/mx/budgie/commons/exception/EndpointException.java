/**
 * 
 */
package mx.budgie.commons.exception;

import org.springframework.http.HttpStatus;

import mx.budgie.commons.utils.ClientCode;

/**
 * @author brucewayne
 *
 */
public class EndpointException extends Exception{

	private HttpStatus code;
	private String message;
	private String addtionalInformation;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EndpointException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public EndpointException(String msg) {
		super(msg);
	}
	public EndpointException(ClientCode clientCode) {
		super(clientCode.getMessage());
	}

	public EndpointException(HttpStatus code, String message, String addtionalInformation) {
		super(message);
		this.code = code;
		this.message = message;
		this.addtionalInformation = addtionalInformation;
	}
	
	public EndpointException(HttpStatus code, String message) {
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
