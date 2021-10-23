package sorting;

import java.util.Arrays;

/**
 * Selection the most right element of the array as a pivot, we start comparing to the most left element, if the right element is greater, we move the right element to the
 * position of the pivot, the pivot move on slot to the left and the element in that left position is moved to the position of the right element. When the pivot is greater
 * we just move the left pointer to right one slot and repeat until the left pointer and the pivot intersect. In that moment we split the array on two halves without including
 * the pivot, and repeat the same process to both half. When the array can be split any more, the array is sorted
 * @author Jose
 *
 */
public class QuickSort {

	public static void main(String[] args) {
		int arr[] = { 5, 9, 3, 2, 100, -10, -1 };
//		int arr[] = { 3, 1, 6, 2 };
//		int arr[] = {};
//		int arr[] = null;
		sort(arr);
		System.out.println(Arrays.toString(arr));

	}

	private static void sort(int[] arr) {
		if (arr != null) {
			pivotePartitioning(arr, 0, arr.length - 1);
		}
	}

	/**
	 * Time Complexity: O(n log n): For every split, we doing O(log n), but for every half, we need to traverse all, so that give us O(n)
	 * Space Complexity: O(n): No additional structure dependent of the input used.
	 * @param arr
	 * @param startIndex
	 * @param endIndex
	 */
	private static void pivotePartitioning(int[] arr, int startIndex, int endIndex) {

		if (startIndex >= endIndex) {
			return;
		}

		int compareIndex = startIndex;
		int pivoteIndex = endIndex;

		while (compareIndex < pivoteIndex) {
			if (arr[pivoteIndex] < arr[compareIndex]) {
				int temp = arr[pivoteIndex - 1];
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
