package com.guru.commands.codewars;

import java.awt.Color;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.guru.bot.Guru;
import com.guru.codewars.modals.CodewarsMeta;
import com.guru.commands.Category;
import com.guru.commands.CommandMeta;
import com.guru.userdata.UserModel;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.messages.MessageEditBuilder;

@CommandMeta(name = {"cstats", "cprofile", "codewarsstats"}, description = "Displays a users codewars stats", category = Category.CODEWARS, usage = {"cstats", "cstats <@user>"})
public class Stats extends CodewarsCommand{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {
		
		
		Optional<Message> updateHandler;
		
		updateHandler = this.sendUpdateMessage(event.getAuthor().getId(), event);

		UserModel model = this.getUserModel(event);;
		
		CodewarsMeta meta = null;
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		if(args.length == 1) {
			if(!model.getCodewars().isRegistered()) {
				this.logError(event, "Sorry, you don't have a codewars profile linked please do ;link {profile} if you've already linked your account, please wait for a developer to verify your status.");
				return;
			}
			meta = model.getCodewars().getMeta();
		}else {
				List<User> a = event.getMessage().getMentions().getUsers();
				if(a.size() > 0) {
					model = Guru.getInstance().getUsersHandler().getUserData(a.get(0));
					if(!model.getCodewars().isRegistered()) {
						this.logError(event, "Sorry, " + a.get(0).getName() + " does not have a codewars profile linked please do ;link {profile} if you've already linked your account, please wait for a developer to verify your status.");
						return;
					}
					meta = model.getCodewars().getMeta();
				}
	
			
			
		}
		
		EmbedBuilder embedBuilder = new EmbedBuilder();
		
		embedBuilder.setTitle("Codewars profile");
		embedBuilder.setColor(Color.orange);
	
		embedBuilder.setDescription("```json" + System.lineSeparator() + gson.toJson(meta) + "```");
		
		embedBuilder.setFooter("if this seems unexpected, please contact @syntex#1389");
		
		if(updateHandler.isEmpty()) {
			event.getMessage().replyEmbeds(embedBuilder.build()).queue();
		}else {
			MessageEditBuilder msg = MessageEditBuilder.fromMessage(updateHandler.get()).clear().setEmbeds(embedBuilder.build());
			updateHandler.get().editMessage(msg.build()).queue();	
		}
		
	}

}
