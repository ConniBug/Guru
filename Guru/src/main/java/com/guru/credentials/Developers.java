package com.guru.credentials;

import net.dv8tion.jda.api.entities.User;

public enum Developers {
	
	SYNTEX("234004050201280512"),
	ARAGORN("740353653495431189"),
	EPRESSO("295968900951179266");

	private final String ID;
	
	private Developers(String ID) {
		this.ID = ID;
	}

	public String getID() {
		return ID;
	}
	
	/**
	 * checks if the user is a developer
	 * @param user the user to be checked
	 * @return wether or not the user is a developer
	 */
	public static boolean isDeveloper(User user) {
		for(Developers o : Developers.values()) {
			if(o.getID().equals(user.getId())) {
				return true;
			}
		}
		return false;
	}


}
