package binary_tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Binary Search Tree insert, delete and search Complexity: O(log(n) Space:
 * iterative => O(1)
 * 
 * @author JoseAlejandro
 *
 * @param <T>
 */
public class BST<T extends Comparable<T>> {

	private TreeNode<T> root;

	public TreeNode<T> getRoot() {
		return root;
	}

	public void InsertNode(T value) {
		TreeNode<T> parent = findPotentialParentInsertionNode(value);
		TreeNode<T> newNode = new TreeNode<T>(value, null, null);

		if (parent == null) {
			root = newNode;
		} else if (value.compareTo(parent.getValue()) < 0) {
			parent.setLeftChild(newNode);
			newNode.setParent(parent);
		} else if (value.compareTo(parent.getValue()) > 0) {
			parent.setRightChild(newNode);
			newNode.setParent(parent);
		}
	}

	public TreeNode<T> searchNode(T value) {

		TreeNode<T> current = root;

		while (current != null) {
			if (value.compareTo(current.getValue()) < 0) {
				current = current.getLeftChild();
			} else if (value.compareTo(current.getValue()) > 0) {
				current = current.getRightChild();
			} else {
				break;
			}
		}
		return current;
	}

	/**
	 * Find the node with the minimum greater value. There are two possible cases:
	 * 
	 * 1 - the refNode has a right child. We take the (find the smallest value,
	 * which going to the left child until we reach null) of this right child.
	 * 
	 * 2 - the refNode has no right child. we go up on the tree using his parent and
	 * go up until we found a node that is the left node of it parent. That parent
	 * is the successor
	 * 
	 * @param refNode node which we want to find it successor
	 * @return successor node
	 */
	public TreeNode<T> findSuccessor(TreeNode<T> refNode) {

		TreeNode<T> current = null;
		TreeNode<T> succesor = null;

		if (refNode != null) {
			current = refNode.getRightChild();
			// case 1 - refNode has a right Child
			if (current != null) {
				while (current != null) {
					succesor = current;
					current = current.getLeftChild();
				}
			}
			// case 2 - refNode has not a right child
			else {
				TreeNode<T> parent = refNode.getParent();
				while (parent != null && parent.getRightChild() == current) {
					current = parent;
					parent = parent.getParent();
				}
				succesor = parent;
			}
		}

		return succesor;
	}

	/**
	 * Delete a node from the BST. There are three cases:
	 * 
	 * 1- When the node is a leaf node. In this case just remove it.
	 * 
	 * 2- When the node has one child. we just remove the node and link the child's
	 * node with the node's parent
	 * 
	 * 3- When the node has two children: We calculate the successor of the node,
	 * swap the node with the successor and apply to the swapped node case 1 or case
	 * 2
	 * 
	 * @param value
	 */
	public void deleteNode(T value) {

		TreeNode<T> current = searchNode(value);

		if (current != null) {

			// Case 3
			if (current.getLeftChild() != null && current.getRightChild() != null) {
				TreeNode<T> succesor = findSuccessor(current);
				if (succesor != null) {

					swap(succesor, current);
				}
			}
			// Case 1 or Case 2
			removeNodeWithOneorNoneChilds(current);
		}

	}

	/***
	 * Traverse the binary tree starting at the root and going one level at a time,
	 * so traverse each level from left to right then continue with the next level
	 * until reach the leaf node (the one with the max value).
	 * 
	 * Time Complexity: O(n): we need to traverse the whole binary tree.
	 * 
	 * Space Complexity: O(n): we need to store the children nodes
	 * 
	 * 
	 * @return T[]
	 */
	@SuppressWarnings("unchecked")
	public T[] getBreathFirstSearch() {

		List<T> nodesInBFS = new ArrayList<>();
		Queue<TreeNode<T>> nodesToVisit = new LinkedList<>();

		TreeNode<T> currentNode = this.root;
		if (this.root != null) {
			nodesToVisit.add(currentNode);

			while (!nodesToVisit.isEmpty()) {

				currentNode = nodesToVisit.poll();
				TreeNode<T> leftChild = currentNode.getLeftChild();
				TreeNode<T> rightChild = currentNode.getRightChild();

				if (leftChild != null) {
					nodesToVisit.add(leftChild);
				}

				if (rightChild != null) {
					nodesToVisit.add(rightChild);
				}

				nodesInBFS.add(currentNode.getValue());
			}
		}

		T[] result = (T[]) Array.newInstance(Comparable.class, nodesInBFS.size());

		return nodesInBFS.toArray(result);

	}

	/**
	 * Recursive version of BFS. This is just for learning purposes, because it less
	 * efficient than the iterative
	 * 
	 * Time Complexity: O(n): we need to traverse the whole binary tree.
	 * 
	 * Space Complexity: O(n): we need to store the children nodes and the stack
	 * frame
	 * 
	 * @return
	 */
	public T[] getBreathFirstSearchRecursive() {

		T[] result = null;

		if (this.root != null) {
			List<T> nodesInBFS = new ArrayList<>();
			Queue<TreeNode<T>> nodesToVisit = new LinkedList<>();

			nodesToVisit.add(this.root);
			result = getBreathFirstSearchRecursiveAux(nodesToVisit, nodesInBFS);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private T[] getBreathFirstSearchRecursiveAux(Queue<TreeNode<T>> nodesToVisit, List<T> nodesInBFS) {

		if (nodesToVisit.isEmpty()) {
			T[] result = (T[]) Array.newInstance(Comparable.class, nodesInBFS.size());
			return nodesInBFS.toArray(result);
		}

		TreeNode<T> currentNode = nodesToVisit.poll();
		TreeNode<T> leftChild = currentNode.getLeftChild();
		TreeNode<T> rightChild = currentNode.getRightChild();

		if (leftChild != null) {
			nodesToVisit.add(leftChild);
		}

		if (rightChild != null) {
			nodesToVisit.add(rightChild);
		}

		nodesInBFS.add(currentNode.getValue());

		return getBreathFirstSearchRecursiveAux(nodesToVisit, nodesInBFS);

	}

	/**
	 * Call the recursive method getDFSinOrderTransversalAux and set it up their
	 * initial values
	 * 
	 * @return T[]
	 */
	@SuppressWarnings("unchecked")
	public T[] getDepthFirstSearch_inOrderTransversal() {
		T[] result = null;
		if (this.root != null) {
			List<T> nodesInDFS = new ArrayList<>();

			getDepthFirstSearch_inOrderTransversalAux(this.root, nodesInDFS);

			result = (T[]) Array.newInstance(Comparable.class, nodesInDFS.size());
			nodesInDFS.toArray(result);
		}

		return result;
	}

	/**
	 * Print elements in the order from lower to greater branch) 
	 * Complexity Time O(n) 
	 * 
	 * Space O(d) => d is the length of the most largest branch
	 * 
	 * @param <T>
	 * @param tree
	 * @param values
	 */
	private void getDepthFirstSearch_inOrderTransversalAux(TreeNode<T> tree, List<T> values) {
		if (tree != null) {
			getDepthFirstSearch_inOrderTransversalAux(tree.getLeftChild(), values);
			values.add(tree.getValue());
			getDepthFirstSearch_inOrderTransversalAux(tree.getRightChild(), values);
		}
	}

	/**
	 * Call the recursive method getDFSpreOrderTransversalAux and set it up their
	 * initial values
	 * 
	 * @return T[]
	 */
	@SuppressWarnings("unchecked")
	public T[] getDepthFirstSearch_preOrderTransversal() {
		T[] result = null;
		if (this.root != null) {
			List<T> nodesInDFS = new ArrayList<>();

			getDepthFirstSearch_preOrderTransversalAux(this.root, nodesInDFS);

			result = (T[]) Array.newInstance(Comparable.class, nodesInDFS.size());
			nodesInDFS.toArray(result);
		}

		return result;
	}

	/**
	 * Print elements in the order that they are traverse (root -left branch, right
	 * branch) 
	 * 
	 * Complexity Time O(n) 
	 * 
	 * Space O(d) => d is the length of the most largest branch
	 * 
	 * @param <T>
	 * @param tree
	 * @param values
	 */
	private void getDepthFirstSearch_preOrderTransversalAux(TreeNode<T> tree, List<T> values) {
		if (tree != null) {
			values.add(tree.getValue());
			getDepthFirstSearch_preOrderTransversalAux(tree.getLeftChild(), values);
			getDepthFirstSearch_preOrderTransversalAux(tree.getRightChild(), values);

		}
	}

	/**
	 * Call the recursive method getDFSpostOrderTransversalAux and set it up their
	 * initial values
	 * 
	 * @return T[]
	 */
	@SuppressWarnings("unchecked")
	public T[] getDepthFirstSearch_postOrderTransversal() {
		T[] result = null;
		if (this.root != null) {
			List<T> nodesInDFS = new ArrayList<>();

			getDepthFirstSearch_postOrderTransversalAux(this.root, nodesInDFS);

			result = (T[]) Array.newInstance(Comparable.class, nodesInDFS.size());
			nodesInDFS.toArray(result);
		}

		return result;
	}

	/**
	 * Print first the left branch, then the right one and at last the root branch)
	 * Complexity Time O(n) Space O(d) => d is the length of the most largest branch
	 * 
	 * @param <T>
	 * @param tree
	 * @param values
	 */
	private void getDepthFirstSearch_postOrderTransversalAux(TreeNode<T> tree, List<T> values) {
		if (tree != null) {
			getDepthFirstSearch_postOrderTransversalAux(tree.getLeftChild(), values);
			getDepthFirstSearch_postOrderTransversalAux(tree.getRightChild(), values);
			values.add(tree.getValue());

		}
	}

	@Override
	public String toString() {

		return genTreeRepresentation(root);
	}

	/**
	 * Complexity space O(log(N))
	 * 
	 * @param current
	 * @return
	 */
	private String genTreeRepresentation(TreeNode<T> current) {

		if (current == null) {

			return "";
		}

		return current.toString().concat("\n").concat(genTreeRepresentation(current.getLeftChild()))
				.concat(genTreeRepresentation(current.getRightChild()));

	}

	private TreeNode<T> findPotentialParentInsertionNode(T value) {

		TreeNode<T> current = root;
		TreeNode<T> parent = root;

		while (current != null) {
			parent = current;
			if (value.compareTo(current.getValue()) < 0) {
				current = current.getLeftChild();
			} else if (value.compareTo(current.getValue()) > 0) {
				current = current.getRightChild();
			} else {
				break;
			}
		}

		return parent;
	}

	/**
	 * Remove a node from the BST depending if the node is a leaf node or if it has
	 * only one child
	 * 
	 * @param current
	 */
	private void removeNodeWithOneorNoneChilds(TreeNode<T> current) {

		if (current != null) {

			// leaf node
			if (current.isLeafNode()) {
				deleteLeafNode(current);
			}
			// node with a left child
			else if (current.getLeftChild() != null && current.getRightChild() == null) {
				deleteNodeWithLeftChild(current);
			}
			// node with a right child
			else if (current.getRightChild() != null && current.getLeftChild() == null) {
				deleteNodeWithRighChild(current);
			}
		}

	}

	private void swap(TreeNode<T> node1, TreeNode<T> node2) {

		TreeNode<T> parentNodeNode2 = node2.getParent();
		// Change the child of the parent of node1 to point to node2
		if (parentNodeNode2.getLeftChild() == node2) {
			parentNodeNode2.setLeftChild(node1);
		} else {
			parentNodeNode2.setRightChild(node1);
		}

		TreeNode<T> tempLeftChild = node1.getLeftChild();
		TreeNode<T> tempRightChild = node1.getRightChild();

		// Interchange child different to the node swapped and invert relationship
		// parent-son
		if (node1 != node2.getLeftChild()) {
			node1.setLeftChild(node2.getLeftChild());
		} else {
			node1.setLeftChild(node2);
		}
		if (node1 != node2.getRightChild()) {
			node1.setRightChild(node2.getRightChild());
		} else {
			node1.setRightChild(node2);
		}
		node1.setParent(node2.getParent());

		node2.setLeftChild(tempLeftChild);
		node2.setRightChild(tempRightChild);
		node2.setParent(node1);
	}

	private void deleteNodeWithRighChild(TreeNode<T> node) {
		TreeNode<T> parent = (node != null) ? node.getParent() : null;

		if (parent != null) {
			if (parent.getLeftChild() == node) {
				parent.setLeftChild(node.getRightChild());
			} else {
				parent.setRightChild(node.getRightChild());

			}
		}
	}

	private void deleteNodeWithLeftChild(TreeNode<T> node) {
		TreeNode<T> parent = (node != null) ? node.getParent() : null;

		if (parent != null) {
			if (parent.getLeftChild() == node) {
				parent.setLeftChild(node.getLeftChild());
			} else {
				parent.setRightChild(node.getLeftChild());

			}
		}
	}

	private void deleteLeafNode(TreeNode<T> node) {
		TreeNode<T> parent = (node != null) ? node.getParent() : null;

		if (parent != null) {
			if (parent.getLeftChild() == node) {
				parent.setLeftChild(null);
			} else {
				parent.setRightChild(null);

			}
		}
	}

	public static void main(String[] args) {
		BST<Double> bst = new BST<Double>();

		bst.InsertNode(9d);
		bst.InsertNode(4d);
		bst.InsertNode(6d);
		bst.InsertNode(20d);
		bst.InsertNode(170d);
		bst.InsertNode(15d);
		bst.InsertNode(1d);

		System.out.println(bst);
		
		System.out.println("BFS-Iterative: " + Arrays.toString(bst.getBreathFirstSearch()));
		System.out.println("BFS-Recursive: " + Arrays.toString(bst.getBreathFirstSearchRecursive()));
		System.out.println("DFS-inOrder: " + Arrays.toString(bst.getDepthFirstSearch_inOrderTransversal()));
		System.out.println("DFS-preOrder: " + Arrays.toString(bst.getDepthFirstSearch_preOrderTransversal()));
		System.out.println("DFS-postOrder: " + Arrays.toString(bst.getDepthFirstSearch_postOrderTransversal()));
		System.out.println();

		bst.deleteNode(20d);

		System.out.println(bst);		
	}
}
