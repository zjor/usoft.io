package io.sharedox;

import java.util.regex.Pattern;

/**
 * @author: Sergey Royz
 * Date: 30.03.2014
 */
public class Routes {

	public static final String ROOT = "/";
	public static final String LANDING = "/landing";
	public static final String DOCUMENTS = "/documents";
	public static final String DOCUMENTS_CREATE = "/documents/create";

	public static Pattern[] PUBLIC_ROUTE_PATTERNS = new Pattern[] {
			Pattern.compile(ROOT + "/?"),
			Pattern.compile(LANDING + "/?")
	};

}
