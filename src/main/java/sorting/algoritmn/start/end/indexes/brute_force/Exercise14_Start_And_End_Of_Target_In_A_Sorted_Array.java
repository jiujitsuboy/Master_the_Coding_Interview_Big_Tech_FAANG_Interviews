package sorting.algoritmn.start.end.indexes.brute_force;

import java.util.Arrays;

import sorting.algoritmn.BinarySeach;

/**
 * Find the initial and final indexes of the target value.
 * 
 * Normally the value should have only one index an less the value appears
 * multiple times in the array, where we need to return the lower index and the
 * higher index of the same value.
 * 
 * 
 * @author Jose
 *
 */
public class Exercise14_Start_And_End_Of_Target_In_A_Sorted_Array {

	public static void main(String[] args) {
		Integer arr[] = { 1, 2, 3, 4, 5, 5, 5, 5, 5, 5, 5, 8, 9, 10 };
//		Integer arr[] = { 1, 2, 3, 4, 5, 5, 5, 5, 5, 5, 5, 8, 9, 10 };
//		Integer arr[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

//		Integer arr[] = null;

		int valueToFind = 5;

		int[] indexes = findIndexesOfTargetValue(arr, valueToFind);
		System.out.println(Arrays.toString(indexes));

	}

	/**
	 * We use Binary Search to find the number, if we found it, we move from that
	 * index position obtains toward left and right until we found a different
	 * number than the target value, in that moment we have the start and end index
	 * of the target number
	 * 
	 * Time Complexity: O(n): binary search gave us O (log n), but because we
	 * need to move towards the edges (right and left), we in the worst case
	 * scenario ends traversing the whole array O(n). In the best case scenario
	 * where we have only one appearance of the target value, we end with O(log n)
	 * complexity.
	 * 
	 * 
	 * Space Complexity: O(1): We are not story any additional data related to the
	 * input
	 * 
	 * @param <T>
	 * @param arr
	 * @param valueToFind
	 * @return
	 */
	private static <T extends Comparable<T>> int[] findIndexesOfTargetValue(T[] arr, T valueToFind) {

		int[] indexes = { -1, -1 };
		int middleIndex = BinarySeach.findIndexOfNumberIterative(arr, valueToFind);

		if (middleIndex > -1) {
			int startIndex = middleIndex;
			int endIndex = middleIndex;

			while (endIndex + 1 < arr.length) {
				endIndex++;
				if (!(arr[endIndex].compareTo(valueToFind) == 0)) {
					endIndex--;
					break;
				}
			}

			while (startIndex - 1 > -1) {
				startIndex--;
				if (!(arr[startIndex].compareTo(valueToFind) == 0)) {
					startIndex++;
					break;
				}
			}

			indexes[0] = startIndex;
			indexes[1] = endIndex;
		}

		return indexes;
	}

}
