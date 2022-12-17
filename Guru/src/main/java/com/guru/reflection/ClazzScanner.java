package com.guru.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.guru.data.Configuration;
import com.guru.data.MemoryManagement;
import com.guru.data.SerializableField;
import com.guru.logger.Logger;

public class ClazzScanner {
	
	private MemoryManagement memoryManagement;
	
	private final List<Object> serializableClazz = new ArrayList<>();
	
	public ClazzScanner(MemoryManagement memoryManagement) {
		this.memoryManagement = memoryManagement;
	}

	
	
	/**
	 * push json values to fields that require it, only permitted to instances added via ClazzScanner#includeClassInScanner
	 */
	public void inject() {
		
		this.serializableClazz.forEach(clazz -> {
			
			Class<?> type = clazz.getClass();
			
			if(type.isAnnotationPresent(Configuration.class)) {
			
				String folder = type.getDeclaredAnnotation(Configuration.class).folder();
				folder = folder.length() <= 1 ? "" : folder + ".";
				
				for(Field field : type.getDeclaredFields()){
					
					for(Annotation annotation : field.getAnnotations()){
						
						if(annotation.annotationType().equals(SerializableField.class)) {
							
							Logger.INFO("injecting to " + clazz);
							
							
							String dir = folder + type.getSimpleName().toLowerCase();
							
							//inject data to field
							memoryManagement.AddConfigurationIfNeeded(clazz, dir);
							
							JSONObject json = memoryManagement.retrieveJsonConfiguration(dir);
							
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
			
			}
			
		});
	
	}
	
	/**
	 * adds an instance to the scanner, this will allow the added class to utilize SerializableField, which will allow for value injection to variables that need it
	 * @param object to be scanned
	 */
	public void includeClassInScanner(Object clazz) {
		this.serializableClazz.add(clazz);
	}

	public MemoryManagement getMemoryManagement() {
		return memoryManagement;
	}
	
}
