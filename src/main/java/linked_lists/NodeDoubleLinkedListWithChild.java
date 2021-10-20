package linked_lists;

public class NodeDoubleLinkedListWithChild extends NodeDoubleLinkedList {

	private NodeDoubleLinkedListWithChild child;

	public NodeDoubleLinkedListWithChild(Object value, NodeDoubleLinkedListWithChild next,
			NodeDoubleLinkedListWithChild prev, NodeDoubleLinkedListWithChild child) {
		super(value, next, prev);
		this.child = child;
	}

	public NodeDoubleLinkedListWithChild getChild() {
		return child;
	}

	public void setChild(NodeDoubleLinkedListWithChild child) {
		this.child = child;
	}

	public NodeDoubleLinkedListWithChild getNext() {
		return (NodeDoubleLinkedListWithChild) next;
	}

	public void setNext(NodeDoubleLinkedListWithChild next) {
		this.next = next;
	}

	public NodeDoubleLinkedListWithChild getPrev() {
		return (NodeDoubleLinkedListWithChild) prev;
	}

	public void setPrev(NodeDoubleLinkedListWithChild prev) {
		this.prev = prev;
	}
}
