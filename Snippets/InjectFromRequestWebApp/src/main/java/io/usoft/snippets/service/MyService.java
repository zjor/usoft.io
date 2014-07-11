package io.usoft.snippets.service;

import io.usoft.snippets.exceptions.MyException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author: Sergey Royz
 * Date: 11.07.2014
 */
@Path("/service")
public interface MyService {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	Response sum(@QueryParam("a") int a, @QueryParam("b") int b);

	@GET
	@Path("/error")
	void causeException() throws MyException;


}
