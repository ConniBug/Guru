package com.guru.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * the path where the configuration ( json ) for the class is to be stored
 * @author synte
 * @version 0.0.1
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Configuration {
    public String folder() default ""; //home folder
}


