package com.syngen.engine;

public class Numbers {
	
	public static boolean isNumber(String num) {
		try {
			Double.parseDouble(num);
			return true;
		}catch (Exception e) {}
		return false;
	}
	
	public static int getNumber(String num) {
		return Integer.parseInt(num);
	}

}
