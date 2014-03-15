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
public class YearlyStoreStrategy extends AbstractStoreStrategy {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy");

	private static final String MONTHS_PREFIX = "months.";

	public YearlyStoreStrategy(DBCollection collection) {
		super(collection);
	}

	@Override
	protected String getDocumentId(Event event) {
		return DATE_FORMAT.format(new Date(event.getTimestamp()));
	}

	@Override
	protected String getChildKey(Event event) {
		DateTime time = new DateTime(event.getTimestamp());
		return MONTHS_PREFIX + time.monthOfYear().getAsText() + "." + event.getKey();
	}

	@Override
	protected String getStoreType() {
		return "yearly";
	}

}
