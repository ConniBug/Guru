package com.guru.commands;

import java.awt.Color;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.guru.bot.Guru;
import com.guru.commands.codewars.CodewarsCommand;
import com.guru.credentials.Developers;
import com.guru.paged.PagedEmbed;
import com.guru.reflection.CommandScanner;
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
	 * these fields are checked during runtime by the command scanner reflections class
	 * @return meta information for this command, defined in the commandmeta annotation for the class
	 * @see CommandScanner#retrieveCommandsFromPackage(String)
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
					if(event.getMember().hasPermission(Permission.valueOf(permission)) || Developers.isDeveloper(event.getAuthor())) {
				
						//only let me ( syntex ) do the command if the command is not available
						if(!this.available && !event.getAuthor().getId().equals("234004050201280512")) {
							this.logError(event, "Sorry, this command is not yet ready, only sir syntex may execute this command.");
							return;
						}
						
						//retrieve the cooldown for the user if any
						Optional<Cooldown> cooldowns = this.cooldowns.stream().filter(o -> o.getUserID().equals(event.getAuthor().getId())).findFirst();
						
						//prevent usage of this command if a cooldown exists, remove the cooldown
						//if the cooldown is below 0, as we add a cooldown whenever the commmand
						//is executed, developers are excempt
						if(cooldowns.isPresent() && !Developers.isDeveloper(event.getAuthor())) {
							Cooldown cooldown = cooldowns.get();
							if(cooldown.timeRemaining() < 0) {
								this.cooldowns.remove(cooldown);
							}else {
								this.logError(event, "Sorry, please wait " + TimeFormatter.formatDuration(cooldown.timeRemaining()/1000) + " before you can do this command again");
								return;
							}
						}
						
						if(this instanceof CodewarsCommand) {
							//run the codewars command, same as command for now
							this.onCommand(event, rawMessage);
						}else {
							//run the command
							this.onCommand(event, rawMessage);	
						}
						
						
						//add the cooldown
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
			this.logError(event, e.getMessage(), e);
		}

	}
	
	/**
	 * a convenient error message sender, which uses a premade error template and then sends out the error
	 * @param event
	 * @param response
	 * @param e 
	 */
	protected void logError(MessageReceivedEvent event, String response) {

		EmbedBuilder errorEmbed = new EmbedBuilder();
		
		errorEmbed.setTitle("Error handler");
		errorEmbed.setColor(Color.red);
		errorEmbed.setDescription("Sorry an error has accured, please look below for details");
		
		errorEmbed.addField("Response", "```" + response + "```", false);
		
		int trace = 2;
		
		errorEmbed.addField("Location", "```" + Thread.currentThread().getStackTrace()[trace] + "```", false);
	
		
		errorEmbed.setFooter("if this seems unexpected, please contact @syntex#1389");
		errorEmbed.setTimestamp(Instant.now());
		
		event.getMessage().replyEmbeds(errorEmbed.build()).queue();
	}
	
	/**
	 * a convenient error message sender, which uses a premade error template and then sends out the error
	 * @param event the message recieved event
	 * @param response the error 
	 * @param e the exception
	 */
	protected void logError(MessageReceivedEvent event, String response, Exception e) {

		try {
			e.printStackTrace();
			
			String json = e.getMessage();
			int LENGTH = 500;
			String format = "css";
			
			if(e.getMessage().length() < LENGTH) {
				EmbedBuilder errorEmbed = new EmbedBuilder();
				
				errorEmbed.setTitle("Error handler");
				errorEmbed.setColor(Color.red);
				errorEmbed.setDescription("Sorry and error has accured, please look below for details");
				
				errorEmbed.addField("Response", "```" + response + "```", false);
				
				errorEmbed.addField("Location", "```" + e.getStackTrace()[0] + "```", false);
							
			
			
				
				errorEmbed.setFooter("if this seems unexpected, please contact @syntex#1389");
				errorEmbed.setTimestamp(Instant.now());
				
				event.getMessage().replyEmbeds(errorEmbed.build()).queue();
				
				return;
			}
			
			List<String> content = new ArrayList<>();
			for(int i = 0; i < json.length(); i+=LENGTH) {
				int min = i;
				int max = i + LENGTH;
				
				if(json.length() < max) {
					content.add(json.substring(min, json.length()));
				}else {
					content.add(json.substring(min, max));
				}
				
			}
			
			PagedEmbed builder = PagedEmbed.create(event, content, 1, true, page -> {
				
				EmbedBuilder errorEmbed = new EmbedBuilder();
				
				errorEmbed.setTitle("Error handler");
				errorEmbed.setColor(Color.red);
				errorEmbed.setDescription("Sorry and error has accured, please look below for details" + System.lineSeparator());
				errorEmbed.appendDescription("You are currently viewing page " + page.getPage() + "/" + (page.getParent().getLastPage()-1));
				
				errorEmbed.addField("Response", "```" + format + System.lineSeparator() + page.getPagedElements().get(0) + "```", false);
				
				errorEmbed.addField("Location", "```" + format + System.lineSeparator() + e.getStackTrace()[0] + "```", false);

				errorEmbed.setFooter("if this seems unexpected, please contact @syntex#1389");
				errorEmbed.setTimestamp(Instant.now());
				
				return errorEmbed;
				
			});
			
			builder.sendAsReply();
			
			
			
		}catch (Exception e2) {
			this.logError(event, e2.getLocalizedMessage(), e2);
		}
		
	}
	
	/**
	 * this method has been temporarily removed.
	 * 
	 */
	@Deprecated
	public void onSlashCommandInteraction1(SlashCommandInteractionEvent event) {
		// TODO Auto-generated method stub
		super.onSlashCommandInteraction(event);
		
		
		try {
			
			this.onSlashCommand(event);
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
	public abstract void onCommand(MessageReceivedEvent event, String[] args) throws Exception;

	/**
	 * wether or not this command can be used by normal users
	 * @return <code>true</code> if usable otherwise <code>false</code>
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * sets the W of this bot
	 * @param available, wether or not this command is available
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	public UserModel getUserModel(MessageReceivedEvent event) {
		return Guru.getInstance().getUsersHandler().getUserData(event.getAuthor());
	}
	
	@Deprecated
	public void onSlashCommand(SlashCommandInteractionEvent e) {}
	
	public void sendStringMessage(String format, String json, MessageReceivedEvent event, int LENGTH) {
		
		if(json.length() < LENGTH) {
			event.getMessage().reply("```" + format + System.lineSeparator()  + json +  System.lineSeparator() + "```").queue();
			return;
		}
		
		List<String> content = new ArrayList<>();
		for(int i = 0; i < json.length(); i+=LENGTH) {
			int min = i;
			int max = i + LENGTH;
			
			if(json.length() < max) {
				content.add(json.substring(min, json.length()));
			}else {
				content.add(json.substring(min, max));
			}
			
		}
		
		PagedEmbed builder = PagedEmbed.create(event, content, 1, true, page -> {
			
			EmbedBuilder embedBuilder = new EmbedBuilder();
			embedBuilder.appendDescription("You are currently viewing page " + page.getPage() + "/" + (page.getParent().getLastPage()-1));
			
			page.getPagedElements().forEach(o -> {
			
				embedBuilder.appendDescription("```" + format + System.lineSeparator() + o + System.lineSeparator() + "```");
				
			});
			return embedBuilder;
			
		});
		
		builder.sendAsReply();
		
	}
}
