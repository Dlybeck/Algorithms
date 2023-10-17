@SuppressWarnings("rawtypes")

public class HashTable <K, V>{
	
	private KVPair[][] values;
	private int total = 0;
	private double LF;
	private final double LFConstUpper = 2;
	private final double LFConstLower = 0.5;
	
	public static void main(String[] args) {
		/*HashTable<Integer, String> HT = new HashTable<>(9);
		
		System.out.println("is empty? " + HT.isEmpty());
		System.out.println("\n");
		
		System.out.println("# of empty slots: " + HT.countEmptySlots());
		System.out.println("\n");
		
		System.out.println("Adding keys and values 0-10");
		for(int i = 0; i < 10; i++) {
			HT.put(i, "Value" + i);
		}
		
		System.out.println("Adding duplicate 5");
		HT.put(5, "Value5b");
		System.out.println("\n");
		
		System.out.println("get key 18 " + HT.get(18));
		System.out.println("get key 3 " + HT.get(3));
		System.out.println("\n");
		
		System.out.println("Delete key 3 " + HT.delete(3));
		System.out.println("Get key 3 " + HT.get(3));
		System.out.println("\n");
		
		System.out.println("Contains key 2? " + HT.containsKey(2));
		System.out.println("Contains key 60? " + HT.containsKey(60));
		System.out.println("\n");
		
		System.out.println("Contains value 'value7'? " + HT.containsValue("Value18"));
		System.out.println("Contains value 'KAJEFNWF'? " + HT.containsValue("KAJEFNWF"));
		System.out.println("\n");
		
		System.out.println("is empty? " + HT.isEmpty());
		System.out.println("\n");
		
		System.out.println("HT size: " + HT.size());
		System.out.println("\n");
		
		System.out.println("reverse lookup 'value7': " + HT.reverseLookup("Value7"));
		System.out.println("reverse lookup 'EDFNQEWKDMLD': " + HT.reverseLookup("EDFNQEWKDMLD"));
		System.out.println("\n");
		
		System.out.println("Get table size: " + HT.getTableSize());
		System.out.println("\n");
		
		System.out.println("# of empty slots: " + HT.countEmptySlots());
		System.out.println("\n");
		
		System.out.println("longest run length: " + HT.findLongestRunLength());
		System.out.println("\n");*/
		
		
		
		HashTable<String, Integer> HT = new HashTable<>(20);
		
		System.out.println("is empty? " + HT.isEmpty());
		System.out.println("\n");
		
		System.out.println("# of empty slots: " + HT.countEmptySlots());
		System.out.println("\n");
		
		System.out.println("Adding keys and values 0-10");
		for(int i = 0; i < 7; i++) {
			HT.put("value" + i, i);
		}
		
		System.out.println("Adding duplicate value5");
		HT.put("Value5", 5);
		System.out.println("\n");
		
		System.out.println("get key value18 " + HT.get("value18"));
		System.out.println("get key value3 " + HT.get("value3"));
		System.out.println("\n");
		
		System.out.println("Delete key value3 " + HT.delete("value3"));
		System.out.println("Get key value3 " + HT.get("value3"));
		System.out.println("\n");
		
		System.out.println("Contains key value2? " + HT.containsKey("value2"));
		System.out.println("Contains key value60? " + HT.containsKey("value60"));
		System.out.println("\n");
		
		System.out.println("Contains value '4'? " + HT.containsValue(4));
		System.out.println("Contains value '90'? " + HT.containsValue(90));
		System.out.println("\n");
		
		System.out.println("is empty? " + HT.isEmpty());
		System.out.println("\n");
		
		System.out.println("HT size: " + HT.size());
		System.out.println("\n");
		
		System.out.println("reverse lookup 2: " + HT.reverseLookup(2));
		System.out.println("reverse lookup '7654': " + HT.reverseLookup(7654));
		System.out.println("\n");
		
		System.out.println("Get table size: " + HT.getTableSize());
		System.out.println("\n");
		
		System.out.println("# of empty slots: " + HT.countEmptySlots());
		System.out.println("\n");
		
		System.out.println("longest run length: " + HT.findLongestRunLength());
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
				if(row[i].key.equals(key)) {
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
		if(LF >= LFConstUpper) {
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
			if((row[i]!=null) && (row[i].key.equals(key))) {
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
			if((row[i]!=null) && (row[i].key.equals(key))) {
				value = (V) row[i].value;	//save value to return
				row[i] = null;
				row[i] = row[row.length-1]; //set value to be deleted to last value
				System.arraycopy(row, 0, values[rowIndex], 0, row.length - 1); //remove last value
				total --;
				
				LF = getLoadFactor();
				if(LF <= LFConstLower && values.length > 10) {
					Rehash(values);
				}
				
				return value;
			}
		}
		LF = getLoadFactor();
		if(LF <= LFConstLower) {
			Rehash(values);
		}
		return null;
	}
	
	public boolean containsKey(K key) {
		int index = hashAndMod(key);
		KVPair[] row = values[index];
		for(int i = 0; i<row.length; i++) {
			if((row[i]!=null) && (row[i].key.equals(key))) {
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
		double loadTotal = total;
		double loadLength = values.length;
		return (double)(loadTotal/loadLength);
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
		total = 0;
		
		//Decrease size of values array
		if(LF < 0.5) {
			System.out.println("shrinking");
			values = new KVPair[oldArray.length/2][1];
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
		//Increase size of values array
		else {
			values = new KVPair[oldArray.length*2+1][1];
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
		
	}
	
	private KVPair[] addToArray(KVPair[] oldRow) {
		KVPair[] newArray = new KVPair[oldRow.length + 1];
		System.arraycopy(oldRow, 0, newArray, 0, oldRow.length);
		
		return newArray;
	}
 	
	private int hashAndMod(K key) {
		int hash = (int) key.hashCode();
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
