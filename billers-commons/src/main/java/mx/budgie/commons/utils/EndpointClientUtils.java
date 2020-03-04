/**
 * 
 */
package mx.budgie.commons.utils;

import mx.budgie.commons.response.ResponseMessage;

/**
 * @author brucewayne
 *
 */
public class EndpointClientUtils {

	public static ResponseMessage responseUnavailable(int code, String message, String details) {
		return new ResponseMessage(code, message, details);
	}
}
