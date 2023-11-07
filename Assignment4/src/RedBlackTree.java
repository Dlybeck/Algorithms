import java.util.*;

public class RedBlackTree<K extends Comparable<K>, V extends Comparable<V>> {
    public static void main(String[] args) {
        RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();
        Random rand = new Random();
        int num;
        int size = 10;
        int[] nums = new int[size];
        for(int i = 0; i < size; i++) {
        	num = rand.nextInt(100);
        	nums[i] = num;
        	System.out.println("Adding: " + num);
        	tree.put(num, num);
        	System.out.println(tree.toString());
            System.out.println("------------------------------");
        }
        
        
        //System.out.println((tree.treeToString(tree.root, "", false)));
        System.out.println(Arrays.toString(nums));
        System.out.println("------------------------------");
        
        System.out.println(tree.toString());

        
        //for(int i = 0; i < size; i++) {
        //	int index = rand.nextInt(size);
        //	System.out.println("Removing: " + nums[index]);
        //	System.out.println(tree.delete(nums[index]));
          //  System.out.println("------------------------------");
        //}
        
        for(int i = 0; i < size; i++) {
        	//System.out.println("Removing: " + nums[i]);
        	tree.delete(nums[i]);
            //System.out.println("------------------------------");
        }
        System.out.println("All Removed:");
        System.out.println(tree.toString());
        

        /*System.out.println("Adding: 13");
        tree.put(13, 13);
        System.out.println("------------------------------");
        
        System.out.println("Adding: 93");
        tree.put(93, 93);
        System.out.println("------------------------------");

        System.out.println("Adding: 14");
        tree.put(14, 14);
        System.out.println("------------------------------");

        System.out.println("Adding: 93");
        tree.put(93, 93);
        System.out.println("------------------------------");

        System.out.println("Adding: 99");
        tree.put(99, 99);
        System.out.println("------------------------------");
        
        System.out.println("Adding: 72");
        tree.put(72, 72);
        System.out.println("------------------------------");

        System.out.println("Adding: 95");
        tree.put(95, 95);
        System.out.println("------------------------------");
        
        System.out.println("Adding: 28");
        tree.put(28, 28);
        System.out.println("------------------------------");

        System.out.println("Adding: 74");
        tree.put(74, 74);
        System.out.println("------------------------------");
        
        System.out.println("Adding: 8");
        tree.put(8, 8);
        System.out.println("------------------------------\n");
        System.out.println("------------------------------\n");
        System.out.println("------------------------------");        
        
        
        
        System.out.println("Removing: 13");
    	System.out.println(tree.delete(13));
        System.out.println("------------------------------");
        
        System.out.println("Removing: 93");
    	System.out.println(tree.delete(93));
        System.out.println("------------------------------");
        
        System.out.println("Removing: 14");
    	System.out.println(tree.delete(14));
        System.out.println("------------------------------");
        
        System.out.println("Removing: 93");
    	System.out.println(tree.delete(93));
        System.out.println("------------------------------");
        
        System.out.println("Removing: 99");
    	System.out.println(tree.delete(99));
        System.out.println("------------------------------");
        
        System.out.println("Removing: 72");
    	System.out.println(tree.delete(72));
        System.out.println("------------------------------");
        
        System.out.println("Removing: 95");
    	System.out.println(tree.delete(95));
        System.out.println("------------------------------");
        
        System.out.println("Removing: 28");
    	System.out.println(tree.delete(28));
        System.out.println("------------------------------");
        
        System.out.println("Removing: 74");
    	System.out.println(tree.delete(74));
        System.out.println("------------------------------");
        
        System.out.println("Removing: 8");
    	System.out.println(tree.delete(8));
        System.out.println("------------------------------");*/
    }

    private Node root;

    public RedBlackTree() {
        this.root = null;
    }

    public void put(K key, V value) {
    	Node newNode = new Node(key, value);
    	this.root = findAndAdd(this.root, newNode);


        this.root.isRed = false;
        //System.out.println((this.treeToString(this.root, "", false)));
    }
    
    private Node findAndAdd(Node currentNode, Node newNode) {
    	if(currentNode == null) {
    		newNode.size = 1;
    		return currentNode = newNode;
    	}
    	
    	//System.out.println("Checking " + currentNode.key);
    	
    	//look to left
    	if(currentNode.key.compareTo(newNode.key) > 0) {
    		currentNode.LChild = findAndAdd(currentNode.LChild, newNode);
    	}
    	//look to right
    	else if(currentNode.key.compareTo(newNode.key) < 0) {
    		currentNode.RChild = findAndAdd(currentNode.RChild, newNode);
    	}
    	//replace value
    	else if(currentNode.key.compareTo(newNode.key) == 0) {
    		currentNode.value = newNode.value;
    	}
    	
    	currentNode.size = 1 + size(currentNode.LChild) + size(currentNode.RChild);
    	return fix(currentNode);
    }

    
    @SuppressWarnings("unchecked")
	public V delete(K key) {
    	if(this.root == null) return null;
    	V root = this.root.value;
    	this.root.isRed = true;
    	Object[] theValue = new Object[1];
    	this.root = findAndDelete(this.root, key, theValue);
    	if(this.root != null) {
    		fix(this.root);
    		this.root.isRed = false;
    		//System.out.println((this.treeToString(this.root, "", false)));
    		return (V) theValue[0];
    	}
    	else {
    		//System.out.println((this.treeToString(this.root, "", false)));
    		//System.out.println("root");
    		return root;
    	}
    }
    
    @SuppressWarnings("unused")
	private Node findAndDelete(Node currentNode, K key, Object[] theValue) {
    	//System.out.println("Checking " + currentNode.key);

    	if(currentNode == null) return fix(currentNode);
    	
    	//no children
    	if(currentNode.RChild == null && currentNode.LChild == null) {
    		//System.out.println("No Children");
    		if(currentNode.key.compareTo(key) == 0){
    			theValue[0] = currentNode.value;
    			assert(isRed(currentNode));
    			fix(currentNode);
    			return null; //This is it! remove from tree
    		}
    		else {
    			theValue[0] = null;
    			return fix(currentNode); //Not in Tree, save it as we recurse back up
    		}
    	}
    
    	//One left child (one right child never happens)
    	else if(currentNode.LChild != null && currentNode.RChild == null) {
    		//System.out.println("One left child");
    		//This is the Node
        	if(currentNode.key.compareTo(key) == 0){
        		//System.out.println("Reutrning Child");
        		assert(isRed(currentNode));
        		theValue[0] = currentNode.value;
        		return currentNode.LChild; //Return child to not have to worry about pointers (forget current node)
        	}
        	else{ //Continue
        		currentNode.LChild = findAndDelete(currentNode.LChild, key, theValue);
        	}
    	}
    	
    	//edge case
    	//else if(node)
    	
    	//if node has 2 black children
    	else if(!isRed(currentNode.LChild) && !isRed(currentNode.RChild)){
    		//System.out.println("Two Black Children");
    		if(isRed(currentNode)) {
    			//System.out.println((this.treeToString(this.root, "", false)));
	    		flipColors(currentNode);
	    		//System.out.println("Flipped Colors since " + currentNode.key + " is red");
	    		//System.out.println((this.treeToString(this.root, "", false)));
    		}

    		if(currentNode.key.compareTo(key) == 0) {
    			Random rand = new Random();
    			int num = rand.nextInt(2);
    			Node succ = findSuccessorNode(currentNode.key);
    			if(num == 0 || !isRed(succ)) {
    				//swap with predecessor
    				Node pred = findPredecessorNode(currentNode.key);
    				swapData(currentNode, pred);
    				//System.out.println("Swapping " + currentNode.key + " with " + pred.key);
    				//System.out.println((this.treeToString(this.root, "", false)));
    				currentNode.LChild = findAndDelete(currentNode.LChild, key, theValue);
    			}
    			else if(num == 1) {
    				//swap with successor
    				//Node succ = findSuccessorNode(currentNode.key);
    				swapData(currentNode, succ);
    				//System.out.println("Swapping " + currentNode.key + " with " + succ.key);
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
    	else if(isRed(currentNode.LChild) && !isRed(currentNode.RChild)) {
    		//System.out.println("Left Red Right Black");
    		//need to go left
    		if(key.compareTo(currentNode.key) < 0) {
    			currentNode.LChild = findAndDelete(currentNode.LChild, key, theValue);
    		}
    		//need to go right
    		else if(key.compareTo(currentNode.key) > 0) {
    			//System.out.print("Rotating parent " + currentNode.key  + " to ");
    			currentNode = rotateRight(currentNode);
    			//System.out.println(currentNode.key);
    			//System.out.println((this.treeToString(this.root, "", false)));
    			currentNode.RChild = findAndDelete(currentNode.RChild, key, theValue);
    		}	
    		//this is the node
    		else {
    			Random rand = new Random();
    			int num = rand.nextInt(2);
				Node succ = findSuccessorNode(currentNode.key);
    			if(num == 0 || !isRed(succ)) {
    				//swap with predecessor
    				Node pred = findPredecessorNode(currentNode.key);
    				swapData(currentNode, pred);
    				//System.out.println("Swapping " + currentNode.key + " with " + pred.key);
    				//System.out.println((this.treeToString(this.root, "", false)));
    				currentNode.LChild = findAndDelete(currentNode.LChild, key, theValue);
    			}
    			else if(num == 1) {
    				//swap with successor
    				swapData(currentNode, succ);
    				//System.out.println("Swapping " + currentNode.key + " with " + succ.key);
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
    	if(node == null) return null;
    	//System.out.println("Fixing from " + node.value);
    	//Two red Children
        if (isRed(node.RChild) && isRed(node.LChild)) {
            //System.out.println("Fixing " + node.key + "...\nTwo red children of Black parent Fixed:");
        	flipColors(node);
            //System.out.println((this.treeToString(this.root, "", false)));
            //System.out.println("");
        }
        
      //left Red Child With right Red Child
        if(isRed(node.LChild) && isRed(node.LChild.RChild)) {
        	node.LChild = rotateLeft(node.LChild);
        	node = rotateRight(node);
        }
        
      //Right Red Child With left Red Child
        if(isRed(node.RChild) && isRed(node.RChild.LChild)) {
        	node.RChild = rotateRight(node.RChild);
        	node = rotateLeft(node);
        }
        
    	//Red Right Child?
        if (isRed(node.RChild)) {
            //System.out.println("Fixing " + node.key + "...\nRed right child fixed:");
            node = rotateLeft(node);
            //System.out.println((this.treeToString(this.root, "", false)));
            //System.out.println("");
        }
        //Two red nodes in a row?
        //System.out.println("Checking for double red at " + node.key);
        if (isRed(node.LChild) && isRed(node.LChild.LChild)) {
            //System.out.println("Fixing " + node.key + "...\nTwo red nodes in a row Fixed:");
        	node = rotateRight(node);
            //System.out.println((this.treeToString(this.root, "", false)));
            //System.out.println("");
        }
        //Two red Children
        if (isRed(node.RChild) && isRed(node.LChild)) {
            //System.out.println("Fixing " + node.key + "...\nTwo red children of Black parent Fixed:");
        	flipColors(node);
            //System.out.println((this.treeToString(this.root, "", false)));
            //System.out.println("");
        }
        
        if(isRed(this.root)) {
        	this.root.isRed = false;
        }

        
       return node;
    }
    
    public boolean containsKey(K key) {
    	Node node = this.root;
    	while(true) {
    		if(node == null) return false; //there is no node
    		if(node.key.compareTo(key) == 0) return true; //this is the node
    		
    		if(key.compareTo(node.key) < 0) node = node.LChild; //go left
    		else node = node.RChild; //go right
    	}
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
    	System.out.println("Parent size  was " + parent.size);
        Node child = parent.RChild;
    	System.out.println("Child size was" + child.size);
        
    	swapData(parent, child);
    	
    	parent.RChild = child.RChild;
    	
    	child.RChild = child.LChild;
    	child.LChild = parent.LChild;
    	parent.LChild = child;
    	
    	System.out.println("parent size is " + parent.size);
    	System.out.println("child size is " + child.size);
    	return parent;
    }

    private Node rotateRight(Node parent) {
    	System.out.println("Parent is " + parent.key);
        Node child = parent.LChild;
    	System.out.println("Child is " + child.key); 
        
    	swapData(parent, child);
    	
    	parent.LChild = child.LChild;
    	
    	child.LChild = child.RChild;
    	child.RChild = parent.RChild;
    	parent.RChild = child;
    	
    	System.out.println("parent size is " + parent.size);
    	System.out.println("child size is " + child.size);
    	
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
    	if(isRed(node.RChild) == isRed(node.LChild) && isRed(node) != isRed(node.LChild)) {
	        node.isRed = !node.isRed;
	        node.LChild.isRed = !node.LChild.isRed;
	        node.RChild.isRed = !node.RChild.isRed;
    	}
    }
    
    
    public boolean containsValue(V value) {
        return containsValue(this.root, value);
    }

    private boolean containsValue(Node node, V value) {
        if (node == null) return false; //not in tree

        if (node.value.equals(value)) return true; //in tree

        //check left and right trees
        return containsValue(node.LChild, value) || containsValue(node.RChild, value);
    }
    
    public boolean isEmpty() {
    	return this.root == null;
    }
    
    public int size() {
    	
    }
    
    public K reverseLookup(V value) {
        return reverseLookup(root, value);
    }

    private K reverseLookup(Node node, V value) {
        if (node == null) return null; // Not found

        if (node.value.equals(value)) return node.key; // Found a node with the given value

        K leftResult = reverseLookup(node.LChild, value);
        if (leftResult != null) return leftResult; // Found in the left subtree

        K rightResult = reverseLookup(node.RChild, value);
        if (rightResult != null) return rightResult; // Found in the right subtree

        return null; // Not found in the current subtree
    }
    
    public K findFirstKey() {
    	if(this.root == null) return null;
    	
    	Node node = this.root;
    	
    	while(node.LChild != null) node = node.LChild;
    	
    	return node.key;
    }
    
    public K findLastKey() {
    	if(this.root == null) return null;
    	
    	Node node = this.root;
    	
    	while(node.RChild != null) node = node.RChild;
    	
    	return node.key;
    }
    
    public K getRootKey() {
    	if(this.root == null) return null;
    	return this.root.key;
    }
    
    public int findRank(K key) {
    	
    }
    
    public K select (int rank) {
    	
    }
    
    public int countRedNodes() {
    	if(this.root == null) return 0;
    	return countRedNodes(this.root, 0);
    }
    
    private int countRedNodes(Node node, int size) {
        if (isRed(node)) size++; //add to size because red
        
        if (node.LChild == null) return size; // Reds from left subtree
        size = countRedNodes(node.LChild, size);

        if (node.RChild == null) return size; // Reds from right subtree
        size = countRedNodes(node.RChild, size);

        return size; // Not found in the current subtree
    }
    
    public int calcHeight() {
    	if(this.root == null) return 0;
    	
    	return calcHeight(this.root, 0, 0);
    }
    
    public int calcHeight(Node node, int height, int maxHeight) {
    	height += 1;
    	if(node != null && height > maxHeight) {
    		maxHeight = height;
    	}
    	
    	if (node.LChild == null) return maxHeight; // Reds from left subtree
        maxHeight = calcHeight(node.LChild, height, maxHeight);

        if (node.RChild == null) return maxHeight; // Reds from right subtree
        maxHeight = calcHeight(node.RChild, height, maxHeight);
        
        return maxHeight;
    }
    
    public int calcBlackHeight() {
    	if(this.root == null) return 0;
    	
    	return calcBlackHeight(this.root, 0, 0);
    }
    
    private int calcBlackHeight(Node node, int blackHeight, int maxBlack) {
    	//is Red and not null
    	if(node != null && !isRed(node)) blackHeight ++;
    	if(blackHeight > maxBlack) maxBlack = blackHeight;
    	
    	if (node.LChild == null) return maxBlack; // Reds from left subtree
        maxBlack = calcBlackHeight(node.LChild, blackHeight, maxBlack);

        if (node.RChild == null) return maxBlack; // Reds from right subtree
        maxBlack = calcBlackHeight(node.RChild, blackHeight, maxBlack);
        
        return maxBlack;
    }
    
    public double calcAverageDepth() {
    	if(this.root == null) return Double.NaN;
    	
    	return calcAverageDepth(this.root, -1, 0);
    }
    
    private double calcAverageDepth(Node node, int depth, double avgDepth) {
    	//NEED SIZE
    }
    
    
    private int size(Node node) {
        return (node != null) ? node.size : 0;
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
        if (node.isRed) builder.append(node.key + "(R) " + node.size);
        else builder.append(node.key + " " + node.size);
        
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
    
    private Boolean isRed (Node node) {
    	if(node == null) {
    		return false;
    	}
    	else {
    		return node.isRed;
    	}
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
