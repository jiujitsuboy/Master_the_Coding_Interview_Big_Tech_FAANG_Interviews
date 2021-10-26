package sorting.algoritmn;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Exercise13_Find_K_Largest_Element {

	public static void main(String[] args) {
//		int[] arr = { 5, 3, 1, 6, 4, 2 };
		Integer[] arr = { 5, 3, 1, 6, 4, 2, 5, 4 };
		int kth_position = 4;
		System.out.println(findKLargest(arr, kth_position));

	}

	private static <T extends Comparable<T>> T findKLargest(T[] arr, int kth_position) {
		T k_largest = null;
		int counter = 0;
		Set<T> alreadyChecked = new HashSet<>();
//		QuickSort.sort(arr);		
		arr = MergeSort.sort(arr);

		System.out.println(Arrays.toString(arr));

		for (int index = arr.length - 1; index > -1; index--) {
			if (!alreadyChecked.contains(arr[index])) {
				counter++;
				alreadyChecked.add(arr[index]);
			}
			if (counter == kth_position) {
				return arr[index];
			}
		}

		return k_largest;
	}

}
