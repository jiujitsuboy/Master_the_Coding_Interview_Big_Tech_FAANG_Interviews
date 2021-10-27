package graph;

public class GraphNode<T> {
	
	T value;
	
	public GraphNode(T value) {	
		this.value = value;
	}	

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
	public String toString() {
		return this.value.toString();
	}
	

}
