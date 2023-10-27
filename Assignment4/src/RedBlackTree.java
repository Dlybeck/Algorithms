import java.util.*;

@SuppressWarnings("rawtypes")
public class RedBlackTree <K extends Comparable<K>, V> {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		RedBlackTree tree = new RedBlackTree();
		
		
		System.out.println("Adding: 7");
		tree.put(7, "g");
		System.out.println("-------------------------");
		
		System.out.println("Adding: 4");
		tree.put(4, "d");
		System.out.println("-------------------------");
		
		System.out.println("Adding: 9");
		tree.put(9, "i");
		System.out.println("-------------------------");
		
		System.out.println("Adding: 2");
		tree.put(2, "b");
		System.out.println("-------------------------");
		
		System.out.println("Adding: 6");
		tree.put(6, "f");
		System.out.println("-------------------------");
		
		System.out.println("Adding: 8");
		tree.put(8, "h");
		System.out.println("-------------------------");
		
		System.out.println("Adding: 5");
		tree.put(5, "e");
		System.out.println("-------------------------");

		System.out.println("Adding: 1");
		tree.put(1, "a");
		System.out.println("-------------------------");

		System.out.println("Adding: 3");
		tree.put(3, "c");
		System.out.println("-------------------------");
		
		//tree.rotateRight(tree.root, tree.root.LChild);
		//tree.printRedBlackTree(tree.root, "", true);
		//tree.rotateLeft(tree.root, tree.root.RChild);
		//tree.printRedBlackTree(tree.root, "", true);

		
	}
	
	
	private Node root;
	
	public RedBlackTree() {
		this.root = null;
	}
	
	@SuppressWarnings("unchecked")
	public void put(K key, V value) {
		//if empty 
		if(root == null) {
			Node newNode = new Node(key, value);
			root = newNode;
			root.flipColor();
			this.printRedBlackTree(this.root, "", true);
		}
		else {
			//add new node
			Node newNode = findAndAdd(root, key, value, null);
			if(newNode.LChild == null  && newNode.RChild == null) {
				this.printRedBlackTree(this.root, "", true);
				
				//Go back up and fix the tree
				//System.out.println("Recursing and looking at node " + newNode.key);
				fix(newNode);
			}
			
		}
	
	}
	
	private Node findAndAdd(Node currentNode, K key, V value) {
		return(findAndAdd(currentNode, key, value, null));
	}
	
	//Added returns the value that was added and is only chaned when it is created
	@SuppressWarnings("unchecked")
	private Node findAndAdd(Node currentNode, K key, V value, Node added) {
		Node newNode = null;
		//go left?
		if(key.compareTo((K) currentNode.key) < 0) {
			//recurse if there is a child
			if(currentNode.LChild != null) {
				added = findAndAdd(currentNode.LChild, key, value, added);
			}
			//otherwise add new node
			else {
				newNode = new Node(key,value);
				//set up new node
				currentNode.LChild = newNode;
				currentNode.LChild.parent = currentNode;
				added = currentNode.LChild;
			}
		}
		//go right?
		else if(key.compareTo((K) currentNode.key) > 0) {
			//recurse if there is a child
			if(currentNode.RChild != null) {
				added = findAndAdd(currentNode.RChild, key, value, added);
			}
			//otherwise add new node
			else {
				newNode = new Node(key,value);
				//set up new node
				currentNode.RChild = newNode;
				currentNode.RChild.parent = currentNode;
				added = currentNode.RChild;

			}
		}
		else {
			//replace value
			currentNode.value = value;
			newNode  = currentNode;
		}
		
		return added;
		
		
		
	}
	
	private void fix(Node node) {
		//System.out.println("Recursing and looking at node: " + node.key);
		System.out.println("NODE IS " + node.key);
		
		//is node red, and right child of black parent? if so rotate left
		if(node.RChild != null) {
			if(node.RChild.isRed && !node.isRed) {
				this.printRedBlackTree(this.root, "", true);
				rotateLeft(node, node.RChild);
				System.out.println("Black Parent with red right child fixed:");
				this.printRedBlackTree(this.root, "", true);
				System.out.println("node IS " + node.key);
				node = node.parent;
			}
		}
			
		//two red nodes in a row?
		if(node.LChild != null && node.LChild.LChild != null) {
			if(node.LChild.isRed && node.LChild.LChild.isRed) {
				rotateRight(node, node.LChild);
				System.out.println("Two red nodes in a row Fixed:");
				this.printRedBlackTree(this.root, "", true);
				System.out.println("node IS " + node.key);
				node = node.parent;
			}
		}
		
		//Two Red Children of one Black node?
		if(node.LChild != null && node.RChild != null) {
			if((node.RChild.isRed && node.LChild.isRed) && !node.isRed) {
				node.flipColor();
				node.LChild.flipColor();
				node.RChild.flipColor();
				System.out.println("Two red children of Black parent Fixed:");
				this.printRedBlackTree(this.root, "", true);
				System.out.println("node IS " + node.key);

			}
		}
		
		//Red Root?
		if(this.root.isRed) {
			this.root.flipColor();
			System.out.println("Red Root Fixed:");
			this.printRedBlackTree(this.root, "", true);
		}
		
		System.out.println("+++++++++++++++++++++++++++++++");
		
		if(node.parent != null) fix(node.parent);
	}
	
	@SuppressWarnings("unchecked")
	private void rotateLeft(Node parent, Node child) {
	    Node temp = child.LChild;
	    child.LChild = parent; // make the right child of child to be the parent
	    parent.RChild = temp; // make the left child parent be the stored right child of child

	    // If stored right child of the child is not null, update its parent
	    if (temp != null) temp.parent = parent;

	    // make the parent of the child the parent of the parent
	    child.parent = parent.parent;

	    // make the parent of parent the child node
	    if (parent.parent == null) root = child;
	    // If the parent is a left child
	    else if (parent == parent.parent.LChild) parent.parent.LChild = child;
	    // If the parent is a right child
	    else parent.parent.RChild = child;

	    parent.parent = child;
	}

	
	@SuppressWarnings("unchecked")
	private void rotateRight(Node parent, Node child) {
		parent.isRed = true;
		child.isRed = false;
	    Node temp = child.RChild;
	    child.RChild = parent; // make the right child of child to be the parent
	    parent.LChild = temp; // make the left child parent be the stored right child of child

	    // If stored right child of the child is not null, update its parent
	    if (temp != null) temp.parent = parent;

	    // make the parent of the child the parent of the parent
	    child.parent = parent.parent;

	    // make the parent of parent to be the child node
	    if (parent.parent == null) root = child;
	    // If the parent is a left child
	    else if (parent == parent.parent.LChild) parent.parent.LChild = child; 
	    // If the parent is a right child
	    else parent.parent.RChild = child;
	    
	    parent.parent = child; //make parent's parent, child
	}
	
	private void printRedBlackTree(Node node, String prefix, boolean isTail) {
	    if (node != null) {
	    	String childLabel = "";
	        String childPrefix = isTail ? "    " : "│   ";
	        String nodeType = isTail ? "└── " : "├── ";
	        if(node.parent != null) {
	        	childLabel = (node == node.parent.LChild) ? "L" : "R";  // Add "L" for left child, "R" for right child
	        }

	        String relations = "";
	        if(node.parent != null) relations += "P=" + node.parent.key;
	        else relations += "P=N";
	        
	        //relations = "";
	        
	        System.out.println(prefix + nodeType + node.key + childLabel + " (" + (node.isRed ? "Red" : "Black") + ") " + relations);
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
