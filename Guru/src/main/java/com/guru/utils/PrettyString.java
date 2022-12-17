package com.guru.utils;

/**
 * 
 * @author synte
 * @version utilities class, usefull for string minipulation 
 *
 */
public class PrettyString {

	/**
	 * @param text to be beautified
	 * @return lowercase string with the first letter capitalised and replaces _ with spaces
	 */
	public static String capitaliseFirstLetter(String text) {
		return (text.substring(0, 1).toUpperCase() + text.toLowerCase().substring(1)).replace("_", " ");
	}
	
	public static String cutEnd(String text) {
		return text.substring(0, text.length() - 1);
	}
	
	public static <T> String prettyArray(T[] arr, String prefix, boolean formatString) {
		String alts = "";
		
		for(Object i : arr.clone()) {
			alts += prefix + i + " ";
		}
		
		if(formatString) {
			return capitaliseFirstLetter(alts.trim());
		}
		
		return alts.trim();
	}
	
	public static <T> String prettyArray(T[] arr, String prefix, boolean formatString, boolean newLine) {
		String alts = "";
		
		for(Object i : arr.clone()) {
			alts += prefix + i + " ";
			if(newLine) {
				alts += System.lineSeparator();
			}
		}
		
		if(formatString) {
			return capitaliseFirstLetter(alts.trim());
		}
		
		return alts.trim();
	}
	
	public static <T> String prettyArray(T[] arr, String prefix) {
		String alts = "";
		
		for(Object i : arr.clone()) {
			alts += prefix + i + " ";
		}
		
		return alts.trim();
	}
	
	public static <T> String prettyArray(T[] arr, boolean formatString) {
		return prettyArray(arr, "", formatString);
	}
	
	public static <T> String prettyArray(T[] arr) {
		return prettyArray(arr, "");
	}
	
	

	
}
