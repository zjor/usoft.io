package io.usoft.snippets;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Sergey Royz
 * Date: 06.06.2014
 */
@Path("/")
public class Endpoint {

	@Inject @Named("language")
	private Provider<String> languageProvider;

	@Context
	private HttpServletRequest httpServletRequest;

	@Log
	@GET
	public Response index(@QueryParam("name") String name) {
		return Response.ok().entity("Provided language: " + languageProvider.get() + "<br/>Hello: " + name).build();
	}

	@GET
	@Path("/parameters.xml")
	@Produces(MediaType.APPLICATION_XML)
	public Map<String, String> getParametersAsXml() {
		Map<String, String> params = new HashMap<String, String>();

		for(Object key: httpServletRequest.getParameterMap().keySet()) {
			String value = httpServletRequest.getParameter("" + key);
			params.put("" + key, value);
		}

		return params;
	}

}
