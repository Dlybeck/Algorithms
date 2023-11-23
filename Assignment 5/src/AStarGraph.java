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
		if(!isValidCity(city1Name) || !isValidCity(city2Name)) throw new IllegalArgumentException("A city does not exist");
		
		City city1 = cities.get(city1Name);
		City city2 = cities.get(city2Name);
		
		if(length < findHeuristic(city1, city2)) throw new IllegalArgumentException("Road is too short");
		
		if(!isValidRoad(city1, city2Name) || !isValidRoad(city2, city1Name)) throw new IllegalArgumentException("Cities already connected");
		
		
		city1.roads.put(city2Name, length); //add road from city1 to 2
		city2.roads.put(city1Name, length); //add road from city2 to 1
	}
	
	public boolean deleteRoad(String city1Name, String city2Name) {
		City city1 = cities.get(city1Name);
		City city2 = cities.get(city2Name);
		
		//if both cities say the road exists remove them
		if(isValidRoad(city1, city2Name) && isValidRoad(city2, city1Name)) {
			city1.roads.remove(city2Name);
			city2.roads.remove(city1Name);
			return true;
		}
		return false;
	}
	
	public boolean isValidCity(String city) {
		return cities.containsKey(city);	
	}
	private boolean isValidRoad(City city, String city2) {
		return city.roads.containsKey(city2);	
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
		if(city != null && isValidRoad(city, city2Name)) return city.roads.get(city2Name);
		//road does not exist
		return -1;
	}
	
	public String[] getNeighboringCities(String cityName) {
		City city = cities.get(cityName);
		String[] neighbors = new String[0];
		neighbors = city.roads.keySet().toArray(neighbors);
		return neighbors;
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
	
	
	
	
	private class City{
		private String name;
		private double lat;
		private double lon;
		private HashMap<String, Double> roads;
		
		public City(String name, double lat, double lon) {
			this.name = name;
			this.lat = lat;
			this.lon = lon;
			this.roads = new HashMap<>();
		}
	}
}
