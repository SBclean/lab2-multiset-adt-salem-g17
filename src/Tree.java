import java.util.ArrayList;
import java.util.Objects;

public class Tree {
    // The Integer root and ArrayList<Tree> subtrees are private instance variables of this class
    private Integer root;
    private ArrayList<Tree> subtrees;

    public Tree() {
        this.root = null;
        this.subtrees = new ArrayList<>();
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
            if (this.root.equals(item)) {
                count += 1;
            }
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

    /**
     * Get the average of all values in the tree
     *
     * Precondition: This is a tree of numbers
     * @return Average value of all items in the tree, given tree is nonempty,
     * If it is empty, return 0
     */
    public double average() {
        if (this.isEmpty()) {
            return 0;
        }
        else {
            double[] result = averageHelper();
            return result[0] / result[1];
        }
    }

    /**
     * Helper function for average() function
     * @return a list of two doubles, result[0] contains the sum of all values in the tree
     * result[1] contains the size of the tree
     */
    private double[] averageHelper() {
        if (this.isEmpty()) {
            return new double[]{0, 0};
        }
        else {
            double[] result = new double[2];
            result[0] = this.root;
            result[1] = 1;
            for (Tree tree : this.subtrees) {
                double[] subtreeInfo = tree.averageHelper();
                result[0] += subtreeInfo[0];
                result[1] += subtreeInfo[1];
            }
            return result;
        }
    }

    /**
     * Return whether this tree is equal to the other tree
     * @param other   the reference object with which to compare.
     * @return boolean value that determines tree equality or inequality
     */
    @Override
    public boolean equals(Object other)
    {
        if  (!(other instanceof Tree)) {
            return false;
        }

        Tree tree = (Tree) other;

        if (this.isEmpty() && tree.isEmpty()) {
            return true;
        }
        else if (this.isEmpty() || tree.isEmpty()) {
            return false;
        }
        else {
            if (!this.root.equals(tree.root)) {
                return false;
            }

            if (this.subtrees.size() != tree.subtrees.size()) {
                return false;
            }

            return this.subtrees.equals(tree.subtrees);
        }
    }

    /**
     * Hash the following tree structure
     * @return If tree is empty, return 0, if tree has no subtrees, return the root value, otherwise use
     * recursive hashing of the root and subtrees object
     */
    @Override
    public int hashCode() {
        if (this.isEmpty()) {
            return 0;
        }
        else if (this.subtrees.isEmpty()) {
            return this.root;
        }
        else {
            return Objects.hash(this.root, this.subtrees);
        }
    }

    /**
     * Find if the tree contains "item"
     *
     * @param item The item to look for in the tree
     * @return Boolean value determining the contaiment of item in the tree, true if found, false if not found
     */
    public boolean contains(int item) {
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

    /**
     * Generate the list of all leaves in the tree
     * @return An arraylist of the leaves (lowermost nodes) in the tree
     */
    public ArrayList<Integer> leaves() {
        if (this.isEmpty()) {
            return new ArrayList<>();
        }
        else if (this.subtrees.isEmpty()) {
            ArrayList<Integer> leaves = new ArrayList<>();
            leaves.add(this.root);
            return leaves;
        }
        else {
            ArrayList<Integer> leaves = new ArrayList<>();
            for (Tree tree : this.subtrees) {
                leaves.addAll(tree.leaves());
            }
            return leaves;
        }
    }

    /**
     * Deletes a single occurence of item within the tree
     * If item is not present in the tree, do not do anything and return false
     *
     * @param item The candidate item to be deleted from the tree
     * @return A boolean to check whether the item was successfully deleted from the tree or not
     */
    public boolean delete(Integer item) {
        if (this.isEmpty()) {
            return false;
        }
        else if (this.root.equals(item)) {
            this.deleteRoot();
            return true;
        }
        else {
            for  (Tree tree : this.subtrees) {
                boolean deleted = tree.delete(item);
                if (deleted && tree.isEmpty()) {
                    this.subtrees.remove(tree);
                    return true;
                }
                else if (deleted) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Delete the root from the tree
     * Precondition: the tree must have at least one subtree
     */
    private void deleteRoot() {
        if (this.subtrees.isEmpty()) {
            this.root = null;
        }
        else {
            Tree chosenSubtree = this.subtrees.remove(this.subtrees.size() - 1);
            this.subtrees.remove(chosenSubtree);

            this.root =  chosenSubtree.root;
            this.subtrees.addAll(chosenSubtree.subtrees);
        }
    }

    /**
     * Insert item using the following algorithm <br><br>
     * 1. If the tree is empty, "item" is the new root of the tree. <br><br>
     * 2. If the tree has a root but no subtrees, create a
     * new tree containing the item, and make this new tree a subtree
     * of the original tree. <br><br>
     * 3. Otherwise, pick a random number between 1 and 3 inclusive.
     *  - If the random number is 3, create a new tree containing
     *    the item, and make this new tree a subtree of the original.
     *  - If the random number is a 1 or 2, pick one of the existing
     *    subtrees at random, and *recursively insert* the new item
     *    into that subtree. <br><br>
     * @param item The item to be inserted into the tree using the following algorithm
     */
    public void insert(Integer item) {
        if (this.isEmpty()) {
            this.root = item;
        }
        else if (this.subtrees.isEmpty()) {
            this.subtrees = new ArrayList<> ();
            this.subtrees.add(new Tree(item));
        }
        else {
            if ((int)(Math.random() * 3) + 1 == 3) {
                this.subtrees.add(new Tree(item));
            }
            else {
                int subtreeIndex = (int) (Math.random() *  this.subtrees.size());
                this.subtrees.get(subtreeIndex).insert(item);
            }
        }
    }

    /**
     * Insert "item" node into the tree as a child of the "parent" node
     * If parent occurs more than once in the tree, this inserts only one item at one parent
     *
     * @param item The item to be inserted as a child of the parent
     * @param parent The parent to be inserted
     * @return True if insertion was successful, false if parent not present in the tree
     */
    public boolean insertChild(int item, int parent) {
        if (this.isEmpty()) {
            return false;
        }
        else if (this.root == parent) {
            this.subtrees.add(new Tree(item));
            return true;
        }
        else {
            for (Tree tree : this.subtrees) {
                if (tree.insertChild(item, parent)) {
                    return true;
                }
            }
            return false;
        }
    }
}
