package com.guru.commands.shop;

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

/**
 * help command class, this is responsible for showing information for all commands, akin to the linux command man
 * @author synte
 * @version 0.0.1 
 *
 */
@CommandMeta(name = {"baltop", "richest", "balancetop"}, description = "show the top 5 richest people in the server", category = Category.MONEY, usage = {"baltop"})
public class BalanceTop extends Command{

	public BalanceTop() {
		this.setAvailable(true);
	}
	
	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {

		
		List<UserModel> richest = Guru.getInstance().getUsersHandler().getUsers().stream().sorted((a, b) -> b.getBablons() - a.getBablons()).limit(5).collect(Collectors.toList());
		
		EmbedBuilder balance = new EmbedBuilder();
		
		balance.setTitle("Richest users 💸");
		balance.setColor(Color.green);
		balance.setDescription("the richest people in the server will be shown below");


		for(int i = 0; i < richest.size(); i++) {
			UserModel user = richest.get(i);
			balance.addField(event.getJDA().retrieveUserById(user.getUserID()).complete().getName() + "\t#" + (i+1), "```" + user.getBablons() + " " + "bablons```", true);			
		}
		
		event.getMessage().replyEmbeds(balance.build()).queue();
		
	}




}
