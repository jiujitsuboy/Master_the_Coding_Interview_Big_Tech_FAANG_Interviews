package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GraphNode<T> implements Cloneable {

	private GraphNode<T> parent;
	private T value;
	private List<GraphNode<T>> edges;

	public GraphNode(T value) {
		this.value = value;
		this.edges = new ArrayList<>();
	}

	public GraphNode<T> getParent() {
		return parent;
	}

	public void setParent(GraphNode<T> parent) {
		this.parent = parent;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public void addEdge(GraphNode<T> edge) {
		edges.add(edge);
	}

	public List<GraphNode<T>> getEdges() {
		return edges;
	}

	public String toString() {
		return this.value.toString();
	}

	public GraphNode<T> clone() {
		GraphNode<T> clone = new GraphNode<>(value);
		clone.parent = parent;		
		clone.edges = edges.stream().map(edge -> edge.clone()).collect(Collectors.toList());
		return clone;
	}
}
