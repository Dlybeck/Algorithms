import java.util.*;

public class RedBlackTree<K extends Comparable<K>, V> {
    public static void main(String[] args) {
        RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();
        Random rand = new Random();
        int num;
        int size = 20;
        int[] nums = new int[size];
        for(int i = 0; i < size; i++) {
        	num = rand.nextInt(100);
        	nums[i] = num;
        	System.out.println("Adding: " + num);
        	tree.put(num, num);
            System.out.println("------------------------------");
        }
        
        
        //System.out.println((tree.treeToString(tree.root, "", false)));
        System.out.println(Arrays.toString(nums));
        System.out.println("------------------------------");

        
        /*for(int i = 0; i < size; i++) {
        	int index = rand.nextInt(size);
        	System.out.println("Removing: " + nums[index]);
        	System.out.println(tree.delete(nums[index]));
            System.out.println("------------------------------");
        }*/
        
        for(int i = 0; i < size; i++) {
        	System.out.println("Removing: " + nums[i]);
        	System.out.println(tree.delete(nums[i]));
            System.out.println("------------------------------");

        }

        /*System.out.println("Adding: 4");
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
        System.out.println("------------------------------");*/
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

    
    @SuppressWarnings("unchecked")
	public V delete(K key) {
    	if(this.root == null) return null;
    	V value = this.root.value;
    	this.root.isRed = true;
    	Object[] theValue = new Object[1];
    	this.root = findAndDelete(this.root, key, theValue);
    	if(this.root != null) {
    		this.root.isRed = false;
    		System.out.println((this.treeToString(this.root, "", false)));
    		return (V) theValue[0];
    	}
    	else {
    		System.out.println((this.treeToString(this.root, "", false)));
    		System.out.println("root");
    		return value;
    	}
    }
    
    private Node findAndDelete(Node currentNode, K key, Object[] theValue) {
    	if(currentNode == null) return null;
    	
    	System.out.println("Checking node " + currentNode.key);
    	//no children
    	if(currentNode.RChild == null && currentNode.LChild == null) {
    		if(currentNode.key.compareTo(key) == 0){
    			theValue[0] = currentNode.value;
    			return null; //This is it! remove from tree
    		}
    		else {
    			theValue[0] = null;
    			return fix(currentNode); //Not in Tree, save it as we recurse back up
    		}
    	}
    
    	//One left child (one right child never happens)
    	else if(currentNode.RChild == null && currentNode.LChild != null) {
    		//This is the Node
        	if(currentNode.key.compareTo(key) == 0){
        		theValue[0] = currentNode.value;
        		return fix(currentNode.LChild); //Return child to not have to worry about pointers (forget current node)
        	}
        	else{ //Continue
        		currentNode.LChild = findAndDelete(currentNode.LChild, key, theValue);
        	}
    	}
    	
    	//if node has 2 black children
    	else if((currentNode.LChild != null && currentNode.RChild != null) && !currentNode.LChild.isRed && !currentNode.RChild.isRed) {
    		if(currentNode.isRed) {
	    		flipColors(currentNode);
    		}

    		if(currentNode.key.compareTo(key) == 0) {
    			Random rand = new Random();
    			int num = rand.nextInt(2);
    			if(num == 0) {
    				//swap with predecessor
    				Node pred = findPredecessorNode(currentNode.key);
    				swapData(currentNode, pred);
    				//System.out.println("Swapping nodes " + currentNode.key + " and " + pred.key);
    		        //System.out.println((this.treeToString(this.root, "", false)));
    				currentNode.LChild = findAndDelete(currentNode.LChild, key, theValue);
    			}
    			else if(num == 1) {
    				//swap with successor
    				Node succ = findSuccessorNode(currentNode.key);
    				swapData(currentNode, succ);
    				//System.out.println("Swapping nodes " + currentNode.key + " and " + succ.key);
    		        //System.out.println((this.treeToString(this.root, "", false)));
    				currentNode.RChild = findAndDelete(currentNode.RChild, key, theValue);
    			}
    			else {
    				System.out.println("Random Num is wrong");
    			}
    		}
    		else {
    			//continue right
    			if(key.compareTo(currentNode.key) > 0) {
    				currentNode.RChild = findAndDelete(currentNode.RChild, key, theValue);
        		}
    			//continue left
    			else if(key.compareTo(currentNode.key) < 0) {
    				currentNode.LChild = findAndDelete(currentNode.LChild, key, theValue);
        		}	
    		}
    	}
    	
    	//If node has left red child, and right black child
    	else if((currentNode.LChild != null && currentNode.RChild != null) && currentNode.LChild.isRed && !currentNode.RChild.isRed) {
    		//need to go left
    		if(key.compareTo(currentNode.key) < 0) {
    			currentNode.LChild = findAndDelete(currentNode.LChild, key, theValue);
    		}
    		//need to go right
    		else if(key.compareTo(currentNode.key) > 0) {
    			//System.out.println("currentNode is " + currentNode.value);
    			currentNode = rotateRight(currentNode);
    			fix(currentNode); //attempt at fixing edge case
    			currentNode.RChild = findAndDelete(currentNode.RChild, key, theValue);
    		}	
    		//this is the node
    		else {
    			Random rand = new Random();
    			int num = rand.nextInt(2);
    			if(num == 0) {
    				//swap with predecessor
    				Node pred = findPredecessorNode(currentNode.key);
    				swapData(currentNode, pred);
    				//System.out.println("Swapping nodes " + currentNode.key + " and " + pred.key);
    		        //System.out.println((this.treeToString(this.root, "", false)));
    				currentNode.LChild = findAndDelete(currentNode.LChild, key, theValue);
    			}
    			else if(num == 1) {
    				//swap with successor
    				Node succ = findSuccessorNode(currentNode.key);
    				swapData(currentNode, succ);
    				//System.out.println("Swapping nodes " + currentNode.key + " and " + succ.key);
    		        //System.out.println((this.treeToString(this.root, "", false)));
    				currentNode.RChild = findAndDelete(currentNode.RChild, key, theValue);
    			}
    			else {
    				System.out.println("Random Num is wrong");
    			}
    		}
    	}
    	return fix(currentNode);
    }
    


    
    private Node fix(Node node) {
    	System.out.println("Fixing from " + node.value);
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
        if (node.LChild != null && node.RChild != null && node.RChild.isRed && node.LChild.isRed) {
            //System.out.println("Checking " + node.key + "...\nTwo red children of Black parent Fixed:");
        	flipColors(node);
            //System.out.println((this.treeToString(this.root, "", false)));
            //System.out.println("");
        }
        //Red Root
        if(this.root.isRed) {
        	this.root.isRed = false;
        }
        System.out.println(this.treeToString(this.root, "", false));
        assert(!(node.RChild != null && node.RChild.isRed));
        assert(!(node.LChild != null && node.LChild.LChild != null && node.LChild.isRed && node.LChild.LChild.isRed));
        assert(!(node.LChild != null && node.RChild != null && node.RChild.isRed && node.LChild.isRed && !node.isRed));
        assert(!(node.RChild != null && node.LChild == null));
       return node;
    }
    
    public K findSuccessor(K key) {
    	Node node = findSuccessorNode(key);
    	if(node!=null) return node.key;
    	else return null;
    }
    
    public K findPredecessor(K key) {
    	Node node = findPredecessorNode(key);
    	if(node!=null) return node.key;
    	else return null;
    }
    
    private Node findSuccessorNode(K key){
    	Node node = this.root;
    	Node pred = null;
    	//find matching node
    	while(node!=null && key.compareTo(node.key) != 0) {
    		//look right
    		if(key.compareTo(node.key) > 0) node = node.RChild;
    		//look left
    		else {
    			pred = node;
    			node = node.LChild; //look left
    		}
    	}
    	//key not in tree
    	if(node == null) return null;
    	
    	//node found, now find successor
    	//successor is in node later in tree
    	if(node.RChild != null) {
	    	node = node.RChild; //move right once
	    	while(node.LChild != null) node = node.LChild; //find leftmost child
	    	return node;
    	}
    	//successor is earlier in tree
    	else {
    		if(pred == null) return null;
    		else {
    			return pred;
    		}
    	}
    }
    
    private Node findPredecessorNode(K key) {
    	Node node = this.root;
    	Node pred = null;
    	//find matching node
    	while(node!=null && key.compareTo(node.key) != 0) {
    		//look right
    		if(key.compareTo(node.key) > 0) {
    			pred = node;
    			node = node.RChild;
    		}
    		//look left
    		else node = node.LChild;
    	}
    	//key not in tree
    	if(node == null) return null;
    	
    	//node found, now find predecessor
    	//predecessor is after node in tree
    	if(node.LChild != null) {
	    	node = node.LChild; //move left once
	    	while(node.RChild != null) node = node.RChild; //find rightmost child
	    	return node;
    	}
    	//predecessor is before node in tree
    	else {
    		if(pred == null) return null;
    		else {
    			return pred;
    		}
    	}
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
        
        if (isTail)builder.append("└── ");
        else builder.append("├── ");
        if (node.isRed) builder.append(node.key + "(R)");
        else builder.append(node.key + "");
        
        builder.append("\n");

        String childPrefix = prefix + (isTail ? "    " : "│   ");
        String leftTree = treeToString(node.LChild, childPrefix, node.RChild == null);
        String rightTree = treeToString(node.RChild, childPrefix, true);

        // Check if the right child is null and add a black branch
        if (node.LChild == null) builder.append(childPrefix + "├── \n");
        builder.append(leftTree);
        // Check if the left child is null and add a black branch
        if (node.RChild == null) builder.append(childPrefix + "└── \n");
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
