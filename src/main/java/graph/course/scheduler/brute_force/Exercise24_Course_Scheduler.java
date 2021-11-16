package graph.course.scheduler.brute_force;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
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
 * Using a topological sort we can reduce the time complexity from the brute
 * force approach from O(p + n ^3) to O(p + n ^2)
 * 
 * Topological sort consist in identify those vertexes that have in indegree (the
 * number of edges pointing to this vertex) of zero. We remove those vertexes (one
 * at a time), put it values into a indegree array and subtract one (reduce by
 * one the indegree of those vertexes) to those vertexes to which theses vertexes
 * points to, and repeat again until the we remove all the vertexes in the graph,
 * ending with the indegree array filled with all the vertex in the graph.
 * 
 * 
 * Example:
 * 
 * This graph 
 * 
 * 		   5     4
 * 		    \  / \
 *           ><   >
 * 		  2	  0   1
 *         \  |
 *          > V
 *            3
 * indegree of each vertex
 * 
 * 0: 2 (5 and 4 points to him)
 * 1: 1 (4 points to him)
 * 2: 0 (nobody points to him)
 * 3: 2 (0 and 2 points to him)
 * 4: 0 (nobody points to him)
 * 5: 0 (nobody points to him)
 * 
 * 1 -We have 3 possible starting points (4,5 o 2) which have zero indegree.
 * 
 * 		   5    4
 * 		    \  / \
 *           ><   >
 * 		  2	  0   1		indegree matrix = []
 *         \  |			indegree value =   0 => 2
 *          > V							   1 => 1
 *            3							   2 => 0
 *            							   3 => 2
 *            							   4 => 0
 *            							   5 => 0
 * 
 * 2 -We chose 4 for example, remove it from the graph,add it value to the indegree
 * array and substract one from vertex 0 and 1.
 * 
 *    	   5     
 * 		    \    
 *           >    
 * 		  2	  0   1		indegree matrix = [4]
 *         \  |         indegree value =   0 => 1
 *          > V							   1 => 0
 *            3							   2 => 0
 *            							   3 => 2
 *            							   4 => 0
 *            							   5 => 0
 *  
 * 3 -Now we take vertex 2, remove it, added to the indegree array and subtract one
 * from vertex 3.
 * 
 *    	   5     
 * 		    \    
 *           >    
 * 		   	  0   1		indegree matrix = [4,2]
 *            |			indegree value =   0 => 1         
 *            V							   1 => 0
 *            3							   2 => 0
 *            							   3 => 1
 *            							   4 => 0
 *            							   5 => 0
 * 
 * 4 -Now we take vertex 1, remove it, added to the indegree array and because 
 * vertex 1 has no edges to any vertex we don´t subtract from any vertex   
 * 
 *    	   5     
 * 		    \    
 *           >    
 * 		   	  0   		indegree matrix = [4,2,1]
 *            |			indegree value =   0 => 1         
 *            V							   1 => 0
 *            3							   2 => 0
 *            							   3 => 1
 *            							   4 => 0
 *            							   5 => 0
 *            
 * 5 - We take 5, remove it from the graph, added to the indegree array and subtract
 * 1 from vertex 0.           
 *
 *  		  0   		indegree matrix = [4,2,1,5]
 *            |			indegree value =   0 => 0         
 *            V							   1 => 0
 *            3							   2 => 0
 *            							   3 => 1
 *            							   4 => 0
 *            							   5 => 0
 * 
 * 
 * 6 - We take vertex 0, remove it from the graph, added to the indegree array and subtract
 * 1 from vertex 3. 
 * 
 * 
 * 			     		indegree matrix = [4,2,1,5,0]
 *            			indegree value =   0 => 0         
 *             							   1 => 0
 *            3							   2 => 0
 *            							   3 => 0
 *            							   4 => 0
 *            							   5 => 0
 * 
 * 
 * 
 * 7 - Finally we take vertex 3
 * 
 *  			     	indegree matrix = [4,2,1,5,0,3]            			
 * 
 * 
 * Topological Sort only works when there is no cycles in the graph. The reason
 * is that if a cycle exist when you remove a node with indegree value of zero,
 * where the cycle exists all the vertexes will remain with a indegree greater 
 * than zero, so the algorithm with not go further. So if the algorithm iterate
 * the number of vertex in the graph, but could´t touch everyone, we have a cycle.
 * 
 * 
 * Constraints
 * 
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
public class Exercise24_Course_Scheduler {

	public static void main(String[] args) {

		int numCourses = 6;
//		int[][] prerequisite = { { 1, 0 }, { 2, 1 }, { 2, 5 }, { 0, 3 }, { 4, 3 }, { 3, 5 }, { 4, 5 } };
		int[][] prerequisite = { { 1, 0 }, { 2, 1 }, { 2, 5 }, { 0, 3 }, { 4, 3 }, { 3, 5 }, { 4, 5 }, { 0, 2 } };

		System.out.println("Is possible to finish this course schedule? : " + isFinishable(numCourses, prerequisite));
	}

	/**
	 * Verify if the graph has loops, if it has, them we can finished the courses.
	 * 
	 * Time Complexity: O(p+n^2) due to buildAdjacentList is O(p+n^2) plus
	 * isCyclesFree O(n)
	 * 
	 * Space Complexity: O(n^2) due to buildAdjacentList
	 * 
	 * @param numCourses   number of courses
	 * @param prerequisite tuple of prerequisite of a course
	 * @return boolean is finishable or not the course schedule
	 */
	public static boolean isFinishable(int numCourses, int[][] prerequisite) {

		List<Integer> indegreeList = new ArrayList<>(numCourses);
		List<List<Integer>> adjacentList = buildAdjacentList(numCourses, prerequisite, indegreeList);

//		return isCyclesFree_DFS(adjacentList);
		return isCyclesFree(numCourses, indegreeList, adjacentList);

	}

	/**
	 * With the indegree list we put into a Stack all the vertex with an indegree
	 * value of ZERO and loop until the stack is empty. With each value in the stack
	 * using the adjacent list, we take the vertex edges and decrease they indegree
	 * value by one and if the new value is zero we added to the stack. When te
	 * stack is empty we validate that we touch every vertex in the graph, using the
	 * numVertexProcessed, if we did, the graph is cycles free.
	 * 
	 * Time Complexity: O(n^2): traverse once the indegree list to store in the
	 * stack the vertexes with indegree value equal to ZERO. The while loop iterate
	 * maximum the number of vertexes (courses), but inside we loop over all the
	 * edges of the vertex (which can be in the worst case n), so this while/for =>
	 * O(n^2)
	 * 
	 * Space Complexity: O(n): we create a stack which in the worst case can have
	 * all the courses
	 * 
	 * 
	 * @param numCourses   number of courses
	 * @param indegreeList list of indegree values for each vertex in the graph
	 * @param adjacentList graph adjacent list
	 * @return boolean has or not cycles
	 */
	private static boolean isCyclesFree(int numCourses, List<Integer> indegreeList, List<List<Integer>> adjacentList) {

		int numVertexProcessed = 0;
		Stack<Integer> vertexesWithIndegreeZero = new Stack<>();

		// added all the vertex (the index of the list is the value of the vertex) that
		// has a indegree of Zero
		for (int index = 0; index < indegreeList.size(); index++) {
			if (indegreeList.get(index) == 0) {
				vertexesWithIndegreeZero.add(index);
			}
		}

		while (!vertexesWithIndegreeZero.isEmpty()) {

			int vertexId = vertexesWithIndegreeZero.pop();
			numVertexProcessed++;
			adjacentList.get(vertexId).stream().forEach(index -> {

				int vertexIndegree = indegreeList.get(index) - 1;

				indegreeList.set(index, vertexIndegree);
				if (vertexIndegree == 0) {
					vertexesWithIndegreeZero.add(index);
				}
			});
		}

		return numVertexProcessed == numCourses;
	}

	/**
	 * Create the adjacent and indegree list based on the prerequisite matrix and
	 * numCourses
	 * 
	 * Time Complexity: O(p+n): We iterate from 0 to n-1 to generate the courses and
	 * them traverse all the rows of the matrix P.
	 * 
	 * Space Complexity: O(n^2): we create a list of list which have all the courses
	 * and the prerequisite, so the first list will be as long as courses we have,
	 * and each sublist can have all the courses. The indegree list is only n long,
	 * so we dont take it into account
	 * 
	 * 
	 * @param numCourses   number of courses
	 * @param prerequisite list of tuple of prerequisite between courses,
	 * @param indegreeList list of indegree values for each vertex in the graph
	 * @return List<List<Integer>> adjacent list
	 */
	private static List<List<Integer>> buildAdjacentList(int numCourses, int[][] prerequisite,
			List<Integer> indegreeList) {

		List<List<Integer>> adjacentList = Stream.iterate(0, x -> x + 1).map(num -> {
			indegreeList.add(0);
			return new ArrayList<Integer>();
		}).limit(numCourses).collect(Collectors.toList());

//		adjacentList = IntStream.range(0,numCourses).boxed().map(num -> new ArrayList<Integer>())		
//		.collect(Collectors.toList());

		for (int index = 0; index < prerequisite.length; index++) {
			indegreeList.set(prerequisite[index][0], indegreeList.get(prerequisite[index][0]) + 1);
			adjacentList.get(prerequisite[index][1]).add(prerequisite[index][0]);
		}

		return adjacentList;
	}
}
