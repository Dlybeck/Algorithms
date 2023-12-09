import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
		
		String[][] strongs = 
	}

	private HashMap<String, ArrayList<String>> vertices;

	public Digraph() {
		this.vertices = new HashMap<String, ArrayList<String>>();
	}
	
	public void addVertex(String name) {
		if(!isValidVertex(name)) {
			ArrayList<String> emptyList = new ArrayList<String>(0);
			vertices.put(name, emptyList);
		}
		else {
			throw new IllegalArgumentException("This element has already been added");
		}
	}
	
	public void addEdge(String vertex1, String vertex2) {
		System.out.println("Adding " + vertex1 + " to " + vertex2);
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
		return (String[]) vertices.get(vertex).toArray();
	}
	
	public String[] getVertices() {
		return (String[]) vertices.keySet().toArray();
	}
	
	public Digraph makeReverseGraph() {
		Digraph reverse = new Digraph();
		vertices.forEach((vertex1, edges) -> {
			for(String vertex2 : edges) {
				//if it is a new vertex add it to the graph
				if(!reverse.isValidVertex(vertex2)) {
					reverse.addVertex(vertex2);
					System.out.println(reverse.toString());
				}
			}
		});
		vertices.forEach((vertex1, edges) -> {
			for(String vertex2 : edges) {
				//if it is a new vertex add it to the graph
				if(!reverse.isValidVertex(vertex2)) {
					reverse.addVertex(vertex2);
					System.out.println(reverse.toString());
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
	
	
	
	public class TopologicalSorter{	
		
		
		public static boolean isDirectedCycle(Digraph digraph) {
	        // allocation
	        HashMap<String, Boolean> marked = new HashMap<>();
	        HashMap<String, Boolean> onStack = new HashMap<>();
	        // look for cycles, 1 component at a time
	        for (String v : digraph.getVertices()) {
	            if (marked.containsKey(v)) continue;
	            if (doCycleFindingDFT(digraph, v, marked, onStack)) return true;
	        }
	        // if no cycles, return false
	        return false;
	    }

	    private static boolean doCycleFindingDFT(Digraph digraph, String vertex, HashMap<String, Boolean> marked, HashMap<String, Boolean> onStack) {
	        // mark the element off & add it to the stack
	        marked.put(vertex, true);
	        onStack.put(vertex, true);
	        // see if any neighbors are on the stack, explore
	        for (String v : digraph.getAdjacencyList(vertex)) {
	            if (onStack.getOrDefault(v, false)) return true;
	            if (!marked.getOrDefault(v, false)) {
	                if (doCycleFindingDFT(digraph, v, marked, onStack)) return true;
	            }
	        }
	        // if we get here, we only found dead ends
	        onStack.put(vertex, false);
	        return false;
	    }

	    public static String[] sortTopologically(Digraph digraph) {
	        // allocation
	    	HashMap<String, Boolean> marked = new HashMap<>();
	        int[] index = {digraph.size() - 1};
	        String[] list = new String[digraph.size()];
	        // traverse
	        for (String v : digraph.getVertices()) {
	            if (marked.containsKey(v)) continue;
	            doTopoSortDFT(digraph, v, marked, list, index);
	        }
	        // return the list
	        return list;
	    }

	    private static void doTopoSortDFT(Digraph digraph, String vertex, HashMap<String, Boolean> marked, String[] list, int[] index) {
	        // mark the element off
	        marked.put(vertex, true);
	        // explore neighbors
	        for (String v : digraph.getAdjacencyList(vertex)) {
	            if (!marked.getOrDefault(v, false)) {
	                doTopoSortDFT(digraph, v, marked, list, index);
	            }
	        }
	        // add the vertex to the list
	        list[index[0]--] = vertex;
	    }
		    
	    public static String[][] findStrongComponents(Digraph digraph){
	    	Digraph reverse = digraph.makeReverseGraph();
	    	String[] reverseList = sortTopologically(reverse);
	    	String[] adjList = null;
	    	ArrayList<String[]> strongs = new ArrayList<String[]>();
	    	HashSet<String> added = new HashSet<String>(); 
	    	
	    	for(String vertex : reverseList) {
	    		if(!added.contains(vertex)) {
	    			added.add(vertex); //add this vertex to added list
		    		adjList = digraph.getAdjacencyList(vertex); //get adjacency list of currently looked at vertex
		    		for(String v : adjList) //add every connected vertex to added list
		    			added.add(v);
		    		strongs.add(adjList);
	    		}
	    	}
	    	
	    	return (String[][])strongs.toArray();
	    }
	}
	
}










