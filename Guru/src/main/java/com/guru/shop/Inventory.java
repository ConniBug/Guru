package com.guru.shop;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.guru.commands.shop.items.ShopItem;

public class Inventory {

	public class InventoryItem{
		
		@SerializedName("id")
		private int id;
		
		@SerializedName("amount")
		private int amount;
		
		public int getId() {
			return id;
		}
		public int getAmount() {
			return amount;
		}
		public void setId(int name) {
			this.id = name;
		}
		public void setAmount(int amount) {
			this.amount = amount;
		}
		public InventoryItem(int id, int amount) {
			this.id = id;
			this.amount = amount;
		}
	}
	
	@SerializedName("items")
	private List<Inventory.InventoryItem> items;
	
	public Inventory() {
		if(this.items == null) {
			items = new ArrayList<>();
		}
	}
	
	public boolean hasItem(int id) {
		return this.items.stream().filter(o -> o.getName().equals(id)).count() > 0;
	}
	
	public int getAmount(int id) {
		if(this.hasItem(id)) {
			return (int)this.items.stream().filter(o -> o.getName()).count();
		}
		return 0;
	}

	public List<Inventory.InventoryItem> getItems() {
		return items;
	}

	public void setItems(List<Inventory.InventoryItem> items) {
		this.items = items;
	}
	
	public void addItem(ShopItem item) {
		InventoryItem invItem = new InventoryItem(item.getMeta().name()[0], 1);
		this.items.add(invItem);
	}
	
	public void addItem(ShopItem item, int amount) {
		InventoryItem invItem = new InventoryItem(item.getMeta().name()[0], amount);
		if(this.hasItem(item.getMeta().id())) {
			
		}
		this.items.add(invItem);
	}
		
	public void addItem(String item) {
		InventoryItem invItem = new InventoryItem(item, 1);
		this.items.add(invItem);
	}
	
	public void addItem(String item, int amount) {
		InventoryItem invItem = new InventoryItem(item, amount);
		this.items.add(invItem);
	}
	
	public void addItem(InventoryItem item) {
		InventoryItem invItem = new InventoryItem(item.getName(), 1);
		this.items.add(invItem);
	}
	
	public void addItem(InventoryItem item, int amount) {
		InventoryItem invItem = new InventoryItem(item.getName(), amount);
		this.items.add(invItem);
	}
	
	
}
