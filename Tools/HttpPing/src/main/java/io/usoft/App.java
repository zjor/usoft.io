package io.usoft;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
	public static void main(String[] args) {
		HttpPingExecutor executor = new HttpPingExecutor();

		List<String> urls = new ArrayList<String>();
		urls.add("http://www.yandex.ru");

		executor.ping(urls);
	}
}

