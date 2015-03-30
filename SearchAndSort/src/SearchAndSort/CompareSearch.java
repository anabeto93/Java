package SearchAndSort;

public class CompareSearch{
	private static long startTime,endTime,duration;
	public CompareSearch(){

	}
	public static void timeUsed(int[] arr,int key, String search){
		if(search.equals("ls")){
			startTime = System.nanoTime();
			LinearSearch isO = new LinearSearch(arr,key);
			endTime = System.nanoTime();
			duration = endTime - startTime;
			System.out.println("Linear search took "+duration+" nano seconds");
		}else if(search.equals("bs")){
			startTime = System.nanoTime();
			BinarySearch isO = new BinarySearch(arr,key);
			endTime = System.nanoTime();
			duration = endTime - startTime;
			System.out.println("Binary search took "+duration+" nano seconds");
		}
	}
}