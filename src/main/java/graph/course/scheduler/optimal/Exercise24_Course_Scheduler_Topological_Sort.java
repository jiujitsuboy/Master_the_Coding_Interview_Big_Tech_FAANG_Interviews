package graph.course.scheduler.optimal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Given a N number of course and list of tuple of prerequisites, find if it is
 * possible to end all courses (boolean answer)
 * 
 * N represent the course labeled from 0 to N-1
 * 
 * The list of course prerequisites is an array of tuples, where the second
 * value in the tuple represent the prerequisite of the course which is the
 * first value. (this is a directed edge), so we would have a directed graph
 * 
 * Constraints
 * 
 * 1 - Can we have courses unconnected to other courses. Yes, account for
 * separate course chains. We can have courses that has no relation with other
 * courses, so each chain can be taken by the student in different orders
 * without any prerequisite conflict.
 * 
 * 
 * @author Jose
 *
 */

public class Exercise24_Course_Scheduler_Topological_Sort {

	public static void main(String[] args) {

		int numCourses = 6;
//		int[][] prerequisite = { { 1, 0 }, { 2, 1 }, { 2, 5 }, { 0, 3 }, { 4, 3 }, { 3, 5 }, { 4, 5 } };
		int[][] prerequisite = { { 1, 0 }, { 2, 1 }, { 2, 5 }, { 0, 3 }, { 4, 3 }, { 3, 5 }, { 4, 5 }, { 0, 2 } };

		System.out.println("Is possible to finish this course schedule? : " + isFinishable(numCourses, prerequisite));
	}

	/**
	 * Verify if the graph has loops, if it has, them we can finished the courses.
	 * 
	 * Time Complexity: O(p+n) due to buildAdjacentList 
	 * 
	 * Space Complexity: O(n^2) due to buildAdjacentList
	 * 
	 * @param numCourses number of courses
	 * @param prerequisite tuple of prerequisite of a course
	 * @return boolean is finishable or not the course schedule
	 */
	public static boolean isFinishable(int numCourses, int[][] prerequisite) {

		List<List<Integer>> adjacentList = buildAdjacentList(numCourses, prerequisite);

//		return isCyclesFree_DFS(adjacentList);
		return isCyclesFree_BFS(adjacentList);

	}

	/**
	 * Create the adjacent list based on the prerequisite matrix and numCourses
	 * 
	 * Time Complexity: O(p+n): We iterate from 0 to n-1 to generate the courses and
	 * them traverse all the rows of the matrix P.
	 * 
	 * Space Complexity: O(n^2): we create a list of list which have all the courses
	 * and the prerequisite, so the first list will be as long as courses we have,
	 * and each sublist can have all the courses.
	 * 
	 * 
	 * @param numCourses   number of courses
	 * @param prerequisite list of tuple of prerequisite between courses
	 * @return List<List<Integer>> adjacent list
	 */
	private static List<List<Integer>> buildAdjacentList(int numCourses, int[][] prerequisite) {

		List<List<Integer>> adjacentList = Stream.iterate(0, x -> x + 1).map(num -> new ArrayList<Integer>())
				.limit(numCourses).collect(Collectors.toList());

//		adjacentList = IntStream.range(0,numCourses).boxed().map(num -> new ArrayList<Integer>())		
//		.collect(Collectors.toList());

		for (int index = 0; index < prerequisite.length; index++) {
			adjacentList.get(prerequisite[index][1]).add(prerequisite[index][0]);
		}

		return adjacentList;
	}

	/**
	 * For every vertex in the adjacentList we validate if it has or not cycles.
	 * 
	 * Time Complexity: O(n): we traverse all the vertexes.
	 * 
	 * Space Complexity: O(n): hasCycle store in the visited Set in the worst case
	 * n-1 vertex. plus the stack which could be also n-1 values.
	 * 
	 * @param adjacentList graph adjacent list
	 * @return boolean has or not cycles
	 */
	private static boolean isCyclesFree_DFS(List<List<Integer>> adjacentList) {

		boolean isCyclesFree = true;

		for (int vertexIndex = 0; vertexIndex < adjacentList.size(); vertexIndex++) {

			Set<Integer> visitedVertex = new HashSet<>();
			visitedVertex.add(vertexIndex);

			if (hasCycle_DFS(vertexIndex, adjacentList, visitedVertex)) {
				isCyclesFree = false;
				break;
			}
		}

		return isCyclesFree;
	}

	/**
	 * receiving a vertex, we take all his edges and traverse each one using DFS
	 * approach (recursive). On every level of the previous traversal we add the
	 * previous vertex to a visited Set so we can see if there is a cycle (if we see
	 * the same value of vertex in the Set). When we return for any of the recursive
	 * call, we remove the vertex from the Set, so other paths, wont think they have
	 * a cycle.
	 * 
	 * Time Complexity: O(n): brute force approach, we traverse all the vertexes.
	 * 
	 * Space Complexity: O(n): we store in the visited Set in the worst case n-1
	 * vertex. plus the stack which could be also n-1 values.
	 * 
	 * @param rootVertexIndex index of the vertex in the adjacentList
	 * @param adjacentList    graph adjacent list
	 * @param visitedVertex   Set with all the vertex already traversed in that path
	 * @return boolean. Has or not cycles that path
	 */
	private static boolean hasCycle_DFS(int rootVertexIndex, List<List<Integer>> adjacentList,
			Set<Integer> visitedVertex) {

		boolean hasCycle = false;

		List<Integer> edges = adjacentList.get(rootVertexIndex);

		for (int edgeIndex = 0; edgeIndex < edges.size(); edgeIndex++) {
			int subVertexIndex = edges.get(edgeIndex);
			if (visitedVertex.contains(subVertexIndex)) {
				hasCycle = true;
				break;
			}
			visitedVertex.add(subVertexIndex);
			hasCycle = hasCycle_DFS(subVertexIndex, adjacentList, visitedVertex);
			if (hasCycle) {
				break;
			}
			visitedVertex.remove(subVertexIndex);
		}
		return hasCycle;
	}

	/**
	 * For every vertex in the adjaentList we validate if it has or not cycles.
	 * 
	 * Time Complexity: O(n): we traverse all the vertexes.
	 * 
	 * Space Complexity: O(n): hasCycle store in the visited Set in the worst case
	 * n-1 vertex. plus the vertex queue which could be also n-1 values.
	 * 
	 * @param adjacentList graph adjacent list
	 * @return boolean has or not cycles
	 */
	private static boolean isCyclesFree_BFS(List<List<Integer>> adjacentList) {

		boolean isCyclesFree = true;

		for (int vertexIndex = 0; vertexIndex < adjacentList.size(); vertexIndex++) {

			Queue<Integer> pendingVertexes = new LinkedList<>();

			// fill the queue with all edges of the current vertex

			// this could be n-1, n-2, n-3, 1 => which converge to n => O(n)
			adjacentList.get(vertexIndex).stream().forEach(num -> pendingVertexes.add(num));

			if (hasCycle_BFS(vertexIndex, adjacentList, pendingVertexes)) {
				isCyclesFree = false;
				break;
			}
		}

		return isCyclesFree;
	}

	/**
	 * receiving a root vertex, we all his edges in the queue if pendingVerteses, we
	 * traverse each one using BFS approach. We validate that any of the vertex
	 * polled from the queue are not the same as the received root vertex. We use a
	 * visitedVertex Set to avoid traverse already traversed vertexes.
	 * 
	 * Time Complexity: O(n): brute force approach, we traverse all the edges of a
	 * vertex.
	 * 
	 * Space Complexity: O(n): we store in the visited Set in the worst case n-1
	 * vertex. plus the queue which could be also n-1 values.
	 * 
	 * @param rootVertexIndex
	 * @param adjacentList
	 * @param pendingVertexes
	 * @return
	 */
	private static boolean hasCycle_BFS(int rootVertexIndex, List<List<Integer>> adjacentList,
			Queue<Integer> pendingVertexes) {

		boolean isCyclesFree = false;
		Set<Integer> visitedVertex = new HashSet<>();

		while (!pendingVertexes.isEmpty()) {
			int vertexIndex = pendingVertexes.poll();

			// validate if somehow we goes again to the rootVertex
			if (vertexIndex == rootVertexIndex) {
				isCyclesFree = true;
				break;
			}

			visitedVertex.add(vertexIndex);

			for (int edge : adjacentList.get(vertexIndex)) {
				if (!visitedVertex.contains(edge)) {
					visitedVertex.add(edge);
					pendingVertexes.add(edge);
				}
			}
		}

		return isCyclesFree;

	}

}
