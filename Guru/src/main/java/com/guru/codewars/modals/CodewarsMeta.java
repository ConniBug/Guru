package com.guru.codewars.modals;
import java.lang.reflect.Field;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.guru.utils.Network;

   
public class CodewarsMeta {

   @SerializedName("skills")
   List<String> skills;

   @SerializedName("ranks")
   Ranks ranks;

   @SerializedName("honor")
   int honor;

   @SerializedName("codeChallenges")
   CodeChallenges codeChallenges;

   @SerializedName("name")
   String name;

   @SerializedName("clan")
   String clan;

   @SerializedName("leaderboardPosition")
   int leaderboardPosition;

   @SerializedName("id")
   String id;

   @SerializedName("username")
   String username;


    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
    public List<String> getSkills() {
        return skills;
    }
    
    public void setRanks(Ranks ranks) {
        this.ranks = ranks;
    }
    public Ranks getRanks() {
        return ranks;
    }
    
    public void setHonor(int honor) {
        this.honor = honor;
    }
    public int getHonor() {
        return honor;
    }
    
    public void setCodeChallenges(CodeChallenges codeChallenges) {
        this.codeChallenges = codeChallenges;
    }
    public CodeChallenges getCodeChallenges() {
        return codeChallenges;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    
    public void setClan(String clan) {
        this.clan = clan;
    }
    public String getClan() {
        return clan;
    }
    
    public void setLeaderboardPosition(int leaderboardPosition) {
        this.leaderboardPosition = leaderboardPosition;
    }
    public int getLeaderboardPosition() {
        return leaderboardPosition;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    
    public static CodewarsMeta fromName(String name) {
    	String json = Network.readURL("https://www.codewars.com/api/v1/users/" + name);
		
    	Gson gson = new Gson();
		
		return gson.fromJson(json, CodewarsMeta.class);	
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
    	  for ( Field field : fields  ) {
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
    
}