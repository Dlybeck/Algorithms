import java.util.*;

import javax.print.attribute.standard.Destination;

public class AStarGraph {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
		if(!cities.containsKey(city1Name) || !cities.containsKey(city2Name)) throw new IllegalArgumentException("A city does not exist");
		
		City city1 = cities.get(city1Name);
		City city2 = cities.get(city2Name);
		
		if(city1.roads.contains(road1) || city2.roads.contains(road2)) throw new IllegalArgumentException("Cities already connected");
		if(length < findHeuristic(city1, city2)) throw new IllegalArgumentException("Road is too short");
		
		Road road1 = new Road(city2Name, length);
		Road road2 = new Road(city1Name, length);
		
		city1.roads.add(road1); //add road from city1 to 2
		city2.roads.add(road2); //add road from city2 to 1
	}
	
	public boolean deleteRoad(String city1Name, String city2Name) {
		City city1 = cities.get(city1Name);
		City city2 = cities.get(city2Name);
		
		//returns true if both removed the road (they should always be together)
		return (city1.roads.remove(city2) && city2.roads.remove(city1));		
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
		Math.sin(num);
	}
	private double cos(double num) {
		Math.cos(num);
	}
	private double arccos(double num) {
		Math.acos(num)
	}
	
	
	
	
	private class City{
		private String name;
		private double lat;
		private double lon;
		private HashSet<Road> roads;
		
		public City(String name, double lat, double lon) {
			this.name = name;
			this.lat = lat;
			this.lon = lon;
			this.roads = new HashSet<>();
		}
	}
	
	private class Road{
		private String destination;
		private double length;
		public Road(String destination, double length) {
			this.destination = destination;
			this.length = length;
		}
	}
}
