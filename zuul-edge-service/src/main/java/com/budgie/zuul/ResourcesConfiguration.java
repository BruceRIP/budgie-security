/**
 * 
 */
package com.budgie.zuul;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author bruno.rivera
 *
 */
@Configuration
@EnableOAuth2Client
@RefreshScope
public class ResourcesConfiguration extends ResourceServerConfigurerAdapter {

	@Value("${budgie.billers.web.security.authorize.matches}")
	private String[] requestMatchers;
	
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(requestMatchers)
                .permitAll()
                .antMatchers("/**")
                .authenticated();
    }
}
