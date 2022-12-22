package com.guru.codewars.modals;
public class Java {
 private float rank;
 private String name;
 private String color;
 private float score;


 // Getter Methods 

 public float getRank() {
  return rank;
 }

 public String getName() {
  return name;
 }

 public String getColor() {
  return color;
 }

 public Java(float rank, String name, String color, float score) {
	super();
	this.rank = rank;
	this.name = name;
	this.color = color;
	this.score = score;
}

public float getScore() {
  return score;
 }

 // Setter Methods 

 public void setRank(float rank) {
  this.rank = rank;
 }

 public void setName(String name) {
  this.name = name;
 }

 public void setColor(String color) {
  this.color = color;
 }

 public void setScore(float score) {
  this.score = score;
 }
}
