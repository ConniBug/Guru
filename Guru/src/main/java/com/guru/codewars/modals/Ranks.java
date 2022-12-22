package com.guru.codewars.modals;
public class Ranks {
 Overall OverallObject;
 Languages LanguagesObject;


 // Getter Methods 

 public Overall getOverall() {
  return OverallObject;
 }

 public Languages getLanguages() {
  return LanguagesObject;
 }

 // Setter Methods 

 public void setOverall(Overall overallObject) {
  this.OverallObject = overallObject;
 }

 public Ranks(Overall overallObject, Languages languagesObject) {
	OverallObject = overallObject;
	LanguagesObject = languagesObject;
}

public void setLanguages(Languages languagesObject) {
  this.LanguagesObject = languagesObject;
 }
}
