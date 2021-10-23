package sorting;

import java.util.Arrays;

/**
 * Using recursion and divide and conquer, we split the whole array into arrays
 * of 1 item, and them order then at that level and merge into an array of the
 * length of the two merged arrays. We repeat the process of merging the next
 * arrays until we end with one array sorted
 * 
 * @author Jose
 *
 */
public class MergeSort {

	public static void main(String[] args) {
		int arr[] = { 5, 9, 3, 2, 100, -10, -1 };
//		int arr[] = { 3, 1, 6, 2 };
//		int arr[] = null;
		System.out.println(Arrays.toString(sort(arr)));

	}

	/**
	 * Time Complexity: O(n log n) Space Complexity: O(n)
	 * 
	 * @param arr
	 * @return
	 */
	private static int[] sort(int[] arr) {

		int[] sorted = arr;
		if (arr != null) {
			sorted = sortAux(arr, 0, arr.length - 1);
		}

		return sorted;
	}

	/**
	 * Split the array until form arrays of one element
	 * 
	 * Time Complexity: O(n) Space Complexity: O(n)
	 * 
	 * @param arr
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	private static int[] sortAux(int[] arr, int startIndex, int endIndex) {

//		System.out.println("pase: (" + startIndex + " - " + endIndex + ")");
		if (startIndex == endIndex) {
			int[] newArr = new int[] { arr[startIndex] };
//			System.out.println(Arrays.toString(newArr));
			return newArr;
		}

		int middleIndex = startIndex + (endIndex - startIndex) / 2;
		return mergeArrays(sortAux(arr, startIndex, middleIndex), sortAux(arr, middleIndex + 1, endIndex));

	}

	/**
	 * Every pair of arrays are merged into an ordered array
	 * 
	 * Time Complexity: O(log n) Space Complexity: O(n)
	 * 
	 * @param subArrayLeft
	 * @param subArrayRight
	 * @return
	 */
	private static int[] mergeArrays(int[] subArrayLeft, int[] subArrayRight) {

		int indexLeft = 0;
		int indexRight = 0;
		int indexFinalArray = 0;
		int[] finalArray = new int[subArrayLeft.length + subArrayRight.length];

		while (indexLeft < subArrayLeft.length || indexRight < subArrayRight.length) {

			if (indexLeft == subArrayLeft.length) {
				finalArray[indexFinalArray++] = subArrayRight[indexRight++];
			} else if (indexRight == subArrayRight.length) {
				finalArray[indexFinalArray++] = subArrayLeft[indexLeft++];
			} else if (subArrayLeft[indexLeft] < subArrayRight[indexRight]) {
				finalArray[indexFinalArray++] = subArrayLeft[indexLeft++];
			} else {
				finalArray[indexFinalArray++] = subArrayRight[indexRight++];
			}

		}

		return finalArray;
	}

}
