package com.guru.commands.codewars;

import java.awt.Color;

import com.guru.bot.Guru;
import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;
import com.guru.userdata.UserModel;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandMeta(name = {"ranks"}, description = "shows all the ranks that can be earned", category = Category.CODEWARS, usage = {"ranks"})
public class Ranks extends Command{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args, UserModel model) throws Exception {
		

		EmbedBuilder ranks = new EmbedBuilder();
		
		ranks.setTitle("Ranks");
		ranks.setColor(Color.green);

		com.syntex.modals.Ranks[] allRanks = com.syntex.modals.Ranks.values();
		
		for(int i = allRanks.length - 1; i >= 0; i--) {
			ranks.appendDescription(Guru.getInstance().getJDA().getRoleById(allRanks[i].getID()).getAsMention() + " `" + allRanks[i].getRequiredXP() + " score`");
			ranks.appendDescription(System.lineSeparator());
		}

		event.getMessage().replyEmbeds(ranks.build()).queue();

	}

}
