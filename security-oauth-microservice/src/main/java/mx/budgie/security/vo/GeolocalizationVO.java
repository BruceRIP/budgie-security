/**
 * 
 */
package mx.budgie.security.vo;

import java.io.Serializable;

/**
 * @author bruno-rivera
 *
 */
public class GeolocalizationVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long latitude;
	private long longitude;
	
	public GeolocalizationVO() {
		super();
	}

	public GeolocalizationVO(Long latitude, Long longitude) {
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