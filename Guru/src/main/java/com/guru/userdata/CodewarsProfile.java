package com.guru.userdata;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import com.google.gson.annotations.SerializedName;
import com.guru.bot.Guru;
import com.guru.codewars.kata.Kata;
import com.guru.codewars.modals.CodewarsMeta;
import com.guru.codewars.users.katas.Datum;

public class CodewarsProfile {

	@SerializedName("profile")
	private String profile;
	
	@SerializedName("meta")
	private CodewarsMeta meta;
	
	@SerializedName("katas")
	private List<Datum> katas;

	public CodewarsProfile(String profile, CodewarsMeta meta, List<Datum> katas) {
		this.profile = profile;
		this.meta = meta;
		this.katas = katas;
	}

	public String getProfile() {
		return profile;
	}

	public CodewarsMeta getMeta() {
		return meta;
	}

	public List<Datum> getKatas() {
		return katas;
	}

	public List<Datum> getKatasSorted() {
		
		this.katas.sort((a, b) -> {
			
			try {
				
				Optional<Kata> kata1 = Guru.getInstance().getKataCasher().getKataFromId(a.id).get();
				Optional<Kata> kata2 = Guru.getInstance().getKataCasher().getKataFromId(b.id).get();
				
				if(kata1.isPresent() && kata2.isPresent()) {
					
					int rank1 = (int)kata1.get().getRankObject().getId()*-1;
					int rank2 = (int)kata2.get().getRankObject().getId()*-1;
					
					return rank1 - rank2;	
				}
				
				if(kata1.isPresent() && !kata2.isPresent()) {
					return -1;
				}
				
				if(!kata1.isPresent() && kata2.isPresent()) {
					return 1;
				}
				
			}catch (Throwable e) {
				e.printStackTrace();
			}
			
			return 0;
			
		});
	
		return katas;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public void setMeta(CodewarsMeta meta) {
		this.meta = meta;
	}

	public void setKatas(List<Datum> katas) {
		this.katas = katas;
	}
	
	public boolean isRegistered() {
		return !this.profile.isEmpty();
	}
	
	public String getName() {
		return this.profile.split("users/")[1];
	}
	
	public String getApiURL() {
		return "https://www.codewars.com/api/v1/users/"+ this.getName();
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
	  for (Field field : fields) {
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
