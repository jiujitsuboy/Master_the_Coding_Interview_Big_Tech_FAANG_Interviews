package binary_tree;

/**
 * Find the deepest branch of a Binary Tree and return the number if nodes that
 * branch has.
 * 
 * @author Jose
 *
 */
public class Exercise15_Max_Depth {

	public static void main(String[] args) {
		BST<Double> bst = new BST<Double>();

		bst.InsertNode(9d);
		bst.InsertNode(10d);
		bst.InsertNode(11d);
		bst.InsertNode(12d);
		bst.InsertNode(13d);
		bst.InsertNode(8d);
//		bst.InsertNode(4d);
//		bst.InsertNode(6d);
//		bst.InsertNode(20d);
//		bst.InsertNode(170d);
//		bst.InsertNode(15d);
//		bst.InsertNode(1d);

		System.out.println(bst);

		System.out.println(findMaxBranchDepth(bst.getRoot()));

	}

	/**
	 * Using BFS, we start traversing the root node and we go us deep as every
	 * branch is, summing 1 per each node we go deep. Then we take the maximum value
	 * between the two children of a node as we go up.
	 * 
	 * Time Complexity: O(n): we need to visit all the nodes of the tree
	 * 
	 * Space Complexity: O(h): no extra structure saved, but the stack go us deep
	 * has the largest branch of the tree. h in a perfect balanced tree (best scenario) is (log n)
	 * and in the worst case h is equal to n (linked list)
	 * 
	 * @param <T>
	 * @param treeNode node to traverse
	 * @return
	 */
	public static <T extends Comparable<T>> int findMaxBranchDepth(TreeNode<T> treeNode) {
		int maxDepth = 0;

		if (treeNode != null) {
			maxDepth = findMaxBranchDepthAux(treeNode, maxDepth);
		}
		return maxDepth;
	}

	private static <T extends Comparable<T>> int findMaxBranchDepthAux(TreeNode<T> treeNode, int maxDepth) {
		if (treeNode == null) {
			return maxDepth;
		}

		return Math.max(findMaxBranchDepthAux(treeNode.getLeftChild(), maxDepth + 1),
				findMaxBranchDepthAux(treeNode.getRightChild(), maxDepth + 1));
	}
}
