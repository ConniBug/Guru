package com.guru.reflection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.guru.commands.shop.items.ShopItem;
import com.guru.logger.Logger;

public class ShopScanner {
	
	/**
	 * scan all the commands in the project, instantiate them, as well as inject any values they need, then return all the commands as an arraylist
	 * @param directory to retrieve commands from
	 */
	public List<ShopItem> retrieveCommandsFromPackage(String dir) {

		//list to store the commands
		List<ShopItem> commands = new ArrayList<>();
		
		//create and instance of the reflections api on the specified package
		Reflections reflections = new Reflections(dir);

		//retrieve all classes that are of type command
		Set<Class<? extends ShopItem>> allClasses = reflections.getSubTypesOf(ShopItem.class);
		
		//for each class with each command instance being called clazz
		allClasses.forEach(clazz -> {
			
			//store the instance type ( some implementation of command )
			Class<? extends ShopItem> type = clazz;

			try {
				
				//create a new instance of the implementation, down casting it to command
				ShopItem command = (ShopItem)type.newInstance();
				

				//log entry
				Logger.INFO("Command " + Arrays.toString(command.getMeta().name()) + " has been registered.");
				
				//add the command instance to the arrylist, this is post variable injection, so the command should work as intended
				commands.add(command);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		});
		
		//return the commands
		return commands;
	
	}

}
