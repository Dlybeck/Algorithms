import java.util.*;

public class RedBlackTree<K extends Comparable<K>, V> {
    public static void main(String[] args) {
        RedBlackTree<Integer, String> tree = new RedBlackTree<>();

        System.out.println("Adding: 4");
        tree.put(4, "d");
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

        System.out.println("Adding: 1");
        tree.put(1, "a");
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
        this.printRedBlackTree(this.root, "", false);
    }

    private Node findAndAdd(Node currentNode, Node newNode) {
    	//look to left
    	if(currentNode.key.compareTo(newNode.key) < 0) {
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
    	else if(currentNode.key.compareTo(newNode.key) > 0) {
    		//place node
    		if(currentNode.RChild == null) {
    			currentNode.RChild = newNode;
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
        //System.out.println("Node is " + node.key);
        if (node.RChild != null && node.RChild.isRed && !node.isRed) {
            System.out.println("Checking " + node.key + "...\nBlack Parent with red right child fixed:");
            node = rotateLeft(node);
            printRedBlackTree(this.root, "", true);
            System.out.println("");
        }
        if (node.LChild != null && node.LChild.LChild != null && node.LChild.isRed && node.LChild.LChild.isRed) {
            System.out.println("Checking " + node.key + "...\nTwo red nodes in a row Fixed:");
        	node = rotateRight(node);
            printRedBlackTree(this.root, "", true);
            System.out.println("");
        }
        if (node.LChild != null && node.RChild != null && node.RChild.isRed && node.LChild.isRed && !node.isRed) {
            System.out.println("Checking " + node.key + "...\nTwo red children of Black parent Fixed:");
        	flipColors(node);
            printRedBlackTree(this.root, "", true);
            System.out.println("");
        }
        if(this.root.isRed) {
        	this.root.isRed = false;
        }
        
        return node;
    }

    private Node rotateLeft(Node parent) {
        Node child = parent.RChild;
        parent.RChild = child.LChild;
        child.LChild = parent;
        child.isRed = parent.isRed;
        parent.isRed = true;
        if(parent == this.root) {
        	this.root = child;
        	this.root.isRed = false;
        }
        return child;
    }

    private Node rotateRight(Node parent) {
        Node child = parent.LChild;
        parent.LChild = child.RChild;
        child.RChild = parent;
        child.isRed = parent.isRed;
        parent.isRed = true;
        if(parent == this.root) {
        	this.root = child;
        	this.root.isRed = false;
        }
        return child;
    }

    private void flipColors(Node node) {
        node.isRed = !node.isRed;
        node.LChild.isRed = !node.LChild.isRed;
        node.RChild.isRed = !node.RChild.isRed;
    }

    private void printRedBlackTree(Node node, String prefix, boolean isTail) {
        if (node != null) {
            String childLabel = "";
            String childPrefix = isTail ? "    " : "│   ";
            String nodeType = isTail ? "└── " : "├── ";
            if (node.LChild != null) {
                childLabel = "L";
            } else if (node.RChild != null) {
                childLabel = "R";
            }

            String relations = "";

            System.out.println(prefix + nodeType + node.key + childLabel + " (" + (node.isRed ? "Red" : "Black") + ") " + relations);
            printRedBlackTree(node.LChild, prefix + childPrefix, false);
            printRedBlackTree(node.RChild, prefix + childPrefix, true);
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
