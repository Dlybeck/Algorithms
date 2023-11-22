import java.util.*;

public class AStarGraph {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private HashMap<String, ArrayList<Destination>> adjList;
	private HashMap<String, City> cities;
	public AStarGraph() {
		this.adjList = new HashMap<String, ArrayList<Destination>>();
		this.cities = new HashMap<String, City>();
	}
	
	public void addCity(String name, double latitude, double longitude) {
		City city = new City(name, latitude, longitude);
		cities.put(city.name, city); //add city to HashMap of all cities
		adjList.put(name, null); //Add city to adjacency list with no pointers
	}
	
	public void addRoad(String city1, String city2, double length) {
		if(!cities.containsKey(city1) || !cities.containsKey(city2)) throw new IllegalArgumentException("A city does not exist");
		Road road = new Road(city1, city2, length);
		
		//make the road 2 way
		Destination toCity1 = new Destination(city1, length);
		if(adjList.get(city2).contains(toCity1)) throw new IllegalArgumentException("This road already exists"); //Is this Right???
		Destination toCity2 = new Destination(city2, length);
		adjList.get(city1).add(toCity2);
		adjList.get(city2).add(toCity1);
	}
	
	
	
	
	
	
	private class City{
		private String name;
		private double lat;
		private double lon;
		
		public City(String name, double lat, double lon) {
			this.name = name;
			this.lat = lat;
			this.lon = lon;
		}
	}
	
	private class Road{
		private String city1;
		private String city2;
		private double length;
		public Road(String city1, String city2, double length) {
			this.city1 = city1;
			this.city2 = city2;
			this.length = length;
		}
	}
	
	private class Destination{
		private double distance;
		private String city;
		public Destination(String city, double distance) {
			this.distance = distance;
			this.city = city;
		}
	}
}
