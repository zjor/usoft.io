package io.usoft.commons.beanutils;

import lombok.Data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Extracts annotated field values as Map
 * @author: Sergey Royz
 * Date: 02.04.2014
 */
@Data
public abstract class ToMapFieldVisitor<A extends Annotation> implements FieldVisitor<A>{

	private Map<String, Object> map = new HashMap<String, Object>();

	@Override
	public void visit(A annotation, Field field, Object instance) {
		try {
			map.put(getKey(annotation), field.get(instance));
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	protected abstract String getKey(A annotation);
}
