package io.sharedox.web.controller;

import com.sun.jersey.api.view.Viewable;
import io.sharedox.web.util.HttpUtils;
import io.sharedox.web.util.UserAgentUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * @author: Sergey Royz
 * Date: 22.03.2014
 */
@Slf4j
@Path("/")
public class ShareDoxController {

	@Context
	private HttpServletRequest httpServletRequest;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response index(
			@HeaderParam("User-Agent") String userAgent
	) {
		log.info("User-Agent: {}", userAgent);
		if (UserAgentUtils.isFacebookCrawler(userAgent)) {
			return Response.ok(new Viewable("/index")).build();
		} else {
			String redirectURL = HttpUtils.getBaseURL(httpServletRequest) + "/static/song.mp3";
			log.info("Redirecting to {}", redirectURL);
			return Response.temporaryRedirect(URI.create(redirectURL)).build();
		}
	}


}
