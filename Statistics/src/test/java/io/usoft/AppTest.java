package io.usoft;

import com.google.inject.*;
import com.mongodb.MongoClient;
import io.usoft.statistics.Configuration;
import io.usoft.statistics.Event;
import io.usoft.statistics.StatisticsService;
import io.usoft.statistics.StatisticsServiceImpl;

import java.math.BigDecimal;
import java.net.UnknownHostException;

public class AppTest {

	public static void main(String[] args) throws UnknownHostException {

		Injector injector = Guice.createInjector(new StatisticsModule());
		StatisticsService service = injector.getInstance(StatisticsService.class);

		service.store(new Event("balance", BigDecimal.valueOf(100000)));
		service.store(new Event("balance", BigDecimal.valueOf(150000)));
		service.store(new Event("balance", BigDecimal.valueOf(-3400)));

	}
}

class StatisticsModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(MongoClient.class).toProvider(MongoProvider.class).in(Singleton.class);
		bind(Configuration.class).toInstance(new Configuration("test", "statistics"));
		bind(StatisticsService.class).to(StatisticsServiceImpl.class).in(Singleton.class);
	}
}

class MongoProvider implements Provider<MongoClient> {

	@Override
	public MongoClient get() {
		try {
			return new MongoClient();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}
}