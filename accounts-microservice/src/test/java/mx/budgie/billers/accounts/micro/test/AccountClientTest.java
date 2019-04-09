/**
 * 
 */
package mx.budgie.billers.accounts.micro.test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import mx.budgie.billers.accounts.constants.AccountPaths;

/**
 * @author bruno-rivera
 *
 */
public class AccountClientTest {

	HttpHeaders headers;
	RestTemplate restClient;
	String accessToken;
	String refreshToken;
	String url = "http://localhost:2221/";
		
	public void configureHeaders(){
		headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
	}

	public void configurehHttpAuthentication(){		
		headers.add("Authentication", "Basic Yy82YXZRTVBNUHhMd2pJSGNmYWdoUT09");
	}
		
	public void createAccount(){
		configureHeaders();
		configurehHttpAuthentication();		
		restClient = new RestTemplate();
		JSONObject request = new JSONObject();		
		try {
			request.put("nickname", "BrunoRivera");
			request.put("password", "UkJhZEVrY1dTZUdmamJ4cVFYdkdrdldTbVhRYUEreURhOVNRLzV4dGcwYz0=");		
			request.put("email", "rip.bruno.dev@gmail.com");
			request.put("phoneNumber", "5526922661");
			JSONObject location = new JSONObject();
			location.put("latitude", null);
			location.put("longitude", null);
			request.put("registerLocation", location);
			request.put("registrationDevice", "Iphone6");
		} catch (JSONException e) {			
			e.printStackTrace();
		}
		
		HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
		ResponseEntity<Object> response = restClient.exchange(url + AccountPaths.ACCOUNT_BASED_PATH + AccountPaths.ACCOUNT_CREATE, HttpMethod.POST, entity, Object.class);
		Assert.assertNotNull("Response is null", response);
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (LinkedHashMap<String, Object>)response.getBody();				
			System.out.println("********************************************************************************");
			if(map.get("code") != null){
				System.out.println("\t code: " + map.get("code"));
				System.out.println("\t message: " + map.get("message"));
				System.out.println("\t description: " + map.get("description"));
			}else{
				System.out.println("\t billerID: " + map.get("billerID"));
				System.out.println("\t accessToken: " + map.get("accessToken"));
				System.out.println("\t nickname: " + map.get("nickname"));
				System.out.println("\t email: " + map.get("email"));
				System.out.println("\t tokenView: " + map.get("view"));				
			}
			System.out.println("********************************************************************************");
	}
		
	public void recoverAccount(){
		configureHeaders();
		configurehHttpAuthentication();		
		restClient = new RestTemplate();		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<Object> response = restClient.exchange("http://localhost:2221/billers/account/recover/2", HttpMethod.GET, entity, Object.class);
		Assert.assertNotNull("Response is null", response);
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (LinkedHashMap<String, Object>)response.getBody();				
			System.out.println("********************************************************************************");
			if(map.get("code") != null){
				System.out.println("\t code: " + map.get("code"));
				System.out.println("\t message: " + map.get("message"));
				System.out.println("\t description: " + map.get("description"));
			}else{
				System.out.println("\t billerID: " + map.get("billerID"));
				System.out.println("\t nickname: " + map.get("nickname"));
				System.out.println("\t email: " + map.get("email"));
				System.out.println("\t phoneNumber: " + map.get("phoneNumber"));
				System.out.println("\t registerLocation: " + map.get("registerLocation"));
				System.out.println("\t registrationDevice: " + map.get("registrationDevice"));
				System.out.println("\t accessToken: " + map.get("accessToken"));
				System.out.println("\t tokenView: " + map.get("view"));
			}
			System.out.println("********************************************************************************");
	}
	
	public void deleteAccount(){
		configureHeaders();
		configurehHttpAuthentication();		
		restClient = new RestTemplate();
		JSONObject request = new JSONObject();		
		try {
			request.put("billerID", 6);
			request.put("password", "UkJhZEVrY1dTZUdmamJ4cVFYdkdrdldTbVhRYUEreURhOVNRLzV4dGcwYz0="); //delete
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
		ResponseEntity<Object> response = restClient.exchange("http://localhost:2221/billers/account/delete", HttpMethod.POST, entity, Object.class);
		Assert.assertNotNull("Response is null", response);
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (LinkedHashMap<String, Object>)response.getBody();				
			System.out.println("********************************************************************************");
			System.out.println("\t code: " + map.get("code"));
			System.out.println("\t message: " + map.get("message"));
			System.out.println("\t description: " + map.get("description"));
			System.out.println("********************************************************************************");
	}
	
	public void updateAccount(){
		configureHeaders();			
		restClient = new RestTemplate();
		JSONObject request = new JSONObject();
		try {
			request.put("billerID", 2);
			request.put("password", "UkJhZEVrY1dTZUdmamJ4cVFYdkdrdldTbVhRYUEreURhOVNRLzV4dGcwYz0="); // developer
		
			request.put("nickname", "PeriquitoScar");				
			request.put("email", "scarlet.cmd@outlook.com");
			//request.put("newPassword", "UkJhZEVrY1dTZUdmamJ4cVFYdkdrdldTbVhRYUEreURhOVNRLzV4dGcwYz0=");
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		
		HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
		ResponseEntity<Object> response = restClient.exchange("http://localhost:2221/billers/account/update", HttpMethod.POST, entity, Object.class);
		Assert.assertNotNull("Response is null", response);
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (LinkedHashMap<String, Object>)response.getBody();				
			System.out.println("********************************************************************************");
			if(map.get("code") != null){
				System.out.println("\t code: " + map.get("code"));
				System.out.println("\t message: " + map.get("message"));
				System.out.println("\t description: " + map.get("description"));
			}else{
				System.out.println("\t billerID: " + map.get("billerID"));
				System.out.println("\t accessToken: " + map.get("accessToken"));
				System.out.println("\t nickname: " + map.get("nickname"));
				System.out.println("\t email: " + map.get("email"));
			}
			System.out.println("********************************************************************************");
	}
}
