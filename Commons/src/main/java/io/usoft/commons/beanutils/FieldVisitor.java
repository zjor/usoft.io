package io.usoft.commons.beanutils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author: Sergey Royz
 * Date: 01.04.2014
 */
public interface FieldVisitor<A extends Annotation> {

	public void visit(A annotation, Field field, Object instance);

}
