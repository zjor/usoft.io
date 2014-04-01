package io.usoft.commons.beanutils;

import io.usoft.commons.annotations.SerializedName;
import lombok.Data;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author: Sergey Royz
 * Date: 01.04.2014
 */
public class BeanIntrospectorTest {

	private static final FieldVisitor<SerializedName> SET_FIELD_VISITOR = new FieldVisitor<SerializedName>() {
		@Override
		public void visit(SerializedName annotation, Field field, Object instance) {
			try {
				field.set(instance, annotation.value());
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	};

	@Test
	public void shouldVisitAnnotatedPrivateFields() {
		Entity entity = new Entity();
		BeanIntrospector.introspect(entity, SerializedName.class, SET_FIELD_VISITOR);
		assertEquals("first_field", entity.getFirstField());
		assertEquals("second_field", entity.getSecondField());
	}

	@Test
	public void shouldVisitInheritedFields() {
		Child entity = new Child();
		BeanIntrospector.introspect(entity, SerializedName.class, SET_FIELD_VISITOR);
		assertEquals("first_field", entity.getFirstField());
		assertEquals("second_field", entity.getSecondField());
		assertEquals("third_field", entity.getThirdField());
	}

	@Test
	public void shouldHandleEmptyObject() {
		ToMapFieldVisitorImpl visitor = new ToMapFieldVisitorImpl();
		BeanIntrospector.introspect(new Object(), SerializedName.class, visitor);
		assertTrue(visitor.getMap().isEmpty());
	}

	public static class ToMapFieldVisitorImpl extends ToMapFieldVisitor<SerializedName>{

		@Override
		protected String getKey(SerializedName annotation) {
			return annotation.value();
		}
	}

	@Data
	public static class Entity {

		@SerializedName("first_field")
		private String firstField;

		@SerializedName("second_field")
		private String secondField;
	}

	@Data
	public static class Child extends Entity {

		@SerializedName("third_field")
		private String thirdField;

	}

}
