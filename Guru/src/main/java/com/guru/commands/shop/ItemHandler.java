package com.guru.commands.shop;

import java.util.List;
import java.util.Optional;

import com.guru.commands.shop.items.ShopItem;
import com.guru.reflection.ShopScanner;

public class ItemHandler {
	
	private final List<ShopItem> items;
	
	public ItemHandler() {
		ShopScanner scanner = new ShopScanner();
		
		this.items = scanner.retrieveCommandsFromPackage("com.guru.commands.shop.items");
	}
	
	public ShopItem getItemByName(String name) {
		return null;
	}

	public List<ShopItem> getItems() {
		return items;
	}
	
	public Optional<ShopItem> getItemById(int id) {
		return this.items.stream().filter(o -> o.getMeta().id() == id).findFirst();
	}

}
