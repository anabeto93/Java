package SearchAndSort;

public class QuickSort{
	public QuickSort(int[] arr){
		int left=0, right = arr.length - 1;
		QSort(arr,left,right);
	}
	
	public static void QSort(int [] arr, int left, int right){
		int i=left, j= right;
		
		int pivot = arr[(left+right)/2];
		while(i <= j){
			while(arr[i]<pivot){
				i++;
			}
			while(arr[j]>pivot){
				j--;
			}
			if(i <= j){
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}
		if(left < j){
			QSort(arr, left, j);
		}
		if(i < right){
			QSort(arr,i,right);
		}
	}
}