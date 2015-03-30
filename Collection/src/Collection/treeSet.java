package Collection;

import java.util.TreeSet;

public class treeSet{
	public static void main(String[] args){
		TreeSet<String> names = new TreeSet<String>();//currently empty;
		names.add("Richard"); names.add("Yaw"); names.add("Cecilia"); names.add("Anabeto");

		System.out.println("The size of the set is "+names.size());
		System.out.println("The tree set is known to arrange data in alphabetical order or in a clean hierarchy");
		System.out.println("The names are: ");

		for(String name: names){
			System.out.println(name);
		}

		System.out.println("The name that comes before Cecilia is "+names.lower("Cecilia"));//the new below Cecilia in the tree
		System.out.println("The name after Cecilia is "+names.higher("Cecilia"));

		System.out.println("First name: "+names.first());
		System.out.println("Last name: "+names.last());
	}
}