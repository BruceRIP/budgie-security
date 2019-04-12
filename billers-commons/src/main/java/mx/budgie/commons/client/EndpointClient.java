/**
 * 
 */
package mx.budgie.commons.client;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import mx.budgie.commons.exception.EndpointClientException;
import mx.budgie.commons.exception.EndpointException;

/**
 * @author bruno.rivera
 *
 */
public class EndpointClient extends ClientConfiguration{

	private static final Logger LOGGER = LogManager.getLogger(EndpointClient.class);	
	private int requestTimeout = 30000;
	private RestTemplate restTemplate;	
	private String oAuthToken;		
	
	/**
	 * Create an instance with a specific URI
	 * @param url
	 * @throws EndpointClientException
	 */
	public EndpointClient(final String url) {
		super();		
		try {			
			settingUri(url);
			restTemplate = new RestTemplate(configureRestTemplate(requestTimeout));			
		} catch (URISyntaxException e) {
			LOGGER.error(e.getMessage());
			throw new EndpointClientException(e.getMessage());
		}
	}	
	
	/**
	 * Create an instance with a specific URI and access token
	 * @param url
	 * @param oAuthToken
	 * @throws EndpointClientException
	 */
	public EndpointClient(final String url, final String oAuthToken) {		
		super();
		try {
			if(oAuthToken == null || oAuthToken.isEmpty()) {
				throw new EndpointClientException("oAuth token cannot be null");
			}
			this.oAuthToken = oAuthToken;
			settingUri(url);
			restTemplate = new RestTemplate(configureRestTemplate(requestTimeout));			
		} catch (Exception e) {
			throw new EndpointClientException(e.getMessage());
		}
	}	
	
	/**
	 * Setting headers
	 * @param headersMap
	 * @return this
	 */
	public EndpointClient putHeaders(final Map<String, String> headersMap) {
		configureHeaders(headersMap);
		return this;
	}
	
	/**
	 * Setting authentication token. A specific Authentication token
	 * @param authenticationToken
	 * @return
	 */
	public EndpointClient putAuthenticationToken(final String authenticationToken) {
		configureClientTokenAuthentication(authenticationToken);
		return this;
	}
	
	/**
	 * Setting bearer token. Access Token that was received of a Authorization Server. Authentication Bearer <token>
	 * @param authenticationToken
	 * @return
	 */
	public EndpointClient putBearerAccessToken(final String authenticationToken) {
		configureBearerAuthentication(authenticationToken);
		return this;
	}
	
	/**
	 * Setting Basic Authentication. Authentication Basic <token>
	 * @param authenticationToken
	 * @return
	 */
	public EndpointClient putBasicAuthentication(final String authenticationToken) {
		configureClientBasicAuthentication(authenticationToken);
		return this;
	}
	
	/**
	 * Build request with parameters. It is must required to configure the parameters in the order indicated
	 * @param params
	 * @return this
	 */
	public EndpointClient requestParameters(final Object ... params) {		
		this.uri = this.uriTemplate.expand(params);
		return this;
	}
	
	/**
	 * Build request body.
	 * @param requestBody
	 * @return this
	 */
	public EndpointClient requestBody(final Object requestBody) {
		configureEntity(requestBody);		
		return this;
	}
	
	/**
	 * Build request body.
	 * @param requestBody
	 * @return this
	 */
	public EndpointClient requestBody(final Object ... requestBody) {
		configureEntity(requestBody);		
		return this;
	}
	
	public <T> ResponseEntity<T> callEndpoint(final HttpMethod method, final Class<T> responseType) {
		if(this.uri == null) {
			requestParameters(new Object());
		}		 
		try {
			String uriRequest = null;
			if(this.oAuthToken != null) {
				uriRequest = normalizeURI();
			}else {
				uriRequest = this.uri.toString();
			}
			return executeEndpoint(uriRequest, method, responseType);
		} catch (URISyntaxException | EndpointException e) {
			throw new EndpointClientException(e.getMessage());
		}
	}
	
	public <T> ResponseEntity<T> callPOST(final Class<T> responseType) throws EndpointException{		
		return executeEndpoint(configureUriRequest(), HttpMethod.POST, responseType);		
	}
	
	public <T> ResponseEntity<T> callGET(final Class<T> responseType) throws EndpointException{
		return executeEndpoint(configureUriRequest(), HttpMethod.GET, responseType);		
	}
	
	public <T> ResponseEntity<T> callDELETE(final Class<T> responseType) throws EndpointException{
		return executeEndpoint(configureUriRequest(), HttpMethod.DELETE, responseType);		
	}
	
	public <T> ResponseEntity<T> callPUT(final Class<T> responseType) throws EndpointException{
		return executeEndpoint(configureUriRequest(), HttpMethod.PUT, responseType);		
	}
	
	/**
	 * Exchange data with the endpoint provided by the URL
	 * @param method
	 * @param responseType
	 * @return Specific Type
	 * @throws EndpointClientException
	 */
	public <T> ResponseEntity<T> executeEndpoint(final String uri, final HttpMethod method, final Class<T> responseType) throws EndpointException{
		try {						
			LOGGER.info(" URL = {}", uri);
			LOGGER.info(" Type = {}", method);
			LOGGER.info(" Headers = {}", headers != null ? headers.toString() : headers);
			LOGGER.info(" Request Body = {}",requestEntity != null ? new Gson().toJson(requestEntity.getBody()).replaceAll("\\\\", "") : requestEntity);
			ResponseEntity<T> response = restTemplate.exchange(uri, method, requestEntity, responseType);
			LOGGER.info(" Response StatusCode = {}", response.getStatusCode());			
			LOGGER.info(" Response Body = {}", response.getBody());
			return response;
		}catch(HttpClientErrorException ex) {
			LOGGER.info(" Response StatusCode = {}", ex.getStatusCode());
			LOGGER.info(" Response HttpClientErrorException: {}", ex.getMessage());
			throw new EndpointException(ex.getStatusCode(), ex.getResponseBodyAsString());
		}catch(ResourceAccessException ex) {			
			LOGGER.info(" Response StatusCode = {}", HttpStatus.NOT_FOUND);
			LOGGER.info(" Response ResourceAccessException: {}", ex.getMessage());
			throw new EndpointException(HttpStatus.NOT_FOUND, ex.getMessage());
		}catch(Exception ex) {			
			LOGGER.info(" Response StatusCode = {}", HttpStatus.INTERNAL_SERVER_ERROR);
			LOGGER.info(" Response Exception: {}", ex.getMessage());			
			throw new EndpointException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}
	
	/**
	 * Configure rest template with additional parameters. Request timeout is 3000 milliseconds
	 * @return
	 */
	private HttpComponentsClientHttpRequestFactory configureRestTemplate(final int requestTimeout) {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
	    factory.setConnectTimeout(requestTimeout);
	    factory.setReadTimeout(requestTimeout);	    
	    return factory;
	}

	/**
	 * Normalized URI
	 * @return
	 * @throws URISyntaxException
	 */
	private String normalizeURI() throws URISyntaxException {
		StringBuilder builder = new StringBuilder(this.uri.getScheme()).append("://");		
		builder.append(this.uri.getHost()).append(":");		
		builder.append(this.uri.getPort()).append(this.uri.getPath());		
		builder.append("?").append("access_token=").append(this.oAuthToken);
	    @SuppressWarnings("deprecation")
		List<NameValuePair> result = URLEncodedUtils.parse(this.uri, "UTF-8");
	    String queryParams = "";
	    for (NameValuePair nvp : result) {
	    	queryParams = queryParams + "&" + nvp.getName() + "=" + nvp.getValue();
	    }
	    builder.append(queryParams);	    
	    return builder.toString();
	}
	
	private String configureUriRequest() throws EndpointException{
		if(this.uri == null) {
			requestParameters(new Object());
		}		 
		try {
			String uriRequest = null;
			if(this.oAuthToken != null) {
				uriRequest = normalizeURI();
			}else {
				uriRequest = this.uri.toString();
			}
			return uriRequest;
		} catch (URISyntaxException e) {
			throw new EndpointException(e.getMessage());
		}
	}
}
