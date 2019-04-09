/**
 * 
 */
package mx.budgie.billers.accounts.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * @author bruno-rivera
 *
 */
public class GeolocalizationVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull	
	private Long latitude = 0l;
	@NotNull	
	private Long longitude = 0l;
	
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