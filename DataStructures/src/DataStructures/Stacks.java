package DataStructures;

import java.util.*; //not really sure for now

public class Stacks{
	public static void main(String []args){
		Stack<String> names= new Stack<String>();

		names.push("Yaw"); names.push("Cecilia"); names.push("Richard"); names.push("Opoku");
		names.push("Anabeto");

		System.out.println("Top of the stack: "+names.peek()+"\n After removing that, next on top is ");// just to see what is on top LIFO
		names.pop(); System.out.println(names.peek());

	}
}