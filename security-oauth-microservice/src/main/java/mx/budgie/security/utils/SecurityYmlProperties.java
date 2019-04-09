/**
 * 
 */
package mx.budgie.security.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @company Budgie Software
 * @author brucewayne
 * @date Jun 25, 2017
 */
@Configuration
public class SecurityYmlProperties {

	@Autowired
	private Environment environment;
	
	public String getProperty(String nameProperty){
		return environment.getProperty(nameProperty);
	}
	
}
