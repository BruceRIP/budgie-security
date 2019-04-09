/**
 * 
 */
package mx.budgie.billers.reporter.jms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * @author brucewayne
 *
 */
@Component
public class ReporterSenderJMS {

	private static final Logger LOGGER = LogManager.getLogger(ReporterSenderJMS.class);
	@Autowired
	private JmsTemplate jmsTemplate;

	public void send(String queue, String message) {
		LOGGER.info("Sending mesage {}", message);
		jmsTemplate.convertAndSend(queue, message);
	}

	public void send(String queue, Object message) {
		LOGGER.info("Sending mesage {}", message);
		jmsTemplate.convertAndSend(queue, message);
	}
}
