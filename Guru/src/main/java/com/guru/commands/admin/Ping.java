package com.guru.commands.admin;

import java.awt.Color;
import java.time.Instant;

import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandMeta(category = Category.ADMIN, description = "shows ping", name = {"ping"}, usage = {"ping"}, permission = {"ADMINISTRATOR"})
public class Ping extends Command{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {
		
		EmbedBuilder ping = new EmbedBuilder();
		
		ping.setTitle(":ping_pong: Pong! :ping_pong:");
		ping.setColor(Color.decode("#85EF93"));
		ping.setThumbnail("https://cdn.discordapp.com/emojis/723073203307806761.gif?v=1");
		
		event.getJDA().getRestPing().queue(o -> {
		

			ping.addField("Client latency", "```" + o + "ms```", false);
			ping.addField("Websocket latency", "```" + event.getJDA().getGatewayPing() + "ms```", false);
			ping.addField("JSON latency", "```" + 0 + "ms```", false);
			
			ping.setFooter("User: " + event.getAuthor().getAsTag() + " | ID: " + event.getAuthor().getId(), event.getAuthor().getAvatarUrl());
			ping.setTimestamp(Instant.now());
		
			event.getMessage().replyEmbeds(ping.build()).queue();
			
		});

	
	}


	
}
