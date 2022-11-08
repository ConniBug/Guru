package com.goru.credentials;

import com.guru.data.SerializableField;

public class Credentials {
	
	public Credentials() {}

	@SerializableField(path = "credentials.auth")
	public String AUTH_KEY; 
	
	public String getAuthKey() {
		return this.AUTH_KEY;
	}
	
}
