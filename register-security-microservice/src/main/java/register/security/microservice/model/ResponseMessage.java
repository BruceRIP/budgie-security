package register.security.microservice.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(Include.NON_NULL)
public class ResponseMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(hidden=true, readOnly = true)
	@JsonInclude(Include.NON_NULL)
	private Integer code;
	@ApiModelProperty(hidden=true, readOnly = true)
	@JsonInclude(Include.NON_NULL)
	private String message;	
	@ApiModelProperty(hidden=true, readOnly = true)
	@JsonInclude(Include.NON_NULL)
	private String description;	
	
	public ResponseMessage() {
		super();
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
