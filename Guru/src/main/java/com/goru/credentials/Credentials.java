package com.goru.credentials;

import com.guru.data.Configuration;
import com.guru.data.SerializableField;

/**
 * This class is used to store the login credentials for the bot
 * @author synte
 * @version 0.0.1
 *
 */
@Configuration(folder = "auth")
public class Credentials {

	@SerializableField(path = "credentials.auth")
	public String AUTH_KEY; //auth key to be injected during startup
	
	public String getAuthKey() {
		return this.AUTH_KEY;
	}
	
}