package com.guru.commands.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;
import com.guru.utils.TimeFormatter;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandMeta(category = Category.ADMIN, description = "lol", name = {"lol"}, usage = {"lol"}, permission = {"ADMINISTRATOR"})
public class AoC extends Command{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {
		
		String date = args[1] + " " + args[2];
		
		String dateString1 = date;
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		Date now = sdf1.parse(dateString1);
		
		
		List<Date> timing = new ArrayList<>();
		
		for(int i = 1; i < 26; i++) {
			
			int day = Integer.parseInt(date.split("/")[0]);
			int month = Integer.parseInt(date.split("/")[1]);
			int year = Integer.parseInt(date.split("/")[2].split(" ")[0]);
			
			int hour = Integer.parseInt(date.split(":")[0].split(" ")[1]);
			
			if(month == 12 && day > 25) {
				year += 1;
			}
			if(day == 25) {
				if(hour >= 5) {
					year += 1;
				}
			}
			String dateString = i + "/12/" + year + " 05:00:00";
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			Date then = sdf.parse(dateString);	
			timing.add(then);
		}
		
		Date closest = timing.get(0);
		
		for(Date i : timing) {
			if(now.before(i)) {
				closest = i;
				break;
			}
		}
		
		long seconds = Math.abs(closest.getTime() - now.getTime());
		
		System.out.println(seconds);
		
		event.getMessage().reply("There is currently " + TimeFormatter.formatDuration(seconds/1000) + " before the next Aoc").queue();
		
		
		
	}

	
}
