package com.guru.commands;

import java.awt.Color;

import com.guru.utils.PrettyString;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandMeta(name = "help", description = "Displays the help command", category = Category.HELP)
public class Help extends Command{

	@Override
	public void onCommand(MessageReceivedEvent event) {

		EmbedBuilder builder = new EmbedBuilder();
		
		builder.setTitle("Help menu");
		builder.setColor(Color.cyan);
		builder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
		builder.setDescription("Here is a list of commands for this bot");
		
		
		for(Category category : Category.values()) {
			
			StringBuilder cmd = new StringBuilder();
			
			cmd.append("```");
			this.commandManager.getCatergoryCommands(category).forEach(command -> {
				cmd.append(command.getMeta().name() + ", ");
			});
			
			if(cmd.length() > 1) {
				cmd.setLength(cmd.length() - 2);
			}
			
			cmd.append("```");
			
			builder.addField(category.getEmoji() + " " + PrettyString.prettyTitle(category.name()), cmd.toString(), false);
		}
		
		event.getChannel().sendMessageEmbeds(builder.build()).queue();
		
	}

}
