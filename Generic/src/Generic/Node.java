package Generic;

public class Node<T>{
	private T data;
	public Node next;//pointer of type Node

	public Node(T input){
		this.data = input;
	}

	public T getData(){
		return this.data;
	}
}