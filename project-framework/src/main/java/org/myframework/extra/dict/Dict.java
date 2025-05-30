package org.myframework.extra.dict;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Dict {

    String category() default "";

    String name();
}
