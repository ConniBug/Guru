package com.guru.commands;

import com.guru.bot.Guru;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public abstract class Command extends ListenerAdapter{

	protected CommandManager commandManager;
	
	public Command() {
		Guru.getInstance().getSharedManager().addEventListeners(this);
		this.commandManager = Guru.getInstance().getCommandManager();
	}
	
	public CommandMeta getMeta() {
		return this.getClass().getAnnotation(CommandMeta.class);
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		if(event.getAuthor().isBot() && !this.getMeta().permitBots()) return;
		
		String prefix = Guru.getInstance().getStartupConfiguration().getCommandPrefix() + this.getMeta().name();
		//System.out.println(event.getMember().getEffectiveName());
		//System.out.println(prefix + " : " + event.getMessage().getContentDisplay());
		
		
		if(event.getMessage().getContentRaw().startsWith(prefix)) {
			
			if(event.getMember().hasPermission(Permission.valueOf(this.getMeta().permission()))) {
				
				this.onCommand(event);
				
			}
			
		}

	}
	
	public abstract void onCommand(MessageReceivedEvent event);
	
}
