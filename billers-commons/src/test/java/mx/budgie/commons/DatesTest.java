/**
 * 
 */
package mx.budgie.commons;

import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author bruno.rivera
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BillersCommonsConfiguration.class })
public class DatesTest {

	private static final Logger LOGGER = LogManager.getLogger(DatesTest.class);
	
	@Test
	public void addMinutosToDateTime() {
		int validitySeconds = 1440;
		Date modified = new Date(System.currentTimeMillis() + (validitySeconds * 1000L));
		LOGGER.info("Actual hour: {}", Calendar.getInstance().getTime());
		LOGGER.info("Hour modified: {}", modified);
	}
}
