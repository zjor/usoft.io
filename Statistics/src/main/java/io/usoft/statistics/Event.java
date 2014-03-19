package io.usoft.statistics;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Sergey Royz
 * Date: 14.03.2014
 */
@Data
public class Event {

	public static final BigDecimal DEFAULT_VALUE = BigDecimal.ONE;

	private Map<String, BigDecimal> values = new HashMap<String, BigDecimal>();

	private long timestamp;

	public Event(long timestamp) {
		this.timestamp = timestamp;
	}

	public Event(String key, BigDecimal value, long timestamp) {
		this.timestamp = timestamp;
		values.put(key, value);
	}

	public Event(String key, BigDecimal value) {
		this(key, value, System.currentTimeMillis());
	}

	public Event(String key, long timestamp) {
		this(key, DEFAULT_VALUE, timestamp);
	}

	public Event(String key) {
		this(key, DEFAULT_VALUE, System.currentTimeMillis());
	}

	public void addValue(String key, BigDecimal value) {
		values.put(key, value);
	}

	public void addValue(String key) {
		values.put(key, DEFAULT_VALUE);
	}


}
