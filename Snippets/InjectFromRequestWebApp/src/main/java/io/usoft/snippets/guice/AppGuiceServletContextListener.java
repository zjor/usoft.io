package io.usoft.snippets.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * @author: Sergey Royz
 * Date: 06.06.2014
 */
public class AppGuiceServletContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new AppGuiceModule());
	}
}
