package com.guru.commands.shop;

import java.awt.Color;
import java.util.List;

import com.guru.bot.Guru;
import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;
import com.guru.userdata.UserModel;
import com.syngen.engine.Numbers;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * help command class, this is responsible for showing information for all commands, akin to the linux command man
 * @author synte
 * @version 0.0.1 
 *
 */
@CommandMeta(name = {"pay", "give", "donate"}, description = "give someone bablons", category = Category.MONEY, usage = {"pay <@user> <amount>"})
public class Pay extends Command{

	public Pay() {
		this.setAvailable(false);
	}
	
	@Override
	public void onCommand(MessageReceivedEvent event, String[] args, UserModel model) throws Exception {
		
		Member user = event.getMember();
		
		if(args.length >= 3) {
			List<Member> a = event.getMessage().getMentions().getMembers();
			if(a.size() > 0) {
				user = a.get(0);
				if(user.getId().equals(event.getAuthor().getId())) {
					this.logError(event, "Sorry, you cannot pay yourself!");
					return;
				}
			}
		}else {
			throw new Exception("usage: " + this.getMeta().usage()[0]);
		}
		
		
		if(!Numbers.isNumber(args[2])) {
			throw new Exception("usage: " + this.getMeta().usage()[0]);
		}
		
		UserModel userData = Guru.getInstance().getUsersHandler().getUserData(event.getMember().getId());
		UserModel transerUser = Guru.getInstance().getUsersHandler().getUserData(user.getId());
		
		EmbedBuilder balance = new EmbedBuilder();
		
		int amount = Numbers.getNumber(args[2]);
		
		if(userData.getBablons() < amount) {
			throw new Exception("You need " + (amount - userData.getBablons()) + " bablons to carry out that operation");
		}
		
		userData.pay(transerUser, amount);
		
		balance.setTitle("Payment 💸");
		balance.setColor(Color.green);
		balance.setDescription("your transcript will be shown below");
		balance.addField("Sender", "```" + event.getMember().getEffectiveName() +"```", false);
		balance.addField("Reciever", "```" + user.getEffectiveName()  +"```", false);
		balance.addField("Amount", "```" + amount +"```", false);
		balance.addField("Remaining balance", "```" + userData.getBablons() +"```", true);
		
		event.getMessage().replyEmbeds(balance.build()).queue();
		
	}




}
