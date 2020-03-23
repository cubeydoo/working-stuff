import java.util.*;

/**
 * Implementation of a BST based String Set.
 * @author Tyler Rathkamp
 */
public class BSTStringSet implements StringSet, Iterable<String>, SortedStringSet {
    /** Creates a new empty set. */
    public BSTStringSet() {
        _root = null;
    }

    @Override
    public void put(String s) {
        _root = putHelper(_root, s);
    }

    public Node putHelper(Node node, String key) {
        if (node == null) {
            return new Node(key);
        } else if (node.s.compareTo(key) < 0) {
            if (node.left == null) {
                node.left = new Node(key);
            } else {
                return putHelper(node.left, key);
            }
        } else if (node.s.compareTo(key) > 0) {
            if (node.right == null) {
                node.right = new Node(key);
            } else {
                return putHelper(node.right, key);
            }
        }
    return _root;
    }

    @Override
    public boolean contains(String s) {
        return containsHelper(_root, s);
    }
    public boolean containsHelper(Node node, String key) {
        if (node == null) {
            return false;
        } else if (node.s.compareTo(key) < 0) {
            return containsHelper(node.left, key);
        } else if (node.s.compareTo(key) > 0) {
            return containsHelper(node.right, key);
        } else if (node.s.equals(key)) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public List<String> asList() {
        ArrayList<String> current = new ArrayList<String>();
        BSTIterator iterator = new BSTIterator(_root);
        while (iterator.hasNext()) {
            current.add(iterator.next());
        }
        return current;
    }


    /** Represents a single Node of the tree. */
    private static class Node {
        /** String stored in this Node. */
        private String s;
        /** Left child of this Node. */
        private Node left;
        /** Right child of this Node. */
        private Node right;

        /** Creates a Node containing SP. */
        Node(String sp) {
            s = sp;
        }
    }

    /** An iterator over BSTs. */
    private static class BSTIterator implements Iterator<String> {
        /** Stack of nodes to be delivered.  The values to be delivered
         *  are (a) the label of the top of the stack, then (b)
         *  the labels of the right child of the top of the stack inorder,
         *  then (c) the nodes in the rest of the stack (i.e., the result
         *  of recursively applying this rule to the result of popping
         *  the stack. */
        private Stack<Node> _toDo = new Stack<>();

        /** A new iterator over the labels in NODE. */
        BSTIterator(Node node) {
            addTree(node);
        }

        @Override
        public boolean hasNext() {
            return !_toDo.empty();
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node node = _toDo.pop();
            addTree(node.right);
            return node.s;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /** Add the relevant subtrees of the tree rooted at NODE. */
        private void addTree(Node node) {
            while (node != null) {
                _toDo.push(node);
                node = node.left;
            }
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new BSTIterator(_root);
    }

    @Override
    public Iterator<String> iterator(String low, String high)  {

    }


    /** Root node of the tree. */
    private Node _root;
}
