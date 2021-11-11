package binary_tree;

/**
 * Count the number of nodes in a full/Complete tree, getting a complexity better 
 * that O(n) which is obtained by traverse every node of the tree once.
 * 
 * Expected Complexity should be O(1) or O(log n)
 * 
 * 
 * O(1) = This is only achievable if the tree is full, because we can calculate
 * the number of nodes using the formula (2^n)-1. But if the tree is Complete, we
 * can have null children in the last level and the formula is not enough.
 *  
 * O(log n) = Calculating the Depth if the tree (), then using a modify version of
 * binary search.
 * 
 * 
 * 
 * Complete tree: when all the nodes of the tree has two
 * children except the last level (where all the children are null). Or it can
 * have nodes that has only one child but the whole should be to the right of
 * the tree,basically all the nodes from left to right must exist until the whole
 * 
 * 					A
 * 			B				C
 * 		D		E		F		null   => This is a complete tree with no children at the right
 * 
 * 
 * Full tree: every node of the tree has two children or none (is not allow to
 * have one child).
 * 
 * 					A
 * 			B				C
 * 		D		E		F		G
 * 
 *    null null null null null null null => This is a full and complete tree with two children in every level except in the  last level
 * 
 * @author Jose
 *
 */
public class Exercise18_Number_Nodes_In_Full_And_Complete_Trees {

//	private static int iteracion = 0;

	public static void main(String[] args) {
		BST<Integer> bst = new BST<Integer>();

		
 //					8
 //			5				10
 // 	1		6		9		12
		
		bst.InsertNode(8);
		bst.InsertNode(5);
		bst.InsertNode(10);
		bst.InsertNode(6);
		bst.InsertNode(1);
		bst.InsertNode(9);
		bst.InsertNode(12);

		System.out.println(bst);
		System.out.print("Number of Nodes: " + getCountNodesBinaryTree(bst));

	}

	/**
	 * Validate the root node is not null and loop until the left pointer is not
	 * longer smaller than the right pointer
	 * 
	 * Time Complexity: O(h) + O (log^2 n) => The while loop is called Log n times
	 * because the binary the modified search method discard half of the nodes 
	 * in each iteration. But every time we call the modified binary search, that
	 * method requires log(n) to get to the last node. So we have log n * log n
	 *  
	 * 
	 * Space Complexity: O(1): only pointers no extra structures
	 * @param <T>
	 * @param bst
	 * @return
	 */
	public static <T extends Comparable<T>> int getCountNodesBinaryTree(BST<T> bst) {
		int numNodes = 0;

		if (bst != null && bst.getRoot() != null) {

			int maxDepth = findMaxDepthFullCompleteTree(bst.getRoot());
			int numNodesPenultimateLevel = (int) Math.pow(2, maxDepth - 1) - 1;  // O(1)
			int leftPointer = 0;
			int rightPointer = numNodesPenultimateLevel;

			// iterate over the nodes of the last level which are N/2 using 
			// binary search so it would be O(log n/2) =>O (log n/2)=>O(h) height of the tree
			while (leftPointer < rightPointer) {
				int indexToFind = (int) Math.round((leftPointer + rightPointer) / 2d);
				if (nodeExist_BinarySearch(bst.getRoot(), maxDepth, numNodesPenultimateLevel, indexToFind)) {
					leftPointer = indexToFind;
				} else {
					rightPointer = indexToFind - 1;
				}
			}

			numNodes = numNodesPenultimateLevel + leftPointer + 1;

		}

		return numNodes;
	}

	
	/**
	 * Calculate how many levels the tree has.
	 * 
	 * Time Complexity: O(h), where h is the height of the tree, which could be 
	 * log n if the tree is full 
	 * 
	 * Space Complexity: O(1), no extra structure depending of the input
	 * @param <T>
	 * @param node
	 * @return
	 */
	private static <T extends Comparable<T>> int findMaxDepthFullCompleteTree(TreeNode<T> node) {

		int maxDepth = 0;
		while (node != null) {
			maxDepth++;
			node = node.getLeftChild();
		}

		return maxDepth;

	}

	
	/**
	 * Modified binary search, which looks for the specified index in the last
	 * level. The nodes in the last level are seem like items in an array, so
	 * we give to each one of them an index (the length of the array is calculated
	 * by the potential number of nodes that the level can have 2^(N-1), because
	 * the last level has the same number of nodes of the previous levels).
	 * 
	 * So this modify versions of binary search would start by calculating the
	 * index to find which would be the half inclusive of the bottom array, and 
	 * initializing two pointers, the left one pointing to index zero and the 
	 * right one pointing to the length-1 of the bottom array.
	 * 
	 * looping for each level of the tree, it would compare the index to find to 
	 * the middle value of (leftPointer + rightPointer)/2, which depending, if 
	 * index to find is equal or greater to the middle point, we took the right
	 * child of the node and set the leftPointer = middle point, otherwise the 
	 * left child and set the rightPointer = middle - 1. We repeat until we reach
	 * the penultimate level of the tree. At this point we reach the leaf node that
	 * is represented by the index we are look for, if the node exists, we assume
	 * that all the nodes to the left exists (complete/full tree condition), so
	 * we move the lefpointer to the index to find, otherwise move the rightPointer
	 * to the index to find - 1. we continue until the left pointer is not longer
	 * smaller than the right pointer.
	 * 
	 * The count would be the number of nodes calculated until the penultimate 
	 * plus the position of the leftPointer + 1,
	 * 
	 * 
	 * Time Complexity:  O (log n): Binary search skip half of the nodes on 
	 * every level 
	 * 
	 * Space Complexity: O(1): only pointers no extra structures
	 * 
	 * 
	 * 
	 * @param <T>
	 * @param node
	 * @param maxDepth
	 * @param numNodesPenultimateLevel
	 * @param indexToFind
	 * @return
	 */
	private static <T extends Comparable<T>> boolean nodeExist_BinarySearch(TreeNode<T> node, int maxDepth,
			int numNodesPenultimateLevel, int indexToFind) {
//		System.out.println("iteracion: " + (++iteracion));

		int treeLevel = 1;
		int leftPointer = 0;
		int rightPointer = numNodesPenultimateLevel;

		while (treeLevel < maxDepth) {
			int midIndex = (int) Math.round((leftPointer + rightPointer) / 2d);

			if (indexToFind >= midIndex) {
				node = node.getRightChild();
				leftPointer = midIndex;
			} else {
				node = node.getLeftChild();
				rightPointer = midIndex - 1;
			}

			treeLevel++;
		}

		return node != null;
	}

}
