package io.usoft.statistics.storage;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import io.usoft.statistics.Event;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author: Sergey Royz
 * Date: 15.03.2014
 */
public abstract class AbstractStoreStrategy {

	private static final String KEYS_PREFIX = "keys.";

	private DBCollection collection;

	protected AbstractStoreStrategy(DBCollection collection) {
		this.collection = collection;
	}

	public void store(Event event) {
		DBObject id = new BasicDBObject("_id", getDocumentId(event));

		DBObject update = new BasicDBObject();
		for (Map.Entry<String, BigDecimal> entry: event.getValues().entrySet()) {
			update.put(getRootKey(entry.getKey()), entry.getValue().floatValue());
			update.put(getChildKey(entry.getKey(), event), entry.getValue().floatValue());
		}

		DBObject command = new BasicDBObject();
		command.put("$inc", update);
		command.put("$set", new BasicDBObject("type", getStoreType()));

		collection.update(id, command, true, false);
	}

	private String getRootKey(String key) {
		return KEYS_PREFIX + key;
	}

	protected abstract String getDocumentId(Event event);

	protected abstract String getChildKey(String key, Event event);

	protected abstract String getStoreType();


}
