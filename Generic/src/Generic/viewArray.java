package Generic;

public class viewArray{
	static <T> void View(T[] arr){
		for(int i=0; i<arr.length; i++){
			if(arr[i] !=null)
			System.out.println(arr[i]+" ");
		}
	}

	public static void main(String []args){
		String names[]=new String[3];
		names[0]="Richard"; names[1]="Anabeto"; names[2]="Opoku";
		View(names);

		Double marks[]=new Double[]{20.0,15.5,70.0};
		View(marks);
	}
}