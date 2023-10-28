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
        tree.printRedBlackTree(tree.root, "", false);
        System.out.println("------------------------------");
    }

    private Node root;

    public RedBlackTree() {
        this.root = null;
    }

    public void put(K key, V value) {
        if (root == null) {
            Node newNode = new Node(key, value);
            root = newNode;
            root.isRed = false;
            printRedBlackTree(this.root, "", true);
        } else {
            Node newNode = findAndAdd(root, key, value);
            if (newNode != null && newNode.isRed) {
                newNode.isRed = false;
                System.out.println("Root color switched");
                printRedBlackTree(this.root, "", true);
            }
        }
    }

    private Node findAndAdd(Node currentNode, K key, V value) {
    	 Node newNode;

	    //continue down tree to the left
	    if (key.compareTo(currentNode.key) < 0) {
	        if (currentNode.LChild != null) {
	            newNode = findAndAdd(currentNode.LChild, key, value);
	        } else {
	            newNode = new Node(key, value);
	            currentNode.LChild = newNode;
	            fix(newNode); // Call fix on the new node
	        }
	    }
	    //continue down tree to the right
	    else if (key.compareTo(currentNode.key) > 0) {
	        if (currentNode.RChild != null) {
	            newNode = findAndAdd(currentNode.RChild, key, value);
	        } else {
	            newNode = new Node(key, value);
	            currentNode.RChild = newNode;
	            fix(newNode); // Call fix on the new node
	        }
	    }
	    //replace value and continue up the tree
	    else {
	        currentNode.value = value;
	        newNode = currentNode;
	    }

	    System.out.println("currentNode: " + currentNode.key);
	    fix(currentNode);
	    return newNode;
    }

    private void fix(Node node) {
        //System.out.println("Node is " + node.key);
        if (node.RChild != null && node.RChild.isRed && !node.isRed) {
            node = rotateLeft(node);
            System.out.println("Checking " + node.key + "...\nBlack Parent with red right child fixed:");
            printRedBlackTree(this.root, "", true);
        }
        if (node.LChild != null && node.LChild.LChild != null && node.LChild.isRed && node.LChild.LChild.isRed) {
            node = rotateRight(node);
            System.out.println("Checking " + node.key + "...\nTwo red nodes in a row Fixed:");
            printRedBlackTree(this.root, "", true);
        }
        if (node.LChild != null && node.RChild != null && node.RChild.isRed && node.LChild.isRed && !node.isRed) {
            flipColors(node);
            System.out.println("Checking " + node.key + "...\nTwo red children of Black parent Fixed:");
            printRedBlackTree(this.root, "", true);
        }
    }

    private Node rotateLeft(Node parent) {
        Node child = parent.RChild;
        parent.RChild = child.LChild;
        child.LChild = parent;
        child.isRed = parent.isRed;
        parent.isRed = true;
        return child;
    }

    private Node rotateRight(Node parent) {
        Node child = parent.LChild;
        parent.LChild = child.RChild;
        child.RChild = parent;
        child.isRed = parent.isRed;
        parent.isRed = true;
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
