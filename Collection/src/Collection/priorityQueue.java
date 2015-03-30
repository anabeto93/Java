package Collection;

import java.util.PriorityQueue;

public class priorityQueue{
	public static void main(String args[]){
		PriorityQueue<String> names = new PriorityQueue<String>();

		names.add("Comfort"); names.add("Alhassan"); names.add("Tani");
		names.add("Nantogmah");

		for(String name: names){
			System.out.println(name);
		}

		names.remove("Nantogmah");
		System.out.println("Removed Nantogmah so now the names are: ");
		for(String name: names){
			System.out.println(name);
		}
	}
}