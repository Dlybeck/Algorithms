@SuppressWarnings("rawtypes")


public class HashTable <K, V>{
	
	private KVPair[][] values;
	private int total = 0;
	private double LF;
	
	public static void main(String[] args) {
		HashTable<Integer, String> HT = new HashTable<>(10);
		
		for(int i = 0; i < 20; i++) {
			HT.put(i, "Value" + i);
		}
		
		HT.put(5, "Value5b");
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
	
	@SuppressWarnings("unchecked")
	public void put (K key, V value) {
		//hash+mod key
		int rowIndex = hashAndMod(key);
		
		//Create KVP to be added
		KVPair<K, V> newPair = new KVPair<K, V>(key, value);
		
		KVPair[] row = values[rowIndex];
		
		//If row is empty add it
		if(row[0] == null) {
			row[0] = newPair;
		}
		else { //add it
			for(int i = 0; i < row.length; i++) {
				if(row[i].key == key) {
					row[i].value = value; //if duplicate key replace value
					return;
				}
			}
			
			row = addToArray(row);	
			
			row[row.length - 1] = newPair; 
		}
		
		total ++;
		
		//rehash?
		LF = getLoadFactor();
		if(LF >= 0.75) {
			Rehash(values);
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
	
	
	/*public V delete(K key) {
		int rowIndex = hashAndMod(key);
		int rowLength = values[rowIndex].length;
		
		values[rowIndex] = new KVPair[1];
		
		total --;
		
	}*/
	
	
	public double getLoadFactor() {
		return (total/values.length);
	}
	
	@SuppressWarnings("unchecked")
	private void Rehash(KVPair[][] oldArray) {
		System.out.println(values.length);
		//reset values
		values = new KVPair[oldArray.length*2][1];
		
		//cycle through all items entered, and re-put them into the new array
		for(int i = 0; i < oldArray.length; i++) {
			KVPair[] row = oldArray[i];
			for(int j = 0; j < row.length; j++) {
				if(row[j] != null) {
					put((K)row[j].key, (V)row[j].value);
				}
			}
		}

		System.out.println("Rehashed!");
		System.out.println(values.length);
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
	
	private static class KVPair <K, V>{
		private K key;
		private V value;
		
		public KVPair(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}
}
