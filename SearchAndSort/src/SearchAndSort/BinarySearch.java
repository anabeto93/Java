package SearchAndSort;

public class BinarySearch{
	public BinarySearch(int[] arr, int key){
		InsertionSort now = new InsertionSort(arr);
		
		int low=0,high=arr.length-1,mid=0;
		for(int i=0; i<(arr.length)/2; i++){
			mid=(low+high)/2;
			if(key==arr[mid]){
				System.out.println("Key found at "+(mid+1));
				return;
			}else if(key > arr[mid]){
				low = mid;
			}else if(key < arr[mid]){
				high=mid;
			}
		}
	}
}