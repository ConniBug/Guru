package com.guru.codewars.kata;

import java.util.Optional;

public class ApprovedBy {
 private Optional<String> url = Optional.empty();
 private Optional<String> username = Optional.empty();
 
public Optional<String> getUrl() {
	return url;
}
public Optional<String> getUsername() {
	return username;
}
public void setUrl(String url) {
	this.url = Optional.of(url);
}
public void setUsername(String username) {
	this.username = Optional.of(username);
}


 

}