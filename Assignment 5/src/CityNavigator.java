/** 
 * This program navigates between cities using the A* algorithm. It takes in a
 * ".geo" file that models cities and roads as a graph. It is then able to
 * recieve requests from the user, planning the best route to take between any
 * two cities in its model.
 *
 * It relies on an AStarGraph object, that you must write. The API is as given
 * in the assignment.
 *
 * @author Adam A. Smith
 * @version 2023.11.08
 */

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

public class CityNavigator {
	private static DecimalFormat locFormat = new DecimalFormat("0.##");
	public static void main(String[] args) {
		// make sure we have a file
		if (args.length == 0) {
			System.err.println("Please enter a file name!");
			System.exit(1);
		}

		// make the graph, and query on it
		try {
			AStarGraph graph = makeAStarGraph(args[0]);
			System.out.println("File \"" +args[0]+ "\" has been loaded with " +graph.size()+ " cities.");
			queryUser(graph);
		}
		catch (IOException e) {
			System.err.println("Couldn't open file \"" +args[0]+ "\".");
			System.exit(1);
		}
	}

	// load a file & make an AStarGraph from its lines
	private static AStarGraph makeAStarGraph(String filename) throws IOException {
		AStarGraph graph = new AStarGraph();
		
		// read the file
		Scanner scanner = new Scanner(new File(filename));
		boolean addCities = true;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.length() == 0) addCities = false;

			// 1st part: adding cities to the graph
			else if (addCities) {
				String[] tokens = line.split("\t");
				graph.addCity(tokens[0], Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));
			}

			// 2nd part: adding roads
			else {
				String[] tokens = line.split("\t");
				graph.addRoad(tokens[0], tokens[1], Double.parseDouble(tokens[2]));
			}
		}

		// close out & return
		scanner.close();
		return graph;
	}

	// enter a loop to query the user for searches & commands
	private static void queryUser(AStarGraph graph) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Please enter your query:\n");
		System.out.print("> ");

		while (scanner.hasNextLine()) {
			String query = scanner.nextLine().trim();

			// do nothing on empty string
			if (query.length() == 0);

			// quit on "!quit" or "!exit"
			else if (query.equals("!quit") || query.equals("!exit")) break;

			// help menu
			else if (query.equals("!help")) printHelpMenu();

			// find path(s)
			else if (query.contains("-")) {
				String[] cities = query.split("-");
				for (int i=0; i<cities.length; i++) cities[i] = cities[i].trim();

				// actual path finding
				for (int i=0; i<cities.length-1; i++) {
					try {
						String[] path = graph.findPath(cities[i], cities[i+1]);

						// print on failure
						if (path == null) {
							System.out.println("I'm sorry. There's not a path from " +cities[i]+ " to " +cities[i+1] + ".");
						}

						// print on found path
						else {
							int distance = (int)Math.round(graph.measurePath(path));
							System.out.print("Path found: " + path[0]);
							for (int j=1; j<path.length; j++) System.out.print(" - " + path[j]);
							System.out.println(" (" +distance+ " km)");
						}
					}

					// catch for bad cities
					catch (IllegalArgumentException ex) {
						System.out.println("I'm sorry. Either \"" +cities[i]+ "\" or \"" + cities[i+1] + "\" is unknown to me.");
					}
				}
				System.out.println();
			}

			// query 1 city--get info on it
			else {
				// if valid, give its location & neighbors
				if (graph.isValidCity(query)) {
					double[] location = graph.getCityLocation(query);
					System.out.print(query + " is located at (");

					// latitude
					if (location[0] > 0) System.out.print(locFormat.format(location[0]) +"° N, ");
					else if (location[0] < 0) System.out.print(locFormat.format(-location[0]) +"° S, ");
					else System.out.print("0°, ");

					// longitude
					if (location[1] > 0) System.out.print(locFormat.format(location[1]) +"° E). ");
					else if (location[1] < 0) System.out.print(locFormat.format(-location[1]) +"° W). ");
					else System.out.print("0°). ");

					// neighbors
					String[] neighbors = graph.getNeighboringCities(query);
					if (neighbors.length == 0) System.out.println("It is not connected to any other city.");
					else {
						if (neighbors.length == 1) System.out.println("It has a road to " +neighbors[0] +".");
						else if (neighbors.length == 2) System.out.println("It has roads to " + neighbors[0] + " and " +neighbors[1] + ".");
						else {
							System.out.print("It has roads to ");
							for (int i=0; i<neighbors.length-1; i++) System.out.print(neighbors[i] + ", ");
							System.out.println("and " +neighbors[neighbors.length-1] + ".");
						}
					}
				}

				// otherwise, error message
				else {
					System.out.println("I'm sorry. There is no city \"" +query+ "\".");
				}
				System.out.println();
			}

			System.out.print("> ");
		}

		System.out.println("Goodbye!");
	}

	// just prints a help menu
	private static void printHelpMenu() {
		System.out.println("\t<city>\t\tprints info on a city");
		System.out.println("\t<city>-<city>\tcalculates a path");
		System.out.println("\t!help\t\tprints this help menu");
		System.out.println("\t!exit\t\texits the program");
	}
}