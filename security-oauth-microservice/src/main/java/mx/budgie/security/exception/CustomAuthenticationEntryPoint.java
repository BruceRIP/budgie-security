/**
 * 
 */
package mx.budgie.security.exception;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.budgie.security.catalog.ResponseCode;
import mx.budgie.security.messages.ResponseMessage;

/**
 * @company Budgie Software
 * @author bruce rip
 * @date Jun 15, 2017
 */
@ControllerAdvice
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{	

	private final Logger LOGGER = LogManager.getLogger(getClass());	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,AuthenticationException authException) throws IOException, ServletException {
		LOGGER.info("---- Access was denied for client ----");
		ResponseMessage responseMessage = new ResponseMessage(ResponseCode.ACCESS_DENIED);
		response.setStatus(ResponseCode.ACCESS_DENIED.getHttpStatus().value());
	    OutputStream out = response.getOutputStream();
	    com.fasterxml.jackson.databind.ObjectMapper mapper = new ObjectMapper();
	    mapper.writeValue(out, responseMessage);
	    out.flush();
	}
}
