/**
 * 
 */
package mx.budgie.security.sso.vo;

import org.springframework.security.oauth2.common.OAuth2RefreshToken;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 25, 2017
 */
public class RefreshTokenVO implements OAuth2RefreshToken{

	private String refreshToken;
	
	public RefreshTokenVO(String refreshToken) {
		super();
		this.refreshToken = refreshToken;
	}

	@Override
	public String getValue() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
