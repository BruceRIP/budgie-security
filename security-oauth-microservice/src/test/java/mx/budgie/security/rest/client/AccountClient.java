/**
 * 
 */
package mx.budgie.security.rest.client;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import mx.budgie.security.constants.SecurityConstants;

/**
 * @author bruno-rivera
 *
 */
public class AccountClient {
	
	HttpHeaders headers;
	RestTemplate restClient;
	String accessToken;
	String refreshToken;
	String usernamePassword = "react:react2";
	String urlAccount = "http://localhost:3001/billers/account/create";
	
	public void configureHeaders(){
		headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
	}
	
	public void configureOauthHttpAuthentication(){
		String encodignBase64 = new String(Base64.encodeBase64(usernamePassword.getBytes()));
		headers.add("Authorization", "Basic " + encodignBase64);
	}

	public void configurehHttpAuthentication(){		
		headers.add("Authentication", "Basic Yy82YXZRTVBNUHhMd2pJSGNmYWdoUT09");
	}
	
	@SuppressWarnings("unchecked")	
	public void createOauthToken(){
		configureOauthHttpAuthentication();
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		restClient = new RestTemplate();
		try{
			ResponseEntity<Object> response = restClient.exchange(SecurityConstants.POST_OAUTH_TOKEN + SecurityConstants.QPM_PASSWORD_GRANT + "UkJhZEVrY1dTZUdmamJ4cVFYdkdrdldTbVhRYUEreURhOVNRLzV4dGcwYz0=", HttpMethod.POST, entity, Object.class);
			if(response != null){
				Map<String, Object> map = (LinkedHashMap<String, Object>)response.getBody();
				accessToken = (String) map.get("access_token");
				refreshToken = (String) map.get("refresh_token");
				System.out.println("******************************************************************");
				System.out.println("** \t access_token: " + map.get("access_token"));
				System.out.println("** \t token_type: " + map.get("token_type"));
				System.out.println("** \t refresh_token: " + map.get("refresh_token"));
				System.out.println("** \t expires_in: " + map.get("expires_in"));
				System.out.println("** \t clientAuthentication: " + map.get("clientAuthentication"));
				System.out.println("******************************************************************");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}		
	}
			
	public void getHelloWorld(){
		headers.add("Content-Type", "application/json");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		restClient = new RestTemplate();
		try{
			ResponseEntity<Object> response = restClient.exchange(SecurityConstants.GET_TEST_HELLO_WORLD + SecurityConstants.QPM_ACCESS_TOKEN + accessToken, HttpMethod.GET, entity, Object.class);
			if(response != null){
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (LinkedHashMap<String, Object>)response.getBody();
				System.out.println("******************************************************************");
				System.out.println("** \t code: " + map.get("code"));
				System.out.println("** \t message: " + map.get("message"));
				System.out.println("******************************************************************");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
		
	public void createTokenFromRefreshToken(){
		configureOauthHttpAuthentication();
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		restClient = new RestTemplate();
		try{
			ResponseEntity<Object> response = restClient.exchange(SecurityConstants.POST_OAUTH_TOKEN + SecurityConstants.QPM_REFRESH_TOKEN + refreshToken, HttpMethod.POST, entity, Object.class);
			if(response != null){
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (LinkedHashMap<String, Object>)response.getBody();				
				System.out.println("******************************************************************");
				System.out.println("** \t access_token: " + map.get("access_token"));
				System.out.println("** \t token_type: " + map.get("token_type"));
				System.out.println("** \t refresh_token: " + map.get("refresh_token"));
				System.out.println("** \t expires_in: " + map.get("expires_in"));				
				System.out.println("******************************************************************");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}	
	}
	
	public void createAccount(){
		configureHeaders();
		configurehHttpAuthentication();		
		restClient = new RestTemplate();
		JSONObject request = new JSONObject();
		request.put("nickname", "BruceRIP");
		request.put("password", "UkJhZEVrY1dTZUdmamJ4cVFYdkdrdldTbVhRYUEreURhOVNRLzV4dGcwYz0=");
		request.put("email", "rip.bruno.dev@hotmail.com");
		request.put("phoneNumber", "5526922661");
		JSONObject location = new JSONObject();
		location.put("latitude", 40.7127837);
		location.put("longitude", -74.00594130000002);
		request.put("registerLocation", location);
		request.put("registrationDevice", "Iphone 6");
		HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
		ResponseEntity<Object> response = restClient.exchange(urlAccount, HttpMethod.POST, entity, Object.class);
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
	
	public void recoverAccount(){
		configureHeaders();
		configurehHttpAuthentication();		
		restClient = new RestTemplate();		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<Object> response = restClient.exchange("http://localhost:2220/billers/account/recover/8", HttpMethod.GET, entity, Object.class);
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
			}
			System.out.println("********************************************************************************");
	}
	
	public void deleteAccount(){
		configureHeaders();
		configurehHttpAuthentication();		
		restClient = new RestTemplate();
		JSONObject request = new JSONObject();		
		request.put("billerID", 6);
		request.put("password", "UkJhZEVrY1dTZUdmamJ4cVFYdkdrdldTbVhRYUEreURhOVNRLzV4dGcwYz0="); //delete
		HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
		ResponseEntity<Object> response = restClient.exchange("http://localhost:2220/billers/account/delete", HttpMethod.POST, entity, Object.class);
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
		request.put("billerID", 8);
		request.put("password", "UkJhZEVrY1dTZUdmamJ4cVFYdkdrdldTbVhRYUEreURhOVNRLzV4dGcwYz0="); // developer
		
		request.put("nickname", "BruceWayne");		
		request.put("email", "rip.bruno.dev@gmail.com");
		request.put("newPassword", "UkJhZEVrY1dTZUdmamJ4cVFYdkdrdldTbVhRYUEreURhOVNRLzV4dGcwYz0=");
		
		HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
		ResponseEntity<Object> response = restClient.exchange("http://localhost:2220/billers/account/update", HttpMethod.POST, entity, Object.class);
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
