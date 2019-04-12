/**
 * 
 */
package mx.budgie.commons.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author bruno.rivera
 * 
 */
@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage {

	private Integer code;
	private String message;
	private String addtionalInformation;
	private Boolean responseFlag;
	
	
	public ResponseMessage() {
		super();
	}

	/**
	 * 
	 * @param code
	 * @param message
	 * @param addtionalInformation
	 * @param responseFlag
	 */
	public ResponseMessage(Integer code, String message, String addtionalInformation, Boolean responseFlag) {
		super();
		this.code = code;
		this.message = message;
		this.addtionalInformation = addtionalInformation;
		this.responseFlag = responseFlag;
	}

	/**
	 * 
	 * @param code
	 * @param message
	 */
	public ResponseMessage(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	/**
	 * 
	 * @param code
	 * @param message
	 * @param addtionalInformation
	 */
	public ResponseMessage(Integer code, String message, String addtionalInformation) {
		super();
		this.code = code;
		this.message = message;
		this.addtionalInformation = addtionalInformation;
	}

	/**
	 * 
	 * @param code
	 * @param message
	 * @param responseFlag
	 */
	public ResponseMessage(Integer code, String message, Boolean responseFlag) {
		super();
		this.code = code;
		this.message = message;
		this.responseFlag = responseFlag;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getAddtionalInformation() {
		return addtionalInformation;
	}

	public Boolean getResponseFlag() {
		return responseFlag;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setAddtionalInformation(String addtionalInformation) {
		this.addtionalInformation = addtionalInformation;
	}

	public void setResponseFlag(Boolean responseFlag) {
		this.responseFlag = responseFlag;
	}

	public String toJsonString() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}	
	
}
