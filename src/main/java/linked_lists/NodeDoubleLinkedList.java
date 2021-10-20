package linked_lists;

/***
 * Class that represent a node of a double linked list
 * 
 * @author Jose
 *
 */
public class NodeDoubleLinkedList {
	protected Object value;
	protected NodeDoubleLinkedList next;
	protected NodeDoubleLinkedList prev;

	public NodeDoubleLinkedList(Object value, NodeDoubleLinkedList next, NodeDoubleLinkedList prev) {
		this.value = value;
		this.next = next;
		this.prev = prev;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public NodeDoubleLinkedList getNext() {
		return next;
	}

	public void setNext(NodeDoubleLinkedList next) {
		this.next = next;
	}

	public NodeDoubleLinkedList getPrev() {
		return prev;
	}

	public void setPrev(NodeDoubleLinkedList prev) {
		this.prev = prev;
	}

	public String toString() {

		StringBuilder sb = new StringBuilder(10);
		NodeDoubleLinkedList node = this;
		do {
			NodeDoubleLinkedList prev = node.getPrev();
			NodeDoubleLinkedList next = node.getNext();
			sb.append("(");
			sb.append((prev != null) ? prev.getValue()+"" : "NULL");
			sb.append(",[");
			sb.append(node.getValue());
			sb.append("],");
			sb.append((next != null) ? next.getValue() : "NULL");
			sb.append(")");
			node = node.getNext();
			if (node != null) {
				sb.append("->");
			}
		} while (node != null);

		return sb.toString();
	}

}
