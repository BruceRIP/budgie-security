/**
 * 
 */
package mx.budgie.security.sso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import mx.budgie.security.sso.constants.SecurityConstants;

/**
 * @company Budgie Software
 * @author bruce rip
 * @date Jun 15, 2017
 */
@Configuration
@Order(1)
public class WebSecurityConfigurationSSO extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier(SecurityConstants.SERVICE_CUSTOM_USER_DETAIL)
	private UserDetailsService userDetailsService;
	@Value("${budgie.billers.web.security.authorize.matches}")
	private String[] authorizeMatches;

	/**
	 * CSRF is activate by default when using WebSecurityConfigurerAdapter
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requestMatchers()
			.antMatchers(authorizeMatches)
			.and()
			.authorizeRequests()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()			
			.permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
}