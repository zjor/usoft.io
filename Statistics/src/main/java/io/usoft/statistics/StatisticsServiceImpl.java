package io.usoft.statistics;

import com.google.inject.Inject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import io.usoft.statistics.storage.DailyStoreStrategy;
import io.usoft.statistics.storage.YearlyStoreStrategy;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Sergey Royz
 * Date: 14.03.2014
 */

@Slf4j
public class StatisticsServiceImpl implements StatisticsService {

	private static final String COLLECTION_PREFIX = "io.usoft.";


	private MongoClient mongoClient;

	private Configuration configuration;

	@Inject
	public StatisticsServiceImpl(MongoClient mongoClient, Configuration configuration) {
		this.mongoClient = mongoClient;
		this.configuration = configuration;
	}

	@Override
	public void storeEvent(Event event) {
		DBCollection collection = getCollection();	//might depend on key in future
		new DailyStoreStrategy(collection).store(event);
		new YearlyStoreStrategy(collection).store(event);
	}

	private DBCollection getCollection() {
		DB db = mongoClient.getDB(configuration.getDatabaseName());
		return db.getCollection(COLLECTION_PREFIX + configuration.getCollectionName());
	}

}
