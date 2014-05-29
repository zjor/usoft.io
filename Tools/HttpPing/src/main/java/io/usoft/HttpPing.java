package io.usoft;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * @author: Sergey Royz
 * Date: 29.05.2014
 */

@Slf4j
public class HttpPing implements Runnable {

	private HttpClient httpClient;
	private String url;

	public HttpPing(HttpClient httpClient, String url) {
		this.httpClient = httpClient;
		this.url = url;
	}

	@Override
	public void run() {
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(request);
			EntityUtils.consume(response.getEntity());
			if (response.getStatusLine().getStatusCode() != HttpURLConnection.HTTP_OK) {
				log.warn("{} -> {}", url, response.getStatusLine());
			} else {
				log.info("{} -> OK", url);
			}
		} catch (IOException e) {
			log.error("Failed to request URL: {}", url, e);
		}
	}
}
