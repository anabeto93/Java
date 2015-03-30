package Collection;

import java.util.HashSet;
import java.util.Scanner;

public class Names{
	public static void main(String[] args){
		HashSet<String> names = new HashSet<String>();

		names.add("Richard"); names.add("Anabeto"); names.add("Cecilia");
		Scanner listener = new Scanner(System.in);

		System.out.println("The number of names is "+names.size());

		System.out.println("These are the names: ");
		for(String name: names){//name in names
			System.out.println(name);
		}

		System.out.print("Enter a name to search for: ");
		String name = listener.next();

		if(names.contains(name)){
			System.out.println("It is in the collection.");
		}else{
			System.out.println("Could not be found");
		}

		System.out.println("These are the current names in the collection");
		for(String nam: names){
			System.out.print(" "+nam+", ");
		}

		names.clear();//deletes all elements in the collection
		listener.close();
		System.out.println("Now the collection is empty and the proof is, the size is "+names.size());
	}
}