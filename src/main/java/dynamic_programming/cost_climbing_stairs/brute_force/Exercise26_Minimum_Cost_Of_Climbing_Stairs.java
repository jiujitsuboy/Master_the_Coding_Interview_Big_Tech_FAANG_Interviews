package dynamic_programming.cost_climbing_stairs.brute_force;

/**
 * DYNAMIC PROGRAMMING: Is a algorithm paradigm, used in optimization problems
 * where at difference of GREEDY algorithms, we can�t take the best solution at
 * every step, but instead we need to calculate all the possible values, but
 * saving on those values that are calculated more that once.
 * 
 * The steps of DYNAMIC PROGRAMMING are:
 * 
 * 1 - Obtain the RECURSIVE RELATION: the recursive formula.
 * 2 - Code the brute force approach using Top-Down (Recursive algorithm)
 * 3 - Add Memoization to the previous step to improve performance and space.
 * 4 - Change the approach to Bottom-up to improve space. Basically rewrite 
 * 	   your algorithm in a iterative way, instead recursive
 * 
 * 
 * 
 * 
 * PROBLEM:
 * 
 * Receiving an array which every position represent a stair with his associate
 * cost and on every step you can take one stair or two stairs, calculate the
 * minimum cost to get to the top of the stair.
 * 
 * 
 * 
 * StairStepCost  = [20,15,30,5]
 * 
 *            __
 *         __|5
 *      __|30
 *   __!15
 *  |20
 * 
 * This is an OPTIMIZATION PROBLEM, so if we want to know what is the minimum 
 * cost, we need to calculate all the options to determine the best one. But
 * this is time consuming.
 * 
 * 
 * Possible combinations
 * 
 * - 20->15->30->5 = 70 (taking one stair at every step)
 * - 20->30 = 50 (taking two stair at every step)
 * - 20->15->5 = 40 
 * - 20->30->5 = 55
 * - 15->30->5 = 50
 * - 15->5 = 20
 * - 15->30 = 45
 * 
 *
 * STATE TREE
 * 
 * 								 0
 * 							 /		  \
 * 						    5		  30
 * 						  /	 \		 /  \
 * 					    15	 30		15	20
 * 				       /	 / \	 \
 * 					  20	15	20	 20
 * 
 * The first step is to get the RECURSIVE RELATION, which is the formula used in 
 * the recursive function to solve our problem. 
 * 
 * In this case the formula would be:
 * 
 *  minCost(i) = cost[i] + min(minCost(i-1),minCost(i-2))
 *  minCost(0) = cost[0]
 *  minCost(1) = cost[1]
 *  i<0 = 0
 * 
 * Second step, using the top-down approach implement a recursive algorithm to 
 * solve the problem.
 * 
 *  
 * @author Jose
 *
 */
public class Exercise26_Minimum_Cost_Of_Climbing_Stairs {

	public static void main(String[] args) {
		// answer is 20
		int[] stairSteps = { 20, 15, 30, 5 };

		System.out.println(minCostClimbingStairs(stairSteps));

	}

	/**
	 * Calculate all the possible steps to find the one with minimum step cost
	 * 
	 * Time Complexity: O(2^n) from calling minCostClimbingStairsAux() function
	 * 
	 * Space Complexity: O(2^n): from calling minCostClimbingStairsAux() function
	 * 
	 * 
	 * @param stairSteps array of steps with their corresponding cost
	 * @return int min number of steps costs
	 */
	public static int minCostClimbingStairs(int[] stairSteps) {
		int minCost = 0;
		if (stairSteps != null && stairSteps.length > 1) {
			int index = stairSteps.length;
			minCost = Math.min(minCostClimbingStairsAux(stairSteps, index - 2),
					minCostClimbingStairsAux(stairSteps, index - 1));
		}
		return minCost;
	}

	/**
	 * Recursive function, which on every iteration takes n-1 and n-2 position 
	 * from the current index. Index start being the length of the array of steps.
	 * When we reach the index 0 or 1 we start returning their cost associated
	 * and sum it with the corresponding index of the step.
	 * 
	 * 
	 * Time Complexity: O(2^n): on every iteration we spawn two more iterations, so
	 * every level we are going to have 2^n calls.
	 * 
	 * Space Complexity: O(2^n): no extra data structure, but the memory stack 
	 * would keep all the values until we reach the index 0 and 1.
	 * 
	 * @param stairSteps array of steps with their corresponding cost
	 * @param index step index
	 * @return cost so far of the steps taken
	 */
	private static int minCostClimbingStairsAux(int[] stairSteps, int index) {

		int costSoFar = 0;

		if (index > -1 && index < 2) {
			costSoFar = stairSteps[index];
		} else if (index > 1) {
			costSoFar = stairSteps[index] + Math.min(minCostClimbingStairsAux(stairSteps, index - 2),
					minCostClimbingStairsAux(stairSteps, index - 1));
		}

		return costSoFar;
	}

}
