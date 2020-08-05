/**
 * 
 */
package com.budgie.zuul.filters;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.budgie.zuul.util.ZuulUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * @author brucewayne
 *
 */
public class FlowPostFilter extends ZuulFilter {
	
	private final Logger LOGGER = Logger.getLogger(FlowPostFilter.class);	
	
	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 9;
	}

	@Override
	public boolean shouldFilter() {
		LOGGER.info("Executing FLOW-POST filter");
		RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        LOGGER.info(request.getRequestURL().toString());
        return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();  
		HttpServletResponse response = RequestContext.getCurrentContext().getResponse();
		LOGGER.info("FLOW-POST Filter: " + String.format("response's content type is %s", response.getStatus()));
		List<String> urlQueryParam = null;
		if(ctx.getRequestQueryParams() != null) {
			urlQueryParam = ctx.getRequestQueryParams().get("redirect");
		}
		if(response.getStatus() != 200 || urlQueryParam == null || urlQueryParam.isEmpty()) {
			InputStream responseBody = ctx.getResponseDataStream();			
			String result = ZuulUtil.getStringFromInputStream(responseBody);
			ctx.setResponseBody(result);
		}else {
			try {
				String url = urlQueryParam.get(0);					
				URL redirectUrl = null;
				if(!url.contains("http") || !url.contains("https")) {
					redirectUrl = new URL("http://" + url);
				}else {
					redirectUrl = new URL(url);
				}
				ctx.setSendZuulResponse(false);
				ctx.put(FilterConstants.FORWARD_TO_KEY, ctx.getRequest().getRequestURI());
				ctx.setResponseStatusCode(HttpStatus.SC_TEMPORARY_REDIRECT);
				ctx.getResponse().sendRedirect(String.format("%s://%s", redirectUrl.getProtocol(), redirectUrl.getHost()));
			} catch (IOException e) {
				LOGGER.error("Error: ", e);
			}			
		}
        return null;
	}
	
}
