package DataStructures;

import java.util.LinkedList;

public class radixSort{
	public static void main(String args[]){
		final int size =50;
		final int numInQueue=10;
		//Create an array of linked lists
		@SuppressWarnings("unchecked")
		LinkedList<Integer>[] numbers = new LinkedList[numInQueue];//the size of each list is 10
		//Initialize each element of numbers
		for(int i=0; i<numInQueue; i++){
			numbers[i] = new LinkedList<Integer>();//empty
		}

		int [] values = new int[size];
		//generate random numbers to fill up the values
		for(int i=0; i<size; i++){
			values[i] = (int)(Math.random()*100);
		}
		//view all the numbers that have been generated
		display(values);
		Distribute(values,numbers,"ones");
		Collect(numbers,values);
		display(values);
		Distribute(values,numbers,"tens");
		Collect(numbers,values);
		display(values);
	}

	private static void Distribute(int[] arr,LinkedList<Integer>[] most,String type){
		for(int i=0; i<arr.length; i++){
			if(type.equals("ones")){
				most[arr[i] % 10].addLast(arr[i]);
			}else{
				most[arr[i]/10].addLast(arr[i]);
			}
		}
	}

	private static void Collect(LinkedList<Integer>[] most, int [] arr){
		int count=0;
		for(int num=0; num<10; num++){
			while(!most[num].isEmpty()){
				arr[count++]=(Integer)most[num].removeFirst();
			}
		}
	}

	private static void display(int[] arr){
		int i=0;
		while(i < arr.length){
			System.out.print(arr[i]+", ");
			if(++i % 10 ==0){
				System.out.println();
			}
		}
		System.out.println();
	}


}