package com.guru.commands.admin;

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

@CommandMeta(category = Category.ADMIN, description = "give a user money", name = {"apay", "agive"}, usage = {"apay <@user>"}, permission = {"ADMINISTRATOR"})
public class AdminGive extends Command{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {
		
		Member user = event.getMember();
		
		if(args.length >= 3) {
			List<Member> a = event.getMessage().getMentions().getMembers();
			if(a.size() > 0) {
				user = a.get(0);
			}
		}else {
			throw new Exception("usage: " + this.getMeta().usage()[0]);
		}
		
		if(!Numbers.isNumber(args[2])) {
			throw new Exception("usage: " + this.getMeta().usage()[0]);
		}
		
		UserModel transerUser = Guru.getInstance().getUsersHandler().getUserData(user.getId());
		UserModel sender = Guru.getInstance().getUsersHandler().getUserData(event.getMember().getId());
		
		EmbedBuilder balance = new EmbedBuilder();
		
		int amount = Numbers.getNumber(args[2]);
		
		sender.adminPay(transerUser, amount);
		
		balance.setTitle("Payment 💸");
		balance.setColor(Color.green);
		balance.setDescription("your transcript will be shown below");
		balance.addField("Sender", "```" + event.getMember().getEffectiveName() +"```", false);
		balance.addField("Reciever", "```" + user.getEffectiveName()  +"```", false);
		balance.addField("Amount", "```" + amount +"```", false);
		
		event.getMessage().replyEmbeds(balance.build()).queue();
		
	}

	
}
