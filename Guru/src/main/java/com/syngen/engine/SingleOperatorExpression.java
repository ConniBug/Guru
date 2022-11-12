package com.syngen.engine;

public class SingleOperatorExpression {
	
	private final double leftExpression;
	private final double rightExpression;
	private final Operators operator;
	
	public SingleOperatorExpression(Operators operator, double leftExpression, double rightExpression) {
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
		this.operator = operator;
	}
	
	public double getLeftExpression() {
		return leftExpression;
	}
	public double getRightExpression() {
		return rightExpression;
	}
	public Operators getOperator() {
		return operator;
	}
	
	public boolean compare(SingleOperatorExpression expression) {
		return this.leftExpression == expression.leftExpression && this.rightExpression == expression.rightExpression && this.operator == expression.operator;
	}

}
