package dynamic_programming.cost_climbing_stairs.iterative;

/**
 * DYNAMIC PROGRAMMING: Is a algorithm paradigm, used in optimization problems
 * where at difference of GREEDY algorithms, we can´t take the best solution at
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
 *  
 * Second step, using the top-down approach implement a recursive algorithm to 
 * solve the problem.
 * 
 * The third in this solution use memoization approach, where we store all previous
 * calculation to save time and space.
 * 
 * The fourth step is to rewrite the algorithm to be iterative, so we can save
 * the stack space consumed by the recursion. Starting from the base cases of
 * our recursion relation
 * 
 * 
 * @author Jose
 *
 */
public class Exercise26_Minimum_Cost_Of_Climbing_Stairs {

	public static void main(String[] args) {
		// answer is 20
		int[] stairSteps = { 20, 15, 30, 5 };
		// answer is 0
//		int[] stairSteps = { 20 };

		// answer is 15
//		int[] stairSteps = { 20, 15 };

		// answer is 0
//		int[] stairSteps = { };

		System.out.println(minCostClimbingStairs2(stairSteps));

	}

	/**
	 * Start at index -1 and get the min value between index+1 and index+2. The one
	 * that is smallest is picked, and the index is moved to that position. This
	 * guarantee you move forward only taking the step until the end.
	 * 
	 * Time Complexity: O(n) Only traverse the array partially once
	 * 
	 * Space Complexity: O(1): No stack space and no other data structure used.
	 * 
	 * 
	 * @param stairSteps array of steps with their corresponding cost
	 * @return int min number of steps costs
	 */
	public static int minCostClimbingStairs1(int[] stairSteps) {
		int minCost = 0;

		if (stairSteps != null && stairSteps.length > 1) {

			int stepIndex = -1;
			while (stepIndex + 2 < stairSteps.length) {

				if (stepIndex > -1) {
					minCost = stairSteps[stepIndex];
				}
				if (stairSteps[stepIndex + 1] < stairSteps[stepIndex + 2]) {
					minCost += stairSteps[stepIndex + 1];
					stepIndex++;
				} else {
					minCost += stairSteps[stepIndex + 2];
					stepIndex += 2;
				}
			}

		}
		return minCost;
	}

	/**
	 * Alternately way, where we start at index 2 and get n-1 and n-2 get the min
	 * and increment by 1 index and repeat.
	 * 
	 * Time Complexity: O(n) Only traverse the array partially once
	 * 
	 * Space Complexity: O(1): No stack space and no other data structure used.
	 * 
	 * 
	 * @param stairSteps array of steps with their corresponding cost
	 * @return int min number of steps costs
	 */
	public static int minCostClimbingStairs2(int[] stairSteps) {
		int minCost = 0;

		if (stairSteps != null && stairSteps.length > 1) {

			int stepIndex = 2;
			while (stepIndex < stairSteps.length) {
				minCost = stairSteps[stepIndex] + Math.min(stairSteps[stepIndex - 1], stairSteps[stepIndex - 2]);
				stairSteps[stepIndex] = minCost;
				stepIndex++;
			}

		}
		return minCost;
	}
}