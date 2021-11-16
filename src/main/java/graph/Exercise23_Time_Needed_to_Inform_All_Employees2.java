package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * THIS VERSION DOESN´T USE THE GRAPH CLASS, INSTEAD USE AN ARRAY OF ARRAYS
 * 
 * 
 * Calculate the total time it takes to inform all the employees of a company,
 * when the news is delivered to their managers and they need to inform any of
 * their employees (every time a manager informs on of his employees, it take
 * sometime). So the total time is going to be the longest path it would take
 * for a manager to communicate his employee and that employee which is the
 * manager of others employee to communicates to their employee until it reach
 * the bottom employee in this subordination chain.
 * 
 * we receive a number n which represent the number of employees the company has
 * from 0 to n-1.
 * 
 * Also receive the headID, which is the top manager which don't have
 * subordination (is like the CEO),
 * 
 * Additionally we receive an array of length n, where the index of the array
 * represent a employee and the value of that array position is the value of his
 * manager. when a employee don't have a manager it would be represented with -1
 * value. This is the case for the headId.
 * 
 * Finally we receive another array with the time it takes every employee to get
 * inform about the new. So if index 0 of this array has 3 as value, it depict
 * that the employee 0 takes 3 minutes to receive the new and communicate it to
 * his employees. So the cases where the value is zero is because that employee
 * is not the manager of anyone, so it would take zero time to inform them.
 * 
 * Example.
 * 
 * n = 8 => [0, 1, 2, 3 ,4 ,5 ,6 ,7]
 * 
 * headId = 4
 * 
 * managers [2, 2, 4, 6, -1, 4, 4, 5]
 * 
 * 
 * 
 * when the structure is a tree we can be sure that is a acyclic graph (no
 * loops)
 * 
 * Constraints
 * 
 * 1 - Can employees have more than one manager?, No, so we can guarantee there
 * is not going to be any kind of circular structure (in trees there are no
 * cycles but can be circular structures, where two nodes has the same child but
 * the direction of the relationship avoids the cycle)
 * 
 * 2- Does every employee have a manager?, yes, the only one who don't is the
 * head of the company. (so this indicates there is not unconnected graphs here)
 * 
 * @author Jose
 *
 */
public class Exercise23_Time_Needed_to_Inform_All_Employees2 {

	public static void main(String[] args) {
		int numEmployee = 8;
		int headId = 4;
		int[] managers = { 2, 2, 4, 6, -1, 4, 4, 5 };
		int[] timeToInform = { 0, 0, 4, 0, 7, 3, 6, 0 };

		int totalTimeTaked = getTotalTimeToInform(numEmployee, headId, managers, timeToInform);

		System.out.println("Total time to inform is : " + totalTimeTaked);

	}

	/**
	 * Calculate the longest time taken to inform the last employee in any
	 * subordination branch.
	 * 
	 * Time Complexity: O(n) we call createCompanyTree, generateManagersAdjacentList
	 * and calculateMaxTimeToInform, where each take O(n)=>O(3n). of employees
	 * 
	 * Space Complexity: O(n): we call createCompanyTree,
	 * generateManagersAdjacentList and calculateMaxTimeToInform, where each take
	 * O(n).
	 * 
	 * @param numEmployee  company number of employees
	 * @param headId       root manager
	 * @param managers     subordination list
	 * @param timeToInform time to inform each manager
	 * @return total time to inform
	 */
	public static int getTotalTimeToInform(int numEmployee, int headId, int[] managers, int[] timeToInform) {
		int totalTime = 0;

		if (numEmployee == managers.length && numEmployee == timeToInform.length) {

			List<List<Integer>> companyEmployee = createCompanyTree(numEmployee);
			generateManagersAdjacentList(companyEmployee, managers);
			totalTime = calculateMaxTimeToInform_DFS(companyEmployee, headId, timeToInform);
//			totalTime = calculateMaxTimeToInform_BFS(companyEmployee, headId, timeToInform);

		}

		return totalTime;
	}

	/**
	 * Create the list of adjacent vertex of each vertex and stored in the arrays
	 * corresponding to the vertex index in the companyEmployee list of lists
	 * 
	 * Time Complexity: O(n): we iterate the managers arrays which is the same
	 * length of employees
	 * 
	 * Space Complexity: O(n): we add every vertex to the array of edges of the
	 * manager vertex, which at the end is the total number of employees, but
	 * because a employee only can have one manager, we can´t end in a n*n sublist,
	 * and that is why we only have n possible elements split into all the
	 * sub arrays
	 * 
	 * @param companyEmployee
	 * @param managers
	 */
	private static void generateManagersAdjacentList(List<List<Integer>> companyEmployee, int[] managers) {

		int managerIndex = -1;
		for (int index = 0; index < managers.length; index++) {
			managerIndex = managers[index];
			if (managerIndex > -1) {
				companyEmployee.get(managerIndex).add(index);
			}

		}
	}

	/**
	 * using the number of employees, it creates a list with numEmployee positions
	 * and in every position we create a empty list to store the adjacent vertex,
	 * but for now is only empty
	 * 
	 * Time Complexity: O(n) we iterate as many times as the numEmployee
	 * 
	 * Space Complexity: O(n): we create a list with the length of numEmployee
	 * 
	 * @param numEmployee number of employee of the company
	 * @return Graph tree with the company employees
	 */
	private static List<List<Integer>> createCompanyTree(int numEmployee) {

		List<List<Integer>> companyTree = new ArrayList<List<Integer>>(numEmployee);

		for (int index = 0; index < numEmployee; index++) {
			companyTree.add(new ArrayList<Integer>());
		}
		return companyTree;
	}

	/**
	 * Using DFS, we start with the headId vertex and go as deep as possible into
	 * his adjacent vertex (employees), summing the time it takes to inform each
	 * subordinate. At the end we take the longest time taken by any of this paths,
	 * which is the max time to inform
	 * 
	 * Time Complexity: O(n) we traverse all the graph
	 * 
	 * Space Complexity: O(n): We only store the sum of the times taken. But we have
	 * in the stack the longest subordinate branch, in the worst case the whole
	 * graph (linked list)
	 * 
	 * @param companyEmployee
	 * @param vertexId
	 * @param timeSoFar
	 * @return
	 */
	private static int calculateMaxTimeToInform_DFS(List<List<Integer>> companyEmployee, int vertexId,
			int[] timeToInform) {

		List<Integer> adjacentVertexes = companyEmployee.get(vertexId);

		int timeSoFarAdjacentVertex = 0;

		for (Integer adjacentVertex : adjacentVertexes) {
			timeSoFarAdjacentVertex = Math.max(timeSoFarAdjacentVertex,
					calculateMaxTimeToInform_DFS(companyEmployee, adjacentVertex, timeToInform));
		}

		return timeSoFarAdjacentVertex + timeToInform[vertexId];
	}

	/**
	 * Using BFS, we start with the headId vertex and scan all this edges and stored
	 * them in a queue. Each time we take a vertex from the queue, we get it time to
	 * inform and sum to the timeSoFar (which is only updated with the max time of a
	 * whole level). So in contrast to DFS, we are calculating the max time per
	 * level as we goes down.
	 * 
	 * Time Complexity: O(n) we traverse all the graph
	 * 
	 * Space Complexity: O(n): We use a queue that in the worts case can have all
	 * the vertex in the graph (linked list)
	 * 
	 * @param companyEmployee
	 * @param headId
	 * @param timeToInform
	 * @return
	 */
	private static int calculateMaxTimeToInform_BFS(List<List<Integer>> companyEmployee, int headId,
			int[] timeToInform) {

		int timeSoFar = 0;
		int vertexProcessed = 0;
		int timeSoFarAdjacentVertex = 0;

		int queueSize = 0;

		Queue<Integer> pendingVertexes = new LinkedList<>();

		pendingVertexes.add(headId);

		while (!pendingVertexes.isEmpty()) {

			if (vertexProcessed == 0) {
				queueSize = pendingVertexes.size();
			}

			int vertexId = pendingVertexes.poll();

			List<Integer> adjacentVertexes = companyEmployee.get(vertexId);

			for (Integer adjacentVertex : adjacentVertexes) {
				pendingVertexes.add(adjacentVertex);
			}

			vertexProcessed++;
			timeSoFarAdjacentVertex = Math.max(timeSoFarAdjacentVertex, timeSoFar + timeToInform[vertexId]);

			if (vertexProcessed == queueSize) {

				timeSoFar = timeSoFarAdjacentVertex;
				vertexProcessed = 0;
			}
		}

		return timeSoFarAdjacentVertex;
	}
}
