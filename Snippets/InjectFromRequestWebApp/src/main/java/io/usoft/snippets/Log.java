package io.usoft.snippets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: Sergey Royz
 * Date: 07.07.2014
 */
@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.METHOD)
public @interface Log {
}
