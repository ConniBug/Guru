package com.guru.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

public class JSONUtils {
	
	public static void main(String[] args) {
		
		new JSONUtils().createPath();
		
		
	}

	
	public void createPath() {
		
		String pathOfField = "a.b.c.d";
		String valueOfField = "asd";
		
		String[] path = pathOfField.split("[.]");
		
		String fieldName = (path.length == 0) ? pathOfField : path[path.length - 1];
	
		List<String> route = new ArrayList<>();
		
		for(int i = 0; i < path.length - 1; i++) {
			route.add(path[i]);
		}
		
		System.out.println("pathOfField -> " + pathOfField);
		System.out.println("valueOfField -> " + valueOfField);
		System.out.println("fieldName -> " + fieldName);
		System.out.println("route -> " + Arrays.toString(route.toArray()));
		
		
		JSONObject test = this.createJsonObjectFromPath(route.toArray(), route.size() - 1, fieldName, valueOfField);
		
		System.out.println(test.toString());
		
		
	}
	
	public JSONObject createJsonObjectFromPath(Object[] dirs, int end, String fieldname, String value) {
		
		//we could also just shorten all this and do
		//return (end < 0) ? new JSONObject() : new JSONObject().put(dirs[end].toString(), createJsonObjectFromPath(dirs, end-1));
		
		if(end < 0) {
			return new JSONObject();
		}
		
		JSONObject obj = new JSONObject();
		JSONObject obj2 = obj.put(dirs[end].toString(), createJsonObjectFromPath(dirs, end-1, fieldname, value));
		
		if(end == 1) {
			obj2.put(fieldname, value);
		}
		
		return obj2;
	}
	
	
}
