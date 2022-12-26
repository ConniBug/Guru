package com.guru.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.gson.annotations.SerializedName;

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
		
		public void increment() {
			this.amount+=1;
		}
		public void increment(int amount) {
			this.amount+=amount;
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
		return this.items.stream().filter(o -> o.getId() == id).count() > 0;
	}
	
	public int getAmount(int id) {
		if(this.hasItem(id)) {
			return (int)this.items.stream().filter(o -> o.getId() == id).count();
		}
		return 0;
	}

	public List<Inventory.InventoryItem> getItems() {
		return items;
	}

	public void setItems(List<Inventory.InventoryItem> items) {
		this.items = items;
	}

	public Optional<InventoryItem> getItem(int id) {
		return this.items.stream().filter(o -> o.getId() == id).findFirst();
	}
	
	public void addItem(int item, int amount) {
		if(this.hasItem(item)) {
			this.getItem(item).get().increment(amount);
		}else {
			InventoryItem invItem = new InventoryItem(item, amount);
			this.items.add(invItem);			
		}
	}
	
	
	
	
}
