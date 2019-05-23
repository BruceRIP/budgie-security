/**
 * 
 */
package mx.budgie.security.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.budgie.security.messages.ResponseMessage;

/**
 * @company Budgie Software Technologies
 * @author brucewayne
 * @date Jun 25, 2017
 */
@RestController
@RequestMapping
public class HelloController {

	@GetMapping(value="/hello"			
			,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseMessage testingRest(){
		return new ResponseMessage(200, "SUCCESS");
	}
}
