import java.util.ArrayList;
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
		graph.addEdge("B", "C");
		graph.addEdge("C", "F");
		graph.addEdge("F", "E");
		graph.addEdge("E", "D");
		graph.addEdge("D", "A");
		graph.addEdge("B", "E");
		graph.addEdge("F", "A");
		System.out.println(graph.toString());
		graph.deleteEdge("F", "A");
		System.out.println(graph.toString());
		
		System.out.println(graph.isValidVertex("C"));
		
		System.out.println("");
		
		Digraph reverse = graph.makeReverseGraph();
		System.out.println(reverse.toString());
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
	
	
	
	private class TopologicalSorter{
		
		public boolean isDirectedCycle(Digraph digraph) {
			// allocation
			boolean[] marked = new boolean[digraph.countVertices()];
			boolean[] onStack = new boolean[digraph.countVertices()];
			// look for cycles, 1 component at a time
			for (int v : digraph.getVertices()) {
				if (marked[v]) continue;
				if (doCycleFindingDFT(digraph, v, marked, onStack)) return true;
			}
			// if no cycles, return false
			return false;
		}
		
		private boolean doCycleFindingDFT(Digraph digraph, int vertex, boolean[] marked, boolean[] onStack) {
			  // mark the element off & add it to the stack
			  marked[vertex] = true;
			  onStack[vertex] = true;
			  // see if any neighbors are on the stack, explore
			  for (int v : digraph.getAdjacencyList(vertex)) {
				  if (onStack[v]) return true;
				  if (!marked[v]) {
					  if (doCycleFindingDFT(digraph, v, marked, onStack)) return true;
				  }
			  }
			  // if we get here, we only found dead ends
			  onStack[vertex] = false;
			  return false;
		}
		
		public int[] sortTopologically(Digraph digraph) {
			// allocation
			boolean[] marked = new boolean[digraph.countVertices()];
			int[] list = new int[digraph.countVertices()];
			int[] index = new int[1];
			index[0] = list.length-1;
			// traverse
			for (int v : digraph.getVertices()) {
				if (marked[v]) continue;
				doTopoSortDFT(digraph, v, marked, list, index);
			}
			// return the list
			return list;
		}
	}
	
}










