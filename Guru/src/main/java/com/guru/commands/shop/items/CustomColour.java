package com.guru.commands.shop.items;

import com.guru.userdata.UserModel;

import net.dv8tion.jda.api.entities.User;

@ShopMeta(cost = 800, name = {"cColour"}, emoji = ":rainbow_flag:", description = "Pick your own colour, ( colours similar to predefined roles are not allowed ) **disclaimer, colours can only be used on custom roles**", id = 2)
public class CustomColour extends ShopItem{

	@Override
	public void onBuyEvent(User user, UserModel profile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUseEvent(User user, UserModel profile) {
		// TODO Auto-generated method stub
		
	}

}
