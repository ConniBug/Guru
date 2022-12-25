package com.guru.commands.codewars;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.guru.bot.Guru;
import com.guru.codewars.kata.Kata;
import com.guru.codewars.users.katas.Datum;
import com.guru.commands.Category;
import com.guru.commands.CommandMeta;
import com.guru.paged.PagedEmbed;
import com.guru.userdata.UserModel;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@CommandMeta(name = {"ckatas", "katas", "completedkatas"}, description = "shows all katas the user has completed", category = Category.CODEWARS, usage = {"katas", "katas <@user>"})
public class CompletedKatas extends CodewarsCommand{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {
		
		List<Member> mentions = event.getMessage().getMentions().getMembers();

		UserModel model;
		
		Optional<Message> updateHandler = null;
		
		if(mentions.size() > 0) {
			
			Member member = mentions.get(0);
			
			updateHandler = this.sendUpdateMessage(member.getId(), event);
			
			model = Guru.getInstance().getUsersHandler().getUserData(mentions.get(0));
			
		}else {
			
			updateHandler = this.sendUpdateMessage(event.getAuthor().getId(), event);
			
			model = this.getUserModel(event);
			
		}
		
		if(!model.getCodewars().isRegistered()) {
			throw new Exception("Sorry, you need to link your account first.");
		}

		List<String> completed = this.getSortedkatas(model);
		
		PagedEmbed embed = PagedEmbed.create(event, completed, 15, false, o -> {	
			EmbedBuilder embedBuilder = new EmbedBuilder();
			embedBuilder.setTitle("Codewars profile");
			embedBuilder.setColor(Color.cyan);
			embedBuilder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
			
			embedBuilder.setDescription("here are the first 200 katas you've done. page " + o.getPage() + "/" + o.getParent().getLastPage());
			
			embedBuilder.appendDescription(System.lineSeparator());
			
			o.forEach(kata -> {
				embedBuilder.appendDescription(System.lineSeparator());
				embedBuilder.appendDescription(kata);
			});
		
			return embedBuilder;
		});
		
		if(updateHandler.isEmpty()) {
			embed.sendAsReply();
		}else {
			embed.fromMessage(updateHandler.get());			
		}
	
	}
	
	public List<String> getSortedkatas(UserModel model) throws InterruptedException, ExecutionException {
		
		List<String> completed = new ArrayList<>();
		
		int counter = 0;
		
		for(Datum o : model.getCodewars().getKatasSorted()) {
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
		
		return completed;
	}

}
