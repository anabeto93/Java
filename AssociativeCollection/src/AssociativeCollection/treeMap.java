package AssociativeCollection;

import java.util.TreeMap;

public class treeMap{
	public static void main(String []args){
		TreeMap<String,String> students = new TreeMap<String,String>();
		students.put("Richard","001"); students.put("Anabeto","002");
		students.put("Opoku","003"); students.put("Cecilia","004");

		System.out.println("Cecilia: "+students.get("Cecilia"));
		
		students.put("Yaw", "005");
		
		System.out.println("The number of students is: "+students.size());
	}
}