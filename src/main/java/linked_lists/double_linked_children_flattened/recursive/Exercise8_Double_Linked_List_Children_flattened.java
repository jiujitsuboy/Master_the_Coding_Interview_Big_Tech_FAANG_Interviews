package linked_lists.double_linked_children_flattened.recursive;

import linked_lists.NodeDoubleLinkedListWithChild;

/***
 * Flatten a double linked list with children (each children is a node that
 * point to another linked list), where children should be inserted into the
 * current parent node and the next node after the parent. Each node can only
 * have one child.
 * 
 * @author Jose
 *
 */
public class Exercise8_Double_Linked_List_Children_flattened {

	public static void main(String[] args) {

		NodeDoubleLinkedListWithChild node11 = new NodeDoubleLinkedListWithChild(11, null, null, null);
		NodeDoubleLinkedListWithChild node10 = new NodeDoubleLinkedListWithChild(10, node11, null, null);
		NodeDoubleLinkedListWithChild node9 = new NodeDoubleLinkedListWithChild(9, null, null, node10);
		NodeDoubleLinkedListWithChild node8 = new NodeDoubleLinkedListWithChild(8, null, null, node9);
		NodeDoubleLinkedListWithChild node7 = new NodeDoubleLinkedListWithChild(7, null, null, node8);
		NodeDoubleLinkedListWithChild node6 = new NodeDoubleLinkedListWithChild(6, node7, null, null);
		NodeDoubleLinkedListWithChild node5 = new NodeDoubleLinkedListWithChild(5, null, null, null);
		NodeDoubleLinkedListWithChild node4 = new NodeDoubleLinkedListWithChild(4, node5, null, null);
		NodeDoubleLinkedListWithChild node3 = new NodeDoubleLinkedListWithChild(3, null, null, node6);
		NodeDoubleLinkedListWithChild node2 = new NodeDoubleLinkedListWithChild(2, node3, null, node4);
		NodeDoubleLinkedListWithChild node1 = new NodeDoubleLinkedListWithChild(1, node2, null, null);
		node2.setPrev(node1);
		node3.setPrev(node2);
		node5.setPrev(node4);
		node7.setPrev(node6);
		node11.setPrev(node10);

		System.out.println(flatenLinkedList(node1));
	}

	private static NodeDoubleLinkedListWithChild flatenLinkedList(NodeDoubleLinkedListWithChild node) {

		NodeDoubleLinkedListWithChild head = node;

		flatenLinkedListAux(head);

		return node;

	}

	/***
	 * Time Complexity: O(n): traverse all the nodes of the linked list only once
	 * Space Complexity: O(n): no extra data structure related to the input, created, but the recursive call store on the stack the returning address of the method
	 * 
	 * @param node
	 * @return
	 */
	private static NodeDoubleLinkedListWithChild flatenLinkedListAux(NodeDoubleLinkedListWithChild node) {

		NodeDoubleLinkedListWithChild previousNode = null;
		NodeDoubleLinkedListWithChild nextNode = null;
		NodeDoubleLinkedListWithChild childNode = null;
		NodeDoubleLinkedListWithChild mergedList = null;

		while (node != null) {

			nextNode = node.getNext();
			childNode = node.getChild();
			
			if (childNode != null) {
				mergedList = flatenLinkedListAux(childNode);
				node.setNext(childNode);
				childNode.setPrev(node);
				node.setChild(null);
				mergedList.setNext(nextNode);
				if (nextNode != null) {
					nextNode.setPrev(mergedList);
				}
				previousNode = mergedList;
			} else {
				previousNode = node;
			}

			node = nextNode;

		}

		return previousNode;

	}

}
