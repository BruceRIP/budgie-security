/**
 * 
 */
package mx.budgie.billers.reporter.jms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author brucewayne
 *
 */
@Component
public class ReporterReceiverJMS { 

	private static final Logger LOGGER = LogManager.getLogger(ReporterReceiverJMS.class);		
	
	@JmsListener(destination = "billers.reporter.queue.request", containerFactory = "configFactory")
	public void receiveMessage(String message) {						
		LOGGER.info("Iniciando proceso de enviar pdf y xml {}", message);
	}
}
