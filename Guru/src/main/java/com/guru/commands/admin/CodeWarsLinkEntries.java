package com.guru.commands.admin;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import com.guru.bot.Guru;
import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;
import com.guru.userdata.UserModel;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandMeta(category = Category.ADMIN, description = "shows the current user to be verified", name = {"entries"}, usage = {"entries"}, permission = {"ADMINISTRATOR"})
public class CodeWarsLinkEntries extends Command{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {
		
		new Thread(() -> {
	
			List<UserModel> users = Guru.getInstance().getUsersHandler().getUsers().stream().filter(o -> !o.getLink().isEmpty()).limit(10).collect(Collectors.toList());
			
			long amount = Guru.getInstance().getUsersHandler().getUsers().stream().filter(o -> !o.getLink().isEmpty()).count();
			
			EmbedBuilder entries = new EmbedBuilder();
			entries.setTitle("Entries");
			entries.setDescription("Showing " + users.size() + "/" + amount + " users.");
			entries.setColor(Color.green);
			
			
			for(int i = 0; i < users.size(); i++) {
				UserModel user = users.get(i);
				entries.addField(event.getGuild().getMemberById(user.getUserID()).getEffectiveName(), user.getLink(), false);
			}
			
			event.getMessage().replyEmbeds(entries.build()).queue();
			
			
		}).start();
	
	}

	
}
