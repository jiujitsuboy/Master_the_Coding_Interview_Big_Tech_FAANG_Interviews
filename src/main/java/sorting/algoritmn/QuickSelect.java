package sorting.algoritmn;

import java.util.Arrays;

/**
 * 
 * Use QuickSort same algorithm with one modification, we are not interesting in
 * sort the whole array, we are interested in finding a specific position in the
 * resulting ordered array, so every time we ordered a partition, we select the
 * next partition where the position could be and discard the other partition.
 * 
 * @author Jose
 *
 */
public class QuickSelect {

	public static void main(String[] args) {
		int index = 9;
		Integer arr[] = { -1, 10, 99, -2, -8, 1000, 50000, 8200, -60, 8 };
//		Integer arr[] = { 5, 9, 3, 2, 100, -10, -1 };
//		Integer arr[] = { 3, 1, 6, 2 };
//		Integer arr[] = {};
//		Integer arr[] = null;
		Integer valueFound = select(arr, index);
		System.out.println(Arrays.toString(arr));
		System.out.println(valueFound);
	}

	public static <T extends Comparable<T>> T select(T[] arr, int positionToFind) {
		T valueFound = null;

		if (arr != null && positionToFind > 0 && positionToFind <= arr.length) {
			pivotePartitioning(arr, 0, arr.length - 1, positionToFind);
			valueFound = arr[positionToFind - 1];
		}

		return valueFound;
	}

	/**
	 * Time Complexity: O(n). On every recursive call we discard half ot the array, so n + n/2 + n/4 + n/8 = 2n, which is O(n) 
	 * Space Complexity: O(1) No additional structure dependent of the input used. This is dude to tail recursion. because we are not calling twice quicksort function
	 * on each pass, the code doesn't not need to keep in  stack the other half of the array while it is processing the other half of the array. So if tail recursion is
	 * supported, the data store in the stack can be removed.
	 * 
	 * @param <T>
	 * @param arr
	 * @param startIndex
	 * @param endIndex
	 */
	private static <T extends Comparable<T>> void pivotePartitioning(T[] arr, int startIndex, int endIndex,
			int positionToFind) {

		if (startIndex < endIndex) {

			int compareIndex = startIndex;
			int pivoteIndex = endIndex;

			while (compareIndex < pivoteIndex) {
				if (arr[pivoteIndex].compareTo(arr[compareIndex]) < 0) {
					T temp = arr[pivoteIndex - 1];
					arr[pivoteIndex - 1] = arr[pivoteIndex];

					if (pivoteIndex - 1 == compareIndex) {
						arr[pivoteIndex] = temp;
					} else {
						arr[pivoteIndex] = arr[compareIndex];
						arr[compareIndex] = temp;
					}
					pivoteIndex--;
				} else {
					compareIndex++;
				}
			}

			if (positionToFind < pivoteIndex) {
				pivotePartitioning(arr, startIndex, pivoteIndex - 1, positionToFind);
			} else if (positionToFind > pivoteIndex) {
				pivotePartitioning(arr, pivoteIndex + 1, endIndex, positionToFind);
			}
		}
	}
}
