/**
 * 
 */
package mx.budgie.billers.packages.mongo.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author brucewayne
 *
 */
@Configuration
//@PropertySource("classpath:package-repository.yml")
public class PackageMongoYmlProperties {

	@Autowired
	private Environment environment;
	
	public String getProperty(String nameProperty){
		return environment.getProperty(nameProperty);
	}
}
