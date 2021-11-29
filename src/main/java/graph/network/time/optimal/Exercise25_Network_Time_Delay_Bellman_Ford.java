package graph.network.time.optimal;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *  
 * Given n networks nodes labeled for 1 to N and given a time array, which every
 * value is an array represented one edge (source node to target node plus the
 * weight of that edge, which represent time). Return the minimum time to send a
 * signal from a node K to all the other nodes. If it is not possible to reach
 * all nodes, return -1.
 * 
 * OPTIMAL APPROACH: Use BELLMAN-FORD is uses DYNAMIC PRORAMMING to calculate 
 *                   the lowest cost path to all nodes and then get the maximum 
 *                   value obtained among all the node and that is the answer. 
 *                   
 *                   This algorithm have one constrain and is that it don´t works
 *                   correctly when there is a NEGATIVE CYCLES to the starting vertex.
 *                   This mean that if a negative edge reach starting point, it
 *                   is the possibility that on every iteration, the cost to this
 *                   edge decrement. (one way to detect this is, run the algorithm
 *                   one time more that the vertex-1 times and check if in this
 *                   iteration the value of the starting node gets decremented 
 *                   below ZERO, if it does, we have a negative loop.)
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
 * times = [[1,2,9],[3,2,3],[5,3,7],[3,1,5],[2,5,-3],[4,5,6],[1,4,2],[4,2,-4]]
 * 
 * 					     5 
 * 				  ___________ 
 * 				 V   9		 \ 
 * 				(1)--->(2)<\  \ 
 * 				 |   >  |   \  \ 
 * 			    2|-4/ -3| 3  \  \ 
 * 				 V /    V     \ |
 * 				(4)--->(5)--->(3) 
 *                  6      7
 * 
 * if K = 1
 * 
 * The shortest path would be 1->4->2->5->3 which would take 2+4+1+7 = 14
 * 
 * We don´t create an adjacent list here, we only keeps the map with the vertex
 * and their current cost. We iterate over the times matrix vertex-1 times, and
 * we keep iterating if in the current iteration, any vertex cost value changed,
 * if not, we break the loop. 
 * 
 * Step 1: Initialize the matrix
 * 
 *  1: 0
 *  2: INF
 *  3: INF
 *  4: INF
 *  5: INF
 * 
 * Iteration 1: iterate the whole times matrix from start to end, what ever edge
 * we take from the times matrix which vertex's value is INF we skip this vertex
 * in this iteration. So for example the first element is (1,2) the cost of 2 
 * becomes 9, then (3,2), 3 is INF so we skip for this iteration, the same with
 * (5,3) and (3,1), but we grab (2,5) and 5 becomes 6 (we take the current value
 * of 2 which is memoization in this case, so we don´t have to recalculate from 
 * vertex 1 to 5), we skip (4,5) and then take (1,4) and 4 become 2 and finally
 * we take (4,2) and 2 become -2
 * 
 *  1: 0
 *  2: -2
 *  3: INF
 *  4: 2
 *  5: 5
 *  
 * Iteration 2: iterate again the whole times matrix, (1,2) 2 is less than 9, so 
 * don´t update, (3,2) skip, (5,3) 3 become 15 (8+7), (3,1) 1 is less than 20, 
 * so no update, (2,5) 5 becomes -5, (4,5) 5 is less than 8 so no update, (1,4)
 * 4 is equal to 2, no update, (4,2) 2 is equal to -2, so no update.
 * 
 *  1: 0
 *  2: -2
 *  3: 15
 *  4: 2
 *  5: -5 
 * 
 * Iteration 3: iterate again the whole times matrix, (1,2) 2 is less than 9, so 
 * don´t update, (3,2) 2 is less than 5 so no update, (5,3) 3 become 2 (-5+7), 
 * (3,1) 1 is less than 7, so no update, (2,5) 5 is equal to-5 so no update, 
 * (4,5) 5 is less than 8 so no update, (1,4) 4 is equal to 2, no update, 
 * (4,2) 2 is equal to -2, so no update.
 * 
 *  1: 0
 *  2: -2
 *  3: 2
 *  4: 2
 *  5: -5 
 * 
 * Iteration 4: iterate again the whole times matrix, (1,2) 2 is less than 9, so 
 * don´t update, (3,2) 2 is less than 5 so no update, (5,3) 3 is equal to 2 so 
 * no updates, (3,1) 1 is less than 7, so no update, (2,5) 5 is equal to-5 so no update, 
 * (4,5) 5 is less than 8 so no update, (1,4) 4 is equal to 2, no update, 
 * (4,2) 2 is equal to -2, so no update. We FINISH here because no vertex got udpated.
 * 
 *  1: 0
 *  2: -2
 *  3: 2
 *  4: 2
 *  5: -5   
 *  
 * We grab the max value from the matrix in this case 2. 
 *  
 *  
 * Example 3:
 * if the graph would be
 * 
 *  					5 
 * 				  ___________ 
 * 				 V   9		 \ 
 * 				(1)--->(2)<\  \ 
 * 				 |   >  |   \  \ 
 * 			    2|-4/ -3| 3  \  \ 
 * 				 V /    V     \ |
 * 				(4)--->(5)    (3) 
 *                  6      
 * 
 * k = 1
 * 
 * Node 3 is unreachable (there is no path to it), so the time taken would be -1
 * 
 *  1: 0
 *  2: -2
 *  3: INF
 *  4: 2
 *  5: 6 
 * 
 * but if k = 3
 * 
 * 
 *  1: 5
 *  2: 3
 *  3: 0
 *  4: 7
 *  5: 0
 * 
 * 
 * all the nodes are reachable so the shortest path will be 3->1->->4->2->5 and
 * the time taken would be 5+2+4+1 = 7
 * 
 * 
 * 
 * 
 * @author Jose
 *
 */
public class Exercise25_Network_Time_Delay_Bellman_Ford {

	public static void main(String[] args) {

		int numNodes = 5;
		int startingPoint = 1;
		int[][] times = { { 1, 2, 9 }, { 3, 2, 3 }, { 5, 3, 7 }, { 3, 1, 5 }, { 2, 5, -3 }, { 4, 5, 6 }, { 1, 4, 2 },
				{ 4, 2, -4 } };

//		int numNodes = 5;
//		int startingPoint = 1;
//		int[][] times = { { 1, 2, 9 }, { 3, 2, 3 }, { 3, 1, 5 }, { 2, 5, -3 }, { 4, 5, 6 }, { 1, 4, 2 }, { 4, 2, -4 } };

//		int numNodes = 2; int startingPoint = 1; int[][] times = { { 1, 2, 9 }};

//		int numNodes = 1; int startingPoint = 1; int[][] times = { {} };

//		int numNodes = 1; int startingPoint = 1; int[][] times = {};

//		int numNodes = 1; int startingPoint = 1; int[][] times = null;

//		int numNodes = 4;
//		int startingPoint = 1;
//		int[][] times = { { 1, 2, 2 }, { 1, 3, 3 }, { 3, 4, 4 }, { 4, 2, -6 }
//		};

//		Infinite loop is we dont use a set rather a list
//		int numNodes = 4;
//		int startingPoint = 1;
//		int[][] times = { { 1, 2, 2 }, { 1, 3, 3 }, { 3, 4, 4 }, { 4, 2, -6 },{ 4, 1, -9 }
//		};

//		int numNodes = 5;
//		int startingPoint = 1;
//		int[][] times = { { 1, 2, 10 }, { 1, 4, 30 }, { 1, 5, 100 }, { 2, 3, 50 },{ 3, 5, 10 },{ 4, 3, 20 },{ 4, 5, 60 }};

		System.out.println(String.format("Minumun time from %d to all other vertex: %d", startingPoint,
				calculateMinimumTimeFromStartNodeToAllNodes(numNodes, startingPoint, times)));
	}

	/**
	 * Calculate the lowest path weigh that traverse all the graph (touch all the
	 * vertexes)
	 * 
	 * Time Complexity: O(n*t): we iterate n-1 times (number of edges -1 in the
	 * worst case) and for each iteration we traverse the whole times list.
	 * 
	 * Space Complexity: O(n): we store a list with the weights for each vertex.
	 * 
	 * @param numNodes      Total number of vertex in the graph
	 * @param startingPoint Node from where we are going to try to traverse the
	 *                      whole graph and return the lowest path weight sum.
	 * @param times         matrix with edges and their respective weight
	 * @return min time take it by the signal to reach at every vertex.
	 */
	private static int calculateMinimumTimeFromStartNodeToAllNodes(int numNodes, int startingPoint, int[][] times) {

		int minTime = 0;

		if (times != null && startingPoint > -1 && startingPoint <= numNodes) {

			int iteration = 1;
			List<Integer> vertexes = IntStream.range(0, numNodes).boxed().map(value -> Integer.MAX_VALUE)
					.collect(Collectors.toList());

			vertexes.set(startingPoint - 1, 0);

			while (iteration++ < numNodes) {
				int changesInIteration = 0;
				
				for (int edgeIndex = 0; edgeIndex < times.length; edgeIndex++) {
					
					int sourceVertexIndex = times[edgeIndex][0];
					int targetVertexIndex = times[edgeIndex][1];
					int edgeWeigth = times[edgeIndex][2];

					int sourceVertexWeight = vertexes.get(sourceVertexIndex - 1);
					int targetVertexWeight = vertexes.get(targetVertexIndex - 1);

					if (sourceVertexWeight != Integer.MAX_VALUE
							&& (sourceVertexWeight + edgeWeigth) < targetVertexWeight) {
						vertexes.set(targetVertexIndex - 1, (sourceVertexWeight + edgeWeigth));
						changesInIteration++;
					}

				}
				if (changesInIteration == 0) {
					break;
				}
			}

			minTime = vertexes.stream().mapToInt(num -> num).max().getAsInt();

		}

		return (minTime == Integer.MAX_VALUE) ? -1 : minTime;
	}

}
