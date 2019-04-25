/**
 * 
 */
package com.budgie.zuul;

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
public class ResourcesConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/clients/authentication**","/oauth-server/oauth/token**","/sso-server/oauth/token**","/sso-server/oauth/authorize**")
                .permitAll()
                .antMatchers("/**")
                .authenticated();
    }
}
