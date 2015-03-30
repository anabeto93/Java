package SearchAndSort;

public class LinearSearch{
	int key;
	public LinearSearch(int [] arr,int key){
		for(int i=0; i<arr.length; i++){
			if(key == arr[i]){
				System.out.println("Key found at "+(i+1));
				return;
			}
		}System.out.println("Key not found");
	}
}