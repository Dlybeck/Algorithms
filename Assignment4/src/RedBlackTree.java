import java.util.*;

@SuppressWarnings("rawtypes")
public class RedBlackTree <K extends Comparable<K>, V> {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		RedBlackTree tree = new RedBlackTree();
		
			
		
		tree.put(6, "f");
		System.out.println("-------------------------");
		
		tree.put(2, "b");
		System.out.println("-------------------------");

		tree.put(1, "a");
		System.out.println("-------------------------");
		
		/*tree.put(7, "g");
		System.out.println("-------------------------");

		tree.put(4, "d");
		System.out.println("-------------------------");
		
		tree.put(9, "i");
		System.out.println("-------------------------");

		tree.put(5, "e");
		System.out.println("-------------------------");

		tree.put(3, "c");
		System.out.println("-------------------------");

		tree.put(8, "h");
		System.out.println("-------------------------");*/

		
	}
	
	
	private Node root;
	
	public RedBlackTree() {
		this.root = null;
	}
	
	@SuppressWarnings("unchecked")
	public void put(K key, V value) {
		Node newNode = null;
		//if empty 
		if(root == null) {
			root = new Node(key, value);
			root.isRed = false;
			newNode = root;
		}
		else {
			//add new node
			newNode = findAndAdd(root, key, value);
		}
		
		this.printRedBlackTree(this.root, "", true);
		
		//Go back up and fix the tree
		balance(newNode);
	}
	
	@SuppressWarnings("unchecked")
	private Node findAndAdd(Node currentNode, K key, V value) {
		//go left?
		if(key.compareTo((K) currentNode.key) < 0) {
			//recurse if there is a child
			if(currentNode.LChild != null) {
				findAndAdd(currentNode.LChild, key, value);
			}
			//otherwise add new node
			else {
				Node newNode = new Node(key,value);
				//set up new node
				currentNode.LChild = newNode;
				newNode.parent = currentNode;
				return newNode;
			}
		}
		//go right?
		else if(key.compareTo((K) currentNode.key) > 0) {
			//recurse if there is a child
			if(currentNode.RChild != null) {
				findAndAdd(currentNode.RChild, key, value);
			}
			//otherwise add new node
			else {
				Node newNode = new Node(key,value);
				//set up new node
				currentNode.RChild = newNode;
				newNode.parent = currentNode;
				return newNode;
			}
		}
		else {
			currentNode.value = value;
		}
		//replace value if keys are equal
		return currentNode;
	}
	
	private void balance(Node node) {
		//check if root (make sure node has parent)
		if(node.RChild != null) {
			//is node red, and right child of black parent? if so rotate left
			if(node.RChild.isRed && !node.isRed) {
				rotateLeft(node.RChild, node);
				//System.out.println("Black Parent with red right child fixed:");
				//this.printRedBlackTree(this.root, "", true);
			}
		}
			
		//two red nodes in a row?
		if(node.LChild != null && node.LChild.LChild != null) {
			if(node.LChild.isRed && node.LChild.LChild.isRed) {
				rotateRight(node.LChild, node);
				//System.out.println("Two Red Children in a row fixed:");
				//this.printRedBlackTree(this.root, "", true);
			}
		}
		//Two Red Children of one Black node?
		if(node.LChild != null && node.RChild != null) {
			if((node.RChild.isRed && node.LChild.isRed) && !node.isRed) {
				changeColor(node);
				changeColor(node.LChild);
				changeColor(node.RChild);
				//System.out.println("Two red children of Black parent Fixed:");
				//this.printRedBlackTree(this.root, "", true);
			}
		}
		
		//Red Root?
		if(this.root.isRed) {
			changeColor(this.root);
			//System.out.println("Red Root Fixed:");
			//this.printRedBlackTree(this.root, "", true);
		}
		
		if(node.parent != null) {
			balance(node.parent);
		}
		

	}
	
	@SuppressWarnings("unchecked")
	private void rotateLeft(Node kid, Node parent) {
		parent.RChild = kid.LChild;
		if(kid.LChild != null) {
			kid.LChild.parent = parent;
		}
		kid.parent = parent.parent;
		//parent is root
		if(parent.parent == null) {
			this.root = kid;
		}
		//dont continue this part if kid is root
		else if(parent == parent.parent.LChild) {
			parent.parent.LChild = kid;
		}
		else {
			parent.parent.RChild = kid;
		}
		kid.LChild = parent;
		parent.parent = kid;
	}
	
	@SuppressWarnings("unchecked")
	private void rotateRight(Node kid, Node parent) {
		parent.RChild = kid.LChild;
		if(kid.LChild != null) {
			kid.LChild.parent = parent;
		}
		kid.parent = parent.parent;
		//parent is root
		if(parent.parent == null) {
			this.root = kid;
		}
		//dont continue this part if kid is root
		else if(parent == parent.parent.LChild) {
			parent.parent.LChild = kid;
		}
		else {
			parent.parent.RChild = kid;
		}
		kid.LChild = parent;
		parent.parent = kid;
	}
	
	//Swaps data for rotate methods
	@SuppressWarnings("unchecked")
	private void swapData(Node node1, Node node2){
		K tempk = (K) node1.key;
		V tempv = (V) node1.value;
		node1.key = node2.key;
		node1.value = node2.value;
		node2.key = tempk;
		node2.value = tempv;
	}
	
	//balance a tree starting at a given node and going up
	private void changeColor(Node node) {
		node.isRed = !node.isRed;
	}
	
	private void printRedBlackTree(Node node, String prefix, boolean isTail) {
	    if (node != null) {
	    	String childLabel = "";
	        String childPrefix = isTail ? "    " : "│   ";
	        String nodeType = isTail ? "└── " : "├── ";
	        if(node.parent != null) childLabel = (node == node.parent.LChild) ? "L" : "R";  // Add "L" for left child, "R" for right child

	        System.out.println(prefix + nodeType + node.key + childLabel + " (" + (node.isRed ? "Red" : "Black") + ")");
	        printRedBlackTree(node.LChild, prefix + childPrefix, false);
	        printRedBlackTree(node.RChild, prefix + childPrefix, true);
	    }
	}

	
	
	private static class Node <K extends Comparable<K>, V>{
		private K key;
		private V value;
		private boolean isRed;
		private int size;
		
		private Node RChild;
		private Node LChild;
		private Node parent;
		
		public Node(K key, V value){
			this.key = key;
			this.value = value;
			this.isRed = true;
			this.size = 1;
			this.RChild = null;
			this.LChild = null;
			this.parent = null;		
		}
		
		public Boolean isLeftChild() {
			return (this == this.parent.LChild);
		}
		
		@SuppressWarnings("unchecked")
		public void flipColor() {
			this.isRed = !this.isRed;
		}
	}
}
