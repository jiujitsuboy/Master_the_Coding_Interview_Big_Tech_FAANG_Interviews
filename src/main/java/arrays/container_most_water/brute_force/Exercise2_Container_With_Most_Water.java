package arrays.container_most_water.brute_force;

public class Exercise2_Container_With_Most_Water {

	public static void main(String[] args) {
		int[] heights = { 1, 8, 6, 2, 9, 4 };
		System.out.println(getMaxContainerWithWater(heights));
	}

	/***
	 * Brute force approach: Calculate the area of all the possible combinations of
	 * points in the array and keep the maximum value
	 * 
	 * Time complexity: O(n^2)
	 * Space complexity: O(1)
	 * @param heights
	 * @return
	 */
	public static int getMaxContainerWithWater(int[] heights) {
		int area = 0;

		for (int indexSample = 0; indexSample < heights.length - 1; indexSample++) {

			for (int indexProspect = indexSample + 1; indexProspect < heights.length; indexProspect++) {

				area = Math.max(area,
						Math.min(heights[indexSample], heights[indexProspect]) * (indexProspect - indexSample));
			}
		}

		return area;
	}

}
