import java.util.ArrayList;
import java.util.HashMap;

public class Digraph {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private HashMap<String, ArrayList<String>> adjacencyList; //Use sets and hashmap like in old graph assignment? To prevent repeating hashed.
	
	public Digraph() {
		this.adjacencyList = new HashMap<String, ArrayList<String>>(11);
	}
}
