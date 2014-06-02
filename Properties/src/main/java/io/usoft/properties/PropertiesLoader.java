package io.usoft.properties;

import io.usoft.commons.beanutils.BeanIntrospector;
import io.usoft.commons.beanutils.FieldVisitor;
import io.usoft.properties.annotations.ResourceFileProperties;
import io.usoft.properties.annotations.Value;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @author: Sergey Royz
 * Date: 18.05.2014
 */
public class PropertiesLoader {

	/**
	 *
	 * @param instance
	 * @throws Exception
	 */
	public static <T extends PropertiesHolder> T load(T instance) throws Exception {

		for (Annotation a: instance.getClass().getDeclaredAnnotations()) {
			if (a instanceof ResourceFileProperties) {
				loadFromFile(instance, ((ResourceFileProperties)a).value());
				break;
			}
		}

		return instance;
	}

	private static void loadFromFile(Object instance, String filename) throws Exception {
		final Properties properties = new Properties();
		properties.load(instance.getClass().getClassLoader().getResourceAsStream(filename));

		BeanIntrospector.introspect(instance, Value.class, new FieldVisitor<Value>() {
			@Override
			public void visit(Value annotation, Field field, Object o) {
				Object value = properties.get(annotation.value());
				if (value != null) {
					try {
						if (String.class.equals(field.getType())) {
							field.set(o, value);
						} else if (Integer.TYPE.equals(field.getType())) {
							field.set(o, Integer.valueOf("" + value));
						} else if (Double.TYPE.equals(field.getType())) {
							field.set(o, Double.valueOf("" + value));
						} else if (Boolean.TYPE.equals(field.getType())) {
							field.set(o, Boolean.valueOf("" + value));
						}
					} catch (IllegalAccessException e) {
						throw new RuntimeException("Assign value: " + value + " to a field: " + field);
					}
				}
			}
		});
	}

}
