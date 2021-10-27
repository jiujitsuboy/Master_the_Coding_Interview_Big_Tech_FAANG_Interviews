package sorting.start.end.indexes.optimal;

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
//		Integer arr[] = { 1, 2, 3, 4, 5, 5, 5, 5, 5, 5, 5, 8, 9, 10 };		
		Integer arr[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

//		Integer arr[] = null;

		int valueToFind = 1;

		int[] indexes = findIndexesOfTargetValue(arr, valueToFind);
		System.out.println(Arrays.toString(indexes));

	}

	/**
	 * We use Binary Search to find the number, if we found it, we move from that
	 * index position obtains toward left and right and repeat Binary Search until 
	 * we obtain -1 (indicating we could find any further values to the edges of the 
	 * already found values)
	 * 
	 * Time Complexity: O(log n): binary search gave us O (log n),
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

			indexes[0] = startIndex;
			indexes[1] = endIndex;

			while (startIndex != -1) {
				startIndex = BinarySeach.findIndexOfNumberIterative(arr, valueToFind, 0, startIndex - 1);
				if (startIndex != -1) {
					indexes[0] = startIndex;
				}
			}

			while (endIndex != -1) {
				endIndex = BinarySeach.findIndexOfNumberIterative(arr, valueToFind, endIndex + 1, arr.length - 1);
				if (endIndex != -1) {
					indexes[1] = endIndex;
				}
			}

		}

		return indexes;
	}

}
