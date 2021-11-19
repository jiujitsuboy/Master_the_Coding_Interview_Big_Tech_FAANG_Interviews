package graph.network.time.optimal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import heaps.PriorityQueue;

/**
 * Given n networks nodes labeled for 1 to N and given a time array, which every
 * value is an array represented one edge (source node to target node plus the
 * weight of that edge, which represent time). Return the minimum time to send a
 * signal from a node K to all the other nodes. If it is not possible to reach
 * all nodes, return -1.
 * 
 * OPTIMAL APPROACH: Use Dijkstra algorithm to calculate the lowest cost path 
 *                   to all nodes and then get the maximum value obtained among
 *                   all the node and that is the answer. 
 *                   
 *                   This algorithm only works with positive weights
 * 
 * Constraints
 * 
 * 1- Can the graph be unconnected?, Yes (in this case we would return -1,
 * because not all vertex can be reached)
 * 
 * 2- Can we have negative weights for the edges? No, because the weight here
 * represent time, it is impossible to have negative time
 * 
 * 
 * Dijkstra algorithm:
 * 
 * 1 - Create a matrix or map with all the vertex value and initially with a 
 * infinite value on each of them.
 * 
 * 1 - We start from the initial node which in this case is from where we want to sent
 * the signal. This vertex in the matrix will have the value of ZERO, because we
 * are already there so there is no cost of moving to him.
 * 
 * 2 - we get all the edges of this vertex and see what is the cost to reach every
 * one of them and update that value in the matrix. We mark as completed this 
 * vertex we already explore.
 * 
 * 3 - We get the smallest value of the vertex in the matrix and that is the next
 * vertex to process.
 * 
 * 4 -  Repeat step 2 and 3 for this new vertex and update the values in the matrix
 * for every vertex this vertex has an edge to, if the value to update is lower
 * than the current one of that vertex, we updated otherwise we left the current
 * value.
 * 
 * 5 - We continue until we process all the vertex in the graph. Then our answer
 * will be the value of the vertex with the greatest value in the matrix or map.
 * 
 * When there is no path to a vertex (Disconnected graph), the resulting matrix
 * will have the value INFINITE in that vertex. So when we retrieve the maximum
 * value we can tell that there is no way to reach all the vertex and we will 
 * return -1
 * 
 * Example
 * 
 * 
 * n = 5 => nodes [1,2,3,4,5]
 * 
 * times = [[1,2,9],[1,4,2],[2,5,1],[4,2,4],[4,5,6],[3,2,3],[5,3,7],[3,1,5]]
 * 
 * 					     5 
 * 				  ___________ 
 * 				 V 9		 \ 
 * 				(1)--->(2)<\  \ 
 * 				 |   >  |   \  \ 
 * 			    2| 4/  1| 3  \  \ 
 * 				 V /    V     \ |
 * 				(4)--->(5)--->(3) 
 *                  6      7
 * 
 * if K = 1
 * 
 * The shortest path would be 1->4->2->5->3 which would take 2+4+1+7 = 14
 * 
 * Iteration 1: Initialize the matrix
 * 
 *  1: 0
 *  2: INF
 *  3: INF
 *  4: INF
 *  5: INF
 * 
 * Iteration 2: Grab k vertex, in this case 1 and get his edges. update 2(9) 
 * and 4(2) vertex
 * 
 * *1: 0
 *  2: 9
 *  3: INF
 *  4: 2
 *  5: INF
 *  
 * Iteration 3: Get the lowest value without being processed in the matrix which 
 * is 4, get his vertex. Update 2(6) because the cost from him is lower than the 
 * current one of 2 and 5(8) vertex
 * 
 * *1: 0
 *  2: 6
 *  3: INF
 * *4: 2
 *  5: 8 
 * 
 * Iteration 4: Get the lowest value without being processed in the matrix which 
 * is 2, get his vertex. Update 5(7) because the cost from him is lower than the 
 * current one of 5.
 * 
 * *1: 0
 * *2: 6
 *  3: INF
 * *4: 2
 *  5: 7 
 * 
 * Iteration 5: Get the lowest value without being processed in the matrix which 
 * is 5, get his vertex. Update 3(14).
 * 
 * *1: 0
 * *2: 6
 *  3: 14
 * *4: 2
 * *5: 7 
 *  
 *  
 * Iteration 5: Get the lowest value without being processed in the matrix which 
 * is 3, get his vertex. No value to update because 1 is ZERO. So we finish 
 * processing vertex
 * 
 * *1: 0
 * *2: 6
 * *3: 14
 * *4: 2
 * *5: 7  
 *  
 * We grab the max value from the matrix in this case 14. 
 *  
 *  
 *  
 * Example 3:
 * if the graph would be
 * 
  * 					5 
 * 				  ___________ 
 * 				 V 9		 \ 
 * 				(1)--->(2)<\  \ 
 * 				 |   >  |   \  \ 
 * 			    2| 4/  1| 3  \  \ 
 * 				 V /    V     \ |
 * 				(4)--->(5)    (3) 
 *                  6      7
 * 
 * k = 1
 * 
 * Node 3 is unreachable (there is no path to it), so the time taken would be -1
 * 
 * *1: 0
 * *2: 6
 * *3: INF
 * *4: 2
 * *5: 7 
 * 
 * but if k = 3
 * 
 * all the nodes are reachable so the shortest path will be 3->1->->4->2->5 and
 * the time taken would be 5+2+4+1 = 12
 * 
 * 
 * 
 * 
 * @author Jose
 *
 */
public class Exercise25_Network_Time_Delay_Dijkstra {

	public static void main(String[] args) {		
		
		int numNodes = 5;
		int startingPoint = 1;
		int[][] times = { { 1, 2, 9 }, { 1, 4, 2 }, { 2, 5, 1 }, { 4, 2, 4 }, { 4, 5, 6 }, { 3, 2, 3 }, { 5, 3, 7 },
				{ 3, 1, 5 } };

//		int numNodes = 5;
//		int startingPoint = 1;
//		int[][] times = { { 1, 2, 9 }, { 1, 4, 2 }, { 2, 5, 1 }, { 4, 2, 4 }, { 4, 5, 6 }, { 3, 2, 3 }, { 3, 1, 5 } };

		
//		int numNodes = 2; int startingPoint = 1; int[][] times = { { 1, 2, 9 }};
		

		
//		int numNodes = 1; int startingPoint = 1; int[][] times = { {} };
		

		
//		int numNodes = 1; int startingPoint = 1; int[][] times = {};
		

		
//		int numNodes = 1; int startingPoint = 1; int[][] times = null;
		

		System.out.println(String.format("Minumun time from %d to all other vertex: %d", startingPoint,
				calculateMinimumTimeFromStartNodeToAllNodes(numNodes, startingPoint, times)));
	}

	/**
	 * Calculate the lowest path weigh that traverse all the graph (touch all the
	 * vertexes)
	 * 
	 * Time Complexity: O(n+t * log n): createAdjacentListWithWeigths => O(n+t),
	 * calculateDijstraforVertexK => O(n+t * log n)
	 * 
	 * Space Complexity: O(n^2): createAdjacentListWithWeigths => O(n^2),
	 * calculateDijstraforVertexK => O(n)
	 * 
	 * @param numNodes      Total number of vertex in the graph
	 * @param startingPoint Node from where we are going to try to traverse the
	 *                      whole graph and return the lowest path weight sum.
	 * @param times         matrix with edges and their respective weight
	 * @return
	 */
	private static int calculateMinimumTimeFromStartNodeToAllNodes(int numNodes, int startingPoint, int[][] times) {

		int minTime = -1;

		if (times != null && startingPoint > -1 && startingPoint <= numNodes) {
			List<List<List<Integer>>> adjacentListWithWeigth = createAdjacentListWithWeigths(numNodes, times);

			minTime = calculateDijstraforVertexK2(numNodes, startingPoint, adjacentListWithWeigth);
			System.out.println((minTime == Integer.MAX_VALUE) ? -1 : minTime);
			minTime = calculateDijstraforVertexK(numNodes, startingPoint, adjacentListWithWeigth);
		}

		return (minTime == Integer.MAX_VALUE) ? -1 : minTime;
	}

	/**
	 * Using the Dijstra algorithm calculate the less cost path to each node from
	 * startingPoint, storing this information in an array. When the algorithm
	 * finished, we take the max value of the array. If the max value is
	 * INTEGER.MAX_VALUE, we return -1 because it was not possible to reach every
	 * node in the graph
	 * 
	 * Time Complexity:  O(n+t * log n): we have O(n) to create the vertexes list 
	 * (Dijstra map), we iterate the priority Queue in the worst case n times and per
	 * vertex we can have t vertexes (we can have n-1 edges, but because those edges
	 * are only visited once we don´t have O(n^2) but O(n+t)) so it would be O(n+t * log n). 
	 * 
	 * Space Complexity: O(n): we have the vertexes list (Dijstra map) and the
	 * priorityQueue with n-1 vertex in the worst case
	 * 
	 * @param numNodes
	 * @param startingPoint
	 * @param adjacentListWithWeigth
	 * @return
	 */
	private static int calculateDijstraforVertexK(int numNodes, int startingPoint,
			List<List<List<Integer>>> adjacentListWithWeigth) {

		int numTimes = 0;
		boolean[] vertexProcessed = new boolean[numNodes];
		// Build the dijkstra list of nodes, where all node are initialize to Infinity
		// and only the starting node to -infinity
		List<DijkstraNode> vertexes = IntStream.rangeClosed(1, numNodes).boxed().map(value ->

		(startingPoint == value) ? new DijkstraNode(value, 0) : new DijkstraNode(value)

		).collect(Collectors.toList()); // O(n)

		// min heap to retrieve the dijkstra node next to process
		PriorityQueue<DijkstraNode> minVertexValueNoProcesed = new PriorityQueue<>();
		minVertexValueNoProcesed.insert(vertexes.get(startingPoint - 1));

		while (!minVertexValueNoProcesed.isEmpty()) {  // O(n)
			numTimes++;
			DijkstraNode vertex = minVertexValueNoProcesed.remove(); // O(log n)

			// mark vertex as processed
			vertexProcessed[vertex.getName() - 1] = true;
			List<List<Integer>> vertexMatrix = adjacentListWithWeigth.get(vertex.getName() - 1);

			//O(vertex) => because we only iterate this vertex once, the complexity is plus (O(n+log(n) + v) 
			// and not multiply (O(n+log(n) * v) 
			for (List<Integer> edgeWithWeight : vertexMatrix) { 
				int edgeVertexName = edgeWithWeight.get(0);
				int edgeVertexCost = edgeWithWeight.get(1);

				DijkstraNode dijkstraNode = vertexes.get(edgeVertexName - 1);
				int newVertexCost = vertex.getValue() + edgeVertexCost;

				if (newVertexCost < dijkstraNode.getValue()) {
					dijkstraNode.setValue(newVertexCost);
					if (!vertexProcessed[edgeVertexName - 1]) {
						vertexProcessed[edgeVertexName - 1] = true;
						minVertexValueNoProcesed.insert(dijkstraNode);
					}
				}
			}

		}

		System.out.println("numTimes: " + numTimes);
		int maxValue = vertexes.stream().map(node -> node.getValue()).mapToInt(num -> num).max().getAsInt();
		return (maxValue == Integer.MAX_VALUE) ? -1 : maxValue;
	}

	/**
	 * This variation took the nodes from the Dijstra matrix without taking the
	 * lower cost value not processed, instead it took the node with the less value
	 * (name)
	 * 
	 * @param numNodes
	 * @param startingPoint
	 * @param adjacentListWithWeigth
	 * @return
	 */
	private static int calculateDijstraforVertexK2(int numNodes, int startingPoint,
			List<List<List<Integer>>> adjacentListWithWeigth) {
		int numTimes = 0;
		boolean[] vertexProcessed = new boolean[numNodes];
		// Build the dijkstra list of nodes, where all node are initialize to Infinity
		// and only the starting node to -infinity
		List<Integer> vertexes = IntStream.range(0, numNodes).boxed().map(value -> Integer.MAX_VALUE)
				.collect(Collectors.toList());

		vertexes.set(startingPoint - 1, 0);
		// min heap to retrieve the dijkstra node next to process
		PriorityQueue<Integer> minVertexValueNoProcesed = new PriorityQueue<>();
		minVertexValueNoProcesed.insert(startingPoint - 1);

		while (!minVertexValueNoProcesed.isEmpty()) {
			numTimes++;
			Integer vertexIndex = minVertexValueNoProcesed.remove();

			// mark vertex as processed
			vertexProcessed[vertexIndex] = true;
			List<List<Integer>> vertexMatrix = adjacentListWithWeigth.get(vertexIndex);

			for (List<Integer> edgeWithWeight : vertexMatrix) {
				int edgeVertexIndex = edgeWithWeight.get(0);
				int edgeVertexWeigth = edgeWithWeight.get(1);

				Integer vertexWeight = vertexes.get(vertexIndex);
				Integer edgeVertexCost = vertexes.get(edgeVertexIndex - 1);
				int newVertexCost = vertexWeight + edgeVertexWeigth;

				if (newVertexCost < edgeVertexCost) {
					vertexes.set(edgeVertexIndex - 1, newVertexCost);
					if (!vertexProcessed[edgeVertexIndex - 1]) {
						vertexProcessed[edgeVertexIndex - 1] = true;
						minVertexValueNoProcesed.insert(edgeVertexIndex - 1);
					}

				}
			}

		}

		System.out.println("numTimes2: " + numTimes);
		int maxValue = vertexes.stream().mapToInt(num -> num).max().getAsInt();
		return (maxValue == Integer.MAX_VALUE) ? -1 : maxValue;
	}

	/**
	 * Create the adjacent list with weights of the graph.
	 * 
	 * Time Complexity:O(n+t): iterate from zero to numNodes(n) and through all the
	 * rows of times(t).
	 * 
	 * Space Complexity: O(n^2): A list of numNodes(n) positions, on each position
	 * we going to have another list, which can have n-1 elements, and each element
	 * could have 3 possible values O(n^2 + 3)
	 * 
	 * @param numNodes number of vertex on the graph
	 * @param times    edges composed of source node, target node and the weight of
	 *                 that edge.
	 * @return List<List<List<Integer>>> adjacent list
	 */
	private static List<List<List<Integer>>> createAdjacentListWithWeigths(int numNodes, int[][] times) {

		List<List<List<Integer>>> adjacentListWithWeigths = new ArrayList<List<List<Integer>>>(numNodes);

		IntStream.range(0, numNodes).forEach(matrix -> adjacentListWithWeigths.add(new ArrayList<List<Integer>>()));

		for (int index = 0; index < times.length; index++) {
			if (times[index].length > 0) {
				List<List<Integer>> vertexMatrix = adjacentListWithWeigths.get(times[index][0] - 1);
				vertexMatrix.add(Arrays.asList(times[index][1], times[index][2]));
				adjacentListWithWeigths.set(times[index][0] - 1, vertexMatrix);
			}
		}
		return adjacentListWithWeigths;
	}

}
