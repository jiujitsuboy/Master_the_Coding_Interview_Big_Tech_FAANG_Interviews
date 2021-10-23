package sorting;

import java.util.Arrays;


/**
 * from left to right find the lowest value, then swap it with the first value of the left, 
 * then  repeat, find the lowest value and swap it with the second value of the left
 * @author Jose
 *
 */
public class SelectionSort {

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
	private static int[] sort(int arr[]) {

		for (int times = 0; times < arr.length - 1; times++) {
			int indexOfMinValue = times;
			for (int index = times + 1; index < arr.length; index++) {
				if (arr[index] < arr[indexOfMinValue]) {
					indexOfMinValue = index;
				}
			}

			int temp = arr[times];
			arr[times] = arr[indexOfMinValue];
			arr[indexOfMinValue] = temp;
		}

		return arr;
	}
}