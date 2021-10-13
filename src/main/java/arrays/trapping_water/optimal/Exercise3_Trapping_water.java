package arrays.trapping_water.optimal;

/***
 * Given n non-negative integers representing an elevation map where the width
 * of each bar is 1, compute how much water it can trap after raining.
 * 
 * https://leetcode.com/problems/trapping-rain-water/
 * 
 * @author Jose
 *
 */
public class Exercise3_Trapping_water {

	public static void main(String[] args) {
		int[] heights = { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };
		System.out.print(getTrappingWater(heights));

	}

	/***
	 * Optimal approach, we used two pointers, one located at each edge of the
	 * heights array. And we start moving each other inwards until they get
	 * together. We store the max value of the left pointer and the max value of the
	 * right pointer (at the beginning there are going to be the initial values of
	 * those pointers). Then choose the value of the lesser pointer and subtract to
	 * the corresponding side max value. Then if the obtained values is greater to zero,
	 * add this intermediate values to the total and move the lesser pointing one position inward.
	 * 
	 * 
	 * Time Complexity = O(n) Space Complexity = O(1)
	 * 
	 * @param heights
	 * @return
	 */

	public static int getTrappingWater(int[] heights) {
		int totalWater = 0;

		int leftIndexPointer = 0;
		int rightIndexPointer = heights.length - 1;
		int maxLeftIndex = 0;
		int maxRightIndex = 0;
		int currentWater = 0;

		while (leftIndexPointer < rightIndexPointer) {
			
			maxLeftIndex = Math.max(maxLeftIndex, heights[leftIndexPointer]);
			maxRightIndex = Math.max(maxRightIndex,heights[rightIndexPointer]);
			
			if (heights[leftIndexPointer] < heights[rightIndexPointer]) {

				currentWater = maxLeftIndex - heights[leftIndexPointer];
				leftIndexPointer++;
								
			} else {
				currentWater = maxRightIndex - heights[rightIndexPointer];
				rightIndexPointer--;
			}
			
			if(currentWater>0) {
				totalWater += currentWater;
			}

		}

		return totalWater;
	}
}
