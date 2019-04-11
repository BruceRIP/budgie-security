/**
 * 
 */
package mx.budgie.security.sso.messages;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import mx.budgie.security.sso.catalog.ResponseCode;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 25, 2017
 */
public class ResponseMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonInclude(Include.NON_NULL)
	private Integer code;
	@JsonInclude(Include.NON_NULL)
	private String message;	
	@JsonInclude(Include.NON_NULL)
	private String description;	
	
	public ResponseMessage() {
		super();
	}
	
	/**
	 * Constructor with Response Code number and message
	 * @param responseCode
	 */
	public ResponseMessage(ResponseCode responseCode) {
		super();
		this.code = responseCode.getCode();
		this.message = responseCode.getMessage();
	}
	
	/**
	 * Constructor with Response Code number and message
	 * @param responseCode
	 */
	public ResponseMessage(ResponseCode responseCode, String message) {
		super();
		this.code = responseCode.getCode();
		this.message = message;
	}
	
	public ResponseMessage(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;		
	}	

	public ResponseMessage(Integer code, String message, String description) {
		super();
		this.code = code;
		this.message = message;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
