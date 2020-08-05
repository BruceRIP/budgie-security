/**
 * 
 */
package mx.budgie.billers.reporter.exception;

/**
 * @author brucewayne
 *
 */
public class BillersEmailException extends BillersResponseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BillersEmailException() {
		super();
	}

	public BillersEmailException(String message) {
		super(99, message, message);
	}
	
	public BillersEmailException(int code, String message, String description) {
		super(code, message, description);
	}
	
}
