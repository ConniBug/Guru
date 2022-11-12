package com.syngen.engine;

/**
 * this class dictates which part of the expression relative to the operater the operater acts on
 * for example 
 * LEFT would mean that the operater on acts on the left expression for example 5!(2) only the 5 will be effected
 * RIGHT would mean that the operater on acts on the right expression for example (3)√9 only the 9 will be effected
 * BOTH would mean that the operater on acts on both expressions, for example 3*3, both 3's will be effected
 * @author synte
 * @version 0.0.1
 */
public enum Binding {
	LEFT, RIGHT, BOTH;
}
