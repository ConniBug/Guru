package com.guru.userdata;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nonnull;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.guru.bot.Guru;
import com.guru.codewars.modals.CodewarsMeta;
import com.guru.codewars.users.katas.Datum;
import com.guru.data.MemoryManagement;
import com.guru.logger.Logger;
import com.syntex.modals.Ranks;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;

public class UsersHandler {

	private Set<UserModel> users = new HashSet<>();
	private MemoryManagement memoryManagement;
	
	private HashMap<String, Date> lastUpdated;
	private long UPDATE_TIMER = 1000*60*10;
	
	public UsersHandler(@Nonnull MemoryManagement management) {
		this.memoryManagement = management;
	}
	
	public void loadUserData() {
		
		//retrieve all user files.
		this.memoryManagement.getJson().entrySet().stream().filter(o -> o.getKey().startsWith("users.")).forEach(o -> {
		
			Gson gson = new Gson();
			
			UserModel user = gson.fromJson(o.getValue().toString(), UserModel.class);
		
			this.users.add(user);
			
		});
		
		this.lastUpdated = new HashMap<>();
		
	}
	
	public Set<UserModel> getUsers() {
		return users;
	}

	public void setUsers(Set<UserModel> users) {
		this.users = users;
	}

	public MemoryManagement getMemoryManagement() {
		return memoryManagement;
	}

	public void setMemoryManagement(MemoryManagement memoryManagement) {
		this.memoryManagement = memoryManagement;
	}
	
	public boolean needsUpdating(String id) {
		Date now = new Date();
		
		if(!this.lastUpdated.containsKey(id) || !(this.users.stream().filter(o -> o.getUserID().equals(id)).count() > 0)) {
			return true;
		}
		
		Date lastUpdated = this.lastUpdated.get(id);
		
		long difference = now.getTime() - lastUpdated.getTime();
		
		if(difference < UPDATE_TIMER) {
			return false;
		}

		return true;
	}
	
	public void update(UserModel data) {
		
		Date now = new Date();
		
		if(!this.lastUpdated.containsKey(data.getUserID())) {
			Logger.INFO("Updating cashe of " + data.getUserID());
			this.cashe(data);
			return;
		}
		
		Date lastUpdated = this.lastUpdated.get(data.getUserID());
		
		long difference = now.getTime() - lastUpdated.getTime();
		
		if(difference < UPDATE_TIMER) {
			return;
		}
		
		Logger.INFO("Updating cashe of " + data.getUserID());
		this.cashe(data);
		
	}
	
	public void cashe(UserModel data) {
		Date now = new Date();

		if(!data.getCodewars().isRegistered()) {
			return;
		}
		
		Logger.INFO("Updated cashe for " + data.getUserID());
		
		CodewarsProfile updatedProfile = new CodewarsProfile(data.getCodewars().getProfile(),
															 CodewarsMeta.fromName(data.getCodewars().getName()),
															 Datum.fromApi(data.getCodewars().getApiURL()));
		
		data.setCodewars(updatedProfile);
		data.save();
		
		this.lastUpdated.put(data.getUserID(), now);
	
		int value = data.getCodewars().getMeta().getRanks().getOverall().getRank();
		Ranks rank = Ranks.fromValue(value);
		
		Guru.getInstance().getJDA().getGuilds().forEach(o -> {
			Optional<Role> roles = o.getRoles().stream().filter(k -> k.getId().equals(rank.getID())).findFirst();
			if(roles.isPresent()) {
				new Thread(() -> {
					o.loadMembers().get().stream().filter(k -> k.getId().equals(data.getUserID())).findFirst().ifPresent(p -> {
						o.addRoleToMember(p, roles.get()).queue();
						System.out.println("saved " + p.getEffectiveName());
					});
				}).start();
			}
		});
	
	
	}

	/**
	 * returns the user data from the users discord id
	 * @param id the discord id of the user
	 * @return
	 */
	public UserModel getUserData(String id) {
		Optional<UserModel> data = this.users.stream().filter(o -> o.getUserID().equals(id)).findFirst();
		
		if(data.isPresent()) {
			this.update(data.get());
			return data.get();
		}
	
		UserModel user = UserModel.empty(id);
		
		this.users.add(user);
		
		Logger.INFO("added user -> " + user);

		return user;
	}
	
	/**
	 * returns the user data from the users
	 * @param the user
	 * @return
	 */
	public UserModel getUserData(User user) {
		
		Optional<UserModel> data = this.users.stream().filter(o -> o.getUserID().equals(user.getId())).findFirst();
		
		if(data.isPresent()) {
			this.update(data.get());
			return data.get().feedUser(user);
		}
		
		UserModel model = UserModel.empty(user);
		
		this.users.add(model);

		return model;
	}
	
	/**
	 * returns the user data from the users
	 * @param the user
	 * @return
	 */
	public UserModel getUserData(Member user) {
		
		Optional<UserModel> data = this.users.stream().filter(o -> o.getUserID().equals(user.getId())).findFirst();
		
		if(data.isPresent()) {
			this.update(data.get());
			return data.get().feedUser(user);
		}
		
		UserModel model = UserModel.empty(user);
		
		this.users.add(model);

		return model;
	}
	
	/**
	 * save the cashed user data.
	 */
	public void save() {
		
		File user = new File(this.memoryManagement.getRes(), "users");
		
		this.users.forEach(o -> {
			
			File userFolder = new File(user, o.getUserID());
			File userFile = new File(userFolder, "userdata.json");
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			String json = gson.toJson(o);
			
			try {
				FileUtils.write(userFile, json, "UTF8");
				Logger.INFO("Updated userdata(" + o.getUserID() + ") -> " + json);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		});
		
	}

}















