package com.guru.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
/**
 * 
 * this marks the field to be serialised into the configuration, the default value of the configuration will be set to whatever the variable has been initialised by
 * if the variable is not defined, and the config does not exist then an error will be thrown
 * @author synte
 * @version 0.0.1
 *
 */
public @interface SerializableField {
    public String path() default ""; //empty path will default to the varaible name
}


