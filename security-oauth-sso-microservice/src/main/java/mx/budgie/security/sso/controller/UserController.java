/**
 * 
 */
package mx.budgie.security.sso.controller;

import java.security.Principal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bruno.rivera
 *
 */
@RestController
public class UserController {

	private static final Logger LOGGER = LogManager.getLogger(UserController.class);
			
	@GetMapping(path = "/user/me")
	public Principal user(Principal principal) {
		LOGGER.info(" *--- Getting principal information after authenticated ---*");
		return principal;
	}
}
