package com.syngen.engine;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 
 * this class takes a mathmatical expression and then simplifies it
 * @author synte
 * @version 0.0.1
 * 
 *
 */
public class SimplifyExpression {
	
	private String expression;
	public List<String> workingOut = new ArrayList<>();
	
	private final boolean DEBUG = false;
	
	//initialize the expression to be parsed.
	public SimplifyExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * Simplifies a mathmatical expression.
	 * @return the simplified expression
	 */
	public String simplify() {

		//make the expression more machine readable such as adding operators between coofficients and simplifying plus and minus operators
		//for example ++ is + and -+ is -
		this.expression = this.makeExpressionReadable(this.expression);

		if(DEBUG) {
			System.out.println("solving -> " + expression);	
		}
		
		String simplfied = this.applyOperation(expression)+"";
		
		//add the answer to the final working out
		this.workingOut.add(simplfied);

		//print the working out
		this.workingOut.stream().distinct().forEach(i -> {
			if(DEBUG) {
				System.out.println("solving -> " + i);	
			}
		});
		
		return simplfied;
	}
	
	/**
	 * Simplifies a mathmatical expression.
	 * @return the simplified expression
	 */
	public double simplifyAsDouble() {
		return Double.parseDouble(simplify());
	}
	
	public <T> SimplifyExpression setVariable(String var, T value) {
		this.expression = this.expression.replace(var, value+"");
		return this;
	}
	
	
	/**
	 * 
	 * @param breaks down the parameters and provides the simplified expression
	 * @return
	 */
	private double applyOperation(String expression) {

		//if there is no open bracket, then we have simplified this expression and can pass it on the the arithmetic function which 
		//parses expressions without brackets.
		if(!expression.contains("(")){
			return this.arithmetic(expression);
		}
		
		//this will be used to store the expression within a bracket.
		StringBuilder builder = new StringBuilder();
		int brackets = 0; //amount of brackets found within the expression
		
		
		for(char i : expression.toCharArray()) {
			
			//it's important to count the brackets as there may be nested brackets in this expression
			//this way we can decrement the brackets value and therefore can tell when the entire expression has been found
			if(i == '(') {
				brackets++; 
			}
			
			//if we are currently inside of the expression, append it to the builder
			if(brackets > 0) {
				builder.append(i+"");	
			}
			
			
			//decrement the brackets var as we located a closing bracket, if there are no nested brackets, this can mark the end of the expression
			if(i == ')') {
				brackets--;
			}
			
			//end of an expression
			if(brackets == 0) {
				//avoid empty expressions as they serve no purpose.
				if(builder.toString().trim().length() == 0) continue;
				
				//return the expression without the initial and end bracket.
				String subExpressionWithoutBrackets = builder.toString().trim().substring(1).substring(0, builder.toString().length() - 2);
				
				//the simplified expression for this expression
				//the expression will be constantly broken down and replaced with it's numeric counter part processed in .arithmetic()
				//for example (4*3(4-3)+1)
				//            (4*3*(4-3)+1)
				//                 (4-3) 
				//                   1
				//            (4*3*1+1)
				//                13
				double value = this.applyOperation(subExpressionWithoutBrackets);
				
				//replace the value of this expression as the value that was calculated.
				expression = expression.replace(builder.toString(), value+"");
				

				//update the full expression with the simplified sub expression
				this.expression = this.expression.replace("(" + subExpressionWithoutBrackets + ")", value+"");

				//add the expression to the working out
				workingOut.add(this.expression);

				//reset the string builder
				builder.setLength(0);
				
			}
			
		}
		
		//subExpressions.forEach(this::applyOperation);
		
		return this.arithmetic(expression);
	}
	
	/**
	 * 
	 * @param expression without brackets
	 * @return the quotiant of an expression ( without brackets)
	 */
	public double arithmetic(String expression) {
		
		double result = 1;
		expression = this.makeExpressionReadable(expression);
		
		//copium 
		//this ugly chunk of code is for a special case where there is only subtraction in the expression
		//the trick is turning all - to +, then adding the first value back into the expression if needed ( incase the first value was none negative )
		
		if(Operators.SUBTRACTION.containsOnly(expression)) {
			
			double initial = 0;
			
			if(DEBUG) {
				//System.out.println("ONLY MINUS(1) => " + expression);	
			}
			
			
			expression = expression.replace("-", "+");
			
			if(DEBUG) {
				//System.out.println("ONLY MINUS(2) => " + expression);	
			}
			
			
			if(expression.startsWith("+")) {
				//STARTS WITH MINUS VALUE
				expression = expression.substring(1);
			}else {
				//STARTS WITH POSITIVE VALUE
				initial = Double.valueOf(expression.split("[+]")[0]);	
			}
			
			//System.out.println("INIT: " + initial);
			
			//System.out.println(expression);
			double sum = this.arithmetic(expression) - (initial*2);
			//System.out.println("sum: " + sum);
			
			return sum * -1;
		}
		
		
		Operators highestOperator = null;
		int indexOfTheHighestPriorityOperator = 0;
		char[] chars = expression.toCharArray();
		boolean minusValue = false;
		
		//look for the highest power operator
		for(int i = 0; i < chars.length; i++) {
			//System.out.println(chars[i] + " => " + Operators.getPriority(chars[i]) + " / " + Operators.getPriority(chars[indexOfTheHighestPriorityOperator]));
			if(Operators.getPriority(chars[i]) > Operators.getPriority(chars[indexOfTheHighestPriorityOperator])) {
				Operators current = Operators.getOperator(chars[i]);
				
				//IF there are multiple minus symbols, and we have not come across a minus symbol yet, therefore DONT subtract, but rather treet this value as negative by including the symbol
				if(current == Operators.SUBTRACTION && !minusValue && expression.split("-").length >= 2) {
					//check if this expression has only 1 - symbol.
					if((expression.split("-").length == 2) && Operators.SUBTRACTION.containsOnly(expression)) {
						//is this value a integer?
						if(expression.split("-")[0].isEmpty()) {
							return Double.valueOf(expression); //return this negative integer	
						}else {
							//this is an expression, a - b; so lets just evaluate this here and return it
							return Operators.SUBTRACTION.compute(expression.split("-")[0], expression.split("-")[1]);
						}
					}
					
					//we've found a minus value, now we will start to treat it like a normal operator
					minusValue = true;
					continue;
				}
				
				//System.out.println("HIGHEST OPERATOR: " + i);
				indexOfTheHighestPriorityOperator = i;
				highestOperator = Operators.getOperator(chars[i]);
			}
		}
		
		//Special case where the operator is in the start of the expression, in this case lets manually set the operator 
		if(highestOperator == null && chars.length > 0) {
			Operators symbol = Operators.getOperator(chars[indexOfTheHighestPriorityOperator]);
			if(symbol != null && symbol.getPriority() > 0 && symbol != Operators.SUBTRACTION) {
				//expression.split("-").length > 2
				highestOperator = symbol;
			}
		}
		
		if(highestOperator != null) {
			
			String leftExpression = "";
			String rightExpression = "";
			
			String ignoredRightExpression = "";
			String ignoredLeftExpression = "";
			
			//System.out.println("HIGHEST OPERATOR = " + highestOperator.getSymbol());
			
			//EVALUATE THE RIGHT HAND SIDE OF THE SYMBOL
			boolean isNegative = false;
			boolean firstSymbol = true;
			for(int i = 0; i < chars.length; i++) {
				if(i > indexOfTheHighestPriorityOperator) {
					//stop the operation as we encountered a symbol with less priority
					//for example 6 ^ 5+6 is (6^5) + 6 NOT 6 ^ (5+6)
					//anything with a priority of 1 or more is a symbol
					if(Operators.getPriority(chars[i]) > 0) {
						Operators op = Operators.getOperator(chars[i]);
						//if this is our first encounter with a minus symbol, and there are more minus symbols, don't stop but include it in the right expression
						//however, if the operation is multiply or stronger, then any encounter of minus should be considered, unless it's the first thing to appear, which marks the 
						//right expression to be negative
						if(op == Operators.SUBTRACTION && !isNegative && expression.split("-").length >= 2 && !((highestOperator.getPriority() >= Operators.MILTIPLICATION.getPriority() && !firstSymbol))) {
							isNegative = true;
						}else {
							//System.out.println("ignored (r) " + expression.substring(i, expression.length()));
							//the ignored right expression to be evaluated
							ignoredRightExpression = expression.substring(i, expression.length());	
							break;
						}
					}
					//keep going we havnt run into a symbol yet ( or more then 1 minus symbols )
					rightExpression += chars[i];
					firstSymbol = false;
				}
			}

			isNegative = false;
			//EVALUATE THE LEFT SIDE OF THE SYMBOL
			//from the left of the symbol to the begginging of the expression
			for(int i = indexOfTheHighestPriorityOperator-1; i >= 0; i--) {
					
					Operators op = Operators.getOperator(chars[i]);
					//System.out.println(chars[i]);
					
					//check if we encountered a sumbol
					if(op != null && op.getPriority() > 0) {
						//if this symbol is negative include it in the left expression
						if(!isNegative && op == Operators.SUBTRACTION) {
							leftExpression = chars[i] + leftExpression;
							
							//we found a symbol, so now lets exclude everything after this symbol (inclusively) and calculate it seperately
							ignoredLeftExpression = expression.substring(0, i);
						}else {
							//we found a symbol, so now lets exclude everything after this symbol (exclusively) and calculate it seperately
							ignoredLeftExpression = expression.substring(0, i+1);
						}
						
						//System.out.println("ignored (l) " + ignoredLeftExpression);
						break;
					}else {
						//keep going, we have not found a symbol yet
						leftExpression = chars[i] + leftExpression;
					}
			}
			
			
			//debug
			//System.out.println("sub expression -> " + expression + "  : " + expression.split("-").length);
			//System.out.println("(ev) -> " + leftExpression + highestOperator.getSymbol() + rightExpression);
			//System.out.println("(l) -> " + leftExpression + " (ig) -> " + ignoredLeftExpression);
			//System.out.println("(r) -> " + rightExpression + " (ig) -> " + ignoredRightExpression);
			
			//only minus? perhaps we should subtract instead of considering the minus to represent a negative value
			if(leftExpression.trim().equals("-")) {
				ignoredLeftExpression += "-";
				leftExpression = highestOperator.defaultExpressionLeft();
			}
			
			if(leftExpression.startsWith("-") && !ignoredLeftExpression.isEmpty()) {
				//since we've included the - symbol in our left expression lets append a + to the ignored left expression, otherwise the computed value of
				//the middle expression will be appended to the last number in the ignored left expression
				//example 1-3+5 -3+5 is 2, this 2 will be appended back so the result will be 12, if we add a + symbol the result will be 1+2
				ignoredLeftExpression += "+";
			}
			
			
			
			//in some instances the left expression can be empty, for example with square roots, in this case assume the opporators defualt value
			//in the case of square roots, the default value is 2 and square root raised to the second power does not change the value
			if(leftExpression.isEmpty()) {
				leftExpression = highestOperator.defaultExpressionLeft();
			}
			
			
			if(!highestOperator.effectsLeft()) {
				//as the right side should not be affected, lets pop it back into the expression
				ignoredRightExpression = rightExpression + ignoredRightExpression;
				result = highestOperator.compute(this.arithmetic(leftExpression), 0);	
			}else if(!highestOperator.effectsRight()) {
				//as the left side should not be affected, lets pop it back into the expression
				ignoredLeftExpression = leftExpression + ignoredLeftExpression;
				result = highestOperator.compute(0, this.arithmetic(rightExpression));	
			}else {
				//compute the left and right side of the expression
				result = highestOperator.compute(this.arithmetic(leftExpression), this.arithmetic(rightExpression));	
			}

			this.expression = this.expression.replace(leftExpression + highestOperator.getSymbol() + rightExpression, result+"");
			this.workingOut.add(this.expression);
			
			//now compute the whole expression placing the computed value back into the eqaution and repeate this process
			double rightExpressionComputation = this.arithmetic(ignoredLeftExpression + result + ignoredRightExpression);
			
			this.expression = this.expression.replace(ignoredLeftExpression + result + ignoredRightExpression, rightExpressionComputation+"");
			this.workingOut.add(this.expression);
			
			//we've retrieved the result!
			result = rightExpressionComputation;

		}else{
			//base case, simply return the value as there is no expression to be parsed.
			result = Double.parseDouble(expression);
		} 
		
		
		
		return result;
	}
	
	/**
	 * @param expression without brackets
	 * @return the quotiant of an expression ( without brackets)
	 */
	public double arithmetic1(String expression) {
		ScriptEngineManager mgr = new ScriptEngineManager();
	    ScriptEngine engine = mgr.getEngineByName("JavaScript");
	    try {
	    	//System.out.println(engine.eval(expression));
			Double d = Double.parseDouble(engine.eval(expression).toString());
			return d;
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	    return 0;
	}
	
	/**
	 * make the code easy to digest by the syntex engine
	 * @param expression to be made machine readable
	 * @return
	 */
	public String makeExpressionReadable(String expression) {
	
		expression = expression.replace(")(",")*(");	
		expression = expression.replace("+(","1*(");	
		expression = expression.replace("-(","-1*(");	

		for(int i = -9; i < 10; i++) {
			expression = expression.replace(i + "(", i + "*(");	
			//expression = expression.replace(")" + i, ")*" + i);	
		}
		
		expression = expression.replace("++", "+");
		expression = expression.replace("--", "+");
		expression = expression.replace("+-", "-");
		expression = expression.replace("-+", "-");
		
		for(Operators i : Operators.values()) {
			expression = expression.replace(i + "+", i + "");
		}
		
		//expression = expression.replace("-", "+-");
		
		expression = expression.replace(" ", "");
		
		expression = expression.replace("e", Math.E+"");
		expression = expression.replace("pi", Math.PI+"");
		
		return expression.trim();
	}
	
	public static SimplifyExpression instance(String expression) {
		return new SimplifyExpression(expression);
	}
	
}
