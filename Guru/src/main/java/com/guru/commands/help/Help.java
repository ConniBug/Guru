package com.guru.commands.help;

import java.awt.Color;
import java.util.Optional;

import com.guru.bot.Guru;
import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;
import com.guru.data.SerializableField;
import com.guru.userdata.UserModel;
import com.guru.utils.PrettyString;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * help command class, this is responsible for showing information for all commands, akin to the linux command man
 * @author synte
 * @version 0.0.1 
 *
 */
@CommandMeta(name = {"help", "h", "cmd", "man", "commands"}, description = "Displays the help command", category = Category.HELP, usage = {"help", "help <command>"})
public class Help extends Command{

	@SerializableField(path = "messages.invalid_command")
	private String invalid_command = "The command {cmd} does not exist!";
	
	@Override
	public void onCommand(MessageReceivedEvent event, String[] args, UserModel model) throws Exception {
		
		if(args.length > 1) {
			event.getMessage().replyEmbeds(this.sendCommandDetails(args[1])).queue();
			return;
		}
		
		event.getMessage().replyEmbeds(this.genericHelpMenu()).queue();;
		
	}
	
	public MessageEmbed genericHelpMenu() {
		
		EmbedBuilder builder = new EmbedBuilder();
		
		builder.setTitle("Help menu");
		builder.setColor(Color.cyan);
		//builder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
		builder.setDescription("Here is a list of commands for this bot");
		
		
		for(Category category : Category.values()) {
			
			StringBuilder cmd = new StringBuilder();
			
			this.commandManager.getCatergoryCommands(category).forEach(command -> {
				cmd.append("`");
				cmd.append(command.getMeta().name()[0]);
				cmd.append("`, ");
			});
			
			if(cmd.length() > 1) {
				cmd.setLength(cmd.length() - 2);
			}
			
			builder.addField(category.getEmoji() + " " + PrettyString.capitaliseFirstLetter(category.name()), cmd.toString(), false);
		}
		
		builder.setFooter("for more details please type " + Guru.getInstance().getStartupConfiguration().getCommandPrefix() + this.getMeta().name()[0] + " {command}");
		
		return builder.build();
	}
	
	public MessageEmbed sendCommandDetails(String subCommand) throws Exception {
		
		Optional<Command> target = this.commandManager.getCommandByName(subCommand);
		
		if(target.isPresent()) {
			
			Command cmd = target.get();
			
			String prefix = Guru.getInstance().getStartupConfiguration().getCommandPrefix();
			CommandMeta meta = cmd.getMeta();
			
			
			EmbedBuilder builder = new EmbedBuilder();
			
			builder.setTitle(meta.name()[0]);
			builder.setColor(Color.cyan);
			builder.setDescription("Here are the details for this command");
			
			builder.addField("Alternative", "```" + PrettyString.prettyArray(meta.name(), prefix, true) + "```", true);
			builder.addField("Permissions", "```" + PrettyString.prettyArray(meta.permission(), true) + "```", true);
			builder.addField("Description", "```" + meta.description() + "```", false);
			
			if(cmd.getMeta().usage().length == 0) {
				builder.addField("Usage", "```" + prefix + meta.name()[0] + "```", false);
			}else {
				builder.addField("Usage", "" + System.lineSeparator() + "<...> means required" + System.lineSeparator() + "{...} means optional```" + "" + PrettyString.prettyArray(meta.usage(), "", false, true) + "```", false);
			}
			
			builder.setFooter("for more details please type " + Guru.getInstance().getStartupConfiguration().getCommandPrefix() + this.getMeta().name()[0] + " {command}");
			
			return builder.build();
		}

		throw new Exception(this.invalid_command.replace("{cmd}", subCommand));
		
		
	}


}
