package linked_lists.cycle_detection.optimal;

import java.util.HashSet;
import java.util.Set;

import linked_lists.NodeSingleLinkedList;

/***
 * Identify if a single linked list has a cycle and not end in a null pointer
 * 
 * @author Jose
 *
 */
public class Exercise9_Floyd_Tortoise_And_Hare_Algorithm {

	public static void main(String[] args) {
		NodeSingleLinkedList node4 = new NodeSingleLinkedList(4, null);
		NodeSingleLinkedList node3 = new NodeSingleLinkedList(3, node4);
		NodeSingleLinkedList node2 = new NodeSingleLinkedList(2, node3);
		NodeSingleLinkedList node1 = new NodeSingleLinkedList(1, node2);
		node4.setNext(node1);

		NodeSingleLinkedList cycleNode = findCycleNode(null);

		System.out.println((cycleNode != null) ? cycleNode.getValue() : cycleNode);


	}

	/***
	 *Optimal approach: using the Floyd´s Tortoise and Hare algorithm, we use two pointers,  one represented as the tortoise which advance one at a time, 
	 *while the hare advance two nodes at the time. if there is no cycle,the hare is point to reach the end of the linked list, but
	 * if there is a cycle, they going to eventually meet at certain node. In that moment, we use another pointer
	 *which start from the root of the linked list, and both the root pointer and the meeting pointer move one node at a time, until they meet, that node where
	 *they meet is the node with the cycle.
	 *
	 *Time Complexity: O(n): traverse the whole linked list a couple of times 
	 *Space Complexity: O(1): only use pointers, no extra data structure dependent of the input
	 * worst case all the nodes from the linked list
	 * 
	 * @param node
	 * @return
	 */
	private static NodeSingleLinkedList findCycleNode(NodeSingleLinkedList node) {
		NodeSingleLinkedList cycleNode = null;
		NodeSingleLinkedList harePointer = node;
		NodeSingleLinkedList tortoisePointer = node;

		while (harePointer != null) {

			tortoisePointer = tortoisePointer.getNext();
			harePointer = harePointer.getNext();
			if (harePointer == null) {
				break;
			}
			harePointer = harePointer.getNext();
			
			if(harePointer==tortoisePointer) {
				cycleNode = findCycleNode(node, harePointer);
				break;
			}
		}

		return cycleNode;
	}

	private static NodeSingleLinkedList findCycleNode(NodeSingleLinkedList root, NodeSingleLinkedList meetingNode) {		
		
		while(root!=meetingNode) {
			root = root.getNext();
			meetingNode = meetingNode.getNext();
		}				
		
		return root;
		
	}

	

}
