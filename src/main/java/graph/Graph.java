package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Implementation of a Graph Data Structure
 * 
 * Graph can be:
 * 
 * 1-Undirected/Directed: which indicate the direction of navigation between
 * vertex connected by edges. 2-Weighted/UnWeighted: If the edge have some value
 * which indicated if preference or not between other edges. 3- Cyclic/Acyclic:
 * with cycles or not 4- Connected/UnConnected: two or more graph that are not
 * connect to each other.
 * 
 * A Binary tree is a directed, unweighed graph. A Matrix is a undirected,
 * unweighed, cyclic graph A LinkedList is also a directed, unweighed graph
 * 
 * 
 * Graph Representation:
 * 
 * 1 5 \ / 0 - 3 - 4 / 2
 * 
 * 1- Adjacent List: we have for each vertex it values follow by a list with all
 * the other vertex to which it has a edge.
 * 
 * 0 [3] 1 [3] 2 [3] 3 [0,1,2,4] => [[3],[3],[3],[0,1,2,4],[3,5],[4]] 4 [3,5] 0
 * 1 2 3 4 5 5 [4]
 * 
 * 2- Adjacent Matrix: Using a matrix of n*n where every vertex is on the X and
 * Y axis, each connection between vertex is represented with the cell that
 * connect the edge on the X and Y vertex with a value of 1.
 * 
 * 0 1 2 3 4 5 --------------------- 0 | 1 1 | 1 2 | 1 3 | 1 1 1 1 4 | 1 1 5 | 1
 * 
 * 
 * 3 -Edge List: Using an array or arrays, every position in the array is
 * another array that depict the connection between two edges.
 * 
 * 
 * Traverse Graphs:
 * 
 * Bread First Search: Using a queue, we grab the first edge in the graph, add
 * to the queue, and then extract the edge get it's value, and grab every edge
 * it has and store in the queue the vertex of those edges. Continue doing the
 * same until the queue is empty.
 * 
 * Depth First Search
 * 
 * 
 * @author Jose
 *
 * @param <T>
 */
public class Graph<T> {

	private Map<T, GraphNode<T>> vertexes;

	public Graph() {
		vertexes = new LinkedHashMap<>();

	}

	public long getNumberOfNodes() {
		return vertexes.size();
	}

	public Map<T, GraphNode<T>> getVertexes() {
		return vertexes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(vertexes);
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
		return Objects.equals(vertexes, other.vertexes);
	}

	public void addVertex(GraphNode<T> node) {
		vertexes.put(node.getValue(), node);
	}

	public void addEdge(GraphNode<T> node1, GraphNode<T> node2, boolean unDirected) {

		List<GraphNode<T>> nodes1Edges = (vertexes.containsValue(node1)) ? vertexes.get(node1.getValue()).getEdges()
				: null;

		if (nodes1Edges != null) {
			nodes1Edges.add(node2);
		}

		if (unDirected) {
			List<GraphNode<T>> nodes2Edges = (vertexes.containsValue(node2)) ? vertexes.get(node2.getValue()).getEdges()
					: null;
			if (nodes2Edges != null) {
				nodes2Edges.add(node1);
			}
		}

	}

	public List<T> getBreathFirstSearch() {

		List<T> vertexValues = new ArrayList<>();

		if (!vertexes.isEmpty()) {
			vertexValues = getBreathFirstSearch(vertexes.values().iterator().next());
		}

		return vertexValues;
	}

	/**
	 * Generate an array with the values of the graph using DFS. Grab the first
	 * vertex of the graph, insert it into the queue and the result array and for
	 * every edged added that vertex to the queue. Do this until the queue is empty
	 * 
	 * Time Complexity: O(n): touch every vertex in the graph
	 * 
	 * Space Complexity: O(n): generate an array with the same size as the input,
	 * plus the queue and the visited map
	 * 
	 * @return
	 */
	public List<T> getBreathFirstSearch(GraphNode<T> node) {

		List<T> vertexValues = new ArrayList<>();

		if (node != null && !vertexes.isEmpty()) {
			Queue<GraphNode<T>> vertexQueued = new LinkedList<>();
			Map<GraphNode<T>, Boolean> visited = new HashMap<>();

			GraphNode<T> initialVertex = vertexes.get(node.getValue());

			if (initialVertex != null) {
				visited.put(initialVertex, true);
				vertexQueued.add(initialVertex);

				while (!vertexQueued.isEmpty()) {
					GraphNode<T> vertex = vertexQueued.poll();
					vertexValues.add(vertex.getValue());

					for (GraphNode<T> edge : vertex.getEdges()) {
						if (!visited.containsKey(edge)) {
							vertexQueued.add(edge);
							visited.put(edge, true);
						}
					}
				}
			}

		}

		return vertexValues;
	}

	public List<T> getDepthFirstSearch() {
		List<T> vertexValues = new ArrayList<>();

		if (!vertexes.isEmpty()) {
			vertexValues = getDepthFirstSearch(vertexes.values().iterator().next());
		}

		return vertexValues;
	}

	/**
	 * Grabbing the first vertex, get their edges and recursive call each one. So we
	 * go as deep has edges on vertex has.
	 * 
	 * Time Complexity: O(n): touch every vertex in the graph
	 * 
	 * Space Complexity: O(n): generate an array with the same size as the input,
	 * plus the visited map and the stack which in the worst case store all the
	 * vertex of the graph
	 * 
	 * @return
	 */
	public List<T> getDepthFirstSearch(GraphNode<T> node) {
		List<T> vertexValues = new ArrayList<>();

		if (node != null && !vertexes.isEmpty()) {
			Map<GraphNode<T>, Boolean> visited = new HashMap<>();

			GraphNode<T> initialVertex = vertexes.get(node.getValue());
			if (initialVertex != null) {
				getDepthFirstSearchAux(vertexValues, visited, initialVertex);
			}
		}

		return vertexValues;
	}

	private void getDepthFirstSearchAux(List<T> vertexValues, Map<GraphNode<T>, Boolean> visited, GraphNode<T> vertex) {

		vertexValues.add(vertex.getValue());
		visited.put(vertex, true);

		for (GraphNode<T> edge : vertex.getEdges()) {
			if (!visited.containsKey(edge)) {
				getDepthFirstSearchAux(vertexValues, visited, edge);
			}
		}

	}

	@Override
	public String toString() {
		StringBuilder graphString = new StringBuilder();
		for (Entry<T, GraphNode<T>> entry : vertexes.entrySet()) {
			String nodeValue = entry.getKey().toString();
			String nodeEdges = entry.getValue().getEdges().stream().map(node -> node.getValue().toString())
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
		GraphNode<Integer> node7 = new GraphNode<Integer>(7);
		GraphNode<Integer> node8 = new GraphNode<Integer>(8);

		Graph<Integer> graph = new Graph<Integer>();

		System.out.println("Nodes: " + graph.getNumberOfNodes());

		graph.addVertex(node0);
		graph.addVertex(node1);
		graph.addVertex(node2);
		graph.addVertex(node3);
		graph.addVertex(node4);
		graph.addVertex(node5);
		graph.addVertex(node6);
		graph.addVertex(node7);
		graph.addVertex(node8);

		System.out.println("Nodes: " + graph.getNumberOfNodes());

		graph.addEdge(node1, node0, true);
		graph.addEdge(node3, node0, true);
		graph.addEdge(node3, node2, true);
		graph.addEdge(node3, node4, true);
		graph.addEdge(node3, node5, true);
		graph.addEdge(node4, node6, true);
		graph.addEdge(node6, node7, true);
		graph.addEdge(node8, node2, true);

		System.out.println("\n" + graph.toString());
		System.out.println("BFS: " + graph.getBreathFirstSearch());
		System.out.println("DFS: " + graph.getDepthFirstSearch());
	}

}
