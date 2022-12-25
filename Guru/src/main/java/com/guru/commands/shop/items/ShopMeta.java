package com.guru.commands.shop.items;

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
public @interface ShopMeta {
	/**
	 * @return the list of names for this command, which can be used to run the command
	 */
    public String[] name(); 
    
    /**
     * 
     * @return the cost for this item
     */
    public long cost();
    
    /**
     * 
     * @return the emoji for this item
     */
    public String emoji();
    
    /**
     * 
     * @return the description for this item
     */
    public String description();
    
    /**
     * 
     * @return the id of this item
     */
    public int id();

}


