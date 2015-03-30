package AssociativeCollection;

import java.util.HashMap;

public class hashMap{
	private static HashMap<String,Integer> grades = new HashMap<String,Integer>();
	public static void main(String args[]){

		grades.put("Math",80); grades.put("Science",75); grades.put("Physics",85);
		System.out.println(Display());
		
	}
	public static String Display(){
		return String.format("Grades: \nMaths: %s\nScience: %s\nPhysics: %s",
				grades.get("Math"),grades.get("Science"),grades.get("Physics"));
	}
}