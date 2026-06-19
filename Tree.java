import java.util.*;

public class Tree <T extends Comparable<T>> {

    private TreeNode<T> root;

    public Tree() {
        this.root = null;
    }

    public void add(T value) {
        if (this.root == null) {
            this.root = new TreeNode<>(value);
        } else {
            add(this.root, value);
        }
    }

    private void add(TreeNode<T> actual, T value) {

        int cmp = value.compareTo(actual.getValue());

        if (cmp < 0) {

            if (actual.getLeft() == null) {
                actual.setLeft(new TreeNode<>(value));
            } else {
                add(actual.getLeft(), value);
            }

        } else if (cmp > 0) {

            if (actual.getRight() == null) {
                actual.setRight(new TreeNode<>(value));
            } else {
                add(actual.getRight(), value);
            }
        }
    }

    public T getRoot() {
        return root.getValue();
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean hasElem(T value) {
        return hasElem(root, value);
    }

    private boolean hasElem(TreeNode<T> node, T value) {

        if (node == null) {
            return false;
        }

        int cmp = value.compareTo(node.getValue());

        if (cmp == 0) {
            return true;
        }

        if (cmp < 0) {
            return hasElem(node.getLeft(), value);
        }

        return hasElem(node.getRight(), value);
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(TreeNode<T> node) {

        if (node == null) {
            return 0;
        }

        int alturaIzq = getHeight(node.getLeft());
        int alturaDer = getHeight(node.getRight());

        return Math.max(alturaIzq, alturaDer) + 1;
    }

    public void printPosOrder() {
        printPosOrder(root);
    }

    private void printPosOrder(TreeNode<T> current) {

        if (current == null) {
            return;
        }

        printPosOrder(current.getLeft());
        printPosOrder(current.getRight());

        System.out.println(current.getValue());
    }

    public void printPreOrder() {
        printPreOrder(root);
    }

    private void printPreOrder(TreeNode<T> current) {

        if (current == null) {
            return;
        }

        System.out.println(current.getValue());

        printPreOrder(current.getLeft());
        printPreOrder(current.getRight());
    }

    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(TreeNode<T> current) {

        if (current == null) {
            return;
        }

        printInOrder(current.getLeft());

        System.out.println(current.getValue());

        printInOrder(current.getRight());
    }

    public ArrayList<T> getLongestBranch() {
        return getLongestBranch(root);
    }

    private ArrayList<T> getLongestBranch(TreeNode<T> current) {

        ArrayList<T> result = new ArrayList<>();

        if (current == null) {
            return result;
        }

        if (current.getLeft() == null && current.getRight() == null) {
            result.add(current.getValue());
            return result;
        }

        ArrayList<T> left = getLongestBranch(current.getLeft());
        ArrayList<T> right = getLongestBranch(current.getRight());

        if (left.size() >= right.size()) {
            left.add(0, current.getValue());
            return left;
        }

        right.add(0, current.getValue());
        return right;
    }

    public ArrayList<T> getFrontera() {

        ArrayList<T> result = new ArrayList<>();

        getFrontera(root, result);

        return result;
    }

    private void getFrontera(TreeNode<T> current, ArrayList<T> result) {

        if (current == null) {
            return;
        }

        if (current.getLeft() == null && current.getRight() == null) {
            result.add(current.getValue());
            return;
        }

        getFrontera(current.getLeft(), result);
        getFrontera(current.getRight(), result);
    }

    public T getMaxElem() {

        if (root == null) {
            return null;
        }

        return getMaxElem(root);
    }

    private T getMaxElem(TreeNode<T> current) {

        if (current.getRight() == null) {
            return current.getValue();
        }

        return getMaxElem(current.getRight());
    }

    public ArrayList<T> getElemAtLevel(int level) {

        ArrayList<T> result = new ArrayList<>();

        getElemAtLevel(root, result, level, 1);

        return result;
    }

    private void getElemAtLevel(TreeNode<T> current,
                                ArrayList<T> result,
                                int level,
                                int currentLevel) {

        if (current == null) {
            return;
        }

        if (currentLevel == level) {
            result.add(current.getValue());
            return;
        }

        getElemAtLevel(current.getLeft(), result, level, currentLevel + 1);
        getElemAtLevel(current.getRight(), result, level, currentLevel + 1);
    }
}