package io.sharedox.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import io.sharedox.controller.SharedoxController;
import io.sharedox.manager.DocumentManager;
import io.sharedox.manager.DocumentManagerImpl;

/**
 * @author: Sergey Royz
 * Date: 30.03.2014
 */
public class SharedoxModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(SharedoxController.class).in(Singleton.class);
		bind(DocumentManager.class).to(DocumentManagerImpl.class).in(Singleton.class);
	}
}
