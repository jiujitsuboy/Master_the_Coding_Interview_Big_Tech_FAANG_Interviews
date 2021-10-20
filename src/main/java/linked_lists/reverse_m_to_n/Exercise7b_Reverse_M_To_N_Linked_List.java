package linked_lists.reverse_m_to_n;

import linked_lists.NodeSingleLinkedList;

public class Exercise7b_Reverse_M_To_N_Linked_List {

	/***
	 * Reverse the portion of a Linked List between the M and N indexes. (first
	 * element is at index 1) M should be equal or greater than 1 N should be
	 * greater than 1 and less than linked list length
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		NodeSingleLinkedList node7 = new NodeSingleLinkedList(7, null);
		NodeSingleLinkedList node6 = new NodeSingleLinkedList(6, node7);
		NodeSingleLinkedList node5 = new NodeSingleLinkedList(5, node6);
		NodeSingleLinkedList node4 = new NodeSingleLinkedList(4, node5);
		NodeSingleLinkedList node3 = new NodeSingleLinkedList(3, node4);
		NodeSingleLinkedList node2 = new NodeSingleLinkedList(2, node3);
		NodeSingleLinkedList node1 = new NodeSingleLinkedList(1, node2);

		System.out.println((node1 != null) ? node1.toString() : null);
		NodeSingleLinkedList revLikedList = reverseLinkedList(node1, 5, 600);
		System.out.println((revLikedList != null) ? revLikedList.toString() : null);
	}

	/***
	 * If node is not between the M and N index, skip and move to the next node.
	 * Once the index is at M, just save the previous node which should point to the
	 * new sub head (the head of the reverse sub linked list) and the current node
	 * which should point to the node after the N index. All the nodes between M+1
	 * and N should be inverted (make them point to the previous node)
	 * 
	 * Time Complexity: O(n) Space Complexity: O(1)
	 * 
	 * @param node
	 * @return
	 */

	private static NodeSingleLinkedList reverseLinkedList(NodeSingleLinkedList node, int fromNodeIndex, int toNodeIndex) {

		NodeSingleLinkedList headNode = node;
		NodeSingleLinkedList nodeToTailNode = null;
		NodeSingleLinkedList nodeToSubHeadNode = null;
		NodeSingleLinkedList prevNode = null;
		
		//Used to obtain the initial node, so the print method can print the whole linked list
		//This position depends where the reverse of the linked list start.
		boolean getSubTailAsHeadNode = false;
		int currentNodeIndex = 1;

		if (fromNodeIndex < 1) {
			fromNodeIndex = 1;
		}

		while (node != null) {
			NodeSingleLinkedList nextNode = node.getNext();

			if (currentNodeIndex == fromNodeIndex) {
				if (currentNodeIndex == 1) {
					getSubTailAsHeadNode = true;
				}
				nodeToSubHeadNode = prevNode;
				nodeToTailNode = node;
			}
			if (currentNodeIndex == toNodeIndex || (nextNode == null && currentNodeIndex < toNodeIndex)) {
				nodeToTailNode.setNext(nextNode);

				if (nodeToSubHeadNode != null) {
					nodeToSubHeadNode.setNext(node);
				}
				if (fromNodeIndex != toNodeIndex) {
					node.setNext(prevNode);
				}
				if (getSubTailAsHeadNode) {
					headNode = node;
				}
			}

			if (currentNodeIndex > fromNodeIndex && currentNodeIndex < toNodeIndex) {
				node.setNext(prevNode);
			}

			prevNode = node;
			node = nextNode;
			currentNodeIndex++;
		}

		return headNode;

	}

}
