package com.guru.codewars.users.katas;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.guru.utils.Network;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
public class Datum{
	
	@SerializedName("id")
    public String id;
	
	@SerializedName("name")
    public String name;
	
	@SerializedName("slug")
    public String slug;
	
	@SerializedName("languages")
    public ArrayList<String> completedLanguages;
	
	@SerializedName("completedAt")
    public Date completedAt;
	
	/**
	 * 
	 * @return the users completed katas
	 */
	public static List<Datum> fromApi(String api){
		Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
		
		List<Datum> entries = new ArrayList<>();
		
		List<CompletedKata> katas = new ArrayList<>();
		
		String json = Network.readURL(api + "/code-challenges/completed?page=0");
		
		System.out.println(json);
		System.out.println(api + "/code-challenges/completed?page=0");
		
		CompletedKata o = gson.fromJson(json, CompletedKata.class);
		
		katas.add(o);
		
		katas.forEach(k -> k.data.forEach(entries::add));
		
		return entries;
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

