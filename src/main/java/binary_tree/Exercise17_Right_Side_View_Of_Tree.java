package binary_tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Show only the nodes most to the right of the tree. So it would be like you
 * were standby on the right of the tree and only see the first node you can
 * see, which would be the most right node of each level.
 * 
 * Using the two ways of traverse a binary tree.
 * 
 * BFS: In this case the worst case for Space Complexity is when the tree is
 * full, so we need to store a lot of nodes in the queue as we goes down the
 * tree O(w)=>w is wide of the lower level of the tree. So this approach is
 * excellent when we have a very thin tree (long but no wide), otherwise is
 * better DFS
 * 
 * 
 * DFS: In this case the worst case is when the tree is a linked list, in this
 * case is better BFS.
 * 
 * @author Jose
 *
 */
public class Exercise17_Right_Side_View_Of_Tree {

//							9
//					4				20
//				1		6		15			170
//			0		3
//		-1		2
//-10	

	public static void main(String[] args) {
		BST<Integer> bst = new BST<Integer>();

		bst.InsertNode(9);
		bst.InsertNode(4);
		bst.InsertNode(6);
		bst.InsertNode(20);
		bst.InsertNode(170);
		bst.InsertNode(15);
		bst.InsertNode(1);
		bst.InsertNode(0);
		bst.InsertNode(3);
		bst.InsertNode(-1);
		bst.InsertNode(2);
		bst.InsertNode(-10);
		System.out.println(bst);
		System.out.println("BFS:     " + Arrays.toString(getRightSideViewBFS(bst)));
		System.out.println("DFS_NLR: " + Arrays.toString(getRightSideViewDFS_PreOrder_NLR(bst)));
		System.out.println("DFS_NRL: " + Arrays.toString(getRightSideViewDFS_PreOrder_NRL(bst)));
	}

	/**
	 * Using the queue size, we can know what level we are, when we are in the last
	 * element of the level, only in that moment store the value.
	 * 
	 * Time Complexity: O(n): we traverse all the nodes of the tree.
	 * 
	 * Space Complexity: O(n): We store one node per level in the final array (the
	 * queue is not important because the size will be the wider level in the tree)
	 * 
	 * @param <T>
	 * @param tree
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T extends Comparable<T>> T[] getRightSideViewBFS(BST<T> tree) {
		T[] arrayRightView = (T[]) Array.newInstance(Comparable.class, 1);
		List<T> listRightView;

		if (tree != null && tree.getRoot() != null) {

			// variable definition
			long queueSize = 0;
			long nodesProcessed = 0;
			listRightView = new ArrayList<>();
			Queue<TreeNode<T>> pendingNodes = new LinkedList<>();

			TreeNode<T> node = tree.getRoot();

			// Adding the root node to the queue
			pendingNodes.add(node);

			while (!pendingNodes.isEmpty()) {

				if (nodesProcessed == 0) {
					queueSize = pendingNodes.size();
				}

				nodesProcessed++;

				node = pendingNodes.poll();

				// When we are in the last node of the level
				if (queueSize == nodesProcessed) {
					listRightView.add(node.getValue());
					nodesProcessed = 0;
				}

				TreeNode<T> leftChildNode = node.getLeftChild();
				TreeNode<T> rightChildNode = node.getRightChild();

				if (leftChildNode != null) {
					pendingNodes.add(leftChildNode);
				}
				if (rightChildNode != null) {
					pendingNodes.add(rightChildNode);
				}
			}

			if (!listRightView.isEmpty()) {
				arrayRightView = (T[]) Array.newInstance(Comparable.class, listRightView.size());
				listRightView.toArray(arrayRightView);
			}

		}

		return arrayRightView;
	}

	/**
	 * Traversing the tree first through all the left nodes and using a variable to
	 * identify the level. Always use the level to insert the value in that
	 * position, which would end with the last element of the level in the array.
	 * 
	 * Time Complexity: O(n): we traverse all the nodes of the tree.
	 * 
	 * Space Complexity: O(n): We store one node per level in the final array
	 * 
	 * @param <T>
	 * @param tree
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T extends Comparable<T>> T[] getRightSideViewDFS_PreOrder_NLR(BST<T> tree) {
		T[] arrayRightView = (T[]) Array.newInstance(Comparable.class, 1);
		List<T> listRightView = new ArrayList<T>(10);
		int currentLevel = 0;

		if (tree != null && tree.getRoot() != null) {
			getRightSideViewDFSAux_PreOrder_NLR(tree.getRoot(), currentLevel, listRightView);
			// Create array from the listLevelOrder
			arrayRightView = (T[]) Array.newInstance(Comparable.class, listRightView.size());
			listRightView.toArray(arrayRightView);
		}
		return arrayRightView;
	}

	private static <T extends Comparable<T>> void getRightSideViewDFSAux_PreOrder_NLR(TreeNode<T> node,
			int currentLevel, List<T> listRightView) {
		if (node != null) {
			if (listRightView.size() >= currentLevel + 1) {
				listRightView.set(currentLevel, node.getValue());
			} else {
				listRightView.add(node.getValue());
			}
			getRightSideViewDFSAux_PreOrder_NLR(node.getLeftChild(), currentLevel + 1, listRightView);
			getRightSideViewDFSAux_PreOrder_NLR(node.getRightChild(), currentLevel + 1, listRightView);
		}
	}

	/**
	 * Traversing the tree first through all the right nodes and using a variable to
	 * identify the level. The difference with the above NLR is that here we only
	 * insert into the array, nodes which level is not already in the array, this is
	 * done by comparing that the level is greater that the current array size.
	 * 
	 * Time Complexity: O(n): we traverse all the nodes of the tree.
	 * 
	 * Space Complexity: O(n): We store one node per level in the final array
	 * 
	 * @param <T>
	 * @param tree
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T extends Comparable<T>> T[] getRightSideViewDFS_PreOrder_NRL(BST<T> tree) {
		T[] arrayRightView = (T[]) Array.newInstance(Comparable.class, 1);
		List<T> listRightView = new ArrayList<T>();
		int currentLevel = 0;

		if (tree != null && tree.getRoot() != null) {
			getRightSideViewDFSAux_PreOrder_NRL(tree.getRoot(), currentLevel + 1, listRightView);
			// Create array from the listLevelOrder
			arrayRightView = (T[]) Array.newInstance(Comparable.class, listRightView.size());
			listRightView.toArray(arrayRightView);
		}
		return arrayRightView;
	}

	private static <T extends Comparable<T>> void getRightSideViewDFSAux_PreOrder_NRL(TreeNode<T> node,
			int currentLevel, List<T> listRightView) {
		if (node != null) {
			if (listRightView.size() < currentLevel) {
				listRightView.add(node.getValue());
			}
			getRightSideViewDFSAux_PreOrder_NRL(node.getRightChild(), currentLevel + 1, listRightView);
			getRightSideViewDFSAux_PreOrder_NRL(node.getLeftChild(), currentLevel + 1, listRightView);
		}
	}

}
