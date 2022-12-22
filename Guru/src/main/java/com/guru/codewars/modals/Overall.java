package com.guru.codewars.modals;
public class Overall {
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

 public float getScore() {
  return score;
 }

 // Setter Methods 

 public Overall(float rank, String name, String color, float score) {
	this.rank = rank;
	this.name = name;
	this.color = color;
	this.score = score;
}

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