import java.util.ArrayList;
import java.util.Objects;

public class HashTable <K, V>{
	
	private ArrayList<K> keys;
	private ArrayList<ArrayList<V>> values;
	int initialSize;
	
	public static void main(String[] args) {
	}
	
	public HashTable(int initialSize) {
		this.keys = new ArrayList<K>(initialSize); //Initialize Keys
		
		//Initialize Values
		this.values = new ArrayList<ArrayList<V>>(initialSize);
		for (int i = 0; i < initialSize; i++) {
			this.values.add(new ArrayList<V>());
		}
	}
	
	public HashTable() {
		this(11);
	}
	
	void put (K key, V value) {
		keys.add(key); //insert key
		
		//hash+mod key
		int index = hashAndMod(key);
		//insert value
	}
	
	private int hashAndMod(K key) {
		//how do I hash a generic object?
	}
	

}
