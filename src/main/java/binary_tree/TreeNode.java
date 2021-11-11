package binary_tree;

public class TreeNode<T> {
	private T value;
	private TreeNode<T> leftChild;
	private TreeNode<T> rightChild;
	private TreeNode<T> parent;

	public TreeNode(T value, TreeNode<T> leftChild, TreeNode<T> rightChild) {
		this.value = value;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public TreeNode<T> getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(TreeNode<T> leftChild) {
		this.leftChild = leftChild;
	}

	public TreeNode<T> getRightChild() {
		return rightChild;
	}

	public void setRightChild(TreeNode<T> rightChild) {
		this.rightChild = rightChild;
	}

	public TreeNode<T> getParent() {
		return parent;
	}

	public void setParent(TreeNode<T> parent) {
		this.parent = parent;
	}

	public boolean isLeafNode() {
		return leftChild == null && rightChild == null;
	}

	@Override
	public String toString() {

		String leftChildValue = (leftChild != null) ? leftChild.getValue().toString() : null;
		String rightChildValue = (rightChild != null) ? rightChild.getValue().toString() : null;
		String parentValue = (parent != null) ? parent.getValue().toString() : null;

		return String.format("[value:%s, LeftChild:%s, RightChild:%s, Parent:%s]", value, leftChildValue,
				rightChildValue, parentValue);
	}

}
