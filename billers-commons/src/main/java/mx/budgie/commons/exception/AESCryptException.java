/**
 * 
 */
package mx.budgie.commons.exception;

/**
 * @author bruno.rivera
 *
 */
public class AESCryptException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AESCryptException(Throwable cause) {
		super(cause);
	}
	
	public AESCryptException(String message, Throwable cause) {
		super(message, cause);
	}

	public AESCryptException(String message) {
		super(message);
	}
	
}
