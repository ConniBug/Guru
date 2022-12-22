package com.guru.codewars.kata;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import com.guru.bot.Guru;
import com.guru.logger.Logger;

public class KataCasher {

	private final List<Kata> katas = new ArrayList<>();
	private Thread workerThread = new Thread();
	
	public void casheKatas() {		
		
		this.workerThread = new Thread(() -> {
		
			File files = new File(Guru.getInstance().getManagement().getFiles() ,"ALL KATAS");
			
			int max = 0;
			
			for(File k : files.listFiles()) {
				
				for(File i : k.listFiles()) {
				
					max++;
					
				try {
					
					JSONObject data = new JSONObject(FileUtils.readFileToString(i, "UTF-8"));
					
					Rank rank = new Rank();
					
					
					try {
						rank.setColor(data.getJSONObject("rank").get("color").toString());
						rank.setName(data.getJSONObject("rank").get("name").toString());
						rank.setId(data.getJSONObject("rank").getInt("id"));
					}catch (Exception e) {
						rank.setColor("null");
						rank.setName("null");
						rank.setId(-10);
					}
					
					int totalCompleted = data.getInt("totalCompleted");
					List<String> languages = data.getJSONArray("languages").toList().stream().map(o -> o.toString()).collect(Collectors.toList());
					String published = data.getString("publishedAt");
					int totalAttempts = data.getInt("totalAttempts");
					
					ApprovedBy approvedBy = new ApprovedBy();
					
					if(data.optJSONObject("approvedBy") != null) {
						approvedBy.setUrl(data.getJSONObject("approvedBy").getString("url"));
						approvedBy.setUsername(data.getJSONObject("approvedBy").getString("username"));	
					}
					
					String description = data.getString("description");
					
					String approvedAt = data.get("approvedAt").toString();
					
					
					String url = data.getString("url");
					
					List<String> tags = data.getJSONArray("tags").toList().stream().map(o -> o.toString()).collect(Collectors.toList());
					
					String createdAt = data.getString("createdAt");
					
					Unresolved unresolved = new Unresolved();
					unresolved.setSuggestions(data.getJSONObject("unresolved").getInt("suggestions"));
					unresolved.setIssues(data.getJSONObject("unresolved").getInt("issues"));
					
					int voteScore = data.getInt("voteScore");
					
					//CreatedBy createdBy = new CreatedBy();		
					//createdBy.setUrl(data.getJSONObject("createdBy").getString("url"));
					//createdBy.setUrl(data.getJSONObject("createdBy").getString("username"));
					
					int totalStars = data.getInt("totalStars");
					
					String name = data.getString("name");
					
					String id = data.getString("id");
					String category = data.getString("category");
					String slug = data.getString("slug");
					boolean contributionWanted = data.getBoolean("contributorsWanted");
					
					Kata kata = new Kata(totalCompleted, languages, published, totalAttempts,
							approvedBy, description, approvedAt, url, tags, createdAt,
							unresolved, voteScore, null, totalStars, name, rank,
							id, category, slug, contributionWanted);
					
					katas.add(kata);
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
				
				}
			}
			
			
			Logger.INFO("registered " + katas.size() + " of " + max);
				
		});
		
		this.workerThread.start();
	}
	
	public Future<Optional<Kata>> getKataFromId(String id) {
		return Executors.newSingleThreadExecutor().submit(() -> this.getKatas().get().stream().filter(o -> o.getId().equals(id)).findFirst());
	}
	
	public Future<List<Kata>> getKatas(){
		
		return Executors.newSingleThreadExecutor().submit(() -> {
			
			while(this.workerThread.isAlive()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return KataCasher.this.katas;
			
		});
	
		
	}
	
}
