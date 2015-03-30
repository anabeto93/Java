package DataStructures;

import java.util.*;

public class baseConvertor{
	public static void main(String args[]){
		Scanner listener = new Scanner(System.in);

		int number, base;
		System.out.println("Enter number: ");
		number = listener.nextInt();
		System.out.println("Enter base: ");
		base = listener.nextInt();
		System.out.println(number+" base "+base+" is "+convertor(number,base));
		
		listener.close();
	}

	private static String convertor(int number, int base){
		Stack<Integer> digits = new Stack<Integer>();
		do{
			digits.push(number % base);//remainder or number/base
			number /= base;

		}while(number!=0);

		String result = "";
		while(!digits.empty()){
			result += digits.peek();
			digits.pop();
		}

		return result;
	}
}