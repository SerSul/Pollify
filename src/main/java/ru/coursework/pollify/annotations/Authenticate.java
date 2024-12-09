package ru.coursework.pollify.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME)
@Target( value = {ElementType.TYPE, ElementType.METHOD})
public @interface Authenticate {
    String value() default "";
}
