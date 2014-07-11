package io.usoft.snippets.service;

import io.usoft.snippets.Log;
import io.usoft.snippets.exceptions.MyException;

import javax.ws.rs.core.Response;

/**
 * @author: Sergey Royz
 * Date: 11.07.2014
 */
@Log(level = Log.LEVEL.DEBUG)
public class MyServiceImpl implements MyService {

	@Override
	public Response sum(int a, int b) {

		nop(null);

		return Response.ok().entity("" + (a + b)).build();
	}

	@Override
	public void causeException() throws MyException {
		throw new MyException("Hello world");
	}

	private void nop(String arg) {

	}
}
