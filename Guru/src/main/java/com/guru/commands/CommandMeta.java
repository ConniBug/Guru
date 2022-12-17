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
	/**
	 * @return the list of names for this command, which can be used to run the command
	 */
    public String[] name(); 
    
    /**
     * empty path will default to the variable name
     * @return the permissions required to execute this command
     */
    public String[] permission() default {"MESSAGE_SEND"}; 
    
    /**
     * @return the description for this command
     */
    public String description();
    
    /**
     * wether or not bots are allowed to run this command
     * @return <code>true</code> if bots can use this command otherwise <code>false</code>
     */
    public boolean permitBots() default false; 
    
    /**
     * @return the category of this command
     */
    public Category category(); 
    
    /**
     * the usage for this command, <...> represents essential arguments
     * @return the usage
     */
    public String[] usage(); 
    
    /**
     * the cooldown for this coomand, by default there is no cooldown
     * @return the cooldown
     */
    public long cooldown() default 0L; 


}


