package binary_tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Create an array that contains one array with all the values per level in the
 * binary tree.
 * 
 * @author Jose
 *
 */
public class Exercise16_Level_Order_Of_Binary_Tree {

	public static void main(String[] args) {
		BST<Integer> bst = new BST<Integer>();

		bst.InsertNode(9);
		bst.InsertNode(4);
		bst.InsertNode(6);
		bst.InsertNode(20);
		bst.InsertNode(170);
		bst.InsertNode(15);
		bst.InsertNode(1);

//		bst.InsertNode(3);
//		bst.InsertNode(6);
//		bst.InsertNode(1);
//		bst.InsertNode(9);
//		bst.InsertNode(2);
//		bst.InsertNode(4);
//		bst.InsertNode(5);
//		bst.InsertNode(8);

		System.out.println(bst);
		Comparable<Integer>[][] response = getLevelOrderBinaryTree3(bst);

		for (int row = 0; row < response.length; row++) {

			System.out.println(Arrays.toString(response[row]));

		}

	}

	/***
	 * Using BFS algorithm we traverse the nodes per level, we use two maps, one to
	 * store the nodes and the level to which they belong and one to store all the nodes from a level.
	 * 
	 * Then we use the map to create for each level an array that finally are stored in the response array
	 * 
	 * 
	 * Time Complexity: O(n): we traverse the whole tree once, and the map once, which is O(2n)=>O(n) 
	 * 
	 * Space Complexity:O(n): We store all the nodes in one final array and one temporal map  (the queue
	 * is not important because the size will be the wider level in the tree)
	 * 
	 * 
	 * @param <T>
	 * @param tree
	 * @return
	 */
	public static Integer[][] getLevelOrderBinaryTree(BST<Integer> tree) {

		Integer[][] arrayLevelOrder = new Integer[1][];
		List<Integer[]> listLevelOrder;

		if (tree != null && tree.getRoot() != null) {

			listLevelOrder = new ArrayList<>();
			Queue<TreeNode<Integer>> pendingNodes = new LinkedList<>();
			Map<TreeNode<Integer>, Long> nodesLevel = new HashMap<>();
			Map<Long, List<Integer>> nodesInEachLevel = new HashMap<>();

			TreeNode<Integer> node = tree.getRoot();

			pendingNodes.add(node);
			nodesLevel.put(node, 1l);

			while (!pendingNodes.isEmpty()) {
				node = pendingNodes.poll();
				Long level = nodesLevel.get(node);

				List<Integer> nodesInThisLevel = nodesInEachLevel.getOrDefault(level, new ArrayList<>());
				nodesInThisLevel.add(node.getValue());
				nodesInEachLevel.put(level, nodesInThisLevel);

				TreeNode<Integer> leftChildNode = node.getLeftChild();
				TreeNode<Integer> rightChildNode = node.getRightChild();

				if (leftChildNode != null) {
					pendingNodes.add(leftChildNode);
					nodesLevel.put(leftChildNode, level + 1);
				}
				if (rightChildNode != null) {
					pendingNodes.add(rightChildNode);
					nodesLevel.put(rightChildNode, level + 1);
				}
			}

			long level = 1;

			while (true) {
				List<Integer> nodesInThisLevel = nodesInEachLevel.get(level++);
				if (nodesInThisLevel == null) {
					break;
				}

				Integer[] levelNodeArray = new Integer[nodesInThisLevel.size()];

				nodesInThisLevel.toArray(levelNodeArray);
				listLevelOrder.add(levelNodeArray);

			}

			arrayLevelOrder = new Integer[listLevelOrder.size()][];
			listLevelOrder.toArray(arrayLevelOrder);

		}

		return arrayLevelOrder;
	}

	/***
	 * Using BFS algorithm we traverse the nodes per level, we use two maps, one to
	 * store the nodes and the level to which they belong and one to store all the nodes from a level.
	 * 
	 * Then we use the map to create for each level an array that finally are stored in the response array
	 * 
	 * 
	 * Time Complexity: O(n): we traverse the whole tree once, and the map once, which is O(2n)=>O(n) 
	 * 
	 * Space Complexity:O(n): We store all the nodes in one final array and one temporal map  (the queue
	 * is not important because the size will be the wider level in the tree)
	 * 
	 * 
	 * @param <T>
	 * @param tree
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Comparable<T>> T[][] getLevelOrderBinaryTree2(BST<T> tree) {

		T[][] arrayLevelOrder = (T[][]) Array.newInstance(Comparable.class, 1, 1);
		List<T[]> listLevelOrder;

		if (tree != null && tree.getRoot() != null) {

			listLevelOrder = new ArrayList<>();
			Queue<TreeNode<T>> pendingNodes = new LinkedList<>();
			Map<TreeNode<T>, Long> nodesLevel = new HashMap<>();
			Map<Long, List<T>> nodesInEachLevel = new HashMap<>();

			TreeNode<T> node = tree.getRoot();

			pendingNodes.add(node);
			nodesLevel.put(node, 1l);

			while (!pendingNodes.isEmpty()) {
				node = pendingNodes.poll();
				Long level = nodesLevel.get(node);

				List<T> nodesInThisLevel = nodesInEachLevel.getOrDefault(level, new ArrayList<>());
				nodesInThisLevel.add(node.getValue());
				nodesInEachLevel.put(level, nodesInThisLevel);

				TreeNode<T> leftChildNode = node.getLeftChild();
				TreeNode<T> rightChildNode = node.getRightChild();

				if (leftChildNode != null) {
					pendingNodes.add(leftChildNode);
					nodesLevel.put(leftChildNode, level + 1);
				}
				if (rightChildNode != null) {
					pendingNodes.add(rightChildNode);
					nodesLevel.put(rightChildNode, level + 1);
				}
			}

			long level = 1;

			while (true) {
				List<T> nodesInThisLevel = nodesInEachLevel.get(level++);
				if (nodesInThisLevel == null) {
					break;
				}

				T[] levelNodeArray = (T[]) Array.newInstance(Comparable.class, nodesInThisLevel.size());

				nodesInThisLevel.toArray(levelNodeArray);
				listLevelOrder.add(levelNodeArray);

			}

			arrayLevelOrder = (T[][]) Array.newInstance(Comparable.class, listLevelOrder.size(), 1);
			listLevelOrder.toArray(arrayLevelOrder);
//			for (int index = 0; index < listLevelOrder.size(); index++) {
//				arrayLevelOrder[index] = listLevelOrder.get(index);
//
//			}

		}

		return arrayLevelOrder;
	}

	/***
	 * Using BFS algorithm we traverse the nodes per level, we use a variable
	 * nodesProcessed and queueSize to know when are at the last element of the
	 * level, so we can create the level array and stored in the result array to
	 * return
	 * 
	 * 
	 * Time Complexity: O(n): we traverse the whole tree once
	 * 
	 * Space Complexity:O(n): We store all the nodes in one final array (the queue
	 * is not important because the size will be the wider level in the tree)
	 * 
	 * 
	 * @param <T>
	 * @param tree
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Comparable<T>> T[][] getLevelOrderBinaryTree3(BST<T> tree) {

		T[][] arrayLevelOrder = (T[][]) Array.newInstance(Comparable.class, 1, 1);
		List<T[]> listLevelOrder;

		if (tree != null && tree.getRoot() != null) {

			long queueSize = 0;
			long nodesProcessed = 0;
			listLevelOrder = new ArrayList<>();
			List<T> nodesInThisLevel = new ArrayList<>();
			Queue<TreeNode<T>> pendingNodes = new LinkedList<>();

			TreeNode<T> node = tree.getRoot();

			pendingNodes.add(node);

			while (!pendingNodes.isEmpty()) {

				if (nodesProcessed == 0) {
					queueSize = pendingNodes.size();
				}

				nodesProcessed++;

				node = pendingNodes.poll();
				nodesInThisLevel.add(node.getValue());

				if (queueSize == nodesProcessed) {
					T[] levelNodeArray = (T[]) Array.newInstance(Comparable.class, nodesInThisLevel.size());
					nodesInThisLevel.toArray(levelNodeArray);
					listLevelOrder.add(levelNodeArray);
					nodesProcessed = 0;
					nodesInThisLevel.clear();
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

			if (!listLevelOrder.isEmpty()) {
				arrayLevelOrder = (T[][]) Array.newInstance(Comparable.class, listLevelOrder.size(), 1);
				listLevelOrder.toArray(arrayLevelOrder);
			}

		}

		return arrayLevelOrder;
	}
}