package linked_lists.reverse;

import linked_lists.NodeSingleLinkedList;

public class Exercise7a_Reverse_Linked_List {

	/***
	 * Reverse a Linked List
	 * @param args
	 */
	public static void main(String[] args) {

		NodeSingleLinkedList node4 = new NodeSingleLinkedList(4, null);
		NodeSingleLinkedList node3 = new NodeSingleLinkedList(3, node4);
		NodeSingleLinkedList node2 = new NodeSingleLinkedList(2, node3);
		NodeSingleLinkedList node1 = new NodeSingleLinkedList(1, node2);

		System.out.println((node1 != null) ? node1.toString() : null);
		NodeSingleLinkedList revLikedList = reverseLinkedList(node1);
		System.out.println((revLikedList != null) ? revLikedList.toString() : null);
	}
	
	/***
	 * Take the current node, copy the reference to the next node, set the next value to prevNode (which start in null), set the prevNode to the current node and repeat
	 * Time Complexity: O(n)
	 * Space Complexity: O(1)
	 * @param node
	 * @return
	 */

	private static NodeSingleLinkedList reverseLinkedList(NodeSingleLinkedList node) {

		NodeSingleLinkedList prevNode = null;

		while (node != null) {
			NodeSingleLinkedList nextNode = node.getNext();
			node.setNext(prevNode);
			prevNode = node;
			node = nextNode;
			System.out.println(prevNode);
		}

		return prevNode;

	}
}
