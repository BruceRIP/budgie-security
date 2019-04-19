/**
 * 
 */
package mx.budgie.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import mx.budgie.security.constants.SecurityConstants;

/**
 * @company Budgie Software
 * @author bruce rip
 * @date Jun 15, 2017
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class GlobalAuthentication extends WebSecurityConfigurerAdapter {
		
    @Autowired
    @Qualifier(SecurityConstants.SERVICE_CUSTOM_USER_DETAIL)
    private UserDetailsService userDetailsService;
    @Value("${budgie.billers.web.security.authorize.matches}")
	private String[] authorizeMatchesResources;
      
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .userDetailsService(userDetailsService)
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(authorizeMatchesResources)
        .permitAll(); 
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userDetailsService);
    }
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
   
}
