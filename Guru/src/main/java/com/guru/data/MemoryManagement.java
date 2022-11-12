package com.guru.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import com.goru.logger.Logger;
import com.guru.bot.Guru;
import com.guru.reflection.ClazzScanner;

/**
 * responsible for variable injection
 * @author synte
 * @version 0.0.1
 */
public class MemoryManagement {

	private ClazzScanner clazzScanner;

	/**
	 * K = Class name
	 * V = JSON configuration
	 */
	private final Map<String, JSONObject> json = new HashMap<>();
	
	public MemoryManagement(){
		
		try {
		
		//load default json files
		File path = new File(MemoryManagement.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
		File res = new File(path, "configuration");
		
		if(path.getAbsolutePath().contains("target")) {
			//res = new File(this.getClass().getResource("/configuration").getFile());
			res = new File("C:\\Users\\synte\\Desktop\\test123\\configuration");
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
		this.clazzScanner.includeClassInScanner(Guru.getInstance());
		
		for(Object i : objects) {
			this.clazzScanner.includeClassInScanner(i);
		}
		
		this.clazzScanner.inject();
			
	}
	
	public void addJsonFiles(File res) {
		
		//System.out.println("Processing " + res.getAbsoluteFile());
		
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
	
	public JSONObject retrieveJsonConfiguration(String path) {
		return this.json.get(this.json.keySet().stream().filter(path::equalsIgnoreCase).findFirst().get());
	}
	
	
	public ClazzScanner getClazzScanner() {
		return clazzScanner;
	}

	public void setClazzScanner(ClazzScanner clazzScanner) {
		this.clazzScanner = clazzScanner;
	}

	
	
	
	
}
