/**
 * 
 */
package com.budgie.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * @author brucewayne
 *
 */
public class FlowRouteFilter extends ZuulFilter {
	
	private final Logger LOGGER = Logger.getLogger(FlowRouteFilter.class);

	@Override
	public String filterType() {
		return "route";
	}

	@Override
	public int filterOrder() {
		return 8;
	}

	@Override
	public boolean shouldFilter() {
		LOGGER.info("Executing FLOW-ROUTE filter");		
		RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        LOGGER.info(request.getRequestURL().toString());
        return true;
	}

	@Override
	public Object run() {		
		RequestContext ctx = RequestContext.getCurrentContext();            		
		HttpServletRequest request = ctx.getRequest();		
        LOGGER.info(String.format("FLOW-ROUTE filter %s request to %s", request.getMethod(), request.getRequestURL().toString()));
        return null;
	}
}
