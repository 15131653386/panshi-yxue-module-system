package com.panshi.log.anno;

import java.lang.annotation.*;

@Documented
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface YxueLog {
    String value() default "";
    String name() default "";
    String tableName() default "";
}
