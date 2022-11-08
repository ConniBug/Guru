package com.guru.bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TestEvent extends ListenerAdapter {
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		super.onMessageReceived(event);
		
		if(event.getAuthor().isBot()) return;
		event.getChannel().sendMessage("asd").queue();
	}
	
}
