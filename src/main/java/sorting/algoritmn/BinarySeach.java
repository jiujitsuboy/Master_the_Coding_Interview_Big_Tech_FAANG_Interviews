package sorting.algoritmn;

import java.util.Arrays;

/**
 * Find the middle element in the array and see if it is the value we are
 * searching or it is lower or greater. If the value is the one we are
 * searching, we stop and return the index of the value. If the middle value is
 * less than the value we are searching, we grab the upper part of the array and
 * repeat the search with this sub array, if not, we apply it to the lower part
 * of the sub array, and repeat until we find it or we exhaust all the possible
 * values
 * 
 * @author Jose
 *
 */
public class BinarySeach {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Integer arr[] = { -1, 10, 99, -2, -8, 1000, 50000, 8200, -60, 8 };
		String arr[] = { "alejo", "lindsay", "brandy", "milo" };
//		int valueToFind = 8200;
		String valueToFind = "lindsay";
		// We are sorting, but this type of search is only effective is the data is
		// sorted, instead if we need to sorted, we will
		// end with a O(n log n) time complexity instead of O(log n)
		QuickSort.sort(arr);

		System.out.println(Arrays.toString(arr));
		System.out.println(findIndexOfNumberRecursive(arr, valueToFind));

		System.out.println(findIndexOfNumberIterative(arr, valueToFind));

	}

	/**
	 * Time Complexity: O(log n) Space Complexity: O(n)
	 * 
	 * @param <T>
	 * @param arr
	 * @param numberToFind
	 * @return
	 */
	public static <T extends Comparable<T>> int findIndexOfNumberRecursive(T[] arr, T numberToFind) {
		int indexFound = -1;
		if (arr != null) {
			indexFound = findIndexOfNumberRecursiveAux(arr, numberToFind, 0, arr.length - 1);
		}
		return indexFound;
	}

	/**
	 * Time Complexity: O(log n): We are skipping half of the data in each step
	 * Space Complexity: O(n): we are not using extra data structures, but we are
	 * using the stack because the recursion. If tail recursion is available it
	 * could be O(1).
	 * 
	 * @param <T>
	 * @param arr
	 * @param numberToFind
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	private static <T extends Comparable<T>> int findIndexOfNumberRecursiveAux(T[] arr, T numberToFind, int startIndex,
			int endIndex) {

		if (startIndex > endIndex) {
			return -1;
		}

		// o it can be implemented like this
		// int midIndex = (endIndex + startIndex) / 2;
		int midIndex = startIndex + (endIndex - startIndex) / 2;

		if (arr[midIndex].compareTo(numberToFind) == 0) {
			return midIndex;
		} else if (arr[midIndex].compareTo(numberToFind) < 0) {
			return findIndexOfNumberRecursiveAux(arr, numberToFind, midIndex + 1, endIndex);
		} else {
			return findIndexOfNumberRecursiveAux(arr, numberToFind, startIndex, midIndex - 1);
		}
	}

	/**
	 * Time Complexity: O(log n): We are skipping half of the data in each step
	 * Space Complexity: O(1): we are not using extra data structures, and due to
	 * the iterative approach, we are not using the stack
	 * 
	 * @param <T>
	 * @param arr
	 * @param numberToFind
	 * @return
	 */
	public static <T extends Comparable<T>> int findIndexOfNumberIterative(T[] arr, T numberToFind) {

		int indexFound = -1;

		if (arr != null) {

			int startIndex = 0;
			int endIndex = arr.length;

			while (startIndex <= endIndex) {

				int midIndex = startIndex + (endIndex - startIndex) / 2;

				if (arr[midIndex].compareTo(numberToFind) == 0) {
					indexFound = midIndex;
					break;
				} else if (arr[midIndex].compareTo(numberToFind) < 0) {
					startIndex = midIndex + 1;
				} else {
					endIndex = midIndex - 1;
				}
			}
		}

		return indexFound;

	}

	public static <T extends Comparable<T>> int findIndexOfNumberIterative(T[] arr, T numberToFind, int startIndex,
			int endIndex) {

		int indexFound = -1;

		if (arr != null && startIndex > -1 && endIndex < arr.length) {

			while (startIndex <= endIndex) {

				int midIndex = startIndex + (endIndex - startIndex) / 2;

				if (arr[midIndex].compareTo(numberToFind) == 0) {
					indexFound = midIndex;
					break;
				} else if (arr[midIndex].compareTo(numberToFind) < 0) {
					startIndex = midIndex + 1;
				} else {
					endIndex = midIndex - 1;
				}
			}
		}

		return indexFound;

	}
}