/**
 * 
 */
package mx.budgie.commons.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;

import mx.budgie.commons.crypto.DigestAlgorithms;

/**
 * @author bruno.rivera
 *
 */
public class CommonsUtil {

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
		synchronized (CommonsUtil.class) {
			if (txIdIncrmentator >= 9999) {
				txIdIncrmentator = 0;
			}
			localIndex = ++txIdIncrmentator;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmm");
		Date date = new Date();
		StringBuilder sb = new StringBuilder();
		sb.append(systemId).append(formatter.format(date));
		DecimalFormat df = new DecimalFormat("0000");
		sb.append(df.format(localIndex));
		return Long.parseLong(sb.toString());
	}
	
	public static String createID(long sequence, String decimalFormat) {	
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
	
	public static Map<String, String> transactionIdHeader(final int systemId) {
		Map<String, String> headers = new LinkedHashMap<>();
		headers.put("transactionId", String.valueOf(CommonsUtil.getTransactionId(systemId)));
		return headers;
	}
	
	public static String normalizingContentType(final String type) {
		String contentType = null;
		switch (type) {
			case "Content-Type":
			case "ContentType":
			case "contenttype":
			case "content-type":
				contentType = "Content-Type";
			break;		
		}
		return contentType;
	}
	
	public static void createFile(final String pathAndNameFile, final String source) throws IOException {
		FileWriter fileWriter = new FileWriter(pathAndNameFile);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.print(source);		    
		printWriter.close();		
	}
	
	public static final String addZerosRight(final String value) {
		if("43.77".equals(value)) {
			return String.format("%-9s", value).replace(" ", "0");
		}
		return String.format("%-8s", value).replace(" ", "0");
	}
	
	public static Map<String, String> createHead(){
		Map<String, String> transactionId = new LinkedHashMap<>();
		transactionId.put("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
		return transactionId;
	}
	
	public static Map<String, String> createHead(String contentTypeD){
		Map<String, String> contentType = new LinkedHashMap<>();
		contentType.put("Content-Type", contentTypeD);
		return contentType;
	}
	
	public static String normalizeResponseBody(String body, int length) {
		if(body != null && !body.isEmpty()) {
			return body.substring(0,body.length() < length ? body.length() : length);
		}
		return null;
	}
	
	public static XMLGregorianCalendar currentXMLTime(String formatDate, String timeZone){
		SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		String date = sdf.format(new Date());
		try {
			XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(date);
			return xmlCal;
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
	
	public static void writeBytesToFileClassic(final String filepath, final String filename, byte[] data) {
		try {
			File file = new File(filepath);
			file.mkdirs();
			File file2 = new File(file.getAbsolutePath() + "/" + filename);			
			FileUtils.writeByteArrayToFile(file2, data);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	public static String getNoCertificateFromCer(final String pathNameFile) {		 
		try {
			X509Certificate certificate509 = ManageKeysUtil.getX509Certificate(new File(pathNameFile));			
			return  ManageKeysUtil.getNoCertificate(certificate509);
		} catch (CertificateException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String generateSignature(final String signature, final DigestAlgorithms type) {
		String finalSignature = null;
		switch (type) {
		case SHA_256:
			finalSignature = DigestUtils.sha256Hex(signature);			
			break;
		case SHA_1:
			finalSignature = DigestUtils.sha1Hex(signature);			
			break;		
		}
		return finalSignature;
	}
	
	public static String buildSignatureSHA256(String formKey, String amount, String orderNumber, String merchantCode, String currency, String transactionType, String pan) {
		String data = amount + orderNumber + merchantCode +  currency + transactionType + pan + formKey;
		return generateSignature(data, DigestAlgorithms.SHA_256);
	}
	public static String buildSignatureSHA1(String formKey, String amount, String orderNumber, String merchantCode, String currency, String transactionType, String pan) {
		String data = amount + orderNumber + merchantCode +  currency + transactionType + pan + formKey;
		return generateSignature(data, DigestAlgorithms.SHA_1);
	}
}
