package com.guru.reflection;

import java.lang.reflect.Field;

import net.dv8tion.jda.api.OnlineStatus;

public class FieldUtils {
	
	public static void setField(Object instance, Field field, Object data) throws Exception {
		
		Class<?> fieldType = field.getType();

		if(fieldType == OnlineStatus.class){
			field.setAccessible(true);
			OnlineStatus status = OnlineStatus.valueOf(data.toString());
			field.set(instance, status);
			return;
		}
		
		if(fieldType.isPrimitive() || fieldType == String.class) {
			field.setAccessible(true);
			field.set(instance, data);
			return;
		}
		
		System.err.println("YOU HAVE NOT CONSIDERED THE DATA TYPE " + fieldType.getName() + " PLEASE ADD THIS DATA TYPE");
		System.exit(0);
		
	}

}
