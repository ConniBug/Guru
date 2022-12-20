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
public class Leaderboards extends Command{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args, UserModel model) throws Exception {
		
		//int o = Guru.getInstance().getUsersHandler().getUsers().stream().sorted((a,b) -> {
			
		//});

		EmbedBuilder ranks = new EmbedBuilder();
		
		ranks.setTitle("Leaderboards");
		ranks.setColor(Color.green);

		for(com.syntex.modals.Ranks i : com.syntex.modals.Ranks.values()) {
			ranks.addField(Guru.getInstance().getJDA().getRoleById(i.getID()).getName(), i.getRequiredXP() + " score", true);
		}

		event.getMessage().replyEmbeds(ranks.build()).queue();
		
		
	}

}
