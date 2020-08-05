/**
 * 
 */
package mx.budgie.security.sso.catalog;

import org.springframework.http.HttpStatus;

/**
 * @author bruno.rivera
 *
 */
public enum ResponseCode {

	SUCCESS(200, "Success", HttpStatus.OK),
	NOT_AVAILABLE(404, "Resource not available", HttpStatus.NOT_FOUND),
	BAD_REQUEST(404, "Server can not process the request", HttpStatus.BAD_REQUEST),
	MISSING_PARAMS(417, "Missing parameters", HttpStatus.EXPECTATION_FAILED),
	ACCESS_DENIED(401, "Access denied", HttpStatus.UNAUTHORIZED);
	
	private Integer code;
	private String message;
	private HttpStatus httpStatus;
	
	private ResponseCode(final Integer code, final String message, final HttpStatus httpStatus){
		this.code = code;
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	
}
