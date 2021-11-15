package graph;

import java.util.ArrayList;
import java.util.List;

public class GraphNode<T> {
	
	T value;
	List<GraphNode<T>> edges;
	
	public GraphNode(T value) {	
		this.value = value;
		this.edges = new ArrayList<>();
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
	
	public List<GraphNode<T>> getEdges(){
		return edges;
	}
	
	public String toString() {
		return this.value.toString();
	}
}
