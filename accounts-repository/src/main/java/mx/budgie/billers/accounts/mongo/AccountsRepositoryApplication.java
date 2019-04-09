package mx.budgie.billers.accounts.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Persistence for accounts
 * @author bruno.rivera
 *
 */
@SpringBootApplication
public class AccountsRepositoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsRepositoryApplication.class, args);
	}
}
