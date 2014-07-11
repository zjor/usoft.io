package io.usoft.snippets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: Sergey Royz
 * Date: 07.07.2014
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Log {

	public enum LEVEL {
		INFO, DEBUG
	}

	LEVEL level() default LEVEL.INFO;

}
