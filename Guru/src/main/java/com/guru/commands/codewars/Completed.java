package com.guru.commands.codewars;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.guru.bot.Guru;
import com.guru.codewars.kata.Kata;
import com.guru.codewars.users.katas.Datum;
import com.guru.commands.Category;
import com.guru.commands.Command;
import com.guru.commands.CommandMeta;
import com.guru.commands.help.PagedEmbed;
import com.guru.userdata.UserModel;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandMeta(name = {"ckatas", "katas"}, description = "shows all katas the user has completed", category = Category.CODEWARS, usage = {"katas", "katas <@user>"})
public class Completed extends Command{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args, UserModel m) throws Exception {

		int counter = 0;
		
		List<Member> mentions = event.getMessage().getMentions().getMembers();
		
		UserModel model = m;
		
		if(mentions.size() > 0) {
			model = Guru.getInstance().getUsersHandler().getUserData(mentions.get(0));
		}
		
		if(model.getCodewars().isEmpty()) {
			throw new Exception("Sorry, you need to link your account first.");
		}
		
		List<Datum> data = model.getCashedKatas();
		
		if(data == null || data.isEmpty()) {
			
			System.out.println("cashing");

			data = model.getKatasSorted((a, b) -> {
				
				try {
					
					Optional<Kata> kata1 = Guru.getInstance().getKataCasher().getKataFromId(a.id).get();
					Optional<Kata> kata2 = Guru.getInstance().getKataCasher().getKataFromId(b.id).get();
					
					if(kata1.isPresent() && kata2.isPresent()) {
						
						int rank1 = (int)kata1.get().getRankObject().getId()*-1;
						int rank2 = (int)kata2.get().getRankObject().getId()*-1;
						
						return rank1 - rank2;	
					}
					
					if(kata1.isPresent() && !kata2.isPresent()) {
						return -1;
					}
					
					if(!kata1.isPresent() && kata2.isPresent()) {
						return 1;
					}
					
				}catch (Throwable e) {
					e.printStackTrace();
				}
				
				
				return 0;
			}).get().stream().collect(Collectors.toList());
			
			model.setKatas(data);
			model.save();
			
		}
		
		
		//List<Datum> data = model.getKatas().get().stream().limit(35).collect(Collectors.toList());
		
		//embedBuilder.appendDescription(System.lineSeparator());
		
		List<String> completed = new ArrayList<>();
		
		for(Datum o : data) {
			Optional<Kata> kata = Guru.getInstance().getKataCasher().getKataFromId(o.id).get();
			
			int textLimit = 35;
			
			if(kata.isPresent()) {
			
				String name = kata.get().getName();
				
				if(name.length() > textLimit) {
					name = name.substring(0, textLimit) + "...";
				}
				
				String kataRank = (Guru.getInstance().getKataCasher().getKataFromId(o.id).get().get().getRankObject().getId()*-1) + "";
				
				if(Guru.getInstance().getKataCasher().getKataFromId(o.id).get().get().getRankObject().getId() == -10) {
					kataRank = "beta";
				}
				
				completed.add("`" + ++counter + ".` [" + name +  "](" + kata.get().getUrl() + ")" + " Kyu `" + kataRank + "`");
			
			}else {
				completed.add("`" + ++counter + ".`" + o.name + " Kyu `Unknown`");
			}
			
		}
		
		PagedEmbed embed = PagedEmbed.create(event, completed, 15, false, o -> {
			
			EmbedBuilder embedBuilder = new EmbedBuilder();
			embedBuilder.setTitle("Codewars profile");
			embedBuilder.setColor(Color.cyan);
			embedBuilder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
			
			embedBuilder.setDescription("here is every kata you have completed thus far. page " + o.getPage() + "/" + o.getParent().getLastPage());
			
			embedBuilder.appendDescription(System.lineSeparator());
			
			o.forEach(kata -> {
				embedBuilder.appendDescription(System.lineSeparator());
				embedBuilder.appendDescription(kata);
			});
		
			return embedBuilder;
		});
		
		embed.sendAsReply();
		
	
		//event.getMessage().replyEmbeds(embedBuilder.build()).queue();
		
	}

}
