package store;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

//Work Cited: https://stackoverflow.com/questions/4194310/can-java-string-indexof-handle-a-regular-expression-as-a-parameter#:~:text=No.&text=WorkAround%20%3A%20Its%20not%20standard%20practice,can%20get%20result%20using%20this.

public class Calculator {

	public static void main(String[] args) {
		
		List<Double> numberList = new ArrayList<Double>(); //arraylist to store all the numbers
		List<String> operationList = new ArrayList<String>(); //arrayList to store all the operators
		List<String> BEDMAS = new ArrayList<String>();
		BEDMAS.add("^");
		BEDMAS.add("*");
		BEDMAS.add("/");
		BEDMAS.add("-");
		BEDMAS.add("+");
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Please input a math operation...");
		String input = scan.nextLine();
		
		input = input.replaceAll("\\s+", ""); // remove spaces
		
		if(input.startsWith("-")) //if operator starts with - add a 0 before it to make it a negative number
		{
			input = 0 + input;
//			int operatorIndex = operationList.indexOf("-");
//			Double secondNumber = numberList.get(operatorIndex);
//			Double result = 0 - secondNumber;
//			operationList.remove(operationList.indexOf("-"));
//			numberList.remove(operatorIndex);
//			numberList.add(operatorIndex, result);
//			
		}
		String patternStr = "[-+*/^s]";
		Pattern pattern = Pattern.compile(patternStr);
	    Matcher matcher = pattern.matcher(input);
	    
		while(matcher.find())
		{		
			if(matcher.start() !=0) //if operator is not the first index, there is a number first
			{
				String number = input.substring(0, matcher.start()); // number is the input from index 0 to the first operator
			    numberList.add(Double.parseDouble(number)); // add the number to the arraylist
			}
			String operator = input.substring(matcher.start(), matcher.start()+1); // finds the operator
			
			if(input.contains(operator + "sqrt") && input.indexOf(operator) == 0)
			{
				operationList.add("s");
				input = input.substring(matcher.start()+5); //cuts off everything before the operator including the operator
			    matcher = pattern.matcher(input);
			}
			else if(input.contains("sqrt"))
			{
			    input = input.substring(matcher.start()+4); //cuts off everything before the operator including the operator
			    matcher = pattern.matcher(input);
			}
			else if(input.contains("s"))
			{
				break;
			}
			else {
				System.out.println(input);
			    input = input.substring(matcher.start()+1); //cuts off everything before the operator including the operator
			    matcher = pattern.matcher(input);
			}
		}
		numberList.add(Double.parseDouble(input)); //adds last number to the array list
		Double result = Double.parseDouble(input);//result is the input entered in the case of entering a number without an operation
		System.out.println(operationList);
		System.out.println(numberList);
		
		while(operationList.contains("s"))
		{
			int operatorIndex = operationList.indexOf("s");
			Double firstNumber = numberList.get(operatorIndex);
			result = Math.sqrt(firstNumber);
			operationList.remove(operationList.indexOf("s"));
			numberList.remove(operatorIndex);
			numberList.add(operatorIndex, result);
		}
		for(String operatorSign: BEDMAS)
		{
			while(!operationList.isEmpty() && operationList.contains(operatorSign))
			{
				int operatorIndex = operationList.indexOf(operatorSign);
				Double firstNumber = numberList.get(operatorIndex);
				Double secondNumber = numberList.get(operatorIndex+1);
				if(operatorSign.equals("^"))
				{
					result = Math.pow(firstNumber, secondNumber);
				}
				else if(operatorSign.equals("*"))
				{
					result = firstNumber * secondNumber;
				}
				else if(operatorSign.equals("/"))
				{
					result = firstNumber / secondNumber;
				}
				else if(operatorSign.equals("-"))
				{
					result = firstNumber - secondNumber;
				}
				else if(operatorSign.equals("+"))
				{
					result = firstNumber + secondNumber;
				}
				operationList.remove(operationList.indexOf(operatorSign));
				numberList.remove(operatorIndex+1);
				numberList.remove(operatorIndex);
				numberList.add(operatorIndex, result);
			}
		}
		System.out.println("Your math operation is equal to: " + result);
	}
}
