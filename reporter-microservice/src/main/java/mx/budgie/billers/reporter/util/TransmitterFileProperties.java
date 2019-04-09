/**
 * 
 */
package mx.budgie.billers.reporter.util;

import java.io.File;
import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author bruce
 *
 */
public class TransmitterFileProperties implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File fileXML;
	private File filePDF;
	private String cfdiXML;
	private String RFC;
	private String folio;
	private String transmitterEmail;
	private String receiverEmail;
	
	public File getFileXML() {
		return fileXML;
	}
	public void setFileXML(File fileXML) {
		this.fileXML = fileXML;
	}
	public String getRFC() {
		return RFC;
	}
	public void setRFC(String rFC) {
		RFC = rFC;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getTransmitterEmail() {
		return transmitterEmail;
	}
	public void setTransmitterEmail(String transmitterEmail) {
		this.transmitterEmail = transmitterEmail;
	}
	public String getReceiverEmail() {
		return receiverEmail;
	}
	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}
	public File getFilePDF() {
		return filePDF;
	}
	public void setFilePDF(File filePDF) {
		this.filePDF = filePDF;
	}	
	
	public String getCfdiXML() {
		return cfdiXML;
	}
	public void setCfdiXML(String cfdiXML) {
		this.cfdiXML = cfdiXML;
	}
	public JSONObject toJSONString() {
		JSONObject json = new JSONObject();
		try {
			json.put("fileXMLAbsolutePath", fileXML.getAbsolutePath());
			json.put("filePDFAbsolutePath", filePDF.getAbsolutePath());
			json.put("fileXMLName", fileXML.getName());
			json.put("filePDFName", filePDF.getName());
			json.put("cfdiXML", cfdiXML);
			json.put("RFC", RFC);
			json.put("folio", folio);
			json.put("transmitterEmail", transmitterEmail);
			json.put("receiverEmail", receiverEmail);
			return json;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
