package com.guru.commands.codewars;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import com.guru.bot.Guru;
import com.guru.commands.Category;
import com.guru.commands.CommandMeta;
import com.guru.paged.PagedEmbed;
import com.guru.userdata.UserModel;
import com.guru.utils.PrettyString;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandMeta(name = {"leaderboard"}, description = "shows the leaderboard", category = Category.CODEWARS, usage = {"leaderboard"})
public class Leaderboards extends CodewarsCommand{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {

		List<UserModel> usermodels = Guru.getInstance().getUsersHandler().getUsers().stream().filter(o -> o.getCodewars().isRegistered()).sorted((a, b) -> (int)b.getCodewars().getMeta().getRanks().getOverall().getScore() - (int)a.getCodewars().getMeta().getRanks().getOverall().getScore()).collect(Collectors.toList());
	
		PagedEmbed<UserModel> embed = PagedEmbed.create(event, usermodels, 5, false, (page -> {
		
			EmbedBuilder embedBuilder = new EmbedBuilder();
			
			embedBuilder.setTitle("Kent Computing (Year 1) Leaderboard");
			embedBuilder.setColor(Color.cyan);
			embedBuilder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
			
			List<UserModel> content = page.getPagedElements();
			
			int digits = String.valueOf((page.getPage()+1)*page.getParent().getElementsPerPage()).length();
			
			for(int i = 0; i < content.size(); i++) {
				
				int pos = (i+1) + (page.getParent().getElementsPerPage())*(page.getPage()-1);
				String rank = String.format("%0" + digits + "d", pos);
				
				String medal = ":medal:";
				
				if(pos == 1) medal = ":first_place:";
				if(pos == 2) medal = ":second_place:";
				if(pos == 3) medal = ":third_place:";
				
				UserModel user = content.get(i);
				embedBuilder.appendDescription(System.lineSeparator());
				embedBuilder.appendDescription("`#" + rank + "` " + medal + " " + PrettyString.capitaliseFirstLetter(user.getEffectiveName()) + "\t - \t`" + user.getCodewars().getMeta().getRanks().getOverall().getScore() + "`");	
			}

			embedBuilder.setFooter("if this seems unexpected, please contact @syntex#1389");
			
			return embedBuilder;
		}));
		
		embed.sendAsReply();

	}

}
