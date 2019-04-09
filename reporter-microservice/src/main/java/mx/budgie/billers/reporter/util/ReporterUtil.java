/**
 * 
 */
package mx.budgie.billers.reporter.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRFillInterruptedException;

/**
 * @author brucewayne
 *
 */
public class ReporterUtil {
	
	public String getAbsolutePathFile(String classpathFile) throws FileNotFoundException{				
		try {
			Path path = Paths.get(getClass().getClassLoader().getResource(classpathFile).toURI());
			File file = new File(path.toUri());
			return file.getAbsolutePath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw new FileNotFoundException();
		}	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" }) 
	public String fillReport(String absolutePath, Map parameters, List dataList) throws JRFillInterruptedException{		
		try {
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
			return JasperFillManager.fillReportToFile(absolutePath, parameters, beanColDataSource);
		} catch (JRException e) {			
			e.printStackTrace();
			throw new JRFillInterruptedException();
		}
	}
	
	public void exportPDF(String printFileName, String absoultePath) throws JRException{
		try {
			JasperExportManager.exportReportToPdfFile(printFileName,absoultePath);
		} catch (JRException e) {
			e.printStackTrace();
			throw new JRException(e.getMessage());
		}
	}
	
	public static XMLGregorianCalendar currentXMLTime(String formatDate, String timeZone){
		SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		String date = sdf.format(new Date());
		try {
			XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(date);
			return xmlCal;
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}	
		return null;
	}

	public static String formatEmptyString(String source, String ... pattern) {
		if(source == null || source.isEmpty()) {
			if(pattern != null && pattern.length > 0) {
				return pattern[0];
			}
			return "";
		}
		return source;
	}
	 
}