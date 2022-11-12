package com.guru.reflection;

import java.lang.reflect.Field;

import net.dv8tion.jda.api.OnlineStatus;

/**
 * Utility class that changes a field from a class utilsing reflection
 * the supported data types are
 *  - ALL PRIMITIVE DATA TYPES
 *  - String, Integer, Long, Double
 *  - OnlineStatus
 * @author synte
 * @version 0.0.1
 */
public class FieldUtils {
	
	public static void setField(Object instance, Field field, Object data) throws Exception {
		
		//get the type of the field
		Class<?> fieldType = field.getType();
	
		if(fieldType == OnlineStatus.class){
			//make the field accessible if it is final or private
			field.setAccessible(true);
			OnlineStatus status = OnlineStatus.valueOf(data.toString());
			//set the field value
			field.set(instance, status);
			return;
		}
		
		if(fieldType.isPrimitive() || fieldType == String.class || fieldType == Integer.class || fieldType == Long.class || fieldType == Double.class) {
			field.setAccessible(true);
			field.set(instance, data);
			return;
		}
		
		System.err.println("YOU HAVE NOT CONSIDERED THE DATA TYPE " + fieldType.getName() + " PLEASE ADD THIS DATA TYPE");
		System.exit(0);
		
	}

}
