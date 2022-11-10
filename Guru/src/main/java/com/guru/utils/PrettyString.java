package com.guru.utils;

public class PrettyString {

	public static String prettyTitle(String text) {
		return text.substring(0, 1).toUpperCase() + text.toLowerCase().substring(1);
	}
	
}
