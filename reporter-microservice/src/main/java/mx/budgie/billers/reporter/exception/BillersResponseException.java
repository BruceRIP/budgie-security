/**
 * 
 */
package mx.budgie.billers.reporter.exception;

/**
 * @author brucewayne
 *
 */
public class BillersResponseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;	
	private String message;
	private String description;
	
	public BillersResponseException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BillersResponseException(int code, String message, String description) {
		super();
		this.code = code;
		this.message = message;
		this.description = description;
	}
	public BillersResponseException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	@Override
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
