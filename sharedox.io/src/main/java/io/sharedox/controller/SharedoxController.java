package io.sharedox.controller;

import com.google.inject.Inject;
import com.sun.jersey.api.view.Viewable;
import io.sharedox.Routes;
import lombok.extern.slf4j.Slf4j;
import me.zjor.auth.service.AuthUserService;
import me.zjor.util.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * @author: Sergey Royz
 * Date: 30.03.2014
 */
@Slf4j
@Path(Routes.ROOT)
public class SharedoxController {

	@Inject
	private AuthUserService authUserService;

	@Context
	private HttpServletRequest httpServletRequest;


	/**
	 * Routes to landing page if user was not authorized or to /documents otherwise
	 *
	 * @return
	 */
	@GET
	public Response index() {
		String baseURL = HttpUtils.getBaseURL(httpServletRequest);

		return Response
				.temporaryRedirect(URI.create(baseURL + (authUserService.get() == null ? Routes.LANDING : Routes.DOCUMENTS)))
				.build();
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path(Routes.LANDING)
	public Response landing() {
		return Response.ok(new Viewable("/landing")).build();
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path(Routes.DOCUMENTS)
	public Response documents() {
		return Response.ok("documents: here you have to be authorized").build();
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path(Routes.DOCUMENTS_CREATE)
	public Response createDocumentForm() {
		return Response.ok("create document form").build();

	}


}
