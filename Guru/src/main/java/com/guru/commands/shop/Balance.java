package com.guru.commands.shop;

import java.awt.Color;
import java.util.List;

import com.guru.bot.Guru;
import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;
import com.guru.userdata.UserModel;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * help command class, this is responsible for showing information for all commands, akin to the linux command man
 * @author synte
 * @version 0.0.1 
 *
 */
@CommandMeta(name = {"money", "balance", "bablons", "bal"}, description = "shows how much money a user has", category = Category.MONEY, usage = {"bablons", "bablons <@user>"})
public class Balance extends Command{

	public Balance() {
		this.setAvailable(true);
	}
	
	@Override
	public void onCommand(MessageReceivedEvent event, String[] args, UserModel model) throws Exception {

		String user = event.getAuthor().getId();
		String name = event.getAuthor().getName();
		
		if(args.length >= 2) {
			List<User> a = event.getMessage().getMentions().getUsers();
			if(a.size() > 0) {
				user = a.get(0).getId();
				name = args[1];
			}
		}
		
		UserModel userData = Guru.getInstance().getUsersHandler().getUserData(user);
		
		EmbedBuilder balance = new EmbedBuilder();
		
		
		balance.setTitle("Balance 💸");
		balance.setColor(Color.green);
		balance.setDescription(name + "'s balance will be displayed below");
		balance.addField("Bablons", "```" +userData.getBablons()+"```", true);
		
		event.getMessage().replyEmbeds(balance.build()).queue();
		
	}




}
