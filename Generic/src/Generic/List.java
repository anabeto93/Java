package Generic;
public class List<T>{
	private T[] datastore;
	private int size,pos;//pos stands for position

	public List(int numElements){
		size = numElements;
		pos=0;
		datastore = (T[]) new Object[size];
	}

	public void Add(T element){ //T means it can be a string, int, float or any datatype
		datastore[pos] = element;
		++pos;
	}

	public String toString(){
		String elements = "";
		for(int i=0; i<pos; i++){
			elements += datastore[i] + " ";
		}

		return elements;
	}
}