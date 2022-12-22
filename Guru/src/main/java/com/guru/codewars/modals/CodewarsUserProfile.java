package com.guru.codewars.modals;

import org.json.JSONObject;

public class CodewarsUserProfile {
 private String id;
 private String username;
 private String name;
 private float honor;
 private String clan;
 private float leaderboardPosition;
 Ranks RanksObject;
 CodeChallenges CodeChallengesObject;
 private JSONObject json;


 public CodewarsUserProfile(String id, String username, String name, float honor, String clan, float leaderboardPosition,
		Ranks ranksObject, CodeChallenges codeChallengesObject, JSONObject obj) {
	this.id = id;
	this.username = username;
	this.name = name;
	this.honor = honor;
	this.clan = clan;
	this.leaderboardPosition = leaderboardPosition;
	RanksObject = ranksObject;
	CodeChallengesObject = codeChallengesObject;
	this.setJson(obj);
}

// Getter Methods 

 public String getId() {
  return id;
 }

 public String getUsername() {
  return username;
 }

 public String getName() {
  return name;
 }

 public float getHonor() {
  return honor;
 }

 public String getClan() {
  return clan;
 }

 public float getLeaderboardPosition() {
  return leaderboardPosition;
 }

 public Ranks getRanks() {
  return RanksObject;
 }

 public CodeChallenges getCodeChallenges() {
  return CodeChallengesObject;
 }

 // Setter Methods 

 public void setId(String id) {
  this.id = id;
 }

 public void setUsername(String username) {
  this.username = username;
 }

 public void setName(String name) {
  this.name = name;
 }

 public void setHonor(float honor) {
  this.honor = honor;
 }

 public void setClan(String clan) {
  this.clan = clan;
 }

 public void setLeaderboardPosition(float leaderboardPosition) {
  this.leaderboardPosition = leaderboardPosition;
 }

 public void setRanks(Ranks ranksObject) {
  this.RanksObject = ranksObject;
 }

 public void setCodeChallenges(CodeChallenges codeChallengesObject) {
  this.CodeChallengesObject = codeChallengesObject;
 }

public JSONObject getJson() {
	return json;
}

public void setJson(JSONObject json) {
	this.json = json;
}
}
