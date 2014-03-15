package io.usoft.statistics;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Sergey Royz
 * Date: 14.03.2014
 */
@Data
public class Event {

	private String key;

	private BigDecimal delta = BigDecimal.ONE;

	private long timestamp;

	public Event(String key) {
		this(key, BigDecimal.ONE);
	}

	public Event(String key, BigDecimal delta) {
		this.key = key;
		this.delta = delta;
		timestamp = new Date().getTime();
	}
}
