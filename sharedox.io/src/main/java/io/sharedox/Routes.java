package io.sharedox;

import java.util.regex.Pattern;

/**
 * @author: Sergey Royz
 * Date: 30.03.2014
 */
public class Routes {

	public static final String ROOT = "/";
	public static final String LANDING = "/landing";

	public static Pattern[] PUBLIC_ROUTE_PATTERNS = new Pattern[] {
			Pattern.compile("/static/.*"),
			Pattern.compile(ROOT + "/?"),
			Pattern.compile(LANDING + "/?"),
			Pattern.compile("/public/?.*")
	};

}
