package io.sharedox.web.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Sergey Royz
 * Date: 22.03.2014
 */
public class HttpUtils {
	/**
	 * @param request
	 * @return baseURL for request, i.e. {schema}://{host}[:port]/{servletName}
	 */
	public static String getBaseURL(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		return url.replace(uri, contextPath);
	}

}
