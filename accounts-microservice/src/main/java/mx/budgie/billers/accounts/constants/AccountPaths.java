/**
 * 
 */
package mx.budgie.billers.accounts.constants;

/**
 * @author bruno.rivera
 *
 */
public class AccountPaths {

	private AccountPaths() {}
	public static final String ACCOUNT_BASED_PATH = "/accounts";
	public static final String ACCOUNT_CREATE = "";
	public static final String ACCOUNT_DELETE = "";
	public static final String ACCOUNT_UPDATE = "";
	public static final String ACCOUNT_UPDATE_ROLES = "/roles";
	public static final String ACCOUNT_ACTIVATE = "/activate";
	public static final String ACCOUNT_RECOVER_BY_ID = "/{billerID}";
//	public static final String ACCOUNT_RECOVER_BY_NICKNAME = "";
	public static final String ACCOUNT_RECOVER_BY_ACTIVATION_CODE = "";
	public static final String ACCOUNT_MANAGER_GET = "/manager/plan/{billerID}";
	public static final String ACCOUNT_MANAGER_UPDATE = "/manager/plan/{billerID}";
	public static final String ACCOUNT_MANAGER_MANAGED = "/manager/plan/{billerID}";
	public static final String ACCOUNT_MANAGER_MANAGED_REMOVE = "/manager/plan/{billerID}";
	
	public static final String CLIENT_BASED_PATH = "/clients";
	public static final String CLIENT_CREATE = "/authentication";
	public static final String CLIENT_DELETE = "/authentication/{clientID}";
	public static final String CLIENT_UPDATE = "/authentication/config/{clientID}";	
	public static final String CLIENT_RECOVER_BY_NAME = "/authentication/{clientId}";
	
	public static final String ACCOUNT_DASHBOARD_INFO = "/dashboard/{billerID}";
}
