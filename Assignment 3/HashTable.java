/**
* This is code for a hash table
* It takes a key and a value and adds them to a hash table structure to be stored for later use
*
* @author David Lybeck
* @version 2023.10.16
*/
@SuppressWarnings("rawtypes")
public class HashTable <K, V>{
	
	private KVPair[][] values;
	private int total = 0;
	private double LF;
	private final double LFConstUpper = 2;
	private final double LFConstLower = 0.5;
	
	/**
	* Main method, with lines to test all the individual methods in this class
	*/
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
	
	/**
	* Constructor for the hash table type
	*
	* @param initialSize Int value for how large the initial hash table structure will be
	*/
	public HashTable(int initialSize) {
		//Initialize Values
		this.values = new KVPair[initialSize][1];
	}
	
	/**
	* Links to the constructor for the hash table type with default size 11
	*/
	public HashTable() {
		this(11);
	}
	
	/**
	* Adds a new key/value pair to the hash table
	*
	* @param key The key to be added
	* @param value, the value to be added
	*/
	@SuppressWarnings("unchecked")
	public void put (K key, V value) {
		int rowIndex = hashAndMod(key); //hash+mod key
		
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
					row[i].value = value; //if duplicate key replace value and return
					return;
				}
			}
			//if not duplicate add new kvp
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
	
	/**
	* Gets the value corresponding to the given key
	*
	* @param key The key matching with the desired value
	* @return Value matching the given key
	*/
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
		
		return null;//If it doesn't exist
	}
		
	/**
	* removes the desired value from the hashtable
	*
	* @param key The key for the key/value pair that will be removed
	* @return The value of the key/value pair being removed
	*/
	@SuppressWarnings("unchecked")
	public V delete(K key) {
		V value;
		int rowIndex = hashAndMod(key);
		KVPair[] row = values[rowIndex];
		
		for(int i = 0; i < row.length; i++) {
			if((row[i]!=null) && (row[i].key.equals(key))) {
				value = (V) row[i].value;	//save value to return
				row[i] = null;
				row[i] = row[row.length-1]; //set value to be deleted to last value
				System.arraycopy(row, 0, values[rowIndex], 0, row.length - 1); //remove last value
				total --;
				 
				//Check and see if needs to be rehashed lower
				LF = getLoadFactor();
				if(LF <= LFConstLower && values.length > 10) {
					Rehash(values);
				}
				return value; //Return Deleted value
			}
		}
		LF = getLoadFactor();
		if(LF <= LFConstLower) {
			Rehash(values);
		}
		return null;
	}
	
	/**
	* Checks if given key is in the hash table
	*
	* @param key that will be searched for in the hash table
	* @return True if key is in the hash table, False if not.
	*/
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
	
	/**
	* Checks if given value is in the hash table
	*
	* @param value that will be searched for in the hash table
	* @return True if value is in the hash table, False if not.
	*/
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
	
	/**
	* Checks if the hash table is empty
	*
	* @return True if the hash table is empty, False if not.
	*/
	public boolean isEmpty() {
		return total==0;
	}
	
	/**
	* Finds the number of key/value pairs in the hashtable
	*
	* @return Int Size of the hash table
	*/
	public int size() {
		return total;
	}
	
	/**
	* Finds the key corresponding with a given value
	*
	* @param value the value corresponding with the desired key
	* @return key The key corresponding with the given value
	*/
	@SuppressWarnings("unchecked")
	public K reverseLookup(V value) {
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values[i].length; j++) {
				if ((values[i][j]!=null) && (values[i][j].value.equals(value))) {
					return (K) values[i][j].key;
				}
			}
		}
		return null;
	}
	
	/**
	* Finds the size of the primary internal array
	*
	* @return int Size of internal array
	*/
	public int getTableSize() {
		return values.length;
	}
	
	/**
	* Finds the load factor of the hash table
	*
	* @return double The load factor of the hash table
	*/
	public double getLoadFactor() {
		double loadTotal = total;
		double loadLength = values.length;
		return (double)(loadTotal/loadLength);
	}
	
	/**
	* finds the number of empty slots in the hash table
	*
	* @return int The number of empty slots
	*/
	public int countEmptySlots() {
		int empty = 0;
		for(int i = 0; i<values.length;  i++) {
			if(values[i][0] == null) empty++;
		}
		return empty;
	}
	
	/**
	* Finds the length of the longest chain
	*
	* @return int length of longest chain
	*/
	public int findLongestRunLength() {
		int longestChain = 0;
		for(int i = 0; i < values.length; i++) {
			if(values[i].length > longestChain) longestChain = values[i].length;
		}
		return longestChain;
	}
	
		
	//rehashes array and adds old values back in
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
	
	//adds one empty spot to an array
	private KVPair[] addToArray(KVPair[] oldRow) {
		KVPair[] newArray = new KVPair[oldRow.length + 1];
		System.arraycopy(oldRow, 0, newArray, 0, oldRow.length);
		
		return newArray;
	}
 	
	//hashes and mods a given key
	private int hashAndMod(K key) {
		int hash = (int) key.hashCode();
		hash &= 0x7fffffff;
		return (hash % values.length);
	}
	
	//Class for Key/value pairs used throughout the hash table
	private static class KVPair <K, V>{
		private K key;
		private V value;
		
		public KVPair(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}
}
