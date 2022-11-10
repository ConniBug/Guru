package com.guru.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandMeta {
    public String name(); //empty path will default to the varaible name
    public String permission() default "MESSAGE_SEND"; //empty path will default to the varaible name
    public String description(); //description of command
    public boolean permitBots() default false; //determine if bots are allowed to do this command
    public Category category(); //category of this command
}


