package com.syngen.engine;

@FunctionalInterface
public interface ExpressionParser {
	public abstract void parse(String expression1, String expression2);
}
