/**
 * 
 */
package mx.budgie.commons.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriTemplate;

/**
 * @author bruno.rivera
 *
 */
public class ClientConfiguration extends BalancedResource{

	/**
	 * Configure a URI from URITemplate, Its use for mapping parameters in a URI
	 * <pre class="code">
	 * For example: http://localhost:8080/hello/{name}?code={code}
	 * </pre>
	 */
	protected UriTemplate uriTemplate;
	
	/**
	 * Complete resource
	 */
	protected URI uri;	
	
	/**
	 * Configure headers
	 */
	protected HttpHeaders headers;
	
	/**
	 * Configure request
	 */
	protected HttpEntity<?> requestEntity;
	
	/**
	 * Setting URI to create instance
	 * @param uri
	 * @throws URISyntaxException
	 */
	protected void settingUri(final String uri) throws URISyntaxException{
		if(uri == null) {
			throw new URISyntaxException("URI is empty", "URI cannot be empty");
		}
		configureURITemplate(uri);
	}
	
	/**
	 * Configure URI from UriTemplate 
	 * @param uri
	 */
	private void configureURITemplate(final String uri) {
		this.uriTemplate = new UriTemplate(uri);		
	}
	
	/**
	 * Configure headers from MAP. 
	 * @param header Key,Value
	 * @return
	 */
	protected void configureHeaders(final Map<String, String> headersMap){					
		if(headers == null) {
			headers = new HttpHeaders();
		}		
		if(headersMap.containsKey("Content-Type")) {
			headers.setContentType(MediaType.parseMediaType(headersMap.get("Content-Type")));
			headersMap.remove("Content-Type");
		}		
		headersMap.forEach((key, value) -> {			
			if(headers.get(key) == null) {
				headers.add(key, value);
			}else {
				headers.remove(key);
				headers.add(key, value);
			}										
		});
		configureEntity();
	}
	
	protected void configureClientTokenAuthentication(final String authenticationToken) {
		if(headers == null) {
			headers = new HttpHeaders();
		}
		headers.add("Authentication", authenticationToken);
	}
	
	protected void configureBearerAuthentication(final String accessToken) {
		if(headers == null) {
			headers = new HttpHeaders();
		}
		headers.add("Authorization", "Bearer " + accessToken);
	}
	
	protected void configureClientBasicAuthentication(final String authenticationToken) {
		if(headers == null) {
			headers = new HttpHeaders();
		}
		headers.add("Authorization", "Basic " + authenticationToken);
	}
	
	/**
	 * Build request body.
	 * @param requestBody
	 * @return this
	 */
	protected void configureEntity(final Object requestBody) {
		requestEntity = new HttpEntity<>(requestBody, headers);
	}
	
	/**
	 * Build request body.
	 * @param requestBody
	 * @return this
	 */
	protected void configureEntity(final Object ... requestBody) {
		if(requestBody != null) {
			requestEntity = new HttpEntity<>(requestBody, headers);
		}
	}
}
