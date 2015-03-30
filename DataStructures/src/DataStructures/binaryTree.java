package DataStructures;

public class binaryTree{
	public Node root;

	public binaryTree(){
		root = null;
	}

	public void insert(int data){
		Node newNode = new Node();
		newNode.data = data;
		if(root == null){
			root = newNode;
		}else{
			Node current = root;
			Node parent;
			while(true){
				parent = current;
				if( data < current.data){
					current = current.left;
					if(current == null){
						parent.left = newNode;
						break;
					}
				}else{
					current = current.right;
					if(current == null){
						parent.right = newNode;
						break;
					}
				}
			}
		}
	}

	public void inOrder(){
		inOrder(root);
	}
	private void inOrder(Node n){
		if(n!=null){
			inOrder(n.left);
			System.out.print(n.getData()+" ");
			inOrder(n.right);
		}
	}
	public void preOrder(){
		preOrder(root);
	}
	private void preOrder(Node n){
		if(n!=null){
			System.out.print(n.getData()+" ");
			preOrder(n.left);
			preOrder(n.right);
		}
	}
	public void postOrder(){
		postOrder(root);
	}
	private void postOrder(Node n){
		if(n!=null){
			postOrder(n.left);
			postOrder(n.right);
			System.out.print(n.getData()+" ");
		}
	}
}