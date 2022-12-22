package com.guru.userdata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nonnull;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.guru.data.MemoryManagement;
import com.guru.logger.Logger;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public class UsersHandler {

	private Set<UserModel> users = new HashSet<>();
	private MemoryManagement memoryManagement;
	
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

	/**
	 * returns the user data from the users discord id
	 * @param id the discord id of the user
	 * @return
	 */
	public UserModel getUserData(String id) {
		Optional<UserModel> data = this.users.stream().filter(o -> o.getUserID().equals(id)).findFirst();
		
		if(data.isPresent()) {
			return data.get();
		}
		
		UserModel user = new UserModel(id, 0, new ArrayList<>(), "", "", "", new ArrayList<>());
		
		this.users.add(user);

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
			return data.get().feedUser(user);
		}
		
		UserModel model = new UserModel(user.getId(), 0, new ArrayList<>(), "", "", user.getName(), new ArrayList<>());
		
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
			return data.get().feedUser(user);
		}
		
		UserModel model = new UserModel(user.getId(), 0, new ArrayList<>(), "", "", user.getEffectiveName(), new ArrayList<>());
		
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















