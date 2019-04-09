/**
 * 
 */
package mx.budgie.billers.accounts.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mx.budgie.billers.accounts.constants.AccountsConstants;

/**
 * @author brucewayne
 *
 */
public class AccountsUtils {	

	private AccountsUtils() {}
	public static int getPassId(String source) {
		int id = -1;
		if (source != null) {
			int pos = source.length();
			source = source.substring(pos - 1);
			id = Integer.valueOf(source);
		}
		return id;
	}

	private static int txIdIncrmentator = 0;

	public static long getTransactionId(long systemId) {
		int localIndex;
		synchronized (AccountsUtils.class) {
			if (txIdIncrmentator >= AccountsConstants.CODE_9999) {
				txIdIncrmentator = 0;
			}
			localIndex = ++txIdIncrmentator;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(AccountsConstants.FORMAT_DATE_WITHOUT_DASH);
		Date date = new Date();
		StringBuilder sb = new StringBuilder();
		sb.append(systemId).append(formatter.format(date));
		DecimalFormat df = new DecimalFormat("0000");
		sb.append(df.format(localIndex));
		return Long.parseLong(sb.toString());
	}
	
	public static String createBillerID(long sequence, String decimalFormat) {	
		java.text.DecimalFormat f = new java.text.DecimalFormat(decimalFormat);		
	    return f.format(sequence);
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
