import java.util.*;

public class RedBlackTree<K extends Comparable<K>, V> {
    public static void main(String[] args) {
        RedBlackTree<Integer, String> tree = new RedBlackTree<>();

        System.out.println("Adding: 4");
        tree.put(4, "d");
        System.out.println("------------------------------");
        
        System.out.println("Adding: 8");
        tree.put(8, "h");
        System.out.println("------------------------------");

        System.out.println("Adding: 2");
        tree.put(2, "b");
        System.out.println("------------------------------");

        System.out.println("Adding: 3");
        tree.put(3, "c");
        System.out.println("------------------------------");

        System.out.println("Adding: 5");
        tree.put(5, "e");
        System.out.println("------------------------------");
        
        System.out.println("Adding: 9");
        tree.put(9, "i");
        System.out.println("------------------------------");

        System.out.println("Adding: 1");
        tree.put(1, "a");
        System.out.println("------------------------------");
        
        System.out.println("Adding: 7");
        tree.put(7, "g");
        System.out.println("------------------------------");

        System.out.println("Adding: 6");
        tree.put(6, "f");
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
    	        System.out.println((this.treeToString(this.root, "", false)));
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

    private Node fix(Node node) {
    	//Red Right Child?
        if (node.RChild != null && node.RChild.isRed) {
            System.out.println("Checking " + node.key + "...\nBlack Parent with red right child fixed:");
            node = rotateLeft(node);
            System.out.println((this.treeToString(this.root, "", false)));
            System.out.println("");
        }
        //Two red nodes in a row?
        System.out.println("Checking for double red at " + node.key);
        if (node.LChild != null && node.LChild.LChild != null && node.LChild.isRed && node.LChild.LChild.isRed) {
            System.out.println("Checking " + node.key + "...\nTwo red nodes in a row Fixed:");
        	node = rotateRight(node);
            System.out.println((this.treeToString(this.root, "", false)));
            System.out.println("");
        }
        //Two red Children
        if (node.LChild != null && node.RChild != null && node.RChild.isRed && node.LChild.isRed && !node.isRed) {
            System.out.println("Checking " + node.key + "...\nTwo red children of Black parent Fixed:");
        	flipColors(node);
            System.out.println((this.treeToString(this.root, "", false)));
            System.out.println("");
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
    
    private void swapChildren(Node parent) {
    	Node temp = parent.RChild;
    	
    	parent.RChild = parent.LChild;
    	parent.LChild = temp;
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
