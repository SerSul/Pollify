package ru.coursework.pollify.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME)
@Target( {ElementType.TYPE, ElementType.FIELD, ElementType.METHOD} )
public @interface Meta {
    String title();
    String comment() default "";
    boolean censored() default false;
    boolean ignoreChanges() default false;
}

