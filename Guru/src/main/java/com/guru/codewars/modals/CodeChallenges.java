package com.guru.codewars.modals;
public class CodeChallenges {
 private float totalAuthored;
 private float totalCompleted;


 // Getter Methods 

 public float getTotalAuthored() {
  return totalAuthored;
 }

 public float getTotalCompleted() {
  return totalCompleted;
 }

 // Setter Methods 

 public CodeChallenges(float totalAuthored, float totalCompleted) {
	this.totalAuthored = totalAuthored;
	this.totalCompleted = totalCompleted;
}

public void setTotalAuthored(float totalAuthored) {
  this.totalAuthored = totalAuthored;
 }

 public void setTotalCompleted(float totalCompleted) {
  this.totalCompleted = totalCompleted;
 }
}
