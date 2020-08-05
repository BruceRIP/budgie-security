/**
 * 
 */
package mx.budgie.commons.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

import mx.budgie.commons.exception.EndpointException;
import mx.budgie.commons.utils.ClientCode;

/**
 * @author bruno.rivera
 *
 */
@Component
public class BalancedResource {

	private static final Logger LOGGER = LogManager.getLogger(BalancedResource.class);
	
	@Autowired(required = false)
	private LoadBalancerClient loadBalancer;
	
	public String getResource(String microserviceName) throws EndpointException{
		ServiceInstance serviceInstance = loadBalancer.choose(microserviceName);	
		if(serviceInstance != null) {
			return serviceInstance.getUri().toString();
		}
		LOGGER.error("Error: We can´t get the instance {}", microserviceName);
		throw new EndpointException(ClientCode.RESOURCE_NOT_FOUND);
	}
}
