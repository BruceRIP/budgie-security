/**
 * 
 */
package mx.budgie.billers.accounts.mongo.documents;

import java.io.Serializable;

/**
 * @author bruno-rivera
 *
 */
public class GeolocalizationDocument implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long latitude;
	private Long longitude;

	public GeolocalizationDocument() {
		super();
	}

	public GeolocalizationDocument(Long latitude, Long longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Long getLatitude() {
		return latitude;
	}

	public void setLatitude(Long latitude) {
		this.latitude = latitude;
	}

	public Long getLongitude() {
		return longitude;
	}

	public void setLongitude(Long longitude) {
		this.longitude = longitude;
	}
	
}
