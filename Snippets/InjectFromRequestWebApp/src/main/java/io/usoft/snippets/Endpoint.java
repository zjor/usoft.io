package io.usoft.snippets;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author: Sergey Royz
 * Date: 06.06.2014
 */
@Path("/")
public class Endpoint {

	@Inject @Named("language")
	private Provider<String> languageProvider;

	@GET
	public Response index() {
		return Response.ok().entity("Provided language: " + languageProvider.get()).build();
	}

}
