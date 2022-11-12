package com.syngen.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public enum Operators {
	
	
	SUBTRACTION('-', 1, (n) -> 0.5, Binding.BOTH),
	ADDITION('+', 2, (n) -> 0.5, Binding.BOTH),
	MILTIPLICATION('*', 3, (n) -> 0.25, Binding.BOTH),
	DIVISION('/', 4, (n) -> 0.25, Binding.BOTH),
	POWER('^', 5, (n) -> 0.1, Binding.BOTH),
	SQUARE_ROOT('√', 6, (n) -> 0.1, Binding.BOTH);
	
	private final char symbol;
	private final Selectable weight;
	private final Binding binding;
	
	private int priority;
	
	private static Map<SingleOperatorExpression, Double> hash = new HashMap<>();
	
	
	/**
	 * 
	 * @param symbol the symbol that represents this operator
	 * @param priority the priority in terms of PEMDAS
	 * @param weight the rate at which it accures in a question given a difficulty n
	 * @param binding the values it effects relative to it's position
	 */
	private Operators(char symbol, int priority, Selectable weight, Binding binding) {
		this.symbol = symbol;
		this.priority = priority;
		this.weight = weight;
		this.binding = binding;
	}

	public char getSymbol() {
		return symbol;
	}

	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public Selectable getWeight() {
		return weight;
	}
	
	public boolean effectsLeft() {
		return binding == Binding.LEFT || binding == Binding.BOTH;
	}
	public boolean effectsRight() {
		return binding == Binding.RIGHT || binding == Binding.BOTH;
	}
	
	public String defaultExpressionLeft() {
		switch (this) {
		case SQUARE_ROOT:
			return "2";
		default:
			return "";
		}
	}
	
	public int getRandomNumber(int difficulty) {
		switch (this) {
		case POWER:
			return ThreadLocalRandom.current().nextInt(2, 5);
		case SQUARE_ROOT:
			return ThreadLocalRandom.current().nextInt(2, 3);
		default:
			if(difficulty > 5) {
				return this.veryBigInt();
			}
			if(difficulty > 2) {
				return this.bigInt();
			}
			return this.smallInt();
		}
	}
	
	public String getPrefix(int difficulty) {
		
		if(this == Operators.SQUARE_ROOT) {
			if(difficulty > 3) {
				return "+" + this.number(3);
			}
			return "+";
		}
		
		return "";
	}
	
	/**
	 * evaluates an expression if the left and right side are simplified and are valid numbers
	 * @param expressionLeft the left value to the operator
	 * @param expressionRight the right value to the operator
	 * @return 
	 */
	public double compute(double expressionLeft, double expressionRight) {
		
		SingleOperatorExpression expression = new SingleOperatorExpression(this, expressionLeft, expressionRight);
		
		for(SingleOperatorExpression key : hash.keySet()) {
			if(expression.compare(key)) {
				return hash.get(key);
			}
		}
		
		double result = 0;
		
		switch (this) {
		case ADDITION:
			result = Maths.round(expressionLeft + expressionRight);
			break;
		case SUBTRACTION:
			result = Maths.round(expressionLeft - expressionRight);
			break;
		case MILTIPLICATION:
			result = Maths.round(expressionLeft * expressionRight);
			break;
		case DIVISION:
			result = Maths.round(expressionLeft / expressionRight);
			break;
		case POWER:
			result = Maths.round(Math.pow(expressionLeft, expressionRight));
			break;
		case SQUARE_ROOT:
			//by default square root is power 1/2;
			result = Maths.round(Math.pow(expressionRight, 1/expressionLeft));
			break;
		}
		
		hash.put(expression, result);

		return result;
	}
	
	/**
	 * TODO improve in the future if the need arises.
	 * @param factorial of number
	 * @return
	 */
	public double factorial(double number) {
		double factorial = 1;
	    for(double i = 1; i <= number; ++i) {
	        factorial *= i;
	    }
	    return factorial;
	}
	
	/**
	 * evaluates an expression if the left and right side are simplified and are valid numbers
	 * @param expressionLeft the left value to the operator
	 * @param expressionRight the right value to the operator
	 * @return 
	 */
	public double compute(String left, String right) {	
		double expressionLeft = Double.parseDouble(left);
		double expressionRight = Double.parseDouble(right);
		return this.compute(expressionLeft, expressionRight);
	}
	
	public static Operators getAny() {
		return values()[ThreadLocalRandom.current().nextInt(values().length)];
	}
	public static Operators getAny(int difficulty) {
		for(int i = values().length - 1; i > 0; i--) {
			Operators current = values()[i];
			if(Chance.chance(current.weight.chance(difficulty))) {
				return current;
			}		
		}
		return Operators.getAny(difficulty);
	}
	
	public static Operators getOperator(char operator) {
		for(Operators i : Operators.values()) {
			if(operator == i.symbol) {
				return i;
			}
		}
		return null;
	}
	
	public static int getPriority(char operator) {
		for(Operators i : Operators.values()) {
			if(operator == i.symbol) {
				return i.priority;
			}
		}
		return 0;
	}
	
	public static boolean containsOperators(String expression) {
		for(Operators i : Operators.values()) {
			if(expression.contains(i.symbol+"")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsOnly(String expression) {
		for(char o : expression.toCharArray()) {
			Operators op = getOperator(o);
			if(op != null && op.getPriority() > 0) {
				if(op != this) {
					//System.out.println("found invalid operator => " + op.symbol);
					return false;
				}
			}
		}
		return true && Operators.containsOperators(expression);
	}
	
	
	private int number(int range) {
		return ThreadLocalRandom.current().nextInt(1, range);
	}
	private int smallInt() {
		return ThreadLocalRandom.current().nextInt(1, 100);
	}
	private int bigInt() {
		return ThreadLocalRandom.current().nextInt(1, 1000);
	}
	private int veryBigInt() {
		return ThreadLocalRandom.current().nextInt(1, 1000);
	}
	
	@Override
	public String toString() {
		return Character.toString(this.getSymbol());
	}
	
	
}
