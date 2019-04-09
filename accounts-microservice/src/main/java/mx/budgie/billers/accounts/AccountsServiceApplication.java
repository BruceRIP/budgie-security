package mx.budgie.billers.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import io.swagger.annotations.Api;
import mx.budgie.billers.accounts.constants.AccountsConstants;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class AccountsServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(AccountsServiceApplication.class, args);
	}
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
	            .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
	            .paths(PathSelectors.any())
	            .build().pathMapping("/")
	            .apiInfo(apiInfo())
	            .useDefaultResponseMessages(false);
	}
	
	@Bean
	public ApiInfo apiInfo() {
	       final ApiInfoBuilder builder = new ApiInfoBuilder();
	       builder.title("Accounts API")
	       		.version(AccountsConstants.API_VERSION)
	       		.license("\u00a9 Copyright Budgie Software Technologies")
	            .description("List of all endpoints used in API");
	       return builder.build();
	   }
}
