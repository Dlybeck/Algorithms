import java.util.ArrayList;
import java.util.Objects;


public class HashTable <K, V>{
	
	
	private KVPair[] keys;
	private ArrayList<ArrayList<V>> values;
	int initialSize;
	
	public static void main(String[] args) {
	}
	
	public HashTable(int initialSize) {
		this.keys = new KVPair[initialSize]; //Initialize Keys
		
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
		KVPair<K, V> newPair = new KVPair<K, V>(key, value);
		values.add()
		
		
	}
	
	private int hashAndMod(K key) {
		int hash = key.hashCode();
		hash &= 0x7fffffff;
		return (hash % keys.size());
	}
	
	private static class KVPair <K, V>{
		private K key;
		private V value;
		
		public KVPair(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}
}
