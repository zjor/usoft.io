package io.usoft.snippets.jersey.ext;

import io.usoft.snippets.exceptions.MyException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author: Sergey Royz
 * Date: 11.07.2014
 */

@Provider
public class MyExceptionMapper implements ExceptionMapper<MyException> {

	@Override
	public Response toResponse(MyException exception) {
		return Response.status(Response.Status.PRECONDITION_FAILED).entity(exception.getMessage()).build();
	}
}
