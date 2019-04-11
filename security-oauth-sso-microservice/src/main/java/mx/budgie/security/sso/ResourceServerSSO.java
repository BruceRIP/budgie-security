/**
 * 
 */
package mx.budgie.security.sso;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @company Budgie Software
 * @author bruce rip
 * @date Jun 15, 2017
 */
@Configuration
@EnableResourceServer
public class ResourceServerSSO extends ResourceServerConfigurerAdapter {
	
//	@Autowired
//	private AuthenticationEntryPoint customAuthenticationEntryPoint;
	@Value("${budgie.billers.context.resourceId}")
	private String resourceId;
//	@Value("${budgie.billers.context.path.authorize.resources}")
//	private String[] authorizeResources;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(resourceId).stateless(false);
	}
//
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http.anonymous().disable().csrf().disable().requestMatchers()
//    	.antMatchers(authorizeResources)
//    	.and()
//    	.authorizeRequests()
//    	.anyRequest()
//    	.authenticated()
//    	.and()
//    	.formLogin()
//    	.permitAll();
//	}
}
