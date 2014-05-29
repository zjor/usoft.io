package io.usoft;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: Sergey Royz
 * Date: 29.05.2014
 */
@Slf4j
public class HttpPingExecutor {

	public static final int THREAD_POOL_SIZE = 5;

	public void ping(Iterable<String> urls) throws Exception {
		HttpClient httpClient = getHttpClient();
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

		Iterator<String> it = urls.iterator();
		while (it.hasNext()) {
			executor.execute(new HttpPing(httpClient, it.next()));
		}

		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			log.error("Ping was interrupted", e);
		}

	}

	private HttpClient getHttpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		SSLContextBuilder builder = new SSLContextBuilder();
		builder.loadTrustMaterial(null, new TrustStrategy() {
			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		});
		SSLContext sslContext = builder.build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create().register("https", sslsf)
				.build();

		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);

		return HttpClientBuilder.create()
				.setConnectionManager(cm).build();
	}

}
