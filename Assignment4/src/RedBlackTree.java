import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class RedBlackTree <K, V> {
	public static void main(String[] args) {
		RedBlackTree tree = new RedBlackTree();
		System.out.println(tree.nodes.get(0));
	}
	
	
	private ArrayList<Node> nodes;
	
	public RedBlackTree() {
		this.nodes = new ArrayList<Node>();
	}
	
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
