import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class RedBlackTree <K extends Comparable<K>, V> {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		RedBlackTree tree = new RedBlackTree();
		
		tree.put(1, "a");
		tree.put(2, "b");
		tree.put(3, "c");
		tree.put(4, "d");
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
			//newNode = root;
		}
		else {
			//add new node
			newNode = findAndAdd(root, key, value);
		}
		
		//Go back up and fix the tree
		balance(newNode);
		
		System.out.print(newNode.value);
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
		//replace value if keys are equal
		currentNode.value = value;
		return currentNode;
	}
	
	private void balance(Node node) {
		//check if root (make sure node has parent)
		if(node.parent != null) {
			//is node red, and right child of black parent? if so rotate left
			if(node.isRed && node.parent.RChild == node) && !node.parent.isRed) {
				rotateLeft(node, node.parent);
			}
			
			//is node red and child of red node?
			
		}
	}
	
	private void rotateLeft(Node Rchild, Node parent) {
		//MIGHT BE ON WRONG TRACK
		swapData(Rchild, parent);
		swapPointers(parent);
	}
	
	private void rotateRight(Node Lchild, Node parent) {
		swapData(Lchild, parent);
		swapPointersRight(parent);
	}
	
	@SuppressWarnings("unchecked")
	private void swapPointersRight(Node parent) {
		
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
