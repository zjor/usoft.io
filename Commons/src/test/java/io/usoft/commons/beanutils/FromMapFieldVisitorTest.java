package io.usoft.commons.beanutils;

import io.usoft.commons.annotations.SerializedName;
import lombok.Data;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author: Sergey Royz
 * Date: 02.04.2014
 */
public class FromMapFieldVisitorTest {

	@Test
	public void shouldPopulateDataFromMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("first_field", "first_value");
		map.put("second_field", "second_value");

		Entity entity = new Entity();
		BeanIntrospector.introspect(entity, SerializedName.class, new FromMapFieldVisitor<SerializedName>(map) {
			@Override
			public String getKey(SerializedName annotation) {
				return annotation.value();
			}
		});

		assertEquals("first_value", entity.getFirstField());
		assertEquals("second_value", entity.getSecondField());
	}

	@Data
	public static class Entity {

		@SerializedName("first_field")
		private String firstField;

		@SerializedName("second_field")
		private String secondField;
	}

}
