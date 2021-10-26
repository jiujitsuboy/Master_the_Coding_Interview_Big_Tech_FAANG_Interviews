package sorting.algoritmn;

import java.util.Arrays;

/**
 * 
 * This use the Lomuto partition schema. There is also Hoare's scheme
 * 
 * ORDER IN PLACE (QuickSort order the array in place and don't create new
 * arrays as MergeSort)
 * 
 * Selection the most right element of the array as a pivot, we start comparing
 * to the most left element, if the right element is greater, we move the right
 * element to the position of the pivot, the pivot move on slot to the left and
 * the element in that left position is moved to the position of the right
 * element. When the pivot is greater we just move the left pointer to right one
 * slot and repeat until the left pointer and the pivot intersect. In that
 * moment we split the array on two halves without including the pivot, and
 * repeat the same process to both half. When the array can be split any more,
 * the array is sorted
 * 
 * @author Jose
 *
 */
public class QuickSort {

	public static void main(String[] args) {
		Integer arr[] = { -1, 10, 99, -2, -8, 1000, 50000, 8200, -60, 8 };
//		Integer arr[] = { 5, 9, 3, 2, 100, -10, -1 };
//		int arr[] = { 3, 1, 6, 2 };
//		int arr[] = {};
//		int arr[] = null;
		sort(arr);
		System.out.println(Arrays.toString(arr));

	}

	public static <T extends Comparable<T>> void sort(T[] arr) {
		if (arr != null) {
			pivotePartitioning(arr, 0, arr.length - 1);

		}
	}

	/**
	 * Time Complexity: O(n log n): For every split, we doing O(log n), but for
	 * every half, we need to traverse all, so that give us O(n) 
	 * Space Complexity: O(1): No additional structure dependent of the input used.
	 * 
	 * @param <T>
	 * @param arr
	 * @param startIndex
	 * @param endIndex
	 */
	private static <T extends Comparable<T>> void pivotePartitioning(T[] arr, int startIndex, int endIndex) {

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

			pivotePartitioning(arr, startIndex, pivoteIndex - 1);
			pivotePartitioning(arr, pivoteIndex + 1, endIndex);
		}
	}
}
