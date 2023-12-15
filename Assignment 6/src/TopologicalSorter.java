import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class TopologicalSorter {
	public static boolean isDirectedCycle(Digraph digraph) {
    	System.out.println("\nChecking if Directed Cycle\n");

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
    	System.out.println("\nSorting Topologically\n");

        // allocation
    	HashSet<String> marked = new HashSet<>();
        int[] index = {digraph.size() - 1};
        String[] list = new String[digraph.size()];
        // traverse
        for (String v : digraph.getVertices()) {
            if (marked.contains(v)) continue;
            else{
            	doTopoSortDFT(digraph, v, marked, list, index);
            }
        }
        // return the list
        return list;
    }

    private static void doTopoSortDFT(Digraph digraph, String vertex, HashSet<String> marked, String[] list, int[] index) {
    	System.out.println("\nDoing doTopoSortDFT\n");
        // mark the element off
        marked.add(vertex);
        // explore neighbors
        for (String connected : digraph.getAdjacencyList(vertex)) {
            if (!marked.contains(connected)) {
                doTopoSortDFT(digraph, connected, marked, list, index);
            }
        }
        // add the vertex to the list
        list[index[0]] = vertex;
        index[0]--;
    }
	    
    public static String[][] findStrongComponents(Digraph digraph){
    	System.out.println("\nFinding Strong Components\n");

    	
    	Digraph reverse = digraph.makeReverseGraph();
    	String[] reversePostfix = sortTopologically(reverse);
    	//REVERSE POSTFIX CONTAINS ELEMENTS (debug comment)
    	ArrayList<String[]> strongs = new ArrayList<String[]>();
    	HashSet<String> closedList = new HashSet<String>(); 
    	
    	for(String vertex : reversePostfix) {
    		//if vertex has not been found
    		if(!closedList.contains(vertex)) {
    			String[] component = doDFT(digraph, vertex, closedList);
    			System.out.println(Arrays.toString(component));
	    		strongs.add(component);
    		}
    	}
    	String[][] array = new String[strongs.size()][];
    	
    	for (int i = 0; i < strongs.size(); i++) {
    	    array[strongs.size() - (1+i)] = strongs.get(i);
    	}
    	
    	System.out.println("array:");
    	for(String[] Vs : strongs) {
    		System.out.println(Arrays.toString(Vs));
    	}
    	
    	return array;
    }
    
    private static String[] doDFT(Digraph digraph, String vertex, HashSet<String> closedList) {
    	System.out.println("\nDoing doDFT\n");
    	ArrayList <String> group = new ArrayList<String>();
    	group = doDFT(digraph, vertex, closedList, group);
    	
    	Object[] objects = group.toArray();
    	String[] strongComponent = Arrays.copyOf(objects, objects.length, String[].class);
    	for(String v : strongComponent) {
    		System.out.print(v + "       ");
    	}
    	System.out.println("");
		return strongComponent;
    }
    
    private static ArrayList<String> doDFT(Digraph digraph, String vertex, HashSet<String> closedList, ArrayList<String> group) {
    	closedList.add(vertex);
        //add to strong component
        System.out.println("Adding " + vertex);
    	group.add(vertex);
    	
    	for(String connected : digraph.getAdjacencyList(vertex)) {
    		if (!closedList.contains(connected)) {
                doDFT(digraph, connected, closedList, group);
            }
    	}
    	
    	return group;
    }
}
