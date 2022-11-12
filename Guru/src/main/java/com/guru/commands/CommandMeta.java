package com.guru.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.guru.reflection.CommandScanner;


/**
 * this class is responsible for storing meta information for commands, this is then used for dynamic initialization 
 * @author synte
 * @version 0.0.1
 * @see CommandScanner#retrieveCommandsFromPackage(String)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandMeta {
	//empty path will default to the varaible name
    public String[] name(); 
    
    //empty path will default to the varaible name
    public String[] permission() default {"MESSAGE_SEND"}; 
    
    //description of command
    public String description();
    
    //example of the arguments of this command.
    public String[] args();
    
    //determine if bots are allowed to do this command
    public boolean permitBots() default false; 
    
    //category of this command
    public Category category(); 
}


