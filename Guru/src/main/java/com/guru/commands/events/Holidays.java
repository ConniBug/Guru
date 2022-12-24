package com.guru.commands.events;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;
import com.syntex.modals.Holiday;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandMeta(category = Category.HOLIDAYS, description = "displays the amount of days remaining until the next holiday", name = {"holiday", "holidays"}, usage = {"holiday"})
public class Holidays extends Command{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {
		event.getMessage().replyEmbeds(this.exec()).queue();;
	}

	public MessageEmbed exec() {
		List<Holiday> holidays = new ArrayList<>();
		
		holidays.add(new Holiday("19/12/2022", "15/01/2023", "Christmas Day"));
		holidays.add(new Holiday("10/04/2023", "07/05/2023", "Holiday"));
		holidays.add(new Holiday("19/06/2023", "06/08/2023", "Holiday"));
		
		
		for(Holiday i : holidays) {
		
			 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			 
		        try {

		            Date start = formatter.parse(i.getStartDate());
		           //Date end = formatter.parse(i.getEndDate());
		            Date now = new Date();
		            
		            if(start.after(now)) {
		            	
		            	long days = (start.getTime() - now.getTime())/(1000*60*60*24);
		            	
		            	EmbedBuilder builder = new EmbedBuilder();
		    			
		    			builder.setTitle(i.getName());
		    			builder.setColor(Color.cyan);
		    			builder.setDescription(i.getName() + " break will begin in " + days + " days.");
		    			
		    			return builder.build();
	
		            }
		            

		        } catch (ParseException e) {
		            e.printStackTrace();
		        }
			
		}
		
		EmbedBuilder builder = new EmbedBuilder();
		
		builder.setTitle("N/A");
		builder.setColor(Color.cyan);
		builder.setDescription("no holiday?!");
		
		return builder.build();
		
		
	}
	
}
