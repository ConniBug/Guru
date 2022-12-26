package com.guru.commands.money;

import java.awt.Color;
import java.util.Optional;

import com.guru.bot.Guru;
import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;
import com.guru.userdata.UserModel;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
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
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {

		Member user = event.getMember();
		
		if(args.length >= 2) {
			Optional<Member> users = this.getMemberFromCommand(event.getGuild(), args[1]).get();
			if(users.isPresent()) {
				user = users.get();
			}else {
				throw new Exception("The user " + args[1] + " does not exist!");
			}
		}

		UserModel userData = Guru.getInstance().getUsersHandler().getUserData(user);
		
		EmbedBuilder balance = new EmbedBuilder();
		
		
		balance.setTitle("Balance 💸");
		balance.setColor(Color.green);
		balance.setDescription(user.getEffectiveName() + "'s balance will be displayed below");
		balance.addField("Bablons", "```" +userData.getBablons()+"```", true);
		
		event.getMessage().replyEmbeds(balance.build()).queue();
		
	}




}
