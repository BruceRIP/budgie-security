/**
 * 
 */
package mx.budgie.ribbon.clients;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mx.budgie.commons.client.BalancedResource;

/**
 * @author bruno.rivera
 *
 */
@RestController
public class LoadBalanceClient {

	@Autowired
	private BalancedResource balanceResource;
		
	public @ResponseBody ResponseEntity<?> execute(final HttpServletRequest request, final HttpServletResponse response){
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
