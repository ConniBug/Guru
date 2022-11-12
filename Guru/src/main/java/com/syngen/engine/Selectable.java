package com.syngen.engine;

@FunctionalInterface
public interface Selectable {
	/**
	 * the weight of this function based on the difficulty, this influences how the question may be made
	 * @param difficulty of the question
	 * @return
	 */
	public abstract double chance(int difficulty);
}
