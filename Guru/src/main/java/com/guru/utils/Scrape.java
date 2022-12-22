package com.guru.utils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

public class Scrape {

	private File root = new File("C:\\Users\\synte\\OneDrive - University of Kent\\Desktop\\test123\\configuration\\katas1");
	
	public void run() {
		
		List<String> o;

		try {
			
			o = FileUtils.readLines(new File(Scrape.class.getResource("ids.txt").getFile()), "UTF-8").stream().distinct().collect(Collectors.toList());
			
			ExecutorService service = Executors.newFixedThreadPool(1);
			
			AtomicInteger counter = new AtomicInteger(5000);
			
			for(int i = 5000; i < o.size(); i++) {
				
				final int index = i;
				
				// now submit our jobs
				service.submit(new Runnable() {
				    public void run() {
				    	
				    	try {   		
							String id = o.get(index);
							
							String json = Network.readURL("https://www.codewars.com/api/v1/code-challenges/" + id);
							JSONObject obj = new JSONObject(json);
							
							String rank = "";
							
							if(obj.getJSONObject("rank").get("name").toString().equals("null")) {
								rank = "Unrated";
							}else {
								rank = obj.getJSONObject("rank").get("name").toString();
							}
							
							File loc = new File(root, rank);
							File target = new File(loc, obj.getString("slug") + ".json");
				
							FileUtils.write(target, obj.toString(3), StandardCharsets.UTF_8);
							
							System.out.println("Completed " + counter.incrementAndGet() + "/" + o.size() + " -> " + ((counter.get()*1.0)/o.size()));
							
				    	}catch (Exception e) {
							e.printStackTrace();
						}
				    	
				    }
				});
					
			}
			
			
			// when no more to submit, call shutdown, submitted jobs will continue to run
			service.shutdown();
			// now wait for the jobs to finish
			service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void getIds() {

		String o;
		try {
	
			o = FileUtils.readFileToString(new File(Scrape.class.getResource("content.txt").getFile()), "UTF-8");	
			
			StringBuilder a = new StringBuilder();
			
			for(String k : o.split("data-id=\"")) {
				String d = k.split("\"")[0];
				
				if(!(d.startsWith("5") || d.startsWith("6"))) {
					continue;
				}
				
				System.out.println(d);
				a.append(d + System.lineSeparator());
			}
			
			File target = new File(root, "CONTENT.json");
			
			FileUtils.write(target, a.toString(), StandardCharsets.UTF_8);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Scrape().run();
	}
	
}
