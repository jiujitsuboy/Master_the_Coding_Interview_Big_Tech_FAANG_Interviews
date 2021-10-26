package sorting.algoritmn;

import java.util.Arrays;

/**
 * from left to right, compare n and n+1 elements and if n+1 is lower than n, swap them. and continue moving. This will leave the largest element in the right,
 * now repeat until length -1 of the array, and keep going. On each iteration, we leave with the max value at the right.
 * 
 * 
 * @author Jose
 *
 */
public class BubbleSort {

	public static void main(String[] args) {
		int arr[] = { 5, 9, 3, 2, 100, -10, -1 };

		System.out.println(Arrays.toString(sort(arr)));
	}

	/**
	 * Time Complexity: O(n^2)
	 * Space Complexity: O(1)
	 * @param arr
	 * @return
	 */
	private static int[] sort(int[] arr) {

		for (int times = 0; times < arr.length - 1; times++) {
			for (int index = 0; index < arr.length - (1 + times); index++) {
				if (arr[index] > arr[index + 1]) {
					int temp = arr[index];
					arr[index] = arr[index + 1];
					arr[index + 1] = temp;
				}
//				System.out.println(Arrays.toString(arr))
				;
			}
		}
		
		return arr;

	}
}
