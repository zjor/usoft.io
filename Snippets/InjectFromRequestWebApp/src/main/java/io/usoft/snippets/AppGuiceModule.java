package io.usoft.snippets;

import com.google.inject.Inject;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import com.google.inject.servlet.ServletScopes;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.MDC;

import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author: Sergey Royz
 * Date: 06.06.2014
 */
public class AppGuiceModule extends ServletModule {

	@Override
	protected void configureServlets() {

		bind(Endpoint.class)
				.in(Singleton.class);

		bind(String.class)
				.annotatedWith(Names.named("sessionId"))
				.toProvider(SessionIdProvider.class)
				.in(ServletScopes.REQUEST);

		bind(String.class)
				.annotatedWith(Names.named("language"))
				.toProvider(LanguageProvider.class)
				.in(ServletScopes.REQUEST);

		bindInterceptor(
				Matchers.any(),
				Matchers.annotatedWith(Log.class),
				new LoggingInterceptor(getProvider(Key.get(String.class, Names.named("sessionId")))));

		filter("/*").through(GuiceContainer.class);
	}

	protected static class SessionIdProvider implements Provider<String> {

		@Inject
		private HttpServletRequest request;

		@Override
		public String get() {
			return request.getSession().getId();
		}
	}

	@Slf4j
	protected static class LoggingInterceptor implements MethodInterceptor {

		private Provider<String> sessionId;

		public LoggingInterceptor(Provider<String> sessionId) {
			this.sessionId = sessionId;
		}

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			MDC.put("sessionId", sessionId.get());

			String methodName = invocation.getMethod().getName();
			log.info("{}: => {}", methodName, Arrays.toString(invocation.getArguments()));
			Object result = invocation.proceed();
			log.info("{}: <= {}", methodName, result);
			return result;
		}
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
