package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation of a Graph Data Structure
 * 
 * @author Jose
 *
 * @param <T>
 */
public class Graph<T> {

	private Map<GraphNode<T>, List<GraphNode<T>>> adjacentList;

	public Graph() {	
		adjacentList = new HashMap<>();

	}
	
	public long getNumberOfNodes() {
		return adjacentList.size();
	}

	public Map<GraphNode<T>, List<GraphNode<T>>> getAdjacentList() {
		return adjacentList;
	}



	@Override
	public int hashCode() {
		return Objects.hash(adjacentList);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Graph<?> other = (Graph<?>) obj;
		return Objects.equals(adjacentList, other.adjacentList);
	}

	public void addVertex(GraphNode<T> node) {
		adjacentList.put(node, new ArrayList<GraphNode<T>>());
	}

	public void addEdge(GraphNode<T> node1, GraphNode<T> node2) {

		List<GraphNode<T>> nodes1Edges = adjacentList.get(node1);
		List<GraphNode<T>> nodes2Edges = adjacentList.get(node2);

		if (nodes1Edges != null) {
			nodes1Edges.add(node2);
		}
		
		if (nodes2Edges != null) {		
			nodes2Edges.add(node1);
		}

	}

	@Override
	public String toString() {
		StringBuilder graphString = new StringBuilder();
		for (Entry<GraphNode<T>, List<GraphNode<T>>> entry : adjacentList.entrySet()) {
			String nodeValue = entry.getKey().getValue().toString();
			String nodeEdges = entry.getValue().stream().map(node -> node.getValue().toString())
					.collect(Collectors.joining("-"));
			graphString.append(nodeValue);
			graphString.append("->");
			graphString.append(nodeEdges);
			graphString.append("\n");
		}
		return graphString.toString();
	}

	public static void main(String[] args) {

		GraphNode<Integer> node0 = new GraphNode<Integer>(0);
		GraphNode<Integer> node1 = new GraphNode<Integer>(1);
		GraphNode<Integer> node2 = new GraphNode<Integer>(2);
		GraphNode<Integer> node3 = new GraphNode<Integer>(3);
		GraphNode<Integer> node4 = new GraphNode<Integer>(4);
		GraphNode<Integer> node5 = new GraphNode<Integer>(5);
		GraphNode<Integer> node6 = new GraphNode<Integer>(6);
		

		Graph<Integer> graph = new Graph<Integer>();
		
		System.out.println("Nodes: "+graph.getNumberOfNodes());
		
		graph.addVertex(node0);
		graph.addVertex(node1);
		graph.addVertex(node2);
		graph.addVertex(node3);
		graph.addVertex(node4);
		graph.addVertex(node5);
		graph.addVertex(node6);
		
		System.out.println("Nodes: "+graph.getNumberOfNodes());
		
		graph.addEdge(node3, node1);
		graph.addEdge(node3, node4);
		graph.addEdge(node4, node2);
		graph.addEdge(node4, node5);
		graph.addEdge(node1, node2);
		graph.addEdge(node1, node0);
		graph.addEdge(node0, node2);
		graph.addEdge(node6, node5);
		
		
		System.out.println(graph.toString());
	}

}
