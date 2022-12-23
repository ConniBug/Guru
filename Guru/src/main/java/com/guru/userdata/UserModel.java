package com.guru.userdata;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.guru.bot.Guru;
import com.guru.codewars.users.katas.CompletedKata;
import com.guru.codewars.users.katas.Datum;
import com.guru.commands.shop.Transaction;
import com.guru.commands.shop.TransactionType;
import com.guru.utils.Network;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public class UserModel {

	@SerializedName("userid")
	private String userID;
	
	@SerializedName("bablons")
	private int bablons;
	
	//@SerializedName("codewars")
	//private String codewars;
	   
	@SerializedName("effectiveName")
	private String effectiveName;
	   
	@SerializedName("katas")
	private List<Datum> katas;
	
	
	@SerializedName("transactions")
	private List<Transaction> transactions;
	
	@SerializedName("link")
	private String link;
	
	@SerializedName("codewars")
	private CodewarsProfile codewars;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public int getBablons() {
		
		if(this.codewars.isRegistered()) {
			return bablons + (int)this.codewars.getMeta().getRanks().getOverall().getScore();
		}
		
		return bablons;
	}
	
	public List<Datum> getCashedKatas() {
		return this.katas;
	}
	
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transaction) {
		this.transactions = transaction;
	}

	public void setBablons(int bablons) {
		this.bablons = bablons;
	}
	
	/**
	 * 
	 * @return the users completed katas
	 */
	public Future<List<Datum>> getKatas1(){
		return Executors.newSingleThreadExecutor().submit(() -> {
			
			Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
			
			List<Datum> entries = new ArrayList<>();
			
			List<CompletedKata> katas = new ArrayList<>();
			
			String json = Network.readURL(this.codewars.getApiURL() + "/code-challenges/completed?page=0");
			
			CompletedKata o = gson.fromJson(json, CompletedKata.class);
			
			katas.add(o);
			
			for(int i = 1; i < o.totalPages; i++) {
				CompletedKata kata = gson.fromJson(Network.readURL(this.codewars.getApiURL() + "/code-challenges/completed?page=" + i), CompletedKata.class);
				katas.add(kata);
			}
			
			katas.forEach(k -> k.data.forEach(entries::add));
			
			return entries;
		});
	}
	
	/**
	 * 
	 * @return the users completed katas
	 */
	public Future<List<Datum>> getKatasSorted1(Comparator<? super Datum> sort){
		return Executors.newSingleThreadExecutor().submit(() -> {
			
			Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
			
			List<Datum> entries = new ArrayList<>();
			
			List<CompletedKata> katas = new ArrayList<>();
			
			String json = Network.readURL(this.codewars.getApiURL() + "/code-challenges/completed?page=0");
			
			CompletedKata o = gson.fromJson(json, CompletedKata.class);
			
			katas.add(o);
			
			for(int i = 1; i < o.totalPages; i++) {
				CompletedKata kata = gson.fromJson(Network.readURL(this.codewars.getApiURL() + "/code-challenges/completed?page=" + i), CompletedKata.class);
				katas.add(kata);
			}
			
			katas.forEach(k -> k.data.forEach(entries::add));
			
			entries.sort(sort);
			
			return entries;
		});
	}
	
	
	/**
	 * updates any details for the user model with the user itself
	 * @param user update the users model with the jda user
	 */
	public UserModel feedUser(User user) {
		this.effectiveName = user.getName();
		return this;
	}
	
	/**
	 * updates any details for the user model with the member itself
	 * @param user update the users model with the jda member
	 */
	public UserModel feedUser(Member user) {
		this.effectiveName = user.getEffectiveName();
		return this;
	}
	
	public UserModel(String userID, int bablons, List<Transaction> transactions, String link, CodewarsProfile codewars, String effectiveName, List<Datum> katas) {
		this.userID = userID;
		this.bablons = bablons;
		this.transactions = transactions;
		this.link = link;
		this.codewars = codewars;
		this.effectiveName = effectiveName;
		this.setKatas(katas);
		
	}
	
	public void createCodeWarsLink(String link) {
		this.setLink(link);
	}
	
	/**
	 * saves this users data
	 */
	public void save() {
		

		File user = new File(Guru.getInstance().getUsersHandler().getMemoryManagement().getRes(), "users");

		File userFolder = new File(user, this.getUserID());
		File userFile = new File(userFolder, "userdata.json");
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String json = gson.toJson(this);
		
		try {
			FileUtils.write(userFile, json, "UTF8");
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		
	}
	
	/**
	 * sends bablons to a user, and logs the transaction to the users record.
	 * @param user the user to recieve the money
	 * @param amount the amount of bablons to send
	 */
	public void pay(UserModel user, int amount) {
		Transaction transaction = new Transaction(amount, this.getUserID(), user.getUserID(), new Date(), TransactionType.PAYMENT);
		this.transactions.add(transaction);
		
		Transaction transactionReceiver = new Transaction(amount, this.getUserID(), user.getUserID(), new Date(), TransactionType.RECIEVEPAYMENT);
		user.transactions.add(transactionReceiver);
		
		this.bablons -= amount;
		user.bablons += amount;
	}
	
	/**
	 * sends bablons to a user, and logs the transaction to the users record.
	 * does not take the users money
	 * @param user the user to recieve the money
	 * @param amount the amount of bablons to send
	 */
	public void adminPay(UserModel user, int amount) {
		Transaction transaction = new Transaction(amount, this.getUserID(), user.getUserID(), new Date(), TransactionType.ADMINPAYMENT);
		this.transactions.add(transaction);
		
		Transaction transactionReceiver = new Transaction(amount, this.getUserID(), user.getUserID(), new Date(), TransactionType.RECIEVEADMINPAYMENT);
		user.transactions.add(transactionReceiver);
		user.bablons += amount;
	}
	
	public String getEffectiveName() {
		return effectiveName;
	}
	public void setEffectiveName(String effectiveName) {
		this.effectiveName = effectiveName;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public void setKatas(List<Datum> katas) {
		this.katas = katas;
	}
	
	public static UserModel empty(String id) {
		return new UserModel(id, 0, Arrays.asList(), "", new CodewarsProfile("", null, null), "", Arrays.asList());
	}
	
	public static UserModel empty(User id) {
		return new UserModel(id.getId(), 0, Arrays.asList(), "", new CodewarsProfile("", null, null), id.getName(), Arrays.asList());
	}
	
	public static UserModel empty(Member id) {
		return new UserModel(id.getId(), 0, Arrays.asList(), "", new CodewarsProfile("", null, null), id.getEffectiveName(), Arrays.asList());
	}
	
	
	@Override
	public String toString() {
		  StringBuilder result = new StringBuilder();
		  String newLine = System.getProperty("line.separator");

		  result.append( this.getClass().getName() );
		  result.append( " Object {" );
		  result.append(newLine);

		  //determine fields declared in this class only (no fields of superclass)
		  Field[] fields = this.getClass().getDeclaredFields();

		  //print field names paired with their values
		  for (Field field : fields) {
		    result.append("  ");
		    try {
		      result.append( field.getName() );
		      result.append(": ");
		      //requires access to private field:
		      result.append( field.get(this) );
		    } catch ( IllegalAccessException ex ) {
		      System.out.println(ex);
		    }
		    result.append(newLine);
		  }
		  result.append("}");

		  return result.toString();
		}
	
	public CodewarsProfile getCodewars() {
		return codewars;
	}
	public void setCodewars(CodewarsProfile codewars) {
		this.codewars = codewars;
	}

}
