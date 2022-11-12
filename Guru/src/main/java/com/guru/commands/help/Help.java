package com.guru.commands.help;

import java.awt.Color;
import java.util.Arrays;
import java.util.Optional;

import com.guru.bot.Guru;
import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;
import com.guru.data.SerializableField;
import com.guru.utils.PrettyString;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * help command class, this is responsible for showing information for all commands, akin to the linux command man
 * @author synte
 * @version 0.0.1 
 *
 */
@CommandMeta(name = {"help", "h", "cmd", "mod", "commands"}, description = "Displays the help command", category = Category.HELP, args = {"help", "help {command}"})
public class Help extends Command{

	@SerializableField(path = "messages.invalid_command")
	private String invalid_command;
	
	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {
		
		if(args.length > 1) {
			this.sendCommandDetails(event, args);
			return;
		}
		
		EmbedBuilder builder = new EmbedBuilder();
		
		builder.setTitle("Help menu");
		builder.setColor(Color.cyan);
		//builder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
		builder.setDescription("Here is a list of commands for this bot");
		
		
		for(Category category : Category.values()) {
			
			StringBuilder cmd = new StringBuilder();
			
			cmd.append("```");
			this.commandManager.getCatergoryCommands(category).forEach(command -> {
				cmd.append(command.getMeta().name()[0] + ", ");
			});
			
			if(cmd.length() > 1) {
				cmd.setLength(cmd.length() - 2);
			}
			
			cmd.append("```");
			
			builder.addField(category.getEmoji() + " " + PrettyString.capitaliseFirstLetter(category.name()), cmd.toString(), false);
		}
		
		builder.setFooter("for more details please type " + Guru.getInstance().getStartupConfiguration().getCommandPrefix() + this.getMeta().name()[0] + " {command}");
		
		event.getChannel().sendMessageEmbeds(builder.build()).queue();
		
	}
	
	public void sendCommandDetails(MessageReceivedEvent event, String[] args) throws Exception {
		
		String subCommand = args[1];
		
		Optional<Command> target = this.commandManager.getCommandByName(subCommand);
		
		if(target.isPresent()) {
			
			Command cmd = target.get();
			
			EmbedBuilder builder = new EmbedBuilder();
			
			builder.setTitle(cmd.getMeta().name()[0]);
			builder.setColor(Color.cyan);
			//builder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
			builder.setDescription("Here are the details for this command");
			
			builder.addField("Alternative", "```" + PrettyString.prettyArray(cmd.getMeta().name(), Guru.getInstance().getStartupConfiguration().getCommandPrefix(), true) + "```", true);
			builder.addField("Permissions", "```" + PrettyString.prettyArray(cmd.getMeta().permission(), true) + "```", true);
			builder.addField("Description", "```" + cmd.getMeta().description() + "```", false);
			
			if(cmd.getMeta().args().length == 0) {
				builder.addField("Usage", "```" + cmd.getMeta().name()[0] + "```", false);
			}else {
				builder.addField("Usage", "```" + Arrays.toString(cmd.getMeta().args()).replace("[", "").replace("]", "") + "```", false);
			}
			
			builder.setFooter("for more details please type " + Guru.getInstance().getStartupConfiguration().getCommandPrefix() + this.getMeta().name()[0] + " {command}");
			
			event.getMessage().replyEmbeds(builder.build()).queue();
			return;
		}
		

		
		//event.getMessage().replyEmbeds(builder.build()).queue();

		throw new Exception(this.invalid_command.replace("{cmd}", args[1]));
		
		
	}

}
