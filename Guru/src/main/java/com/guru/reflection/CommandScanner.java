package com.guru.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.reflections.Reflections;

import com.guru.bot.Guru;
import com.guru.commands.Command;
import com.guru.data.MemoryManagement;
import com.guru.data.SerializableField;

public class CommandScanner {
	
	private final String COMMANDS_FOLDER = "commands";
	
	/**
	 * scan all the commands in the project, instantiate them, as well as inject any values they need, then process them via the consumer.
	 * @param consumer
	 */
	public List<Command> retrieveCommandsFromPackage(String dir) {

		MemoryManagement memoryManagement = Guru.getInstance().getManagement();
		
		List<Command> commands = new ArrayList<>();
		
		Reflections reflections = new Reflections(dir);

		Set<Class<? extends Command>> allClasses = reflections.getSubTypesOf(Command.class);
		
		allClasses.forEach(clazz -> {
			
			Class<? extends Command> type = clazz;

			try {
				
				Command command = (Command)type.newInstance();

				String folder = COMMANDS_FOLDER + ".";
				
				for(Field field : type.getDeclaredFields()){
					
					for(Annotation annotation : field.getAnnotations()){
						
						if(annotation.annotationType().equals(SerializableField.class)) {
							
							System.out.println(type);
							
							//inject data to field
							JSONObject json = memoryManagement.retrieveJsonConfiguration(folder + type.getSimpleName());
							
							SerializableField fieldData = (SerializableField)annotation;
							
							String[] path = fieldData.path().split("[.]");
							
							JSONObject loc = json;
							for(int i = 0; i < path.length; i++) {
								if((i+1) < path.length) {
									loc = loc.getJSONObject(path[i]);
								}
							}
							
							Object newValue = loc.get(path[path.length - 1]);
							
							try {
								FieldUtils.setField(clazz, field, newValue);
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}
					}
				}
				commands.add(command);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		});
		
		return commands;
	
	}

}
