/**
 * 
 */
package register.security.microservice.constants;

/**
 * @author bruno.rivera
 *
 */
public class RegisterSecurityConstants {
	
	private RegisterSecurityConstants() {}

	public static final String PATH_BASE = "/register";
	public static final String PATH_LOGIN = "/login";
	public static final String PATH_CREATE_ACCOUNT= "/";
	public static final String PATH_CREATE_CLIENT = "/authorization/{billerID}";
	public static final String PATH_CREATE_ADMIN_CLIENT = "/administration/authorization";
}
