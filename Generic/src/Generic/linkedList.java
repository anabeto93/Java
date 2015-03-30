package Generic;

public class linkedList{
	public static void main(String []args){
		Node<String> node1 = new Node("Richard");
		Node<String> node2 = new Node("Anabeto");
		Node<String> node3 = new Node("Opoku");

		node1.next = node2;
		node2.next = node3;
		Node<String> iter;
		iter = node1;

		//View the Nodes or LinkedList
		while(iter!=null){
			System.out.println("Name: "+iter.getData());
			iter = iter.next; //iter now points to the next node in the link
		}
	}
}