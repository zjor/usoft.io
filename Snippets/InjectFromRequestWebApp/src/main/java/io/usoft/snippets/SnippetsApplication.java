package io.usoft.snippets;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: Sergey Royz
 * Date: 10.07.2014
 */
public class SnippetsApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(XmlBodyWriter.class);
		return classes;
	}
}
