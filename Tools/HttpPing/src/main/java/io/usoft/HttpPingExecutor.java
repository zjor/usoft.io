package io.usoft;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

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

	public static final int THREAD_POOL_SIZE = 10;

	public void ping(Iterable<String> urls) {
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

	private HttpClient getHttpClient() {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setDefaultMaxPerRoute(20);
		cm.setMaxTotal(50);
		return HttpClientBuilder.create().setConnectionManager(cm).build();
	}

}
