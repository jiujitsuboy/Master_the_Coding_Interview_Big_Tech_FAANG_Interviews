package linked_lists.cycle_detection.brute_force;

import java.util.HashSet;
import java.util.Set;

import linked_lists.NodeSingleLinkedList;

/***
 * Identify if a single linked list has a cycle and not end in a null pointer
 * 
 * @author Jose
 *
 */
public class Exercise9_Linked_List_Cycle_Detection {

	public static void main(String[] args) {
		NodeSingleLinkedList node4 = new NodeSingleLinkedList(4, null);
		NodeSingleLinkedList node3 = new NodeSingleLinkedList(3, node4);
		NodeSingleLinkedList node2 = new NodeSingleLinkedList(2, node3);
		NodeSingleLinkedList node1 = new NodeSingleLinkedList(1, node2);
		node4.setNext(node2);

		NodeSingleLinkedList cycleNode = findCycleNode(node1);

		System.out.println((cycleNode != null) ? cycleNode.getValue() : cycleNode);

	}

	/***
	 * brute force approach: using a Set we store all the seem nodes to identify
	 * when we see a node for a second time. Time Complexity: O(n): traverse the
	 * whole linked list once Space Complexity: O(n): using a Set to store in the
	 * worst case all the nodes from the linked list
	 * 
	 * @param node
	 * @return
	 */
	private static NodeSingleLinkedList findCycleNode(NodeSingleLinkedList node) {
		NodeSingleLinkedList cycleNode = null;
		Set<NodeSingleLinkedList> nodesTrasversed = new HashSet<>();

		while (node != null) {
			if (nodesTrasversed.contains(node)) {
				cycleNode = node;
				break;
			} else {
				nodesTrasversed.add(node);
			}
			node = node.getNext();
		}

		return cycleNode;
	}

}
