package binary_tree;

/*
 * Validate if a BST or a TreeNode is valid (has the value distribution according to  BST rules)
 */
public class Exercise19_Validate_Binary_Tree {

	public static void main(String[] args) {

		testTreeNode();
		testBST();

	}

	public static void testTreeNode() {
		TreeNode<Double> node15 = new TreeNode<Double>(15d, null, null);
		TreeNode<Double> node11 = new TreeNode<Double>(11d, null, null);
		TreeNode<Double> node13 = new TreeNode<Double>(13d, node11, node15);
		TreeNode<Double> node1 = new TreeNode<Double>(1d, null, null);
		TreeNode<Double> node7 = new TreeNode<Double>(7d, null, null);
		TreeNode<Double> node5 = new TreeNode<Double>(5d, node1, node7);
		TreeNode<Double> wrong = new TreeNode<Double>(5d, node11, node13);
		TreeNode<Double> node10 = new TreeNode<Double>(10d, node5, wrong);

		System.out.println(node10);
		System.out.println(node5);
		System.out.println(node1);
		System.out.println(node7);
		System.out.println(wrong);
		System.out.println(node11);
		System.out.println(node13);
		System.out.println(node15);

		System.out.println(String.format("\nIs Valid: %b\n", isValidBST(node10)));

	}

	public static void testBST() {
		BST<Double> bst = new BST<Double>();

		bst.InsertNode(10d);
		bst.InsertNode(5d);
		bst.InsertNode(15d);
		bst.InsertNode(2d);
		bst.InsertNode(7d);
		bst.InsertNode(12d);
		bst.InsertNode(13d);
		bst.InsertNode(20d);
		bst.InsertNode(21d);

		System.out.println(bst);

		System.out.println(String.format("Is Valid: %b", isValidBST(bst)));
	}

	/**
	 * Recursive Method to validate each node 
	 * 
	 * Time Complexity: O(n): We need to traverse all the nodes once. 
	 * 
	 * Space Complexity O(d) where d is the length of the largest branch. 
	 * It is not O(log(n)), because unlike the binary search, we here need to go 
	 * down all the branch until we can said it is valid, so we are going to have 
	 * on the stack as much as node that branch
	 * will have
	 * 
	 * @param tree
	 * @return
	 */
	public static boolean isValidBST(BST<Double> tree) {

		if (tree != null) {
			double minValue = Double.MIN_VALUE;
			double maxValue = Double.MAX_VALUE;

			TreeNode<Double> root = tree.getRoot();

			return validateSubTree(root, minValue, maxValue);
		}
		return true;
	}

	public static boolean isValidBST(TreeNode<Double> root) {

		if (root != null) {
			double minValue = Double.MIN_VALUE;
			double maxValue = Double.MAX_VALUE;

			return validateSubTree(root, minValue, maxValue);
		}
		return true;
	}

	private static boolean validateSubTree(TreeNode<Double> node, double minValue, double maxValue) {
		

		if (node.getValue() >= minValue && node.getValue() < maxValue) {

			TreeNode<Double> leftChildNode = node.getLeftChild();
			TreeNode<Double> rightChildNode = node.getRightChild();

			if (leftChildNode != null) {
				if (!validateSubTree(node.getLeftChild(), minValue, node.getValue())) {
					return false;
				}

			}

			if (rightChildNode != null) {
				if (!validateSubTree(node.getRightChild(), node.getValue(), maxValue)) {
					return false;
				}
			}			

			return true;
		}

		return false;
	}

}
