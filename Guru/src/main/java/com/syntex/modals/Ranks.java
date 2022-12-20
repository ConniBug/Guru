package com.syntex.modals;

public enum Ranks {
	
	KYU_SEVEN("1052289248398675968", 20),
	KYU_SIX("1052292541816516679", 76),
	KYU_FIVE("1044548140654657596", 229),
	KYU_FOUR("1053206004520714280", 643),
	KYU_THREE("1049689385160953927", 1768),
	KYU_TWO("1052289318355488899", 4829),
	KYU_ONE("1052298525033123920", 13147),
	DAN_ONE("1052290796600164473", 35759);
	
	private final String id;
	private final int requiredXP;
	
	private Ranks(String id, int requiredXP) {
		this.id = id;
		this.requiredXP = requiredXP;
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
	
}
