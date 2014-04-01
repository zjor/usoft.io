package io.usoft.commons.beanutils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author: Sergey Royz
 * Date: 02.04.2014
 */
public abstract class FromMapFieldVisitor<A extends Annotation> implements FieldVisitor<A>{

	private Map<String, ?> map;

	protected FromMapFieldVisitor(Map<String, ?> map) {
		this.map = map;
	}

	@Override
	public void visit(A annotation, Field field, Object instance) {
		String key = getKey(annotation);
		if (map.containsKey(key)) {
			try {
				field.set(instance, convertValue(field, map.get(key)));
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public abstract String getKey(A annotation);

	protected Object convertValue(Field field, Object source) {
		return source;
	}
}
