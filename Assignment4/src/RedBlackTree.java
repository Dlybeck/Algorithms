import java.util.*;

public class RedBlackTree<K extends Comparable<K>, V> {
    public static void main(String[] args) {
        RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();
        
        Random rand = new Random();
        int num;
        int[] nums = new int[15];
        for(int i = 0; i < 15; i++) {
        	num = rand.nextInt(100);
        	nums[i] = num;
        	System.out.println("Adding: " + num);
        	tree.put(num, num);
            System.out.println("------------------------------");
        }
        
        System.out.println(Arrays.toString(nums));
        System.out.println("------------------------------");

        
        for(int i = 0; i < 3; i++) {
        	int index = rand.nextInt(15);
        	System.out.println("Removing: " + nums[index]);
        	tree.delete(nums[index]);
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

    
    public V delete(K key) {
    	if(this.root == null) return null;
    	V value = this.root.value;
    	this.root.isRed = true;
    	this.root = findAndDelete(this.root, key);
    	if(this.root != null) {
    		this.root.isRed = false;
    		System.out.println((this.treeToString(this.root, "", false)));
    		return this.root.value;
    	}
    	else {
    		System.out.println((this.treeToString(this.root, "", false)));
    		return value;
    	}
    }
    
    private Node findAndDelete(Node currentNode, K key) {
    	if(currentNode == null) return null;
    	//no children
    	if(currentNode.RChild == null && currentNode.LChild == null) {
    		if(currentNode.key.compareTo(key) == 0) {
    			return null; //This is it! remove from tree
    			//How do I know what the deleted node is?
    		}
    		else {
    			return fix(currentNode); //Not in Tree, save it as we recurse back up
    		}
    	}
    
    	//One left child (one right child never happens)
    	if(currentNode.RChild == null && currentNode.LChild != null) {
    		//This is the Node
        	if(currentNode.key.compareTo(key) == 0) {
        		return currentNode.LChild; //Return child to not have to worry about pointers (forget current node)
        	}
        	else{ //Continue
        		currentNode.LChild = findAndDelete(currentNode.LChild, key);
        	}
    	}
    	
    	//if node has 2 black children
    	if(!currentNode.LChild.isRed && !currentNode.RChild.isRed) {
    		flipColors(currentNode);
    		if(currentNode.key.compareTo(key) == 0) {
    			Random rand = new Random();
    			int num = rand.nextInt(2);
    			if(num == 0) {
    				swapData(currentNode, findPredecessorNode(currentNode)); //swap with predecessor
    				currentNode.LChild = findAndDelete(currentNode.LChild, key);
    			}
    			else if(num == 1) {
    				swapData(currentNode, findSuccessorNode(currentNode)); //swap with predecessor
    				currentNode.RChild = findAndDelete(currentNode.RChild, key);
    			}
    			else {
    				System.out.println("Random Num is wrong");
    			}
    		}
    		else {
    			//continue right
    			if(key.compareTo(currentNode.key) > 0) {
    				currentNode.RChild = findAndDelete(currentNode.RChild, key);
        		}
    			//continue left
    			else if(key.compareTo(currentNode.key) < 0) {
    				currentNode.LChild = findAndDelete(currentNode.LChild, key);
        		}	
    		}
    	}
    	
    	//If node has left red child, and right black child
    	if(currentNode.LChild.isRed && !currentNode.RChild.isRed) {
    		//need to go left
    		if(key.compareTo(currentNode.key) < 0) {
    			currentNode.LChild = findAndDelete(currentNode.LChild, key);
    		}
    		//need to go right
    		else if(key.compareTo(currentNode.key) > 0) {
    			System.out.println("currentNode is " + currentNode.value);
    			currentNode = rotateRight(currentNode);
    			System.out.println("currentNode is " + currentNode.value);
    			currentNode.RChild = findAndDelete(currentNode.RChild, key);
    		}	
    		//this is the node
    		else {
    			Random rand = new Random();
    			int num = rand.nextInt(2);
    			if(num == 0) {
    				swapData(currentNode, findPredecessorNode(currentNode)); //swap with predecessor
    				currentNode.LChild = findAndDelete(currentNode.LChild, key);
    			}
    			else if(num == 1) {
    				swapData(currentNode, findSuccessorNode(currentNode)); //swap with predecessor
    				currentNode.RChild = findAndDelete(currentNode.RChild, key);
    			}
    			else {
    				System.out.println("Random Num is wrong");
    			}
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
        if (node.LChild != null && node.RChild != null && node.RChild.isRed && node.LChild.isRed && !node.isRed) {
            //System.out.println("Checking " + node.key + "...\nTwo red children of Black parent Fixed:");
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
    
    public K findSuccessor(K key){
    	Node node = this.root;
    	//find matching node
    	while(node!=null && key.compareTo(node.key) != 0) {
    		//look right
    		if(key.compareTo(node.key) > 0) node = node.RChild;
    		//look left
    		else node = node.LChild; //look left
    	}
    	//key not in tree
    	if(node == null) return null;
    	
    	//node found, now find successor
    	if(node.RChild == null) return null; //no successor
    	node = node.RChild; //move right once
    	
    	while(node.LChild != null) node = node.LChild; //find leftmost child
    	
    	return node.key;
    }
    
    public K findPredecessor(K key) {
    	Node node = this.root;
    	//find matching node
    	while(node!=null && key.compareTo(node.key) != 0) {
    		//look right
    		if(key.compareTo(node.key) > 0) node = node.RChild;
    		//look left
    		else node = node.LChild;
    	}
    	//key not in tree
    	if(node == null) return null;
    	
    	//node found, now find predecessor
    	if(node.LChild == null) return null; //no predecessor
    	node = node.LChild; //move left once
    	
    	while(node.RChild != null) node = node.RChild; //find rightmost child
    	
    	return node.key;
    }
    
    public Node findSuccessorNode(Node node){
    	//node found, now find successor
    	if(node.RChild == null) return null; //no successor
    	node = node.RChild; //move right once
    	
    	while(node.LChild != null) node = node.LChild; //find leftmost child
    	
    	return node;
    }
    
    public Node findPredecessorNode(Node node) {
    	//node found, now find predecessor
    	if(node.LChild == null) return null; //no predecessor
    	node = node.LChild; //move left once
    	
    	while(node.RChild != null) node = node.RChild; //find rightmost child
    	
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
