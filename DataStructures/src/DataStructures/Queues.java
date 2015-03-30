package DataStructures;

import java.util.LinkedList;

public class Queues{
	public static void main(String args[]){
		LinkedList<String> movies = new LinkedList<String>();
		movies.addLast("The Hobbit"); movies.addLast("The Marine");
		movies.addLast("The Matrix"); movies.addLast("Lord of the Rings");
		
		for(String name: movies){
			System.out.println(name);
		}
	}
}