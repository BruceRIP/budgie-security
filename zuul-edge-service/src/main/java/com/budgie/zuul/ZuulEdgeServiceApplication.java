package com.budgie.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.budgie.zuul.filters.FlowPostFilter;
import com.budgie.zuul.filters.FlowPreFilter;
import com.budgie.zuul.filters.FlowRouteFilter;

@SpringBootApplication
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
