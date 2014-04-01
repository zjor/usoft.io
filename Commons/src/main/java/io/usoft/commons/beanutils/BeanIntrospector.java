package io.usoft.commons.beanutils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author: Sergey Royz
 * Date: 01.04.2014
 */
public class BeanIntrospector {

	/**
	 * Iterates over instance's fields marked with annotation with of given type.
	 * Invokes FieldVisitor on each matched field.
	 * @param instance
	 * @param annotationType
	 * @param visitor
	 * @param <A>
	 */
	public static <A extends Annotation> void introspect(final Object instance, final Class<A> annotationType, final FieldVisitor<A> visitor) {
		Class instanceClass = instance.getClass();

		while (!Object.class.equals(instanceClass)) {

			for (Field field: instanceClass.getDeclaredFields()) {
				field.setAccessible(true);
				A annotation = field.getAnnotation(annotationType);
				if (annotation != null) {
					visitor.visit(annotation, field, instance);
				}
			}

			instanceClass = instanceClass.getSuperclass();
		}

	}

}
