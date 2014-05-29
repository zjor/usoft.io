package io.usoft.properties;

import io.usoft.commons.beanutils.BeanIntrospector;
import io.usoft.commons.beanutils.FieldVisitor;
import lombok.AllArgsConstructor;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @author: Sergey Royz
 * Date: 18.05.2014
 */
@AllArgsConstructor
public class FilePropertiesLoader implements PropertiesLoader {

	private String filename;

	@Override
	public void load(Object instance) throws Exception {

		final Properties properties = new Properties();
		properties.load(FilePropertiesLoader.class.getClassLoader().getResourceAsStream(filename));

		BeanIntrospector.introspect(instance, Value.class, new FieldVisitor<Value>() {
			@Override
			public void visit(Value value, Field field, Object o) {
				String propertyName = value.value();
				if (properties.containsKey(propertyName)) {
					try {
						field.set(o, properties.get(propertyName));
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				}
			}
		});

	}
}
