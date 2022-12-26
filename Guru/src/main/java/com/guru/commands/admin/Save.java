package com.guru.commands.admin;

import java.awt.Color;

import com.guru.bot.Guru;
import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandMeta(category = Category.ADMIN, description = "save the cashed data", name = {"save", "cashe"},
usage = {"save"}, permission = {"ADMINISTRATOR"})
public class Save extends Command{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {
		
		Guru.getInstance().getUsersHandler().save();
		
		EmbedBuilder balance = new EmbedBuilder();
		
		balance.setTitle("Save");
		balance.setColor(Color.green);
		balance.setDescription("The local cashe has been saved.");
		balance.addField("Saved users", "```" + Guru.getInstance().getUsersHandler().getUsers().size() +"```", true);
		
		event.getMessage().replyEmbeds(balance.build()).queue();
		
	}

	
}
