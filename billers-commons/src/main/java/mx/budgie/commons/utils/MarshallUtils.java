/**
 * 
 */
package mx.budgie.commons.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * Marshall String source from a Object and unmarshal Object from a String source
 * Marshall: You need to send a specific object and its return a String source
 * 	V = Object and T = String source
 * Unmarshal: You need to send a specific String source and its return a object
 * T = String source and V = Object 
 * @author bruno.rivera
 *
 */
public class MarshallUtils<V,T> {

	public static final String SAT_SCHEMA_LOCATION = "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd";
	public static final String SAT_ENCODIG = "UTF-8";
	private final Logger LOGGER = LogManager.getLogger(getClass());	

	/**
	 * Create a String xml from a Object source
	 * @param Object
	 * @return String
	 * @throws JAXBException
	 */
	public String marshall(V source) throws JAXBException{
        try {
        	LOGGER.info("Creating Marshaller schema");
        	JAXBContext jc = JAXBContext.newInstance(type);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new DefaultNamespacePrefixMapper());
            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, SAT_SCHEMA_LOCATION);	 
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, SAT_ENCODIG);      
		    StringWriter stringWriter = new StringWriter();
			marshaller.marshal(source, stringWriter);
			LOGGER.info("Marshaller schema was created successful");
			return stringWriter.toString();
		} catch (PropertyException e) {
			LOGGER.error("Error ocurred: {}", e);
			throw new JAXBException(e);
		} catch (JAXBException e) {
			LOGGER.error("Error ocurred: {}", e);
			throw new JAXBException(e);
		}		
	}
	
	/**
	 * Create a object from a String source xml
	 * @param source
	 * @return
	 * @throws JAXBException
	 */
	public V unmarshall(String source) throws JAXBException{
		try {
        	LOGGER.info("Creating Marshaller schema");
        	// create JAXBContext which will be used to create a Binder
            JAXBContext jc = JAXBContext.newInstance(type);                       
            // unmarshaller obj to convert xml data to java content tree
            Unmarshaller u = jc.createUnmarshaller();
            StringReader reader = new StringReader(source);
            // unmarshaller xml data to java content tree
            @SuppressWarnings("unchecked")
			V s = (V) u.unmarshal(reader);
            return s;
		} catch (Exception e) {
			LOGGER.error("Error ocurred: {}", e);
			throw new JAXBException(e);
		}	
	}
	
	private final Class<V> type;

    public MarshallUtils(Class<V> type) {
         this.type = type;
    }

    public Class<V> getMyType() {
        return this.type;
    }
	
	protected class DefaultNamespacePrefixMapper extends NamespacePrefixMapper {

		public static final String SAT_XSI_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
		public static final String SAT_CFDI_NAMESPACE = "http://www.sat.gob.mx/cfd/3";
		public static final String SAT_XSI = "xsi";
		public static final String SAT_CFDI = "cfdi";
		private Map<String, String> namespaceMap = new HashMap<String, String>();

		/**
		 * Create mappings.
		 */
		public DefaultNamespacePrefixMapper() {
			namespaceMap.put(SAT_XSI_NAMESPACE, SAT_XSI);
			namespaceMap.put(SAT_CFDI_NAMESPACE, SAT_CFDI);
		}

		@Override
		public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
			return namespaceMap.getOrDefault(namespaceUri, suggestion);
		}
	}
}
