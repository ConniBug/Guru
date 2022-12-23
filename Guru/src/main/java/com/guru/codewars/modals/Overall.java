package com.guru.codewars.modals;

import java.lang.reflect.Field;

import com.google.gson.annotations.SerializedName;

   
public class Overall {

   @SerializedName("score")
   int score;

   @SerializedName("color")
   String color;

   @SerializedName("name")
   String name;

   @SerializedName("rank")
   int rank;


    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    public String getColor() {
        return color;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    
    public void setRank(int rank) {
        this.rank = rank;
    }
    public int getRank() {
        return rank;
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