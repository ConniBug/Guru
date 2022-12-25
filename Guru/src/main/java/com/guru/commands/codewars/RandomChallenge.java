package com.guru.commands.codewars;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.json.JSONObject;

import com.guru.bot.Guru;
import com.guru.codewars.kata.Kata;
import com.guru.commands.Category;
import com.guru.commands.CommandMeta;
import com.syngen.engine.Numbers;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandMeta(name = {"challenge", "puzzle"}, description = "returns a random codewars challenge", category = Category.CODEWARS, usage = {"challenge", "challenge {kata}"}, cooldown = 60*5)
public class RandomChallenge extends CodewarsCommand{

	private final int[] difficulty = {4,5,6,7};
	
	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {
		
		int diff = difficulty[ThreadLocalRandom.current().nextInt(difficulty.length)];
		
		if(args.length == 2) {
			if(Numbers.isNumber(args[1])) {
				diff = Numbers.getNumber(args[1]);
			}
		}
		
		final int d = diff;
		
		List<Kata> katas = Guru.getInstance().getKataCasher().getKatas().get().stream().filter(o -> {
			if(o.getRankObject().getId() == d*-1) return true;
			return false;
		}).collect(Collectors.toList());
		
		Kata kata = katas.get(ThreadLocalRandom.current().nextInt(katas.size()));
		
		EmbedBuilder embedBuilder = new EmbedBuilder();
		
		embedBuilder.setTitle(kata.getName() + " - " + kata.getRankObject().getName(), kata.getUrl());
		
		switch (kata.getRankObject().getColor()) {
		case "yellow":
			embedBuilder.setColor(Color.yellow);
			break;
		case "blue":
			embedBuilder.setColor(Color.cyan);
			break;
		default:
			break;
		}
		
		String desc = kata.getDescription().replaceAll("<[^>]*>", "");
		
		if(desc.length() > 4000) {
			desc = desc.substring(0, 4000) + " *(" + (desc.length()-4000) + System.lineSeparator() + " more lines... )*";
		}
		
		if(desc.contains("<img src=\"")) {
			embedBuilder.setThumbnail(desc.split("<img src=\"")[1].split("\"")[0]);
		}
		
		if(desc.contains("<img src = '")) {
			embedBuilder.setThumbnail(desc.split("<img src = '")[1].split("'")[0]);
		}
		
		
		embedBuilder.setDescription(desc.replaceAll("<[^>]*>", ""));
		
		
		event.getMessage().replyEmbeds(embedBuilder.build()).queue();
		
	}
	
	public void jsonToEmbed(MessageReceivedEvent event, JSONObject data) {
		
		EmbedBuilder embedBuilder = new EmbedBuilder();
		
		embedBuilder.setTitle(data.getString("name"));
		embedBuilder.setColor(Color.cyan);
		//embedBuilder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());

		embedBuilder.setDescription("You can view this kata at https://www.codewars.com/kata/" + data.getString("id"));
		
		data.keySet().stream().forEach(o -> {
			if(!o.equals("description")) {
				embedBuilder.addField(o, "```html "+data.get(o)+"```", true);	
			}
		});
		
		
		event.getMessage().replyEmbeds(embedBuilder.build()).queue();
		
	}

}
