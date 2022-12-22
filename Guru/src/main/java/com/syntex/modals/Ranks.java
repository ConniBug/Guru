package com.syntex.modals;

import java.awt.Color;

public enum Ranks {
	
	KYU_SEVEN("1052289248398675968", 20, Color.white),
	KYU_SIX("1052292541816516679", 76, Color.white),
	KYU_FIVE("1044548140654657596", 229, Color.yellow),
	KYU_FOUR("1053206004520714280", 643, Color.yellow),
	KYU_THREE("1049689385160953927", 1768, Color.blue),
	KYU_TWO("1052289318355488899", 4829, Color.blue),
	KYU_ONE("1052298525033123920", 13147, Color.MAGENTA),
	DAN_ONE("1052290796600164473", 35759, Color.MAGENTA);
	
	private final String id;
	private final int requiredXP;
	private final Color color;
	
	private Ranks(String id, int requiredXP, Color colour) {
		this.id = id;
		this.requiredXP = requiredXP;
		this.color = colour;
	}
	
	/**
	 * 
	 * @return the id for this role
	 */
	public String getID() {
		return this.id;
	}

	/**
	 * 
	 * @return the required xp for this role
	 */
	public int getRequiredXP() {
		return requiredXP;
	}

	public Color getColor() {
		return color;
	}
	
}
