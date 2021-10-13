package arrays.trapping_water.brute_force;


/***
 * Given n non-negative integers representing an elevation map where the width of each bar is 1, 
 * compute how much water it can trap after raining.
 * 
 * https://leetcode.com/problems/trapping-rain-water/
 * 
 * 
 * @author Jose
 *
 */
public class Exercise3_Trapping_water {

	public static void main(String[] args) {
		int[] heights = { 0, 1, 0, 2, 1, 0, 3, 1, 0, 1, 2 };
		System.out.print(getTrappingWater(heights));

	}
	
	/***
	 * Brute force approach, where we start from left to right traversing the heights array, 
	 * and at every index position in the array, we calculate the max value to the left and to the right. Then
	 * we choose the min value between those two maximums found (the left max and the right max). When then subtract
	 * the min max value and the height of the current index. This give us the total amount of water contained in that
	 * particular point. Add this intermediate value to the total and move to the next index and repeat until we traverse the
	 * height array.
	 * 
	 * Time Complexity = O(n^2)
	 * Space Complexity = O(1)
	 * @param heights 
	 * @return
	 */

	public static int getTrappingWater(int[] heights) {
		int totalWater = 0;

		for (int index = 0; index < heights.length; index++) {
			
			int leftIndex = index;
			int rightIndex = index;
			int maxLeftIndex = 0;
			int maxRightIndex = 0;
			int currentWater = 0;

			while (leftIndex >= 0) {
				maxLeftIndex = Math.max(maxLeftIndex, heights[leftIndex]);
				leftIndex--;
			}

			while (rightIndex < heights.length) {
				maxRightIndex = Math.max(maxRightIndex, heights[rightIndex]);
				rightIndex++;
			}
			
			currentWater = Math.min(maxRightIndex, maxLeftIndex) - heights[index];
			
			if(currentWater>0) {
				totalWater += currentWater;
			}
		}

		return totalWater;
	}
}
