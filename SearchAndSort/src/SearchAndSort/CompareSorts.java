package SearchAndSort;

import java.util.Scanner;

public class CompareSorts{
	//private static Scanner capture = new Scanner(System.in);
	public CompareSorts(){
		
	}
	private static long startTime,endTime,duration;
	public static void timeCalc(String algo, int[] arr){
		if(algo.equals("is")){
			startTime = System.nanoTime();
			InsertionSort isO = new InsertionSort(arr);
			endTime = System.nanoTime();
			duration = endTime - startTime;
			System.out.println("Insertion Sort took "+duration+" nano seconds");
		}else if(algo.equals("bs")){
			startTime = System.nanoTime();
			BubbleSort bsO = new BubbleSort(arr);
			endTime = System.nanoTime();
			System.out.println("Bubble Sort took "+duration+" nano seconds");
		}else if(algo.equals("qs")){
			startTime = System.nanoTime();
			QuickSort qsO = new QuickSort(arr);
			endTime = System.nanoTime();
			duration = endTime - startTime;
			System.out.println("Quick Sort took "+duration+" nano seconds");
		}else if(algo.equals("ms")){
			startTime = System.nanoTime();
			MergeSort msO = new MergeSort(arr);
			endTime = System.nanoTime();
			duration = endTime - startTime;
			System.out.println("Merge Sort took "+duration+" nano seconds");
		}
	}
	public static void Menu(){
		int choice=0;
		System.out.println("----CHOOSE 2 SORT ALGORITHMS----"
			+"\n1.Insertion Sort vs Bubble Sort\n2.Insertion Sort vs Quick Sort"
			+"\n3.Insertion Sort vs Merge Sort\n4.Bubble Sort vs Quick Sort"
			+"\n5.Buble Sort vs Merge Sort\n6.Quick Sort vs Merge Sort\n7.Enter 0 to exit");
	}
}