@SuppressWarnings("rawtypes")


public class HashTable <K, V>{
	
	private KVPair[][] values;
	private int total = 0;
	private double LF;
	
	public static void main(String[] args) {
		HashTable<Integer, String> HT = new HashTable<>();
		
		HT.put(2, "Value 2a");
		HT.put(3, "Value 3");
		HT.put(2, "Value2b");
		System.out.println("\n");
		
		System.out.println(HT.get(2));
		System.out.println(HT.get(3));
		System.out.println("\n");
	}
	
	public HashTable(int initialSize) {
		//Initialize Values
		this.values = new KVPair[initialSize][1];
	}
	public HashTable() {
		this(11);
	}
	
	public void put (K key, V value) {
		//hash+mod key
		int rowIndex = hashAndMod(key);
		
		//Create KVP to be added
		KVPair<K, V> newPair = new KVPair<K, V>(key, value);
		
		KVPair[] row = values[rowIndex];
		
		//If there is an empty spot take it
		if(row[0] == null) {
			row[0] = newPair;
		}
		else { //if the spot is full copy previous values into new array and insert new value
			row = addToArray(row);	
			
			row[row.length - 1] = newPair; 
		}
		
		total ++;
		
		//rehash?
		LF = calcLF(total, values.length);
		if(LF >= 0.75) {
			//REHASH
		}
	}
	
	@SuppressWarnings("unchecked")
	public V get(K key){
		//get hashed index
		int rowIndex = hashAndMod(key);
		KVPair[] row = values[rowIndex];
		
		//check every KVP in row for given key
		for(int i = 0; i < row.length; i++) {
			if(row[i].key == key) {
				return (V) row[i].value; //return value at matching key
			}
		}
		
		//If it doesn't exist return null
		return null;
	}
	
	
	private KVPair[] addToArray(KVPair[] KVPs) {
		KVPair[] newArray = new KVPair[KVPs.length + 1];
		System.arraycopy(KVPs, 0, newArray, 0, KVPs.length);
		
		return newArray;

	}
 	
	private int hashAndMod(K key) {
		int hash = key.hashCode();
		hash &= 0x7fffffff;
		return (hash % values.length);
	}
	
	private double calcLF(int numVal, int length) {
		return (numVal/length);
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
