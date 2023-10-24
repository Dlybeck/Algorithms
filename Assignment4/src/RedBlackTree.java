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
		//if empty 
		if(root.key == null) {
			root.key = key;
			root.value = value;
		}
		else {
			//add new node
			Node newNode = findAndAdd(root, key, value);
			//go back up and fix tree
			
			System.out.print(newNode.value);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private Node findAndAdd(Node currentNode, K key, V value) {
		//go left if possible
		if(key.compareTo((K) currentNode.key) < 0) {
			if(currentNode.LChild != null) {
				findAndAdd(currentNode.LChild, key, value);
			}
			else {
				Node newNode = new Node(key,value);
				//set up new node
				currentNode.LChild = newNode;
				newNode.parent = currentNode;
				return newNode;
			}
		}
		//go right if possible
		else if(key.compareTo((K) currentNode.key) > 0) {
			if(currentNode.RChild != null) {
				findAndAdd(currentNode.RChild, key, value);
			}
			else {
				Node newNode = new Node(key,value);
				//set up new node
				currentNode.RChild = newNode;
				newNode.parent = currentNode;
				return newNode;
			}
		}
		//replace value
		else {
			currentNode.value = value;
			return currentNode;
		}
		
		//backup
		return null;
		
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
