import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class RedBlackTree <K extends Comparable<K>, V> {
	public static void main(String[] args) {
		RedBlackTree tree = new RedBlackTree();
	}
	
	
	private Node root;
	
	public RedBlackTree() {
		this.root = null;
	}
	
	public void put(k key, V value) {
		Node root = findAndAdd(root);
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
