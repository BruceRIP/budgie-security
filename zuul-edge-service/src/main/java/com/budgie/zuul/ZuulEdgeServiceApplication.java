package com.budgie.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.budgie.zuul.filters.FlowPostFilter;
import com.budgie.zuul.filters.FlowPreFilter;
import com.budgie.zuul.filters.FlowRouteFilter;

@SpringBootApplication
@EnableResourceServer
@EnableGlobalMethodSecurity(
		  prePostEnabled = true, // enables Spring Security pre/post annotations
		  securedEnabled = true, // property determines if the @Secured annotation should be enabled
		  jsr250Enabled = true) // property allows us to use the @RoleAllowed annotation
@EnableZuulProxy
@EnableDiscoveryClient
@ComponentScan(basePackages = { "com.budgie.zuul" })
public class ZuulEdgeServiceApplication {

	public static void main(String[] args) {		
		SpringApplication.run(ZuulEdgeServiceApplication.class, args);
	}	

	@Bean
	public FlowPostFilter flowPostFilter() {
		return new FlowPostFilter();
	}

	@Bean
	public FlowPreFilter flowPreFilter() {
		return new FlowPreFilter();
	}

	@Bean
	public FlowRouteFilter flowRouteFilter() {
		return new FlowRouteFilter();
	}
		
}
