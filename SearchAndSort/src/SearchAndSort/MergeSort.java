package SearchAndSort;

public class MergeSort{
	public MergeSort(int[] arr){
		int low =0, high = arr.length; high-=1;

		MSort(arr,low,high);
	}
	public static void MSort(int [] A, int x, int y){
		int low =x; int high = y; int middle=0;

		if(low<high){
			middle = (high+low)/ 2;
			MSort(A, low, middle);
			MSort(A, middle+1, high);
			Merger(A, low, middle, high);
		}
	}

	public static void Merger(int[] A, int a, int b , int c){
		int size = A.length;
		int [] temp = new int[size];

		for(int i=a; i<= c; i++){
			temp[i] = A[i];
		}
		int i=a , j=b+1 , k= a;

		while(i <= b  && j <= c){
			if(temp[i] <= temp[j]){
				A[k] = temp[i];
				++i;
			}else{
				A[k] = temp[j];
				++j;
			}
			k++;
		}

		while(i <= b){
			A[k] = temp[i];
			++k; ++i;
		}

	}
}