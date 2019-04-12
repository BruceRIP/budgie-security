/**
 * 
 */
package mx.budgie.commons.utils;

import org.springframework.http.HttpStatus;

/**
 * @author bruno.rivera
 *
 */
public enum ClientCode {

	RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());

	private String message;
	private int status;

	private ClientCode(int status, String message) {
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public int getStatus() {
		return status;
	}

}
