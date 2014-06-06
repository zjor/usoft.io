package io.usoft.snippets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import com.google.inject.servlet.ServletScopes;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: Sergey Royz
 * Date: 06.06.2014
 */
public class AppGuiceModule extends ServletModule {

	@Override
	protected void configureServlets() {

		bind(Endpoint.class).in(Singleton.class);
		bind(String.class)
				.annotatedWith(Names.named("language"))
				.toProvider(LanguageProvider.class)
				.in(ServletScopes.REQUEST);

		filter("/*").through(GuiceContainer.class);
	}

	protected static class LanguageProvider implements Provider<String> {

		/**
		 * By default, there are several elements available to be injected, each of which is bound in RequestScope:
		 * 	HttpServletRequest / ServletRequest
		 * 	HttpServletResponse / ServletResponse
		 * 	@RequestParameters Map<String, String[]>
		 */
		@Inject
		private HttpServletRequest request;

		@Override
		public String get() {
			return request.getLocale().getLanguage();
		}
	}

}
