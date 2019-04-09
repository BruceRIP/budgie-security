/**
 * 
 */
package mx.budgie.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @company Budgie Software
 * @author bruce rip
 * @date Jun 15, 2017
 */
@Configuration
@EnableResourceServer
public class ResourceServer extends ResourceServerConfigurerAdapter {
	
	@Autowired
	private AuthenticationEntryPoint customAuthenticationEntryPoint;
	@Value("${budgie.billers.context.resourceId}")
	private String resourceId;
	@Value("${budgie.billers.context.path.authorized.resources}")
	private String[] authorizedResources;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(resourceId).stateless(false);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers(authorizedResources)
		.permitAll()		
		.antMatchers("/**")
		.authenticated()
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(customAuthenticationEntryPoint);
	}
}
