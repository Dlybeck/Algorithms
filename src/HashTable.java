@SuppressWarnings("rawtypes")


public class HashTable <K, V>{
	
	private KVPair[][] values;
	private int total = 0;
	private double LF;
	private final double LFConst = 0.75;
	
	public static void main(String[] args) {
		HashTable<Integer, String> HT = new HashTable<>(9);
		
		System.out.println(HT.isEmpty());
		System.out.println("\n");
		
		System.out.println(HT.countEmptySlots());
		System.out.println("\n");
		
		for(int i = 0; i < 100; i++) {
			HT.put(i, "Value" + i);
		}
		
		HT.put(5, "Value5b");
		System.out.println("\n");
		
		System.out.println(HT.get(18));
		System.out.println(HT.get(3));
		System.out.println("\n");
		
		System.out.println(HT.delete(3));
		System.out.println(HT.get(3));
		System.out.println("\n");
		
		System.out.println(HT.containsKey(2));
		System.out.println(HT.containsKey(60));
		System.out.println("\n");
		
		System.out.println(HT.containsValue("Value18"));
		System.out.println(HT.containsValue("KAJEFNWF"));
		System.out.println("\n");
		
		System.out.println(HT.isEmpty());
		System.out.println("\n");
		
		System.out.println(HT.size());
		System.out.println("\n");
		
		System.out.println(HT.reverseLookup("Value7"));
		System.out.println(HT.reverseLookup("EDFNQEWKDMLD"));
		System.out.println("\n");
		
		System.out.println(HT.getTableSize());
		System.out.println("\n");
		
		System.out.println(HT.countEmptySlots());
		System.out.println("\n");
		
		System.out.println(HT.findLongestRunLength());
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
			
			values[rowIndex] = addToArray(row);	
			
			values[rowIndex][row.length] = newPair; 
		}
		
		total ++;
		
		//rehash?
		LF = getLoadFactor();
		if(LF >= LFConst) {
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
			if((row[i]!=null) && (row[i].key == key)) {
				return (V) row[i].value; //return value at matching key
			}
		}
		
		//If it doesn't exist return null
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public V delete(K key) {
		int rowIndex = hashAndMod(key);
		KVPair[] row = values[rowIndex];
		V value;
		
		for(int i = 0; i < row.length; i++) {
			if((row[i]!=null) && (row[i].key == key)) {
				value = (V) row[i].value;	//save value to return
				row[i] = null;
				row[i] = row[row.length-1]; //set value to be deleted to last value
				System.arraycopy(row, 0, values[rowIndex], 0, row.length - 1); //remove last value
				total --;
				return value;
			}
		}
		return null;
	}
	
	public boolean containsKey(K key) {
		int index = hashAndMod(key);
		KVPair[] row = values[index];
		for(int i = 0; i<row.length; i++) {
			if((row[i]!=null) && (row[i].key == key)) {
				return true;
			}
		}
		return false;
	}
	
	
	public boolean containsValue(V value) {
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values[i].length; j++) {
				if ((values[i][j]!=null) && (values[i][j].value.equals(value))) { //Will this work if value is not a String?
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isEmpty() {
		return total==0;
	}
	
	public int size() {
		return total;
	}
	
	@SuppressWarnings("unchecked")
	public K reverseLookup(V value) {
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values[i].length; j++) {
				if ((values[i][j]!=null) && (values[i][j].value.equals(value))) { //Will this work if value is not a String?
					return (K) values[i][j].key;
				}
			}
		}
		
		return null;
	}
	
	
	public int getTableSize() {
		return values.length;
	}
	
	public double getLoadFactor() {
		return (total/values.length);
	}
	
	public int countEmptySlots() {
		int empty = 0;
		for(int i = 0; i<values.length;  i++) {
			if(values[i][0] == null) empty++;
		}
		return empty;
	}
	
	public int findLongestRunLength() {
		int longestChain = 0;
		for(int i = 0; i < values.length; i++) {
			if(values[i].length > longestChain) longestChain = values[i].length;
		}
		
		return longestChain;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	private void Rehash(KVPair[][] oldArray) {
		//reset values
		values = new KVPair[oldArray.length*2][1];
		total = 0;
		//cycle through all items entered, and re-put them into the new array
		for(int i = 0; i < oldArray.length; i++) {
			KVPair[] row = oldArray[i];
			for(int j = 0; j < row.length; j++) {
				if(row[j] != null) {
					put((K)row[j].key, (V)row[j].value);
				}
			}
		}
	}
	
	
	private KVPair[] addToArray(KVPair[] oldRow) {
		KVPair[] newArray = new KVPair[oldRow.length + 1];
		System.arraycopy(oldRow, 0, newArray, 0, oldRow.length);
		
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
