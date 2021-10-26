package sorting.algoritmn;

import java.lang.reflect.Array;
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
//		Integer arr[] = { 5, 9, 3, 2, 100, -10, -1 };
//		Integer arr[] = { 3, 1, 6, 2 };
		String arr[] = { "b", "d", "a" };
//		Integer arr[] = null;
		System.out.println(Arrays.toString(sort(arr)));

	}

	/**
	 * Time Complexity: O(n log n) 
	 * Space Complexity: O(n)
	 * 
	 * @param arr
	 * @return
	 */
	public static <T extends Comparable<T>> T[] sort(T[] arr) {

		T[] sorted = arr;
		if (arr != null) {
			sorted = sortAux(arr, 0, arr.length - 1);
		}

		return sorted;
	}

	/**
	 * Split the array until form arrays of one element
	 * 
	 * Time Complexity: O(n) 
	 * Space Complexity: O(n)
	 * 
	 * @param arr
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T extends Comparable<T>> T[] sortAux(T[] arr, int startIndex, int endIndex) {

//		System.out.println("pase: (" + startIndex + " - " + endIndex + ")");
		if (startIndex == endIndex) {
//			int[] newArr = new int[] { arr[startIndex] };
			T[] newArr = (T[]) Array.newInstance(Comparable.class, 1);
			newArr[0] = arr[startIndex];
//			System.out.println(Arrays.toString(newArr));
			return newArr;
		}

		int middleIndex = startIndex + (endIndex - startIndex) / 2;
		return mergeArrays(sortAux(arr, startIndex, middleIndex), sortAux(arr, middleIndex + 1, endIndex));

	}

	/**
	 * Every pair of arrays are merged into an ordered array
	 * 
	 * Time Complexity: O(log n) 
	 * Space Complexity: O(n)
	 * 
	 * @param subArrayLeft
	 * @param subArrayRight
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T extends Comparable<T>> T[] mergeArrays(T[] subArrayLeft, T[] subArrayRight) {

		int indexLeft = 0;
		int indexRight = 0;
		int indexFinalArray = 0;
		T[] finalArray = (T[]) Array.newInstance(Comparable.class, subArrayLeft.length + subArrayRight.length);

		while (indexLeft < subArrayLeft.length || indexRight < subArrayRight.length) {

			if (indexLeft == subArrayLeft.length) {
				finalArray[indexFinalArray++] = subArrayRight[indexRight++];
			} else if (indexRight == subArrayRight.length) {
				finalArray[indexFinalArray++] = subArrayLeft[indexLeft++];
			} else if (subArrayLeft[indexLeft].compareTo(subArrayRight[indexRight]) < 0) {
				finalArray[indexFinalArray++] = subArrayLeft[indexLeft++];
			} else {
				finalArray[indexFinalArray++] = subArrayRight[indexRight++];
			}

		}

		return finalArray;
	}

}
