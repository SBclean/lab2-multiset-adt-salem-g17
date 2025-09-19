import java.util.ArrayList;

public class Tree {
    // The Integer root and ArrayList<Tree> subtrees are private instance variables of this class
    private Integer root;
    private ArrayList<Tree> subtrees;

    public Tree() {
        this.root = null;
    }

    public Tree(Integer root) {
        this.root = root;
        this.subtrees = new ArrayList<>();
    }

    /**
     * Check if the tree is empty
     *
     * @return True if root is a numerical value, false if root is null
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    /**
     * @return Size of the tree in terms of how many items (nodes) are in the tree
     */
    public Integer size() {
        if (this.isEmpty()) {
            return 0;
        }
        else {
            Integer count = 1;
            for (Tree tree : this.subtrees) {
                count += tree.size();
            }
            return count;
        }
    }

    /**
     * Count the instances of a number in the tree
     *
     * @param item The item to find in the tree
     * @return The number of occurences of item in the tree
     */
    public Integer count(Integer item) {
        if (this.isEmpty()) {
            return 0;
        }
        else {
            Integer count = 0;
            for (Tree tree : this.subtrees) {
                count += tree.count(item);
            }
            return count;
        }
    }

    /**
     * @return String representation of the tree, if empty
     * just print Tree() to denote empty tree
     */
    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "Tree()";
        }
        else {
            return this.strIndented();
        }
    }

    /**
     * Give a nice visual of the tree object
     * Private since only used as a helper function for toString()
     *
     * @param depth The depth of the tree used as a horizontal factor for our visual
     * @return A string showing the hierarchical design of the tree
     */
    private String strIndented(Integer depth) {
        StringBuilder s =  new StringBuilder();
        s.append(' ' * depth).append(this.root.toString()).append('\n');
        for (Tree tree : this.subtrees) {
            s.append(tree.strIndented(depth + 1));
        }
        return s.toString();
    }

    /**
     * @return Same as str_indented, but at the depth of 0, so starting at root
     */
    private String strIndented() {
        return this.strIndented(0);
    }

    /**
     * Check whether the item is in the tree
     *
     * @param item Item to be checked whether it is in the tree or not
     * @return True if item is in the tree, false otherwise
     */
    public boolean contains(Integer item) {
        if (this.isEmpty()) {
            return false;
        }

        if (this.root.equals(item)) {
            return true;
        }
        else {
            for (Tree tree : this.subtrees) {
                if (tree.contains(item)) {
                    return true;
                }
            }
            return false;
        }
    }
}
