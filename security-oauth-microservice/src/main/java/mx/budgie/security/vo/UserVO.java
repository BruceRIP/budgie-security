/**
 * 
 */
package mx.budgie.security.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 25, 2017
 */
public class UserVO implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nickname;
	private String password;
	private boolean enabled;
	private Collection<GrantedAuthority> authorities;

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.nickname;
	}
	
	@SuppressWarnings("serial")
	public void setAuthorities(Set<String> roles) {
		this.authorities = new ArrayList<GrantedAuthority>();
		for(String s: roles) {
			this.authorities.add(new GrantedAuthority() {				
				@Override
				public String getAuthority() {					
					return s;
				}
			});
		}	
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {						
		return this.authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
