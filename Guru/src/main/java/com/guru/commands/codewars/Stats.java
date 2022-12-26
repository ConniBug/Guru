package com.guru.commands.codewars;

import java.awt.Color;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.guru.bot.Guru;
import com.guru.codewars.modals.CodewarsMeta;
import com.guru.commands.Category;
import com.guru.commands.CommandMeta;
import com.guru.userdata.UserModel;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.utils.messages.MessageEditBuilder;

@CommandMeta(name = {"cstats", "cprofile", "codewarsstats"}, description = "Displays a users codewars stats", category = Category.CODEWARS, usage = {"cstats", "cstats <@user>"})
public class Stats extends CodewarsCommand{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {
		
		
		Optional<MessageCreateAction> updateHandler = this.sendUpdateMessage(event.getAuthor().getId(), event);
		Message message = null;
		
		if(updateHandler.isPresent()) {
			message = updateHandler.get().complete();
		}
		
		
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
			Optional<Member> users = this.getMemberFromCommand(event.getGuild(), args[1]).get();
				if(users.isPresent()) {
					model = Guru.getInstance().getUsersHandler().getUserData(users.get());
					if(!model.getCodewars().isRegistered()) {
						this.logError(event, "Sorry, " + users.get().getEffectiveName() + " does not have a codewars profile linked please do ;link {profile} if you've already linked your account, please wait for a developer to verify your status.");
						return;
					}
					meta = model.getCodewars().getMeta();
				}else {
					throw new Exception("The user " + args[1] + " does not exist!");
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
			MessageEditBuilder msg = MessageEditBuilder.fromMessage(message).clear().setEmbeds(embedBuilder.build());
			message.editMessage(msg.build()).queue();	
		}
		
	}

}
