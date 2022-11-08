package com.guru.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.guru.data.MemoryManagement;
import com.guru.data.SerializableField;

import net.dv8tion.jda.api.OnlineStatus;

public class ClazzScanner {
	
	private MemoryManagement memoryManagement;
	
	private final List<Object> serializableClazz = new ArrayList<>();
	
	public ClazzScanner(MemoryManagement memoryManagement) {
		this.memoryManagement = memoryManagement;
	}

	
	
	/**
	 * push database/json values to fields that require it, only permitted to instances added via ClazzScanner#includeClassInScanner
	 */
	public void inject() {
	
		this.serializableClazz.forEach(clazz -> {
			
			Class<?> type = clazz.getClass();
			
			for(Field field : type.getDeclaredFields()){
				
				for(Annotation annotation : field.getAnnotations()){
					
					if(annotation.annotationType().equals(SerializableField.class)) {
						
						//inject data to field
						JSONObject json = memoryManagement.retrieveJsonConfiguration(type);
						
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
							this.setField(clazz, field, newValue);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				}
			}
			
		});
	
	}
	
	public void setField(Object instance, Field field, Object data) throws Exception {
		
		Class<?> fieldType = field.getType();

		if(fieldType == OnlineStatus.class){
			field.setAccessible(true);
			OnlineStatus status = OnlineStatus.valueOf(data.toString());
			field.set(instance, status);
			return;
		}
		
		if(fieldType == String.class) {
			field.setAccessible(true);
			field.set(instance, data);
			return;
		}
		
		System.err.println("YOU HAVE NOT CONSIDERED THE DATA TYPE " + fieldType.getName() + " PLEASE ADD THIS DATA TYPE");
		System.exit(0);
		
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
