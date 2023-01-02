package com.guru.commands.admin;

import java.awt.Color;

import com.guru.async.message.AsyncMessageSender;
import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandMeta(category = Category.ADMIN, description = "a developer command used to test certain things", name = {"test", "t"}, usage = {"test"}, permission = {"ADMINISTRATOR"})
public class Test extends Command{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {
		
		AsyncMessageSender.generateUserUpdate(event, (e2) -> {
		
			//do some long task here
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			EmbedBuilder embedBuilder = new EmbedBuilder();
			embedBuilder.setTitle("Test");
			embedBuilder.setColor(Color.cyan);
			embedBuilder.setDescription("We are currently in the process of updating this users data, please hold for a few seconds...");
		
			return embedBuilder;
			
		}).queue();
		
		
		
	}


	
}
