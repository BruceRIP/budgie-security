package mx.budgie.billers.reporter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ReporterMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReporterMicroserviceApplication.class, args);
	}
	
}
