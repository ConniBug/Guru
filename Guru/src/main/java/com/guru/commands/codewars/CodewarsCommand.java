package com.guru.commands.codewars;

import java.awt.Color;

import com.guru.bot.Guru;
import com.guru.commands.Command;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class CodewarsCommand extends Command{
	
	public Message sendUpdateMessage(String id, MessageReceivedEvent event) {
		if(Guru.getInstance().getUsersHandler().needsUpdating(id)) {
			EmbedBuilder embedBuilder = new EmbedBuilder();
			embedBuilder.setTitle("Update handler");
			embedBuilder.setColor(Color.cyan);
			embedBuilder.setThumbnail("https://flevix.com/wp-content/uploads/2020/01/Quarter-Circle-Loading.svg");
			embedBuilder.setDescription("We are currently in the process of updating this users data, please hold for a few seconds...");
			return event.getMessage().replyEmbeds(embedBuilder.build()).complete();
		}
		return null;
	}

}
