package com.syngen.engine;

/**
 * The all powerful maths engine made by syntex, providing all mathmatical computation
 * @author synte
 * @version 0.0.1
 *
 */
public class SynEngine {
	
	public String simplify(String expression) {
		//return SimplifyExpression.instance(expression).calculate() + "";
		return "";
	}
	

	
	public static void main(String[] args) {
		//String question = new BasicAlgebra(2).getQuestion().getQuestion();
		//String question = "√(3(4^2+36)/(4^2))(6^2*2*2)";
		//String question = "(3(4^2+36)/(4^2))(6^2*2*2)";
		//-1-2-3*-4(-3)
		//String question = "(5^2)3+3+2*2+3+3(5^2)";
		//String question = "3+6-4-4";
		//(-5^2)3-3-2*2-3-3-(5^2)
		//(5^2)3-3-2*2-3-3-(5^2)
		//String question = "25.0*3-3-2*2-3-3-25.0";
		//25.0*3-3-2*2-3-3-25.0
		//String question = "0.0+2.0+-28.0";
		
		//a = 1, b = 9, c = 2
		
		//SimplifyExpression.instance("-9+((√(9^2-4(1)(2))/2(1))").simplify();
		//SimplifyExpression.instance("(-9-√(9^2-4(1)(2)))/2(1)").simplify();
		
		
		//quadratic solver
		//int a = 1;
		//int b = 9;
		//int c = 2;
		
		//System.out.println("a: " + a + " b: " + b + " c: " + c);
		//SimplifyExpression.instance("(-" + b + "+√(" + b + "^2-4(" + a + ")(" + c + ")))/2(" + a + ")").simplify();
		//SimplifyExpression.instance("(-" + b + "-√(" + b + "^2-4(" + a + ")(" + c + ")))/2(" + a + ")").simplify();
		
		//SimplifyExpression.instance("(7^(-2+7^(-2)-2-2-2-2-2)-2-7^(-2)-2-2-2-2-22-2-7^(-2)-2-2-2-2-22-2)-2").simplify();
		//SimplifyExpression.instance("-2-2-2-2-2-2-2-2-2-2").simplify();
		
		//SimplifyExpression.instance("√(3(4^2+36)/(4^2))(6^2*2*2)").simplify();
		
		//SimplifyExpression.instance("√(3(4^2+36)/(4^2))(6^2*2*2)+12!").simplify();
		
		//SimplifyExpression.instance("(-9+√(81.0-8.0))").simplify();
		//SimplifyExpression.instance("(-9+√(81.0-8.0))").simplify();
		
		//-3^-3*-3-3-3/-3/-3/-3
		
		//SimplifyExpression.instance("-2-2").simplify();
		//System.out.println("==========================");
		//SimplifyExpression.instance("2-2").simplify();
		
		SimplifyExpression.instance("√(3(4^2+36)/(4^2))(6^2*2*2) ").simplify();
		
		
		
		
		//9^2
		//(highestOperator.getPriority() >= Operators.MILTIPLICATION.getPriority() && firstSymbol)
	}
	
}
