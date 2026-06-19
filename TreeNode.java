
public class TreeNode<T> {

	private T value;
	private TreeNode<T> left;
	private TreeNode<T> right;

	public TreeNode(T value2) {
		this.value = value2;
		this.left = null;
		this.right = null;

	}

	public TreeNode<T> getLeft() {
		return left;
	}

	public void setLeft(TreeNode<T> left) {
		this.left = left;
	}

	public TreeNode<T> getRight() {
		return right;
	}

	public void setRight(TreeNode<T> right) {
		this.right = right;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value){
		this.value=value;
	}
    
}
