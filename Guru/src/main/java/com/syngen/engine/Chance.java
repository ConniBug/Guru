package com.syngen.engine;

import java.util.concurrent.ThreadLocalRandom;

/**
 * all to do with chance
 * @author synte
 * @version 0.0.1
 */
public class Chance {

	/**
	 * returns a chance ( chance is out of 1 )
	 * this method cannot handle longs
	 * @return this method return true chance% amount of times otherwise it will return false
	 */
	public static <T extends Number> boolean chance(T chance) {
		return ThreadLocalRandom.current().nextDouble() <= Double.valueOf(chance.toString());
	}
	
}
