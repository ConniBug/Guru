package com.guru.commands.shop.items;

import com.guru.userdata.UserModel;

import net.dv8tion.jda.api.entities.User;

public abstract class ShopItem {
	
	public abstract void onBuyEvent(User user, UserModel profile);
	public abstract void onUseEvent(User user, UserModel profile);

	/**
	 * @return meta information for this command, defined in the commandmeta annotation for the class
	 */
	public ShopMeta getMeta() {
		return this.getClass().getAnnotation(ShopMeta.class);
	}
	
}
