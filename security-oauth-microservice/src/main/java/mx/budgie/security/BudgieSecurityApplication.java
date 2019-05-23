package mx.budgie.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@ComponentScan(basePackages={"mx.budgie"})
public class BudgieSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgieSecurityApplication.class, args);
	}
}
