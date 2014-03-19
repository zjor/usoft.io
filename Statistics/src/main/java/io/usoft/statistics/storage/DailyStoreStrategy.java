package io.usoft.statistics.storage;

import com.mongodb.DBCollection;
import io.usoft.statistics.Event;
import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Sergey Royz
 * Date: 15.03.2014
 */
public class DailyStoreStrategy extends AbstractStoreStrategy {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final String HOURS_PREFIX = "hours.";

	public DailyStoreStrategy(DBCollection collection) {
		super(collection);
	}

	@Override
	protected String getDocumentId(Event event) {
		return DATE_FORMAT.format(new Date(event.getTimestamp()));
	}

	@Override
	protected String getChildKey(String key, Event event) {
		DateTime time = new DateTime(event.getTimestamp());
		return HOURS_PREFIX + time.getHourOfDay() + "." + key;
	}

	@Override
	protected String getStoreType() {
		return "daily";
	}
}
