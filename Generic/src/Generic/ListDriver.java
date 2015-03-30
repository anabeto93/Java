package Generic;

public class ListDriver{
	public static void main(String[] args){
		List<String> glist = new List<String> (10); //contains 10 elements
		glist.Add("Bread");
		glist.Add("Eggs");

		System.out.println("Breakfast: "+glist.toString());

		List<Double> marks = new List<Double> (2);
		marks.Add(10.0); marks.Add(20.0);

		System.out.println("Marks are : "+marks.toString());
	}
}