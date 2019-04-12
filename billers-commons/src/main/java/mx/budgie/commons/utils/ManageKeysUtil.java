/**
 * 
 */
package mx.budgie.commons.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

/**
 * @author bruno.rivera
 *
 */
public class ManageKeysUtil {

	/**
	 * Genera certificados a paratir del archivo .cer
	 * @param certificateFile
	 * @return
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static X509Certificate getX509Certificate(final File certificateFile) throws CertificateException, IOException{
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(certificateFile);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			return (X509Certificate) cf.generateCertificate(fis);
		}finally {
			if(fis != null) {
				fis.close();
			}
		}
	}
		
	public static String getCertificateBase64(final X509Certificate certificate) throws CertificateEncodingException{
		return new String(Base64.encodeBase64(certificate.getEncoded()));
	} 
	
	/**
	 * Obtiene el numero de certificado
	 * @param certificate
	 * @return
	 */
	public static String getNoCertificate(final X509Certificate certificate) {
		BigInteger serial = certificate.getSerialNumber();
		byte[] serialArray = serial.toByteArray();
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i<serialArray.length; i++) {
			buffer.append((char) serialArray[i]);
		}
		return buffer.toString();
	} 
	
	/**
	 * Recibe el xslt de la cadena original y el xml CFDI
	 * EJ: ../{path}/cadenaoriginal_3_3.xslt
	 * @param streamSource
	 * @param xml
	 * @return
	 * @throws TransformerException
	 */
	public static String generateOriginalSource(final File xslt, final File cfdi) throws TransformerException {
		StreamSource sourceXSLT = new StreamSource(xslt);
		StreamSource sourceXML = new StreamSource(cfdi);
		// crear el procesador XSLT que nos ayudará a generar la cadena original con base en las reglas del archivo XSLT
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer(sourceXSLT);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		transformer.transform(sourceXML, new StreamResult(output));
		return output.toString();
	}
	
	/**
	 * Genera una llave privada a partir del archivo .key y el password
	 * @param keyFile
	 * @param password
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static PrivateKey generatePrivateKey(final File keyFile, final String password) throws GeneralSecurityException, IOException {
		FileInputStream fis = new FileInputStream(keyFile);		
		org.apache.commons.ssl.PKCS8Key pkcs8key = new org.apache.commons.ssl.PKCS8Key(IOUtils.toByteArray(fis), password.toCharArray());
		return pkcs8key.getPrivateKey();		
	}
	
	/**
	 * 
	 * @param privateKey
	 * @param originalSource
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException 
	 */
	public static String buildOriginalStamp(final PrivateKey privateKey, final String originalSource) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initSign(privateKey);
		signature.update(originalSource.getBytes("UTF-8"));
		byte[] sign = signature.sign();
		return new String(Base64.encodeBase64(sign));
	}	
	
	/**
	 * 
	 * @param xmlFile
	 * @return
	 * @throws IOException
	 */
	public static String readXMLFile(String xmlFile) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(xmlFile));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		String ls = System.getProperty("line.separator");
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}
		// delete the last new line separator
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		reader.close();
		String xml = stringBuilder.toString();
		return xml;
	}
	
	/**
	 * Get a private key in Base64
	 * @param privateKey
	 * @return
	 */
	public static String privateKeyInBase64(PrivateKey privateKey) {
		return new String(Base64.encodeBase64(privateKey.getEncoded()));
	}
}
