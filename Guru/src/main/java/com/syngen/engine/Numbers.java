package com.syngen.engine;

public class Numbers {
	
	public static boolean isNumber(String num) {
		try {
			Double.parseDouble(num);
			return true;
		}catch (Exception e) {}
		return false;
	}

}
