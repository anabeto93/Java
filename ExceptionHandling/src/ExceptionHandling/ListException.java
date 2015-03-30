package ExceptionHandling;

public class ListException<T>{
	private T[] datastore;
	private int size;
	private int pos;//position

	public ListException(int numElements){
		size = numElements;
		pos = 0;
		datastore = (T[]) new Object[size];
	}

	public void Add(T element){
		datastore[pos] = element;
		++pos;
	}

	public String toString() throws EmptyListException{
		if(datastore[0]==null){
			throw new EmptyListException();
		}

		String members="";
		for(int i=0; i<pos; i++){
			members+= datastore[i] + " ";

		}
		return members;
	}
}