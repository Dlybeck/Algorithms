import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * A non-instantiable class that has public methods for manipulating a Digraph object
 * 
 * @author David Lybeck
 * @version 23.12.15
 */
public class TopologicalSorter {
	
	/**
	 * Checks to see if the given digraph has a cycle
	 * @param digraph Digraph
	 * @return boolean true if there is a cycle
	 */
	public static boolean isDirectedCycle(Digraph digraph) {
        // allocation
        HashSet<String> marked = new HashSet<>();
        HashSet<String> onStack = new HashSet<>();
        // look for cycles, 1 component at a time
        for (String v : digraph.getVertices()) {
            if (marked.contains(v))
            	continue;
            if (doCycleFindingDFT(digraph, v, marked, onStack))
            	return true;
        }
        // if no cycles, return false
        return false;
    }

	//does a depth first traversal on a given digraph
    private static boolean doCycleFindingDFT(Digraph digraph, String vertex, HashSet<String> marked, HashSet<String> onStack) {
        // mark the element off & add it to the stack
        marked.add(vertex);
        onStack.add(vertex);
        // see if any neighbors are on the stack, explore
        for (String v : digraph.getAdjacencyList(vertex)) {
            if (onStack.contains(v))
            	return true;
            if (!marked.contains(v))
                if (doCycleFindingDFT(digraph, v, marked, onStack))
                	return true;
        }
        // if we get here, we only found dead ends
        onStack.add(vertex);
        return false;
    }

    /**
     * Makes a topological list of a given Digraph
     * @param digraph Digraph
     * @return String[]
     */
    public static String[] sortTopologically(Digraph digraph) {
        // allocation
    	HashSet<String> marked = new HashSet<>();
        int[] index = {digraph.size() - 1};
        String[] list = new String[digraph.size()];
        // traverse
        for (String v : digraph.getVertices()) {
            if (marked.contains(v))
            	continue;
            else
            	doTopoSortDFT(digraph, v, marked, list, index);
        }
        // return the list
        return list;
    }

    //depth first traversal for sortTopologically()
    private static void doTopoSortDFT(Digraph digraph, String vertex, HashSet<String> marked, String[] list, int[] index) {
        // mark the element off
        marked.add(vertex);
        // explore neighbors
        for (String connected : digraph.getAdjacencyList(vertex))
            if (!marked.contains(connected))
                doTopoSortDFT(digraph, connected, marked, list, index);
        
        // add the vertex to the list
        list[index[0]] = vertex;
        index[0]--;
    }
	    
    /**
     * Makes an Array of Strong components (each a String array) from a given digraph
     * @param digraph Digraph
     * @return String[][] List of Strong components
     */
    public static String[][] findStrongComponents(Digraph digraph){
    	Digraph reverse = digraph.makeReverseGraph();
    	String[] reversePostfix = sortTopologically(reverse);
    	//REVERSE POSTFIX CONTAINS ELEMENTS (debug comment)
    	ArrayList<String[]> strongs = new ArrayList<String[]>();
    	HashSet<String> closedList = new HashSet<String>(); 
    	
    	for(String vertex : reversePostfix) {
    		//if vertex has not been found
    		if(!closedList.contains(vertex)) {
    			String[] component = doDFT(digraph, vertex, closedList);
	    		strongs.add(component);
    		}
    	}
    	String[][] array = new String[strongs.size()][];
    	
    	for (int i = 0; i < strongs.size(); i++)
    	    array[strongs.size() - (1+i)] = strongs.get(i);
    	
    	return array;
    }
    
    //Starting method for DFT for finding strong components
    private static String[] doDFT(Digraph digraph, String vertex, HashSet<String> closedList) {
    	ArrayList <String> group = new ArrayList<String>();
    	group = doDFT(digraph, vertex, closedList, group);
    	
    	Object[] objects = group.toArray();
    	String[] strongComponent = Arrays.copyOf(objects, objects.length, String[].class);

		return strongComponent;
    }
    
    //Recursive function for DFT for finding strong components
    private static ArrayList<String> doDFT(Digraph digraph, String vertex, HashSet<String> closedList, ArrayList<String> group) {
    	closedList.add(vertex);
        //add to strong component
    	group.add(vertex);
    	
    	for(String connected : digraph.getAdjacencyList(vertex))
    		if (!closedList.contains(connected))
                doDFT(digraph, connected, closedList, group);
    	
    	return group;
    }
}
