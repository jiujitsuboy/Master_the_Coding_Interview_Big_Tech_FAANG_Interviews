package arrays.container_most_water.optimal;

public class Exercise2_Container_With_Most_Water {

	public static void main(String[] args) {
		int[] heights = { 1, 8, 6, 2, 9, 4 };
		System.out.println(getMaxContainerWithWater(heights));
	}

	/***
	 * optimal approach: use two pointer on each edge of the array, calculate the area using this two pointers and move inwards
	 * the pointer which array values is lesser. This until the pointer converse to the same index position
	 * 
	 * Time complexity: O(n) 
	 * Space complexity: O(1)
	 * 
	 * @param heights
	 * @return
	 */
	public static int getMaxContainerWithWater(int[] heights) {
		int area = 0;
		int indexLeftPointer = 0;
		int indexRightPointer = heights.length - 1;

		while (indexLeftPointer < indexRightPointer) {

			area = Math.max(area, Math.min(heights[indexLeftPointer], heights[indexRightPointer])
					* (indexRightPointer - indexLeftPointer));

			if (heights[indexLeftPointer] <= heights[indexRightPointer]) {
				indexLeftPointer++;
			} else {
				indexRightPointer--;
			}
		}

		return area;
	}

}
