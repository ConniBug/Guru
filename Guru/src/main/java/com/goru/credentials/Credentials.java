package com.goru.credentials;

import com.guru.data.Configuration;
import com.guru.data.SerializableField;

@Configuration(folder = "auth")
public class Credentials {

	@SerializableField(path = "credentials.auth")
	public String AUTH_KEY; 
	
	public String getAuthKey() {
		return this.AUTH_KEY;
	}
	
}
