/**
 * 
 */
package mx.budgie.security.sso.constants;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 25, 2017
 */
public class SecurityConstants {

	private SecurityConstants() {}
	public static final String SERVICE_CUSTOM_USER_DETAIL = "customUserDetailsSSOService";
	public static final String SERVICE_CUSTOM_CLIENT_DETAIL = "customClientDetailsSSOService";
	public static final String SERVICE_CUSTOM_TOKEN_STORE = "tokenStoreSSOService";
	public static final String SERVICE_ACCOUNT = "accountService";
	
	public static final String YML_APP_FILE = "classpath:application.yml";
	public static final String YML_BUDGIE_APP_FILE = "classpath:budgie-application.properties";	
	public static final int CODE_9999 = 9999;
	public static final String FORMAT_DATE_WITHOUT_DASH = "yyMMddHHmm";
	
	public static final String BILLERS_RESOURCE_ID = "budgie.billers.context.resourceId";
	public static final String BILLERS_FORMAT_DATE = "${budgie.billers.context.formatDate}";
	
	/*
	 * Only for test	
	 */
	public static final String POST_OAUTH_TOKEN = "http://localhost:8788/billers/oauth/token";
	public static final String GET_TEST_HELLO_WORLD = "http://localhost:2222/billers/hello";	
	
	public static final String QPM_PASSWORD_GRANT = "?grant_type=password&username=BruceRIP&password=";    
    public static final String QPM_ACCESS_TOKEN = "?access_token=";
    public static final String QPM_REFRESH_TOKEN ="?grant_type=refresh_token&refresh_token=";
}
