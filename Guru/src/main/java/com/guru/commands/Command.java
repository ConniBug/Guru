package com.guru.commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.guru.bot.Guru;
import com.guru.userdata.UserModel;
import com.guru.utils.TimeFormatter;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
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
	
	private boolean available;
	
	private List<Cooldown> cooldowns;
	
	public Command() {
		//register this instance of command to the evenet listener
		Guru.getInstance().getSharedManager().addEventListeners(this);
		
		//Initialize the commandmanager;
		this.commandManager = Guru.getInstance().getCommandManager();
		
		this.available = true;
		
		this.cooldowns = new ArrayList<>();
	}
	
	/**
	 * @return meta information for this command, defined in the commandmeta annotation for the class
	 */
	public CommandMeta getMeta() {
		return this.getClass().getAnnotation(CommandMeta.class);
	}
	
	//public abstract List<Argument> options();
	
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
				
						if(!this.available && !event.getAuthor().getId().equals("234004050201280512")) {
							this.logError(event, "Sorry, this command is not yet ready, only sir syntex may execute this command.");
							return;
						}
						
						Optional<Cooldown> cooldowns = this.cooldowns.stream().filter(o -> o.getUser().equals(event.getAuthor().getId())).findFirst();
						
						if(cooldowns.isPresent()) {
							Cooldown cooldown = cooldowns.get();
							if(cooldown.timeRemaining() < 0) {
								this.cooldowns.remove(cooldown);
							}else {
								this.logError(event, "Sorry, please wait " + TimeFormatter.formatDuration(cooldown.timeRemaining()/1000) + " before you can do this command again");
								return;
							}
						}
						
						//run the command
						this.onCommand(event, rawMessage, Guru.getInstance().getUsersHandler().getUserData(event.getAuthor().getId()));
						
						this.cooldowns.add(new Cooldown(event.getAuthor().getId(), this, new Date()));
						
						return;
						
					}else {
						
						this.logError(event, "You require the permissions " + Arrays.toString(this.getMeta().permission()) + " in order to execute this command");
						
					}
					
				}
				
			}
			
		}
		
		}catch (Exception e) {
			e.printStackTrace();
			this.logError(event, e.getMessage());
		}

	}
	
	protected void logError(MessageReceivedEvent event, String response) {

		EmbedBuilder errorEmbed = new EmbedBuilder();
		
		errorEmbed.setTitle("Error handler");
		errorEmbed.setColor(Color.red);
		//builder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
		errorEmbed.setDescription("Sorry and error has accured, please look below for details");
		
		errorEmbed.addField("Response", "```" + response + "```", false);
		errorEmbed.setFooter("if this seems unexpected, please contact @syntex#1389");
		
		event.getMessage().replyEmbeds(errorEmbed.build()).queue();
	}
	
	/**
	 * this method has been temporarily removed.
	 * 
	 */
	@Override
	@Deprecated
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		// TODO Auto-generated method stub
		super.onSlashCommandInteraction(event);
		
		
		try {
			
			//this.onSlashCommand(event);
			//GenericMessageSendEvent e = new GenericMessageSendEvent(event);
			//String[] args = event.getCommandString().substring(1).split(" ");
			//this.onCommand(event, args);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			EmbedBuilder errorEmbed = new EmbedBuilder();
			
			errorEmbed.setTitle("Error handler");
			errorEmbed.setColor(Color.cyan);
			//builder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
			errorEmbed.setDescription("Sorry and error has accured, please look below for details");
			
			errorEmbed.addField("Response", "```" + e.getMessage() + "```", false);
			errorEmbed.setFooter("if this seems unexpected, please contact @syntex#1389");
			
			event.replyEmbeds(errorEmbed.build()).queue();
		}
	}
	
	
	/**
	 * this method runs when the command has been executed.
	 * @param userModel 
	 * @param MessageReceivedEvent of the message
	 */
	public abstract void onCommand(MessageReceivedEvent event, String[] args, UserModel userModel) throws Exception;

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	
}
