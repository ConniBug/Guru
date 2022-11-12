package com.syngen.engine;

public class QuadraticParser {
	
	private final double a;
	private final double b;
	private final double c;
	
	public QuadraticParser(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public double[] getRoots() {
		//double x1 = SimplifyExpression.instance("(-" + b + "+√(" + b + "^2-4(" + a + ")(" + c + ")))/2(" + a + ")").simplifyAsDouble();
		//double x2 = SimplifyExpression.instance("(-" + b + "-√(" + b + "^2-4(" + a + ")(" + c + ")))/2(" + a + ")").simplifyAsDouble();
		
		double x1 = SimplifyExpression.instance("(-b+√((b^2) - (4)(a)(c)))/(2*a)").setVariable("a", a).setVariable("b", b).setVariable("c", c).simplifyAsDouble();
		double x2 = SimplifyExpression.instance("(-b-√((b^2) - (4)(a)(c)))/(2*a)").setVariable("a", a).setVariable("b", b).setVariable("c", c).simplifyAsDouble();
		
		return new double[] {x1, x2};
	}
	
	public static void main(String[] ags) {
		
		double a = 3;
		double b = 5;
		double c = -2;
		
		double[] roots = new QuadraticParser(a, b, c).getRoots();
		
		//-b +- √b^2 - 4ac
		//----------------
		//      2a
		
		//(-b+-√((b^2) - (4)(a)(c)))/2(a)
		
		System.out.println("a: " + a + " b: " + b + " c: " + c);
		System.out.println("x1: " + roots[0]);
		System.out.println("x2: " + roots[1]);
		
	}
	
	
}
