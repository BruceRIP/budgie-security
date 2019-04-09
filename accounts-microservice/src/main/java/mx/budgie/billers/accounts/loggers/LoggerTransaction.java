/**
 * 
 */
package mx.budgie.billers.accounts.loggers;

import java.util.Calendar;

import mx.budgie.billers.accounts.utils.AccountsUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author brucewayne
 *
 */
public class LoggerTransaction {
	
	private static final Logger LOGGER = LogManager.getLogger("transactional-log");

	public static void printTransactionalLog(String instance
									,String port
									,Calendar startTime
									,Calendar endTime									
									,long transactionId																											
									,String resource
									,boolean status,
									String description) {
		
		StringBuilder str = new StringBuilder()
				.append((instance == null) ? "WITHOUT_INSTANCE" : instance)
				.append("|")
				.append((port == null) ? "WITHOUT_PORT" : port)	
				.append("|").append((startTime == null) ? "WITHOUT_TIME" : AccountsUtils.getCurrentDateLongTransLog(startTime.getTimeInMillis()))
				.append("|")
				.append((endTime == null) ? "WITHOUT_ENDTIME" : AccountsUtils.getCurrentDateTransLog(endTime))
				.append("|")
				.append(AccountsUtils.getTimeLongDifference(startTime == null ? 0l : startTime.getTimeInMillis(),endTime == null ? Calendar.getInstance().getTimeInMillis() : endTime.getTimeInMillis()))				
				.append("|").append(transactionId)																			
				.append("|").append((resource == null) ? "WITHOUT_RESOURCE" : resource)
				.append("|").append((status) ? "SUCCESS" : "FAILED")
				.append("|").append((description == null) ? "WITHOUT_DESCRIPTION" : description);
		LOGGER.info(str.toString());
	}
}
