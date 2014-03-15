package io.usoft.statistics.storage;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import io.usoft.statistics.Event;

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
		update.put(getRootKey(event), event.getDelta().floatValue());
		update.put(getChildKey(event), event.getDelta().floatValue());

		DBObject command = new BasicDBObject();
		command.put("$inc", update);
		command.put("$set", new BasicDBObject("type", getStoreType()));

		collection.update(id, command, true, false);
	}

	private String getRootKey(Event event) {
		return KEYS_PREFIX + event.getKey();
	}

	protected abstract String getDocumentId(Event event);

	protected abstract String getChildKey(Event event);

	protected abstract String getStoreType();


}
