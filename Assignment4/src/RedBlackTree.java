import java.util.*;

public class RedBlackTree<K extends Comparable<K>, V> {
    public static void main(String[] args) {
        RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();

        System.out.println("Adding: 4");
        tree.put(4, 4);
        System.out.println("------------------------------");
        
        System.out.println("Adding: 8");
        tree.put(8, 8);
        System.out.println("------------------------------");

        System.out.println("Adding: 2");
        tree.put(2, 2);
        System.out.println("------------------------------");

        System.out.println("Adding: 3");
        tree.put(3, 3);
        System.out.println("------------------------------");

        System.out.println("Adding: 5");
        tree.put(5, 5);
        System.out.println("------------------------------");
        
        System.out.println("Adding: 9");
        tree.put(9, 9);
        System.out.println("------------------------------");

        System.out.println("Adding: 1");
        tree.put(1, 1);
        System.out.println("------------------------------");
        
        System.out.println("Adding: 7");
        tree.put(7, 7);
        System.out.println("------------------------------");

        System.out.println("Adding: 6");
        tree.put(6, 6);
        System.out.println("------------------------------");
        
        System.out.println("Adding: 10");
        tree.put(10, 10);
        System.out.println("------------------------------");
        
        System.out.println("Removing: 8");
        tree.delete(8);
        System.out.println("------------------------------");
        
        System.out.println("Removing: 4");
        tree.delete(4);
        System.out.println("------------------------------");



    }

    private Node root;

    public RedBlackTree() {
        this.root = null;
    }

    public void put(K key, V value) {
    	Node newNode;
        if(this.root == null) {
        	newNode = new Node(key, value);
        	newNode.isRed = false;
        	this.root = newNode;
        }
        else {
        	newNode = new Node(key, value);
        	newNode = findAndAdd(this.root, newNode);
        }
        fix(this.root);
        System.out.println((this.treeToString(this.root, "", false)));
    }
    
    private Node findAndAdd(Node currentNode, Node newNode) {
    	//look to left
    	if(currentNode.key.compareTo(newNode.key) > 0) {
    		//place node
    		if(currentNode.LChild == null) {
    			currentNode.LChild = newNode;
    			return fix(newNode);
    		}
    		//recurse down
    		else {
    			newNode = findAndAdd(currentNode.LChild, newNode);
    			return fix(currentNode.LChild);
    		}
    	}
    	//look to right
    	else if(currentNode.key.compareTo(newNode.key) < 0) {
    		//place node
    		if(currentNode.RChild == null) {
    			currentNode.RChild = newNode;
    	        //System.out.println((this.treeToString(this.root, "", false)));
    			return fix(newNode);
    		}
    		//recurse down
    		else {
    			newNode = findAndAdd(currentNode.RChild, newNode);
    			return fix(currentNode.RChild);
    		}
    	}
    	//replace value
    	else {
    		currentNode.value = newNode.value;
    		return currentNode;
    	}
    }

    
    public V delete(K key) {
    	if(this.root == null) return null;
    	this.root.isRed = true;
    	Node node = findAndDelete(this.root, key);
    	if(node != null) {
	    	V value = node.value;
	    	this.root.isRed = false;
	        System.out.println((this.treeToString(this.root, "", false)));
	    	return value;
    	}
    	this.root.isRed = false;
    	return null;
    }
    
    private Node findAndDelete(Node currentNode, K key) {
    	//no children
    	if(currentNode.RChild == null && currentNode.LChild == null) {
    		System.out.println("No Children");
    		//key not there
    		if(currentNode.key.compareTo(key) == 0) {
    			return fix(currentNode);
    			//Fix tree?
    		}
    		else {
    			return null;
    		}
    	}
    
    	//One left child
    	if(currentNode.RChild == null && currentNode.LChild != null) {
    		//System.out.println("One left child");
        	//Node matches
        	if(currentNode.key.compareTo(key) == 0) {
        		swapData(currentNode, currentNode.LChild);
        		System.out.println("Returning " + currentNode.LChild.value);
        		return fix(currentNode.LChild);
        	}
        	else { //Continue
        		Node node = findAndDelete(currentNode.LChild, key);
        		if(node.key != null) {
        			node.key = null;
            		currentNode.LChild = null;
        		}
        		System.out.println("Returning " + node.value);
        		return fix(node);
        	}
    	}
    	//One right child
    	else if(currentNode.LChild == null && currentNode.RChild != null) {
    		//System.out.println("One Right child");

    		//Node matches
        	if(currentNode.key.compareTo(key) == 0) {
        		swapData(currentNode, currentNode.RChild);
        		System.out.println("Returning " + currentNode.RChild.value);
        		return fix(currentNode.RChild);
        	}
        	else { //Continue
        		Node node = findAndDelete(currentNode.RChild, key);
        		if(node.key != null) {
        			node.key = null;
            		currentNode.RChild = null;
        		}
        		System.out.println("Returning " + node.value);
        		return fix(node);
        	}
    	}
    	
    	//if node has 2 black children
    	if(!currentNode.LChild.isRed && !currentNode.RChild.isRed) {
    		//System.out.println("Two Black Children");
    		flipColors(currentNode);
    		if(currentNode.key.compareTo(key) == 0) {
    			swapData(currentNode, currentNode.RChild);//Swap node down until it can be deleted as a leaf
    			Node node = findAndDelete(currentNode.RChild, key);
        		if(node.key != null) {
        			node.key = null;
            		currentNode.RChild = null;
        		}
        		System.out.println("Returning " + node.value);
        		return fix(node);
    		}
    		else {
    			if(currentNode.key.compareTo(key) > 0) {
    				Node node = findAndDelete(currentNode.LChild, key);
            		if(node.key != null) {
            			node.key = null;
                		currentNode.LChild = null;
            		}
            		System.out.println("Returning " + node.value);

            		return fix(node);
        		}
    			else if(currentNode.key.compareTo(key) < 0) {
    				Node node = findAndDelete(currentNode.RChild, key);
            		if(node.key != null) {
            			node.key = null;
                		currentNode.RChild = null;
            		}
            		System.out.println("Returning " + node.value);
            		return fix(node);
        		}	
    		}
    	}
    	
    	//If node has left red child, and right black child
    	if(currentNode.LChild.isRed && !currentNode.RChild.isRed) {
    		//need to go left
    		if(currentNode.key.compareTo(key) > 0) {
    			Node node = findAndDelete(currentNode.LChild, key);
        		if(node.key != null) {
        			node.key = null;
            		currentNode.LChild = null;
        		}
        		System.out.println("Returning " + node.value);
        		return fix(node);
    		}
    		//need to go right
    		else if(currentNode.key.compareTo(key) < 0) {
    			rotateRight(currentNode);
    			Node node = findAndDelete(currentNode.RChild, key);
        		if(node.key != null) {
        			node.key = null;
        			currentNode.RChild = null;
        		}
        		System.out.println("Returning " + node.value);
        		return fix(node);
    		}	
    		//this is the node
    		else {
    			swapData(currentNode, currentNode.RChild); //Swap node down until it can be deleted as a leaf
    			Node node = findAndDelete(currentNode.RChild, key);
        		if(node.key != null) {
        			node.key = null;
        			currentNode.RChild = null;
        		}
        		System.out.println("Returning " + node.value);
        		return fix(node);
    		}
    	}
    	
    	System.out.println("Something went wrong at " + currentNode.key);
        System.out.println((this.treeToString(this.root, "", false)));
    	return null;
    	
    }
    


    
    private Node fix(Node node) {
    	//Red Right Child?
        if (node.RChild != null && node.RChild.isRed) {
            //System.out.println("Checking " + node.key + "...\nBlack Parent with red right child fixed:");
            node = rotateLeft(node);
            //System.out.println((this.treeToString(this.root, "", false)));
           // System.out.println("");
        }
        //Two red nodes in a row?
        //System.out.println("Checking for double red at " + node.key);
        if (node.LChild != null && node.LChild.LChild != null && node.LChild.isRed && node.LChild.LChild.isRed) {
            //System.out.println("Checking " + node.key + "...\nTwo red nodes in a row Fixed:");
        	node = rotateRight(node);
            //System.out.println((this.treeToString(this.root, "", false)));
            //System.out.println("");
        }
        //Two red Children
        System.out.println("Two Red Children?? at " + node.value);
        if (node.LChild != null && node.RChild != null && node.RChild.isRed && node.LChild.isRed && !node.isRed) {
            System.out.println("Checking " + node.key + "...\nTwo red children of Black parent Fixed:");
        	flipColors(node);
            //System.out.println((this.treeToString(this.root, "", false)));
            //System.out.println("");
        }
        //Red Root
        if(this.root.isRed) {
        	this.root.isRed = false;
        }
        
        return node;
    }

    private Node rotateLeft(Node parent) {
    	//System.out.println("Parent is " + parent.key);
        Node child = parent.RChild;
    	//System.out.println("Child is " + child.key);
    	swapData(parent, child);
    	
    	parent.RChild = child.RChild;
    	
    	child.RChild = child.LChild;
    	child.LChild = parent.LChild;
    	parent.LChild = child;
    	
    	return parent;
    	
    }

    private Node rotateRight(Node parent) {
    	//System.out.println("Parent is " + parent.key);
        Node child = parent.LChild;
    	//System.out.println("Child is " + child.key);
    	swapData(parent, child);
    	
    	parent.LChild = child.LChild;
    	
    	child.LChild = child.RChild;
    	child.RChild = parent.RChild;
    	parent.RChild = child;
    	
    	return parent;
    }
    
    private void swapData(Node x, Node y) {
    	K k = x.key;
    	V v  = x.value;
    	
    	x.key = y.key;
    	x.value = y.value;
    	
    	y.key = k;
    	y.value = v;
    }

    private void flipColors(Node node) {
        node.isRed = !node.isRed;
        node.LChild.isRed = !node.LChild.isRed;
        node.RChild.isRed = !node.RChild.isRed;
    }
    

    public String toString() {
        return treeToString(this.root, "", true);
    }

    private String treeToString(Node node, String prefix, boolean isTail) {
        if (node == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        builder.append(isTail ? "└── " : "├── ");
        builder.append(node.key + (node.isRed ? "(R)" : ""));
        builder.append("\n");

        String childPrefix = prefix + (isTail ? "    " : "│   ");
        String leftTree = treeToString(node.LChild, childPrefix, node.RChild == null);
        String rightTree = treeToString(node.RChild, childPrefix, true);

        builder.append(leftTree);
        builder.append(rightTree);

        return builder.toString();
    }





    private class Node {
        private K key;
        private V value;
        private boolean isRed;
        private Node RChild;
        private Node LChild;
        private int size;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.isRed = true;
            this.RChild = null;
            this.LChild = null;
            this.size = 1;
        }
    }
}
