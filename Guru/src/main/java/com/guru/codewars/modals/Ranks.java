package com.guru.codewars.modals;

import java.lang.reflect.Field;

import com.google.gson.annotations.SerializedName;

   
public class Ranks {

   @SerializedName("languages")
   Languages languages;

   @SerializedName("overall")
   Overall overall;


    public void setLanguages(Languages languages) {
        this.languages = languages;
    }
    public Languages getLanguages() {
        return languages;
    }
    
    public void setOverall(Overall overall) {
        this.overall = overall;
    }
    public Overall getOverall() {
        return overall;
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