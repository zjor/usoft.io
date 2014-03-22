package io.sharedox.web.guice;

import io.sharedox.web.controller.ShareDoxController;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * @author: Sergey Royz
 * Date: 22.03.2014
 */
public class ShareDoxModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ShareDoxController.class).in(Singleton.class);
	}
}
