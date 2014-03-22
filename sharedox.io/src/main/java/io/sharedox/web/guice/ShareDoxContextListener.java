package io.sharedox.web.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * @author: Sergey Royz
 * Date: 22.03.2014
 */
public class ShareDoxContextListener extends GuiceServletContextListener {
	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new ShareDoxModule(), new GuiceServletModule());
	}
}
