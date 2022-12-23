package com.syntex.modals;

import java.awt.Color;

public enum Ranks {
	
	KYU_SEVEN("1052289248398675968", 20, Color.white, -7),
	KYU_SIX("1052292541816516679", 76, Color.white, -6),
	KYU_FIVE("1044548140654657596", 229, Color.yellow, -5),
	KYU_FOUR("1053206004520714280", 643, Color.yellow, -4),
	KYU_THREE("1049689385160953927", 1768, Color.blue, -3),
	KYU_TWO("1052289318355488899", 4829, Color.blue, -2),
	KYU_ONE("1052298525033123920", 13147, Color.MAGENTA, -1),
	DAN_ONE("1052290796600164473", 35759, Color.MAGENTA, 1);
	
	private final String id;
	private final int requiredXP;
	private final Color color;
	private final int rank;
	
	private Ranks(String id, int requiredXP, Color colour, int rank) {
		this.id = id;
		this.requiredXP = requiredXP;
		this.color = colour;
		this.rank = rank;
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

	public int getRank() {
		return rank;
	}
	
	public static Ranks fromValue(int rank) {
		for(Ranks i : Ranks.values()) {
			if(i.rank == rank) {
				return i;
			}
		}
		return null;
	}
	
}
