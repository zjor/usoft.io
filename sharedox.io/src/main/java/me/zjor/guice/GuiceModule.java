package me.zjor.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletScopes;
import me.zjor.JpaInitializer;
import me.zjor.auth.UserId;
import me.zjor.auth.UserIdProvider;
import me.zjor.auth.manager.AuthUserManager;
import me.zjor.auth.manager.SocialProfileManager;
import me.zjor.auth.service.AuthUserService;
import me.zjor.auth.service.SocialProfileService;
import me.zjor.auth.service.SocialProfileServiceImpl;
import me.zjor.session.Session;
import me.zjor.session.SessionManager;
import me.zjor.session.SessionService;
import me.zjor.session.expiration.SessionExpirationPolicyChain;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * @author: Sergey Royz
 * @since: 31.10.2013
 */
public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(JpaInitializer.class).asEagerSingleton();

        bind(SessionService.class).asEagerSingleton();
		bind(SessionManager.class).in(Singleton.class);
		bind(SessionExpirationPolicyChain.class).toProvider(SessionExpirationPolicyChainProvider.class).in(Singleton.class);

        bind(AuthUserManager.class).in(Singleton.class);
		bind(SocialProfileManager.class).in(Singleton.class);
        bind(AuthUserService.class).in(Singleton.class);
		bind(SocialProfileService.class).to(SocialProfileServiceImpl.class).in(Singleton.class);

        bind(String.class).annotatedWith(UserId.class).toProvider(UserIdProvider.class).in(ServletScopes.REQUEST);

		bind(HttpClient.class).toProvider(HttpClientProvider.class).in(Singleton.class);

    }

	protected static class SessionExpirationPolicyChainProvider implements Provider<SessionExpirationPolicyChain> {

		private SessionManager sessionManager;

		@Inject
		public SessionExpirationPolicyChainProvider(SessionManager sessionManager) {
			this.sessionManager = sessionManager;
		}

		@Override
		public SessionExpirationPolicyChain get() {
			return new SessionExpirationPolicyChain.ExpireSessionPolicy(sessionManager,
					new SessionExpirationPolicyChain.ExtendSessionPolicy(
							Session.DEFAULT_EXTENSION_PERIOD_MILLIS,
							sessionManager,
							null)
			);
		}
	}

	protected static class HttpClientProvider implements Provider<HttpClient> {

		@Override
		public HttpClient get() {
			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
			cm.setDefaultMaxPerRoute(20);
			cm.setMaxTotal(50);
			return HttpClientBuilder.create().setConnectionManager(cm).build();
		}
	}

}
