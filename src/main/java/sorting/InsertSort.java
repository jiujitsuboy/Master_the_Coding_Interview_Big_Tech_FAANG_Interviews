package sorting;

import java.util.Arrays;

/**
 * Starting at second element and moving until the end of the array, verify using binary search, if the current number is correctly located of it should be relocated 
 * in the subsection 0 -> index-1.
 * @author Jose
 *
 */
public class InsertSort {

	public static void main(String[] args) {
//		int arr[] = { 5, 9, 3, 2, 100, -10, -1 };
//		int arr[] = { 1, 2, 6, 7, 8, 0 };
		int arr[] = null;
		System.out.println(Arrays.toString(sort(arr)));

	}

	/***
	 * Time Complexity: O(n^2) -> we traverse the array once to grab the element to sort O(n), the use binary search to find the location O(log n), then rearrange the array 
	 * which took O(n). So for every element we need to traverse part of the array. This is the reason of the O(n). 
	 * Space Complexity: O(1)
	 * @param arr
	 * @return
	 */
	private static int[] sort(int[] arr) {

		if (arr != null) {
			for (int index = 1; index < arr.length; index++) {
				locateNumber(arr, index);
			}
		}
		return arr;
	}

	private static void locateNumber(int[] arr, int index) {

		int firstElement = 0;
		int midElement = 0;
		int lastElement = index - 1;

		while (true) {
			midElement = (int) (firstElement + Math.round(((lastElement - firstElement - 1)) / 2d));

			if (arr[index] < arr[midElement]) {
				lastElement = midElement - 1;

			} else {
				firstElement = midElement + 1;
			}

			if (firstElement == index) {
				break;
			}

			else if (lastElement < firstElement) {
				relocateArrayElements(arr, firstElement, index);
				break;
			}

		}

	}

	private static void relocateArrayElements(int[] arr, int destinationIndex, int relocateIndex) {

		int relocateValue = arr[relocateIndex];
		for (int index = relocateIndex - 1; index >= destinationIndex; index--) {
			arr[index + 1] = arr[index];
		}
		arr[destinationIndex] = relocateValue;
	}

}
