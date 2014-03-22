package io.sharedox.web.guice;

import com.google.common.collect.ImmutableMap;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * @author: Sergey Royz
 * Date: 22.03.2014
 */
public class GuiceServletModule extends ServletModule {

	@Override
	protected void configureServlets() {

		filter("/*").through(GuiceContainer.class, ImmutableMap.of(
				"com.sun.jersey.config.property.JSPTemplatesBasePath", "/jsp",
				"com.sun.jersey.config.property.WebPageContentRegex", "(/static/.*)|(.*\\.jsp)",
				"com.sun.jersey.api.json.POJOMappingFeature", "true"
		));
	}
}
