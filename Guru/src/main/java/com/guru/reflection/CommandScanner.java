package com.guru.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.reflections.Reflections;

import com.guru.bot.Guru;
import com.guru.commands.Command;
import com.guru.data.MemoryManagement;
import com.guru.data.SerializableField;
import com.guru.logger.Logger;

public class CommandScanner {
	
	private final String COMMANDS_FOLDER = "commands";
	
	/**
	 * scan all the commands in the project, instantiate them, as well as inject any values they need, then return all the commands as an arraylist
	 * @param directory to retrieve commands from
	 */
	public List<Command> retrieveCommandsFromPackage(String dir) {

		//retrieve the memory management instance from the main program
		MemoryManagement memoryManagement = Guru.getInstance().getManagement();
		
		//list to store the commands
		List<Command> commands = new ArrayList<>();
		
		//create and instance of the reflections api on the specified package
		Reflections reflections = new Reflections(dir);

		//retrieve all classes that are of type command
		Set<Class<? extends Command>> allClasses = reflections.getSubTypesOf(Command.class);
		
		//for each class with each command instance being called clazz
		allClasses.forEach(clazz -> {
			
			//store the instance type ( some implementation of command )
			Class<? extends Command> type = clazz;

			try {
				
				//create a new instance of the implementation, down casting it to command
				Command command = (Command)type.newInstance();

				//used to find the path of the config for this command
				String folder = COMMANDS_FOLDER + ".";
				
				
				for(Field field : type.getDeclaredFields()){
				
					//check if the fields needs a variable inject, by checking for the seializablefield
					for(Annotation annotation : field.getAnnotations()){
						
						//check if the annotation exists.
						if(annotation.annotationType().equals(SerializableField.class)) {

							//inject data to field
							JSONObject json = memoryManagement.retrieveJsonConfiguration(folder + type.getSimpleName());
	
							//retrieve the annotation
							SerializableField fieldData = (SerializableField)annotation;
							
							//find the path of the variable in the json configuration
							String[] path = fieldData.path().split("[.]");
							
							//find the jsonObject storing the variable
							JSONObject loc = json;
							for(int i = 0; i < path.length; i++) {
								if((i+1) < path.length) {
									loc = loc.getJSONObject(path[i]);
								}
							}
							
							//find the value of variable stored in the configuration
							Object newValue = loc.get(path[path.length - 1]);

							//System.out.println(loc + " : " + path[path.length - 1] + " >> " + newValue);
							
							try {
								//set the value for this variable
								FieldUtils.setField(command, field, newValue);
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}
					}
				}
				
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
