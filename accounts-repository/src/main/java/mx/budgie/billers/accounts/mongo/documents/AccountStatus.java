/**
 * 
 */
package mx.budgie.billers.accounts.mongo.documents;

/**
 * @author bruno.rivera
 *
 */
public enum AccountStatus {
	
	REGISTER,
	ACTIVE,
	TO_CONFIRM,
	INACTIVE;
	
	public static boolean validateStatus(final String value) {		
		if(value != null && !value.isEmpty() && AccountStatus.valueOf(value).toString().equals("ACTIVE")) {
			return true;
		}		
		return false;
	}
	
}
