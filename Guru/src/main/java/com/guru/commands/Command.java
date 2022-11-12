package com.guru.commands;

import java.awt.Color;

import com.guru.bot.Guru;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * This is the parent class for every type of command
 * @author synte
 * @version 0.0.1
 *
 */
public abstract class Command extends ListenerAdapter{

	//store an instance of the command manager as we may require command information
	protected CommandManager commandManager;
	
	public Command() {
		//register this instance of command to the evenet listener
		Guru.getInstance().getSharedManager().addEventListeners(this);
		
		//Initialize the commandmanager;
		this.commandManager = Guru.getInstance().getCommandManager();
	}
	
	/**
	 * @return meta information for this command, defined in the commandmeta annotation for the class
	 */
	public CommandMeta getMeta() {
		return this.getClass().getAnnotation(CommandMeta.class);
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event){
		
		try {
		
		//don't allow this command to be processed for bots, if the command in question doesnt allow bots
		if(event.getAuthor().isBot() && !this.getMeta().permitBots()) return;
		
		for(String alternative : this.getMeta().name()) {

			//prefix for the command, by taking the command prefix + the name of this command
			String prefix = Guru.getInstance().getStartupConfiguration().getCommandPrefix() + alternative;
			
			String[] rawMessage = event.getMessage().getContentRaw().split(" ");

			//check if the first argument is the command
			if(rawMessage[0].equalsIgnoreCase(prefix)) {
				
				for(String permission : this.getMeta().permission()) {
				
					//check if the user has the required permissions to execute this command
					if(event.getMember().hasPermission(Permission.valueOf(permission))) {
						
						//run the command
						this.onCommand(event, rawMessage);
						return;
						
					}
					
				}
				
			}
			
		}
		
		}catch (Exception e) {
			e.printStackTrace();
			
			EmbedBuilder errorEmbed = new EmbedBuilder();
			
			errorEmbed.setTitle("Error handler");
			errorEmbed.setColor(Color.cyan);
			//builder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
			errorEmbed.setDescription("Sorry and error has accured, please look below for details");
			
			errorEmbed.addField("Response", "```" + e.getMessage() + "```", false);
			errorEmbed.setFooter("if this seems unexpected, please contact @syntex#1389");
			
			event.getMessage().replyEmbeds(errorEmbed.build()).queue();
		}

	}
	
	/**
	 * this method runs when the command has been executed.
	 * @param MessageReceivedEvent of the message
	 */
	public abstract void onCommand(MessageReceivedEvent event, String[] args) throws Exception;
	
}
