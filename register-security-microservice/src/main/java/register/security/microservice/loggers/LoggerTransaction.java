/**
 * 
 */
package register.security.microservice.loggers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
				.append("|").append((startTime == null) ? "WITHOUT_TIME" : getCurrentDateLongTransLog(startTime.getTimeInMillis()))
				.append("|")
				.append((endTime == null) ? "WITHOUT_ENDTIME" : getCurrentDateTransLog(endTime))
				.append("|")
				.append(getTimeLongDifference(startTime == null ? 0l : startTime.getTimeInMillis(),endTime == null ? Calendar.getInstance().getTimeInMillis() : endTime.getTimeInMillis()))				
				.append("|").append(transactionId)																			
				.append("|").append((resource == null) ? "WITHOUT_RESOURCE" : resource)
				.append("|").append((status) ? "SUCCESS" : "FAILED")
				.append("|").append((description == null) ? "WITHOUT_DESCRIPTION" : description);
		LOGGER.info(str.toString());
	}
	
	public static String getCurrentDateLongTransLog(Long timeInMillis) {
		Date date = new Date();
		date.setTime(timeInMillis);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
		String sDate = sdf.format(date);
		return sDate;
	}
	
	public static String getCurrentDateTransLog(Calendar date) {
		DateFormat formaterTransLog = new SimpleDateFormat("HH:mm:ss:SSS");
		return formaterTransLog.format(date.getTime());
	}
	
	public static long getTimeLongDifference(long startTime, long endTime) {
		long differenceTime = endTime - startTime;		
		//long diffSeconds = differenceTime / 1000 % 60;
		return differenceTime;
	}
}
