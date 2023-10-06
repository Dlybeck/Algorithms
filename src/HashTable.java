import java.util.ArrayList;

public class HashTable <K, V>{
	
	private ArrayList<K> keys;
	private ArrayList<ArrayList<V>> values;
	
	public HashTable (int initialSize) {
		this.keys = new ArrayList<K>(initialSize);
		this.values = new ArrayList<ArrayList<V>>(10);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
