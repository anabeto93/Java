package Collection;

import java.util.ArrayList;

public class AList{
	public static void main(String []args){
		ArrayList<Integer> grades= new ArrayList<Integer>();//empty
		grades.add(100); grades.add(90); grades.add(80); grades.add(70); grades.add(60);

		int total=0;
		/*for(int i=0; i<grades.size(); i++){
			total+= grades.get(i);
		}*/

		for(Integer grade: grades){//just like grade in grades of Python
			total+=grade;
		}

		double average = total/grades.size();

		System.out.println("Average is : "+average);
		System.out.println("Actual size of array is "+grades.size());
		grades.remove(4);
		System.out.println("New size is : "+grades.size());
	}
}