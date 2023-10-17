/** 
 * This program analyzes the a text that is given to it, counting every
 * individual word. It is then able to report on them to the user. It can also
 * give basic statistics on the hash table, such as its size, load factor, etc.
 *
 * It relies on a HashTable object, that you must write. The API is as given in
 * the assignment.
 *
 * @author Adam A. Smith
 * @version 2023.09.28
 */

import java.io.*;
import java.util.*;

public class WordFreqs {
	public static void main(String[] args) {
		// make sure we have a file
		if (args.length == 0) {
			System.err.println("Please enter a file name!");
			System.exit(1);
		}

		// make the hash, and query on it
		try {
			HashTable<String, Integer> hash = makeHashTable(args[0]);
			queryUser(hash);
		}
		catch (IOException e) {
			System.err.println("Couldn't open file \"" +args[0]+ "\".");
			System.exit(1);
		}
	}

	// load a file & make a HashTable from its words
	private static HashTable<String, Integer> makeHashTable(String filename) throws IOException {
		HashTable<String, Integer> hash = new HashTable<>();
		
		// set up Scanner that tokenizes based on non-(letters, numbers, underscore, apostrophe)
		Scanner scanner = new Scanner(new File(filename));
		scanner.useDelimiter("[^A-Za-z0-9'_]+");

		// read, token by token
		while (scanner.hasNext()) {
			String token = scanner.next().toLowerCase();

			// remove leading/trailing apostrophes
			if (token.startsWith("'")) token = token.replaceAll("^'+", "");
			if (token.endsWith("'")) token = token.replaceAll("'+$", "");

			// skip if it was just apostrophes
			if (token.length() == 0) continue;

			// add to table
			if (hash.containsKey(token)) {
				int freq = hash.get(token) + 1;
				hash.put(token, freq);
			}
			else hash.put(token, 1);
		}

		// close out & return
		scanner.close();
		return hash;
	}

	// enter a loop to query the user for searches & commands
	private static void queryUser(HashTable<String, Integer> hash) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("The text contains " +hash.size()+ " unique words.");
		System.out.println("Please enter a word to query, or \"!help\" for help, or \"!exit\" to exit.");
		System.out.print("> ");

		while (scanner.hasNextLine()) {
			String query = scanner.nextLine().trim();

			// do nothing on empty string
			if (query.length() == 0);

			// quit on "!quit" or "!exit"
			else if (query.equals("!quit") || query.equals("!exit")) break;

			// check for size
			else if (query.equals("#")) System.out.println("The text contains " +hash.size()+ " unique words.");

			// print stats on the backing table
			else if (query.equals("!stats")) {
				System.out.println("Hash table statistics:");
				System.out.println("\tSize (n): " +hash.size());
				System.out.println("\tTable size (m): " +hash.getTableSize());
				System.out.printf("\tLoad factor (α): %1.3f\n", hash.getLoadFactor());
				System.out.println("\tEmpty units: " +hash.countEmptySlots());
				System.out.println("\tLongest run: " +hash.findLongestRunLength());
			}

			// help menu
			else if (query.equals("!help")) printHelpMenu();

			// unknown special command
			else if (query.startsWith("!")) {
				System.out.println("I didn't recognize that. Try \"!help\"?");
			}

			// deletion
			else if (query.startsWith("-")) {
				String wordToDelete = query.substring(1);
				if (wordToDelete.length() == 0) System.out.println("What word do you want to delete? Please try again.");
				else {
					Integer value = hash.delete(wordToDelete);
					if (value == null) System.out.println("\""+ wordToDelete+ "\" was not present.");
					else if (value == 1) System.out.println("1 entry of \"" +wordToDelete+ "\" has been deleted.");
					else System.out.println(value + " entries of \"" +wordToDelete+ "\" have been deleted.");
				}
			}

			// add a word or increase its frequency
			else if (query.startsWith("+")) {
				String newWord = query.substring(1);
				if (newWord.length() == 0) System.out.println("What word do you want to add? Please try again.");
				else if (hash.containsKey(newWord)) {
					int freq = hash.get(newWord) + 1;
					hash.put(newWord, freq);
					System.out.println("\"" +newWord+ "\" now appears " +freq+ "×.");
				}
				else {
					hash.put(newWord, 1);
					System.out.println("\"" +newWord+ "\" now appears 1×.");
				}
			}

			// regular query
			else {
				Integer freq = hash.get(query);
				if (freq == null || freq == 0) System.out.println("\"" +query+ "\" is not in the text.");
				else System.out.println("\"" +query+ "\" appears " +freq+ "× in the text.");
			}
			System.out.print("> ");
		}

		System.out.println("Goodbye!");
	}

	// just prints a help menu
	private static void printHelpMenu() {
		System.out.println("\t<word>\t\tprints word frequency");
		System.out.println("\t-<word>\t\tdeletes word");
		System.out.println("\t+<word>\tadds word");
		System.out.println("\t#\t\tprints the table size");
		System.out.println("\t!help\t\tprints this help menu");
		System.out.println("\t!stats\t\tprints in-depth stats on the table");
		System.out.println("\t!exit\t\texits the program");
	}
}
