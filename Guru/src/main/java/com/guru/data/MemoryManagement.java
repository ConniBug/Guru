package com.guru.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import com.goru.credentials.Credentials;
import com.guru.bot.Guru;
import com.guru.reflection.ClazzScanner;

public class MemoryManagement {

	private ClazzScanner clazzScanner;
	
	
	private Credentials credentials;

	/**
	 * K = Class name
	 * V = JSON configuration
	 */
	private final Map<String, JSONObject> json = new HashMap<>();
	
	public MemoryManagement(){
		
		this.credentials = new Credentials();
		
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
		this.clazzScanner.includeClassInScanner(credentials);
		
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
		return this.json.get(this.json.keySet().stream().filter(path::equals).findFirst().get());
	}
	
	
	public ClazzScanner getClazzScanner() {
		return clazzScanner;
	}
	public Credentials getCredentials() {
		return credentials;
	}
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	public void setClazzScanner(ClazzScanner clazzScanner) {
		this.clazzScanner = clazzScanner;
	}

	
	
	
	
}
