package graph.network.time.brute_force;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Given n networks nodes labeled for 1 to N and given a time array, which every
 * value is an array represented one edge (source node to target node plus the
 * weight of that edge, which represent time). Return the minimum time to send a
 * signal from a node K to all the other nodes. If it is not possible to reach
 * all nodes, return -1.
 * 
 * 
 * Constraints
 * 
 * 1- Can the graph be unconnected?, Yes (in this case we would return -1,
 * because not all vertex can be reached)
 * 
 * 2- Can we have negative weights for the edges? No, because the weight here
 * represent time, it is impossible to have negative time
 * 
 * Example
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
 * Example 2:
 * 
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
 * but if k = 3
 * 
 * all the nodes are reachable so the shortest path will be 3->1->->4->2->5 and
 * the time taken would be 5+2+4+1 = 12
 * 
 * 
 * THIS APPROACH BUILDS THE STATE SPACE TREE (a tree which every branch depict 
 * a possible path to the answer) OF THE GRAPH TO TAKE THE LESS EXPENSIVE PATH.
 * 
 * @author Jose
 *
 */
public class Exercise25_Network_Time_Delay {

	static int numtimes=0;
	public static void main(String[] args) {
		int numNodes = 5;
		int startingPoint = 1;
		int[][] times = { { 1, 2, 9 }, { 1, 4, 2 }, { 2, 5, 1 }, { 4, 2, 4 }, { 4, 5, 6 }, { 3, 2, 3 }, { 5, 3, 7 },
				{ 3, 1, 5 } };

		/*int numNodes = 5;
		int startingPoint = 1;
		int[][] times = { { 1, 2, 9 }, { 1, 4, 2 }, { 2, 5, 1 }, { 4, 2, 4 }, { 4, 5, 6 }, { 3, 2, 3 },
				{ 3, 1, 5 } };*/

		/*int numNodes = 2;
		int startingPoint = 1;
		int[][] times = { { 1, 2, 9 }};*/

		/*int numNodes = 1;
		int startingPoint = 1;
		int[][] times = { {} };*/

		/*int numNodes = 1;
		int startingPoint = 1;
		int[][] times = {};*/

		/*int numNodes = 1;
		int startingPoint = 1;
		int[][] times = null;*/

		System.out.println(String.format("Minumun time from %d to all other vertex: %d", startingPoint,
				calculateMinimumTimeFromStartNodeToAllNodes(numNodes, startingPoint, times)));
	}

	/**
	 * Calculate the lowest path weigh that traverse all the graph (touch all the vertexes)
	 * 
	 * Time Complexity: O(n+v+t): createAdjacentListWithWeigths => O(n+t), getMinimumTimeFromStartNodeToAllNodes => O(n+v)
	 * 
	 * Space Complexity: O(n+t): createAdjacentListWithWeigths => O(n+t), getMinimumTimeFromStartNodeToAllNodes => O(1)
	 * 
	 * @param numNodes      Total number of vertex in the graph
	 * @param startingPoint Node from where we are going to try to traverse the
	 *                      whole graph and return the lowest path weight sum.
	 * @param times matrix with edges and their respective weight
	 * @return
	 */
	private static int calculateMinimumTimeFromStartNodeToAllNodes(int numNodes, int startingPoint, int[][] times) {

		int[] minTime = { -1, -1 };

		if (times != null && startingPoint > -1 && startingPoint <= numNodes) {
			List<List<List<Integer>>> adjacentListWithWeigth = createAdjacentListWithWeigths(numNodes, times);
			Set<Integer> visitedVertex = new HashSet<>();
			int[] timeSoFarAndVertexVisited = { 0, 1 };

			minTime = getMinimumTimeFromStartNodeToAllNodes(adjacentListWithWeigth, visitedVertex, startingPoint,
					numNodes, timeSoFarAndVertexVisited);
		}

		return (minTime[1] != numNodes) ? -1 : minTime[0];
	}

	/**
	 * Recursive function which take a vertex, get is edges and using DFS goes as
	 * deep as it can, summing the weight of the edge taken and counting how many
	 * vertex we traversed. When it traverse all the possible combination of paths,
	 * it would take the lowest cost path (the smaller sum of weights) which has
	 * traversed all the nodes in the graph(it discard those path that don´t touch
	 * every vertex)
	 * 
	 * Time Complexity:O(n+v): for every vertex we iterate over his edges, which can
	 * be n-1 edges, but this edges are unique to the vertex, so that is why we sum
	 * and not multiply.
	 * 
	 * Space Complexity:O(1): we create 2 arrays of two position that don´t depend
	 * from the input.
	 * 
	 * @param adjacentListWithWeigth    adjacent list with corresponding edge's
	 *                                  weight
	 * @param visitedVertex             Set which store those vertex already visited
	 *                                  to avoid traversing the graph on an endless
	 *                                  loop
	 * @param startingPoint             Node from where to grab the edges to
	 *                                  traverse
	 * @param numNodes                  Total number of vertex in the graph
	 * @param timeSoFarAndVertexVisited array with two values, sum of the weight of
	 *                                  edges traversed and how many vertex has been
	 *                                  traversed
	 * @return int[] array with two values, sum of the lowest path's weight and the
	 *         number of vertex traversed.
	 */
	private static int[] getMinimumTimeFromStartNodeToAllNodes(List<List<List<Integer>>> adjacentListWithWeigth,
			Set<Integer> visitedVertex, int startingPoint, int numNodes, int[] timeSoFarAndVertexVisited) {
		System.out.println(++numtimes);
		int[] minValueEdges = { Integer.MAX_VALUE, timeSoFarAndVertexVisited[1] };
		int[] tempTimeSoFarAndVertexVisited = new int[2];

		visitedVertex.add(startingPoint);
		List<List<Integer>> vertexMatrix = adjacentListWithWeigth.get(startingPoint - 1);

		for (List<Integer> edgeWithWeight : vertexMatrix) {
			int vertexValue = edgeWithWeight.get(0);
			if (!visitedVertex.contains(vertexValue)) {
				tempTimeSoFarAndVertexVisited[0] = timeSoFarAndVertexVisited[0] + edgeWithWeight.get(1);
				tempTimeSoFarAndVertexVisited[1] = timeSoFarAndVertexVisited[1] + 1;

				tempTimeSoFarAndVertexVisited = getMinimumTimeFromStartNodeToAllNodes(adjacentListWithWeigth,
						visitedVertex, vertexValue, numNodes, tempTimeSoFarAndVertexVisited);

				if (tempTimeSoFarAndVertexVisited[1] == numNodes
						&& tempTimeSoFarAndVertexVisited[0] < minValueEdges[0]) {
					minValueEdges[0] = tempTimeSoFarAndVertexVisited[0];
					minValueEdges[1] = tempTimeSoFarAndVertexVisited[1];

				}
				visitedVertex.remove(vertexValue);
			}
		}

		return (minValueEdges[0] == Integer.MAX_VALUE) ? timeSoFarAndVertexVisited : minValueEdges;

	}

	/**
	 * Create the adjacent list with weights of the graph.
	 * 
	 * Time Complexity:O(n+t): iterate from zero to numNodes(n) and through all the
	 * rows of times(t).
	 * 
	 * Space Complexity: O(n+t): We create a list with numNodes(n) positions, on each position
	 * we going to have another list, which can have n-1 elements (the edges defined 
	 * in times array), and each element could have 3 possible values O(n^2 + 3)
	 * 
	 * @param numNodes number of vertex on the graph
	 * @param times    edges composed of source node, target node and the weight of
	 *                 that edge.
	 * @return List<List<List<Integer>>> adjacent list
	 */
	private static List<List<List<Integer>>> createAdjacentListWithWeigths(int numNodes, int[][] times) {

		List<List<List<Integer>>> adjacentListWithWeigths = new ArrayList<List<List<Integer>>>(numNodes);

		//O(n)
		IntStream.range(0, numNodes).forEach(matrix -> adjacentListWithWeigths.add(new ArrayList<List<Integer>>()));

		//O(t)
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
