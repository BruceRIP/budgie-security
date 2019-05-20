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
	public static final String ACCOUNT_DELETE = "/{billerID}";
	public static final String ACCOUNT_UPDATE = "/{billerID}";
	public static final String ACCOUNT_RECOVER_BY_ID = "/{billerID}";
	public static final String ACCOUNT_RESET_OR_RECOVER_PASSWORD = "/reset";
	public static final String ACCOUNT_UPDATE_ROLES = "/roles/{billerID}";
	public static final String ACCOUNT_ACTIVATE = "/activate";
	public static final String ACCOUNT_LOGIN = "/login";
	public static final String ACCOUNT_RECOVER_BY_ACTIVATION_CODE = "/activate";
	public static final String ACCOUNT_MANAGER_GET = "/manager/plan/{billerID}";
	public static final String ACCOUNT_MANAGER_UPDATE = "/manager/plan/{billerID}";
	public static final String ACCOUNT_MANAGER_MANAGED = "/manager/plan/{billerID}";
	public static final String ACCOUNT_MANAGER_MANAGED_REMOVE = "/manager/plan/{billerID}";
	
	public static final String CLIENT_BASED_PATH = "/clients";
	public static final String CLIENT_CREATE = "/authentication/{billerID}";
	public static final String CLIENT_DELETE = "/authentication/{billerID}/{clientID}";
	public static final String CLIENT_UPDATE = "/authentication/config/{billerID}/{clientID}";	
	public static final String CLIENT_RECOVER_BY_CLIENT_ID = "/authentication/{clientId}";
	public static final String GET_ALL_CLIENT_BY_BUDGIE_ID = "/authentication/{budgieID}/all";
	
	public static final String ACCOUNT_DASHBOARD_INFO = "/dashboard/{billerID}";
}
