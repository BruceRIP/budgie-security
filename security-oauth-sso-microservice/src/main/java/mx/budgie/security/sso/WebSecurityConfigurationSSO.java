/**
 * 
 */
package mx.budgie.security.sso;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;

import mx.budgie.security.sso.constants.SecurityConstants;
import mx.budgie.security.sso.controller.ClientResources;

/**
 * @company Budgie Software
 * @author bruce rip
 * @date Jun 15, 2017
 * Aqui es donde todas las configuraciones con repecto a la web, flujo de login tiene que ir
 */
@Configuration
@EnableOAuth2Client
@Order(200)
public class WebSecurityConfigurationSSO extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier(SecurityConstants.SERVICE_CUSTOM_USER_DETAIL)
	private UserDetailsService userDetailsService;
	@Value("${budgie.billers.web.security.authorize.matches}")
	private String[] authorizeMatches;
	
	@Autowired
	private OAuth2ClientContext oauth2ClientContext;
	@Autowired
	private ClientResources github;	
	@Autowired
	private ClientResources facebook;		
	

	/**
	 * CSRF is activate by default when using WebSecurityConfigurerAdapter
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requestMatchers()
			.antMatchers(authorizeMatches)
			.and()
			.authorizeRequests()		
			.antMatchers(authorizeMatches)
			.permitAll()						
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.and()
			.logout()
			.logoutSuccessUrl("/")
			.permitAll()
			.and()
			.csrf()
			.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			.and()
			.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	private Filter ssoFilter() {
		CompositeFilter filter = new CompositeFilter();
		List<Filter> filters = new ArrayList<>();
		filters.add(ssoFilter(facebook, "/login/facebook"));
		filters.add(ssoFilter(github, "/login/github"));
		filter.setFilters(filters);
		return filter;
	}

	private Filter ssoFilter(ClientResources client, String path) {
		OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
		OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
		filter.setRestTemplate(template);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId());
		tokenServices.setRestTemplate(template);
		filter.setTokenServices(tokenServices);
		return filter;
	}
}