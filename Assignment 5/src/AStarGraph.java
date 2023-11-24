import java.util.*;

public class AStarGraph {

	public static void main(String[] args) {
		AStarGraph graph = new AStarGraph();
		
		
		System.out.println("Adding Salem");
		graph.addCity("Salem", 44.9308333333, -123.0288888889);
		
		System.out.println("Adding Sacremento");
		graph.addCity("Sacremento", 38.5555555556, -121.4688888889);
		
		System.out.println("Adding Boston");
		graph.addCity("Boston", 42.3580555556, -71.0636111111);
		
		System.out.println("Adding Nashville");
		graph.addCity("Nashville", 36.1666666667, -86.7833333333);
		
		
		System.out.println("Adding Road from Salem to Nashville");
		graph.addRoad("Salem", "Nashville", 4100);
		
		System.out.println("Adding Road from Salem to Sacremento");
		graph.addRoad("Salem", "Sacremento", 900);
		
		System.out.println("Adding Road from Nashville to Sacremento");
		graph.addRoad("Nashville", "Sacremento", 3900);
		
		System.out.println("Adding Road from Nashville to Boston");
		graph.addRoad("Nashville", "Boston", 2000);
		
		//System.out.println("Adding Road from Salem to Boston");
		//graph.addRoad("Salem", "Boston", 5200);
		
		
		//System.out.println("Removing Road from Salem to Boston");
		//graph.deleteRoad("Salem", "Boston");
		
		System.out.println("Top of priority Queue:");
		graph.findPath("Salem", "Boston");
		
		

	}
	
	private HashMap<String, City> cities;
	public AStarGraph() {
		this.cities = new HashMap<String, City>();
	}
	
	public void addCity(String name, double latitude, double longitude) {
		City city = new City(name, latitude, longitude);
		cities.put(city.name, city); //add city to HashMap of all cities
	}
	
	public void addRoad(String city1Name, String city2Name, double length) {
		if(!isValidCity(city1Name) || !isValidCity(city2Name)) throw new IllegalArgumentException("A city does not exist");
		
		City city1 = cities.get(city1Name);
		City city2 = cities.get(city2Name);
		
		if(length < findHeuristic(city1, city2)) throw new IllegalArgumentException("Road is too short");
		if(isExistingRoad(city1, city2Name) || isExistingRoad(city2, city1Name)) throw new IllegalArgumentException("Cities already connected");
		
		
		Road road1 = new Road(city2Name, length);
		Road road2 = new Road(city1Name, length);
		
		city1.roads.add(road1); //add road from city1 to 2
		city2.roads.add(road2); //add road from city2 to 1
	}
	
	public boolean deleteRoad(String city1Name, String city2Name) {
		City city1 = cities.get(city1Name);
		City city2 = cities.get(city2Name);
		
		//if both cities say the road exists remove them
		if(!isExistingRoad(city1, city2Name) && !isExistingRoad(city2, city1Name)) {
			for(int i = 0; i < city1.roads.size(); i++) {
				if(city1.roads.get(i).dest.compareTo(city2Name) == 0) city1.roads.remove(i);
				return true;
			}
			for(int i = 0; i < city2.roads.size(); i++) {
				if(city2.roads.get(i).dest.compareTo(city1Name) == 0) city2.roads.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean isValidCity(String city) {
		return cities.containsKey(city);	
	}
	private boolean isExistingRoad(City city, String city2) {
		for(Road road : city.roads) {
			if(road.dest.compareTo(city2) == 0) return true;
		}
		return false;
	}
	
	public double[] getCityLocation(String cityName) {
		City city = cities.get(cityName);
		try {
			double[] coords = {city.lat, city.lon};
			return coords;
		}
		//if a city is null
		catch (Exception e){
			return null;
		}
	}
	
	public double getRoadLength(String city1Name, String city2Name) {
		City city = cities.get(city1Name);
		if(city != null && isExistingRoad(city, city2Name)) {
			for(Road road : city.roads) {
				//this is the road
				if (road.dest.compareTo(city2Name) == 0) return road.length;
			}
		}
		//road does not exist
		return -1;
	}
	
	public String[] getNeighboringCities(String cityName) {
		City city = cities.get(cityName);
		String[] neighbors = new String[city.roads.size()];
		for(int i = 0; i < city.roads.size(); i++) neighbors[i] = city.roads.get(i).dest;
		return neighbors;
	}
	
	public String[] findPath(String city1Name, String city2Name) {
		//Create Lists
		HashSet<String> closedList = new HashSet<String>();
		PriorityQueue<City> openList = new PriorityQueue<City>();
		
		//Set Up Cities
		City startCity = cities.get(city1Name);
		City endCity = cities.get(city2Name);
		City currentCity;
		City tempCity;
		startCity.pathScore = 0;
		startCity.score = findHeuristic(startCity, endCity) + 0;
		openList.add(startCity);
		
		double pathScore = 0;
		double heuristic = 0;
		while(!openList.isEmpty()) {
			currentCity = openList.poll();
			System.out.println("Best city is " + currentCity.name + ". With score " + currentCity.score);
			
			//if this city has already been checked move to the next in queue
			if(closedList.contains(currentCity.name)) continue;
			//if this is the city
			if(currentCity.name.compareTo(endCity.name) == 0) {
				closedList.add(currentCity.name);
				break;
			}
			//add to openList
			for(Road road : currentCity.roads) {
				tempCity = cities.get(road.dest); //get the destination from the given road
				pathScore = road.length + currentCity.pathScore; //add pathScore for new city
				heuristic = findHeuristic(tempCity, endCity); //add current heuristic
				if(tempCity.score == 0 || tempCity.score > (pathScore+heuristic)) {
					tempCity.pathScore = pathScore; //Set updated pathScore
					tempCity.score = pathScore+heuristic; //set updated score
					System.out.println("	Potential city is " + tempCity.name + ". With score " + tempCity.score);
					//System.out.println("	pathScore is "+ pathScore);
					//System.out.println("	heuristic is "+ heuristic + "\n");
					System.out.println();
					openList.add(tempCity);
				}
			}
			
			closedList.add(currentCity.name);
		}
		
		//create path array
		//interate through path and always choose "worse" path when given a choice
		
		
		
		
		return null;
	}
	
	
	
	
	
	
	private double findHeuristic(City city1, City city2) {
		//Math.PI/180 to convert to radians for sin calculations
		double latA = city1.lat * Math.PI/180;
		double latB = city2.lat * Math.PI/180;
		double lonA = city1.lon * Math.PI/180;
		double lonB = city2.lon * Math.PI/180;
		
		//calculate distance
		return (arccos(sin(latA) * sin(latB) + cos(latA) * cos(latB) * cos(lonA-lonB)) * 6317);
	}
	private double sin(double num) {
		return Math.sin(num);
	}
	private double cos(double num) {
		return Math.cos(num);
	}
	private double arccos(double num) {
		return Math.acos(num);
	}
	
	
	
	
	private class City implements Comparable<City>{
		private String name;
		private double lat;
		private double lon;
		private ArrayList<Road> roads;
		private double pathScore;
		private double score;
		
		public City(String name, double lat, double lon) {
			this.name = name;
			this.lat = lat;
			this.lon = lon;
			this.roads = new ArrayList<>();
			this.pathScore = 0;
			this.score = 0;
		}
		
		
		@SuppressWarnings("unused")
		@Override
		public int compareTo(City other) {
			//-1 to prioritize lowest
			return -1*Integer.compare((int)(other.score*100), (int)(this.score*100));
		}
	}
	
	private class Road{
		private String dest;
		private double length;
		
		public Road(String destCity, double length) {
			this.dest = destCity;
			this.length = length;
		}
		
		public int compareTo(Road other) {
			return Integer.compare((int)(other.length*100), (int) (this.length*100));
		}
	}
}
