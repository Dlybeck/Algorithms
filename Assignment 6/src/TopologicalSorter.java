import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TopologicalSorter {
	public static boolean isDirectedCycle(Digraph digraph) {
        // allocation
        HashSet<String> marked = new HashSet<>();
        HashSet<String> onStack = new HashSet<>();
        // look for cycles, 1 component at a time
        for (String v : digraph.getVertices()) {
            if (marked.contains(v)) continue;
            if (doCycleFindingDFT(digraph, v, marked, onStack)) return true;
        }
        // if no cycles, return false
        return false;
    }

    private static boolean doCycleFindingDFT(Digraph digraph, String vertex, HashSet<String> marked, HashSet<String> onStack) {
        // mark the element off & add it to the stack
        marked.add(vertex);
        onStack.add(vertex);
        // see if any neighbors are on the stack, explore
        for (String v : digraph.getAdjacencyList(vertex)) {
            if (onStack.contains(v)) return true;
            if (!marked.contains(v)) {
                if (doCycleFindingDFT(digraph, v, marked, onStack)) return true;
            }
        }
        // if we get here, we only found dead ends
        onStack.add(vertex);
        return false;
    }

    public static String[] sortTopologically(Digraph digraph) {
        // allocation
    	HashSet<String> marked = new HashSet<>();
        int[] index = {digraph.size() - 1};
        String[] list = new String[digraph.size()];
        // traverse
        for (String v : digraph.getVertices()) {
            if (marked.contains(v)) continue;
            doTopoSortDFT(digraph, v, marked, list, index);
        }
        // return the list
        return list;
    }

    private static void doTopoSortDFT(Digraph digraph, String vertex, HashSet<String> marked, String[] list, int[] index) {
        // mark the element off
        marked.add(vertex);
        // explore neighbors
        for (String v : digraph.getAdjacencyList(vertex)) {
            if (!marked.contains(v)) {
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
    	
    	String[][] array = new String[strongs.size()][];
    	
    	for (int i = 0; i < strongs.size(); i++) {
    	    array[i] = strongs.get(i);
    	}
    	return array;
    }
}
