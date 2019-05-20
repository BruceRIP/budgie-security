/**
 * 
 */
package com.budgie.zuul;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * @author bruno.rivera
 *
 */
@Configuration
@RefreshScope
@EnableOAuth2Client
@EnableGlobalMethodSecurity(
		  prePostEnabled = true, // enables Spring Security pre/post annotations
		  securedEnabled = true, // property determines if the @Secured annotation should be enabled
		  jsr250Enabled = true) // property allows us to use the @RoleAllowed annotation
public class ResourcesConfiguration extends ResourceServerConfigurerAdapter {

	@Value("${budgie.billers.web.security.authorize.matches}")
	private String[] requestMatchers;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(requestMatchers)
		.permitAll()
		.antMatchers("/**")
		.authenticated()
		.and()
		.formLogin()
		.loginPage("/login.html")
		.permitAll().and().logout().permitAll().and().exceptionHandling()
		.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
	}
	
}
