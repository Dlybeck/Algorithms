import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TopologicalSorter {
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
    	
    	String[][] array = new String[strongs.size()][];
    	
    	for (int i = 0; i < strongs.size(); i++) {
    	    array[i] = strongs.get(i);
    	}
    	return array;
    }
}
