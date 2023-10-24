import java.util.ArrayList;

public class RedBlackTree <K, V> {
	public static void main(String[] args) {
	}
	
	private Node
	private
	

	private static class Node <K, V>{
		private K key;
		private V value;
		private boolean isRed;
		private Node RChild;
		private Node LChild;
		
		public Node(K key, V value){
			this.key = key;
			this.value = value;
			this.isRed = true;
			this.RChild = null;
			this.LChild = null;
		}
	}
}
