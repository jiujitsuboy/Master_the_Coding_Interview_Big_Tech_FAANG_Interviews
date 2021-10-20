package linked_lists;

/***
 * Class that represent a node of a single linked list
 * @author Jose
 *
 */
public class NodeSingleLinkedList {
	private Object value;
	private NodeSingleLinkedList next;

	public NodeSingleLinkedList(Object value, NodeSingleLinkedList next) {
		this.value = value;
		this.next = next;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public NodeSingleLinkedList getNext() {
		return next;
	}

	public void setNext(NodeSingleLinkedList next) {
		this.next = next;
	}

	public String toString() {

		StringBuilder sb = new StringBuilder(10);
		NodeSingleLinkedList node = this;
		do {
			sb.append(node.getValue());
			node = node.getNext();
			if (node != null) {
				sb.append("->");
			}
		} while (node != null);

		return sb.toString();
	}

}
