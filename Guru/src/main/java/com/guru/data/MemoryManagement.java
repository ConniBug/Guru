package com.guru.data;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import com.guru.bot.Guru;
import com.guru.logger.Logger;
import com.guru.reflection.ClazzScanner;

/**
 * responsible for variable injection
 * @author synte
 * @version 0.0.1
 */
public class MemoryManagement {

	private ClazzScanner clazzScanner;
	private File res;

	/**
	 * K = Class name
	 * V = JSON configuration
	 */
	private final Map<String, JSONObject> json = new HashMap<>();
	
	public MemoryManagement(){
		this.load();
	}

	public void load() {
		try {
			
		//load default json files
		File path = new File(MemoryManagement.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
		this.res = new File(path, "configuration");
		
		if(path.getAbsolutePath().contains("target")) {
			//res = new File(this.getClass().getResource("/configuration").getFile());
			this.res = new File("C:\\Users\\synte\\OneDrive - University of Kent\\Desktop\\test123\\configuration");
		}
		
		this.addJsonFiles(res);
		
		this.clazzScanner = new ClazzScanner(this);
		
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void inject() {
		
		//instantiate the reflection scanner
		this.clazzScanner.includeClassInScanner(Guru.getInstance());
		this.clazzScanner.inject();
			
	}
	
	public void inject(Object... objects) {
		
		//instantiate the reflection scanner
		Logger.INFO("Added " + Guru.getInstance().getClass() + " to the scanner.");
		this.clazzScanner.includeClassInScanner(Guru.getInstance());
		
		for(Object i : objects) {
			//Logger.INFO("Added " + i.getClass() + " to the scanner.");
			this.clazzScanner.includeClassInScanner(i);
		}
		
		this.clazzScanner.inject();
			
	}
	
	public void addJsonFiles(File res) {
		
		System.out.println("Processing " + res.getAbsoluteFile());
		
		for(File configuration : res.listFiles()) {
			if(configuration.isDirectory()) {
				this.addJsonFiles(configuration);
				continue;
			}
			
			String name = configuration.getAbsolutePath().split("configuration")[1].replace("\\", ".").substring(1).replace(".json", "");
			
			
			StringBuilder data = new StringBuilder();
			try {
				List<String> lines = FileUtils.readLines(configuration, StandardCharsets.UTF_8);
				lines.forEach(data::append);
				Logger.INFO("Logged configuration > " + name + " > " + lines);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			JSONObject clazzConfiguration = new JSONObject(data.toString());
			this.json.put(name, clazzConfiguration);
		}
		
	}
	
	
	public Map<String, JSONObject> getJson() {
		return json;
	}
	
	public void AddConfigurationIfNeeded(Object clazz, String path) {
		
		try {
		
		if(!this.contains(path)) {
			
			System.err.println("configuration for " + path + " does not exist");
			
			//create the json folder path
			
			File file = res;
			
			for(String dir : path.split("[.]")) {
				file = new File(file, dir);
			}
			
			file = new File(file.getParent(), file.getName() + ".json");
			
			System.err.println("Creating file: " + file.getAbsolutePath());
			
			file.getParentFile().mkdirs();
			file.createNewFile();
			
			JSONObject json = new JSONObject();
			
			JSONObject temp;
			
			for(Field field : clazz.getClass().getDeclaredFields()){
				
				Object value = field.get(clazz);
				temp = new JSONObject();
				
				for(Annotation annotation : field.getAnnotations()){
					
					if(annotation.annotationType().equals(SerializableField.class)) {
						
						SerializableField serializableField = (SerializableField)annotation;
						
						String pathOfField = serializableField.path();
						String[] dirs = pathOfField.split("[.]");
						
						String p = (dirs.length == 0) ? pathOfField : dirs[dirs.length - 1];
						
						for(int i = 0; i < dirs.length - 1; i++) {
							JSONObject route = temp;
							json.put(dirs[i], route);
							temp = route;
						}
						
						temp.put(p, value);
						
						
						System.out.println("want to set value " + pathOfField + " to " + file.getAbsolutePath() + " the key is " + p);
						System.out.println(json);
						
					}
				}
			}
			
			System.exit(0);
			
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public JSONObject retrieveJsonConfiguration(String path) {
		return this.json.get(this.json.keySet().stream().filter(path::equalsIgnoreCase).findFirst().get());
	}
	
	public boolean contains(String path) {
		return this.json.keySet().stream().filter(path::equalsIgnoreCase).count() > 0;
	}
	
	
	public ClazzScanner getClazzScanner() {
		return clazzScanner;
	}

	public void setClazzScanner(ClazzScanner clazzScanner) {
		this.clazzScanner = clazzScanner;
	}

	public File getRes() {
		return res;
	}

	public void setRes(File res) {
		this.res = res;
	}

	
	
	
	
}
