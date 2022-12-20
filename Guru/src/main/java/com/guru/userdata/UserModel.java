package com.guru.userdata;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.guru.bot.Guru;
import com.guru.codewars.modals.CodewarsUserProfile;
import com.guru.commands.shop.Transaction;
import com.guru.commands.shop.TransactionType;
import com.guru.logger.Logger;
import com.guru.utils.Network;

public class UserModel {

	private String userID;
	private int bablons;
	private String codewars;
	
	private List<Transaction> transactions;
	private List<String> link;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public int getBablons() {
		return bablons;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transaction) {
		this.transactions = transaction;
	}
	public String getCodewars() {
		return codewars;
	}
	public void setCodewars(String codewars) {
		this.codewars = codewars;
	}
	public List<String> getLink() {
		return link;
	}
	public void setLink(List<String> link) {
		this.link = link;
	}
	public void setBablons(int bablons) {
		this.bablons = bablons;
	}
	
	public UserModel(String userID, int bablons, List<Transaction> transactions, List<String> link, String codewars) {
		this.userID = userID;
		this.bablons = bablons;
		this.transactions = transactions;
		this.link = link;
		this.codewars = codewars;
		
	}
	
	public void createCodeWarsLink(String link) {
		this.link.clear();
		this.link.add(link);
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
			Logger.INFO("Updated userdata(" + this.getUserID() + ") -> " + json);
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
	public CodewarsUserProfile getCodewarsProfile() {
		
		String url = "https://www.codewars.com/api/v1/users/" + this.codewars.split("users/")[1];
		String json = Network.readURL(url);
		
		Gson gson = new Gson();
		
		CodewarsUserProfile profile = gson.fromJson(json, CodewarsUserProfile.class);
		
		return profile;
	}
	
	
	
	
}
