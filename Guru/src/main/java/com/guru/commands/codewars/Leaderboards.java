package com.guru.commands.codewars;

import java.awt.Color;
import java.util.ArrayList;
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

@CommandMeta(name = {"leaderboard"}, description = "shows the leaderboard", category = Category.CODEWARS, usage = {"leaderboard", "top"})
public class Leaderboards extends CodewarsCommand{

	@Override
	public void onCommand(MessageReceivedEvent event, String[] args) throws Exception {

		List<UserModel> usermodels = Guru.getInstance().getUsersHandler().getUsers().stream().filter(o -> o.getCodewars().isRegistered()).sorted((a, b) -> (int)b.getCodewars().getMeta().getRanks().getOverall().getScore() - (int)a.getCodewars().getMeta().getRanks().getOverall().getScore()).collect(Collectors.toList());
	
		PagedEmbed<UserModel> embed = PagedEmbed.create(event, usermodels, 10, true, (page -> {
		
			EmbedBuilder embedBuilder = new EmbedBuilder();
			
			//embedBuilder.setTitle("Kent Computing (Year 1) Leaderboard");
			embedBuilder.setColor(Color.cyan);
			embedBuilder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
			
			List<UserModel> content = page.getPagedElements();
			
			int digits = String.valueOf((page.getPage()+1)*page.getParent().getElementsPerPage()).length();
			
			
			int maxLen = 0;
			
			for(int i = 0; i < content.size(); i++) {
				int len = content.get(i).getEffectiveName().length();
				if(len > maxLen) {
					maxLen = len;
				}
			}
			
			StringBuilder desc = new StringBuilder();
			
			List<String[]> fields = new ArrayList<>();
			
			for(int i = 0; i < content.size(); i++) {
				
				String[] data = new String[3];
				
				int pos = (i+1) + (page.getParent().getElementsPerPage())*(page.getPage()-1);
				
				String rank = String.format("%0" + digits+1 + "d", pos);
				
				data[0] = pos+"";
				
				
				String medal = ":bug:";
				
				
				if(pos == 1) medal = ":trophy: ";
				if(pos == 2) medal = ":medal:";
				if(pos == 3) medal = ":military_medal:";
				
				if(pos > 3) {
					//medal = ":pray:";
				}
		
				if(pos == page.getParent().getItems().size()) {
					medal = ":rock:";
				}
				
				UserModel user = content.get(i);
				embedBuilder.appendDescription(System.lineSeparator());
				
				String name = PrettyString.capitaliseFirstLetter(user.getEffectiveName());
				
				
				if(pos < 4) {
					data[1] = medal + " **" + name + "**";	
				}else {
					data[1] = medal + " " + name;
				}
				
				System.out.println(name.length() + " " + maxLen);
				String space = "";
				for(int o = name.length(); o < maxLen; o++) {
					space += " ";
				}
				String bold = "";
				
				String score = String.format("%0" + 4 + "d", user.getCodewars().getMeta().getRanks().getOverall().getScore());
				
				data[2] = score;
				
				if(pos < 4) {
					desc.append("`#" + rank + "` " + medal + " " + bold + name + bold + space + "\t - `" + user.getCodewars().getMeta().getRanks().getOverall().getScore() + "`");	
					
				}else {
					desc.append("`#" + rank + "` " + medal + " " + name + space + "\t - `" + user.getCodewars().getMeta().getRanks().getOverall().getScore() + "`");	
				}
				
				desc.append(System.lineSeparator());
				
				fields.add(data);
				
			}
			
			embedBuilder.setTitle("Kent Computing (Year 1) Leaderboard");
			
			StringBuilder a = new StringBuilder();
			StringBuilder b = new StringBuilder();
			StringBuilder c = new StringBuilder();
			
			for(String[] i : fields) {
				a.append("`#" + i[0] + "`" + System.lineSeparator());
				b.append(i[1] + System.lineSeparator());
				c.append("`" + i[2] + "`"  + System.lineSeparator());
			}
			
			
			embedBuilder.addField("Rank", a.toString(), true);
			embedBuilder.addField("Author", b.toString(), true);
			embedBuilder.addField("Score", c.toString(), true);	

			embedBuilder.setFooter("if this seems unexpected, please contact @syntex#1389");
			
			return embedBuilder;
		}));
		
		embed.sendAsReply();

	}

}
