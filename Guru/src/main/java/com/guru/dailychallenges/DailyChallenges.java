package com.guru.dailychallenges;

import java.awt.Color;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.guru.bot.Guru;
import com.guru.codewars.kata.Kata;
import com.guru.logger.Logger;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class DailyChallenges {
	
	public static final String CHANNEL_ID = "1056111324075147326";
	public static final String GUILD_ID = "1024256989645242421";
	public static final int[] difficulty = {4,5,6,7};


	public void start() {
		
		Guild guild = Guru.getInstance().getJDA().getGuildById(GUILD_ID);
		TextChannel channel = guild.getTextChannelById(CHANNEL_ID);
		
	    ScheduledExecutorService schedulerFirstLesson = Executors.newScheduledThreadPool(1);
	    schedulerFirstLesson.scheduleAtFixedRate(() -> {
	    	
	    	MessageHistory history = MessageHistory.getHistoryFromBeginning(channel).complete();
	    	
	    	
	    	
	    	if(!history.isEmpty() && !channel.getLatestMessageId().equals("0")) {
	    		try {
	    			//history.getRetrievedHistory().get(0);
			    	//channel.retrieveMessageById(channel.getLatestMessageId()).queue(o -> {
			    		
			    		long hours = Math.abs(ChronoUnit.HOURS.between(OffsetDateTime.now(), history.getRetrievedHistory().get(0).getTimeCreated()));
			    		Logger.INFO(hours + "hrs has passed since the last challenge.");
			    		
			    		if(hours > 18) {
					    	try {
					    		this.tryAgain(channel);
					    	}catch (Exception e) {
					    		e.printStackTrace();
					    	}	
			    		}
			    	//});		
	    		}catch (Exception e) {
					try {
						this.tryAgain(channel);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ExecutionException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
	    	}else {
	    		try {
		    		this.tryAgain(channel);
		    	}catch (Exception e) {
		    		e.printStackTrace();
		    	}	
	    	}
			
	    	
	    }, 0L, TimeUnit.MINUTES.toMicros(15), TimeUnit.SECONDS);
		
	}
	
	public void tryAgain(TextChannel channel) throws InterruptedException, ExecutionException {
    	final int diff = difficulty[ThreadLocalRandom.current().nextInt(difficulty.length)];
		
		List<Kata> katas = Guru.getInstance().getKataCasher().getKatas().get().stream()
				
		.filter(o -> o.getLanguages().contains("java"))
		.filter(o -> {
			if(o.getRankObject().getId() == diff*-1) return true;
			return false;
		}).collect(Collectors.toList());
		
		Kata kata = katas.get(ThreadLocalRandom.current().nextInt(katas.size()));
		
		EmbedBuilder embedBuilder = new EmbedBuilder();
		
		embedBuilder.setTitle(kata.getName() + " - " + kata.getRankObject().getName(), kata.getUrl());
		
		switch (kata.getRankObject().getColor()) {
		case "yellow":
			embedBuilder.setColor(Color.yellow);
			break;
		case "blue":
			embedBuilder.setColor(Color.cyan);
			break;
		default:
			break;
		}
		
		String desc = kata.getDescription().replaceAll("<[^>]*>", "");
		
		if(desc.length() > 4000) {
			desc = desc.substring(0, 4000) + " *(" + (desc.length()-4000) + System.lineSeparator() + " more lines... )*";
		}
		
		if(desc.contains("<img src=\"")) {
			embedBuilder.setThumbnail(desc.split("<img src=\"")[1].split("\"")[0]);
		}
		
		if(desc.contains("<img src = '")) {
			embedBuilder.setThumbnail(desc.split("<img src = '")[1].split("'")[0]);
		}
		
		embedBuilder.setDescription(desc.replaceAll("<[^>]*>", ""));
		
		channel.sendMessageEmbeds(embedBuilder.build()).queue();
		
	}
	
	
}
