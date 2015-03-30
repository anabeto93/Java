package DataStructures;

public class BTree{
	public static void main(String args[]){
		binaryTree bst = new binaryTree();
		bst.insert(10); bst.insert(2); bst.insert(11); bst.insert(5); bst.insert(13); bst.insert(12);
		bst.insert(20); bst.insert(1); bst.insert(6);

		System.out.println("Displaying tree in order traversal: ");
		bst.inOrder(); System.out.println();
		
		System.out.println("Displaying in pre order traversal: ");
		bst.preOrder(); System.out.println();
		
		System.out.println("Displaying in post order traversal: ");
		bst.postOrder(); System.out.println();

	}
}