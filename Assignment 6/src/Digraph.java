import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Digraph {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Digraph graph = new Digraph();
		
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		graph.addVertex("E");
		graph.addVertex("F");
		System.out.println(graph.toString());
		
		graph.addEdge("A", "B");
		graph.addEdge("A", "D");
		graph.addEdge("B", "C");
		graph.addEdge("B", "E");
		graph.addEdge("C", "B");
		graph.addEdge("C", "F");
		graph.addEdge("D", "E");
		graph.addEdge("E", "F");

		System.out.println(graph.toString());
		
		System.out.println(graph.isValidVertex("C"));
		
		System.out.println("");
		
	}

	private HashMap<String, ArrayList<String>> vertices;

	public Digraph() {
		this.vertices = new HashMap<String, ArrayList<String>>();
	}
	
	public void addVertex(String name) {
		System.out.println("Adding: " + name);
		if(!isValidVertex(name)) {
			ArrayList<String> emptyList = new ArrayList<String>(0);
			vertices.put(name, emptyList);
		}
		else {
			throw new IllegalArgumentException("This element has already been added");
		}
	}
	
	public void addEdge(String vertex1, String vertex2) {
		System.out.println("Connecting " + vertex1 + " to " + vertex2);
		//add vertex2 as a pointer in vertex1
		if(isValidVertex(vertex1) && isValidVertex(vertex2)) {
			System.out.println(vertices.get(vertex1).toString());
			vertices.get(vertex1).add(vertex2);
		}
		else {
			throw new IllegalArgumentException("Vertex1 or vertex2 does not exist");
		}
	}
	
	public boolean deleteEdge(String vertex1, String vertex2) {
		//if both vertices are where they should be
		System.out.println("Deleting edge between " +vertex1 + " and " + vertex2);
		if(isValidVertex(vertex1) && isValidVertex(vertex2)) {
			//edge exists
			if(vertices.get(vertex1).contains(vertex2)) {
				vertices.get(vertex1).remove(vertex2);
				return true;
			}
			//edge does not exist
			else return false;
		}
		//a vertex does not exist
		else throw new IllegalArgumentException("Vertex1 or vertex2 does not exist");
	}
	
	public int size() {
		return vertices.size();
	}
	
	public boolean isValidVertex(String vertex) {
		if(vertices.containsKey(vertex)) return true;
		else return false;
	}
	
	public String[] getAdjacencyList(String vertex) {
		Object[] objects = vertices.get(vertex).toArray();
		return Arrays.copyOf(objects, objects.length, String[].class);
	}
	
	public String[] getVertices() {
		Object[] objects = vertices.keySet().toArray();
		return Arrays.copyOf(objects, objects.length, String[].class);
	}
	
	public Digraph makeReverseGraph() {
		Digraph reverse = new Digraph();
		vertices.forEach((vertex1, edges) -> {
			for(String vertex2 : edges) {
				//if it is a new vertex add it to the graph
				if(!reverse.isValidVertex(vertex2)) {
					reverse.addVertex(vertex2);
				}
			}
		});
		vertices.forEach((vertex1, edges) -> {
			for(String vertex2 : edges) {
				//if it is a new vertex add it to the graph
				if(!reverse.isValidVertex(vertex2)) {
					reverse.addVertex(vertex2);
				}
			}
		});
		vertices.forEach((vertex1, edges) -> {
			for(String vertex2 : edges) {
				reverse.addEdge(vertex2, vertex1);
			}
		});

		
		return reverse;
	}
	
	@Override
	public String toString() {
		return vertices.toString();
	}
	
}










