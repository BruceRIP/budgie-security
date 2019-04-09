/**
 * 
 */
package mx.budgie.billers.reporter.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mx.budgie.billers.commons.response.ResponseMessage;
import mx.budgie.billers.reporter.email.EmailSender;
import mx.budgie.billers.reporter.exception.BillersEmailException;
import mx.budgie.billers.reporter.vo.EmailSenderVO;

/**
 * @author bruno.rivera
 *
 */
@RestController
@RequestMapping("/email")
@RefreshScope
public class ReporterController {

	private static final Logger LOGGER = LogManager.getLogger(ReporterController.class);		
	@Autowired
	private EmailSender emailSender;
	
	@PostMapping(value = "/send", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseMessage getHello(@RequestBody @Valid EmailSenderVO sender, @RequestHeader("transactionId") final long transactionId) {
		try {
			ThreadContext.push(Long.toString(transactionId));
			emailSender.send(sender);
			return new ResponseMessage(200, "Hello World ");
		} catch (BillersEmailException e) {
			LOGGER.error("Error {}", e);
			return new ResponseMessage(500, e.getMessage());
		}finally{	
			ThreadContext.clearStack();
		}
	}
		
}
