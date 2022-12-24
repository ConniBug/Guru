package com.guru.commands.codewars;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import com.guru.bot.Guru;
import com.guru.commands.Category;
import com.guru.commands.CommandMeta;
import com.guru.userdata.UserModel;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandMeta(name = {"leaderboard"}, description = "shows the leaderboard", category = Category.CODEWARS, usage = {"leaderboard"})
public class Leaderboards extends CodewarsCommand{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {

		List<UserModel> z = Guru.getInstance().getUsersHandler().getUsers().stream().filter(o -> o.getCodewars().isRegistered()).sorted((a, b) -> (int)b.getCodewars().getMeta().getRanks().getOverall().getScore() - (int)a.getCodewars().getMeta().getRanks().getOverall().getScore()).limit(10).collect(Collectors.toList());
	
		EmbedBuilder embedBuilder = new EmbedBuilder();
		
		embedBuilder.setTitle("Codewars profile");
		embedBuilder.setColor(Color.cyan);
		embedBuilder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
		embedBuilder.setDescription("here is the leaderboard, do keep in mind that this data might be outdated.");
		
		for(int i = 0; i < z.size(); i++) {
			UserModel data = z.get(i);
			embedBuilder.appendDescription(System.lineSeparator());
			embedBuilder.appendDescription("`#" + (i+1) + "` " + data.getEffectiveName() + " - `" + data.getCodewars().getMeta().getRanks().getOverall().getScore() + "`");	
		}

		embedBuilder.setFooter("if this seems unexpected, please contact @syntex#1389");
		
		MessageEmbed embed = embedBuilder.build();
		
		event.getMessage().replyEmbeds(embed).queue();
		
		
	}

}
