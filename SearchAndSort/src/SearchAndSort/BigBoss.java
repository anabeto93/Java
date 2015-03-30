package SearchAndSort;

import java.util.Scanner;

public class BigBoss{
	private static int size;
	private static int[] arr;
	static Scanner listener = new Scanner(System.in);
	public static void main(String args[]){
		
		System.out.println("THIS IS THE SEARCHING AND SORTING ALGORITHMS PROGRAM\n");
		int choice1,choice3; String choice2;
		System.out.println("Do you want to use random numbers(r) or enter numbers manually(m)?");
		choice2 = listener.next();
		
		//POPULATE THE ARRAY WITH NUMBERS
		if(choice2.equals("r")){
			Populate();
		}else if(choice2.equals("m")){
			System.out.print("How many numbers do you want to enter: ");
			size = listener.nextInt();
			Populate(size);
		}else{
			System.out.println("Choice not understood");
		}
		//SORT OR SEARCH OR COMPARE
		System.out.print("Do you want to sort(1) or search(2) or compare sorts(3) or compare searches(4) : ");
		choice3 = listener.nextInt();
		if(choice3 == 1){
			//SORT
			DisplayMenu(1); choice1 = listener.nextInt();
			sortSwitch(1);
		}else if(choice3 ==2){
			//SEARCH
			ViewNums();
			System.out.print("Enter the number to search for: ");
			int key = listener.nextInt();
			DisplayMenu(2); choice1 = listener.nextInt();
			searchSwitch(choice1,key);
		}else if(choice3 ==3){
			choice3 = 0;
			do{
				DisplayMenu(3);
				choice3 = listener.nextInt();
				compareSwitch(choice3);
			}while(choice3!=0);
		}else if(choice3==4){
			choice3=0;
			CompareSearch csO = new CompareSearch();
			Populate(); ViewNums(); System.out.print("Enter a number to search for: ");
			choice3 = listener.nextInt();
			System.out.println("Searching using Linear Search...");
			csO.timeUsed(arr,choice3,"ls");
			System.out.println("Searching using Binary Search...");
			csO.timeUsed(arr, choice3, "bs");
			
		}
		
		listener.close();
	}
	public static void DisplayMenu(int x){
		if(x==1){
		System.out.println("----------CHOOSE AND OPTION----------"
				+ "\n1.Insertion Sort\n2.Bubble Sort\n3.Quick Sort\n4. Merge Sort");
		}else if(x==2){
			System.out.println("-----------CHOOSE AN OPTION BELOW--------------"
					+ "\n1.Linear Search\n2.Binary Search");
		}else if(x==3){
			CompareSorts csO = new CompareSorts();
			csO.Menu();
		}
	}
	public static void Populate(int x){
		//VALUES TO BE ENTERED MANUALLY
		arr = new int[x]; //System.out.print("X is "+x+" and array length is "+arr.length);
		int num;
		for(int i=0; i<x; i++){
			System.out.print("Enter number "+(i+1)+": ");
			num=listener.nextInt(); System.out.println("Num is "+num);
			arr[i] = num;
		}
	}
	public static void Populate(){
		//RANDOM NUMBERS GENERATED HERE 
		arr = new int[100000]; int num;
		for(int i=0; i<100000; i++){
			num =(int) (Math.random()*10000); //System.out.print(num);
			arr[i] = num;
		}
	}
	public static void ViewNums(){
		int i=0;
		while(i<arr.length){
			for(int j=0; j<10; j++){
				if(j == arr.length){
					break;
				}
				System.out.print(arr[i]+", "); i++;
			}System.out.println();
		}System.out.println();
	}
	public static void sortSwitch(int x){
		System.out.println("Before sorting, these are the numbers: ");
		ViewNums();
		switch(x){
			case 1:
				InsertionSort algo = new InsertionSort(arr);
			break;
			case 2:
				BubbleSort algo1 = new BubbleSort(arr);
			break;
			case 3:
				QuickSort algo2 = new QuickSort(arr);
			break;
			case 4:
				MergeSort algo3 = new MergeSort(arr);
			break;
		}
		System.out.println("After sorting, these are the numbers: ");
		ViewNums();
	}
	public static void searchSwitch(int x, int y){
		switch(x){
		case 1:
			LinearSearch find = new LinearSearch(arr,y);
		break;
		case 2:
			BinarySearch find1 = new BinarySearch(arr,y);
		break;
		}
	}
	public static void compareSwitch(int x){
		//create or populate a new array everytime
		CompareSorts csO = new CompareSorts();
		switch(x){
		case 1:
			Populate(); csO.timeCalc("is", arr);
			Populate(); csO.timeCalc("bs", arr);
			break;
		case 2:
			Populate(); csO.timeCalc("is", arr);
			Populate(); csO.timeCalc("qs", arr);
			break;
		case 3:
			Populate(); csO.timeCalc("is", arr);
			Populate(); csO.timeCalc("ms", arr);
			break;
		case 4:
			Populate(); csO.timeCalc("bs", arr);
			Populate(); csO.timeCalc("qs", arr);
			break;
		case 5:
			Populate(); csO.timeCalc("bs", arr);
			Populate(); csO.timeCalc("ms", arr);
			break;
		case 6:
			Populate(); csO.timeCalc("qs", arr);
			Populate(); csO.timeCalc("ms", arr);
			break;
		}
	}
}