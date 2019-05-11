/**
 * 
 */
package mx.budgie.commons.email;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import mx.budgie.commons.client.EndpointClient;
import mx.budgie.commons.exception.EndpointException;

/**
 * @author bruno.rivera
 *
 */
@Component
public class SendEmail {

	private static final Logger LOGGER = LogManager.getLogger(SendEmail.class);
	
	public void sendEmailRequest(final String urlSendEmail, final EmailRequest emailRequest) {		
		try {
			ResponseEntity<String> sendEmailResponse = new EndpointClient(String.format("%s", urlSendEmail))
															.putHeaders(emailRequest.getHeaders())
															.putAuthorizationToken(emailRequest.getAuthorizationToken())
															.requestBody(emailRequest)
															.callPOST(String.class);
			LOGGER.info("Email Response: ", sendEmailResponse.getBody());
		} catch (EndpointException e) {
			LOGGER.error("We cant send email");
		}
	}
}
