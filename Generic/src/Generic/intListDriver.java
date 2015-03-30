package Generic;

public class intListDriver<T> implements intList<T>{
	private T[] datastore;
	private int size;
	private int pos;

	public intListDriver(int numElements){
		size = numElements;
		pos=0;
		datastore =(T[]) new Object[size];
	}

	public void add(T element){
		datastore[pos] = element;
		pos++;
	}

	public T get(int n){
		return datastore[n];
	}
}