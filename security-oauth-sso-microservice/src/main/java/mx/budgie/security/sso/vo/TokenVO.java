/**
 * 
 */
package mx.budgie.security.sso.vo;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 25, 2017
 */
public class TokenVO implements OAuth2AccessToken {
	
	private RefreshTokenVO refreshTokenVO;
	private String tokenType;
	private String tokenValue;
	private Date expirationDate;	
	private int expiresIn;	
	private Set<String> scope;
	private Map<String, Object> additionalInformation;
	private boolean expired;

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return additionalInformation;
	}

	@Override
	public Set<String> getScope() {
		return scope;
	}

	@Override
	public OAuth2RefreshToken getRefreshToken() {
		return refreshTokenVO;
	}

	@Override
	public String getTokenType() {
		return tokenType;
	}
	
	@Override
	public Date getExpiration() {
		return expirationDate;
	}

	@Override
	public int getExpiresIn() {
		return expiresIn;
	}

	@Override
	public String getValue() {
		return tokenValue;
	}

	public void setRefreshTokenVO(RefreshTokenVO refreshTokenVO) {
		this.refreshTokenVO = refreshTokenVO;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public void setScope(Set<String> scope) {
		this.scope = scope;
	}

	public void setAdditionalInformation(Map<String, Object> additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	@Override
	public boolean isExpired() {
		return expired;
	}
	
	

}
