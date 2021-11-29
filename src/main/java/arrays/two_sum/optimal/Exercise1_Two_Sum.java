package arrays.two_sum.optimal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * From an array of integer, return the indices of those two numbers that add up
 * to a given target
 * 
 * 
 * Constraints:
 * 
 * 1 - Are all the number positive or we can have negative numbers? All the
 * number are positive 2 - Are duplicate numbers in the array? No they are no
 * duplicates 3 - Will be always a solution available? No, they may be no always
 * be a solution 4 - What we return if there is no solution? return -1 for both
 * indices 5 - Can be multiple pair that can add to the target number?, no, only
 * one pair
 * 
 * @author Jose
 *
 */
public class Exercise1_Two_Sum {

	public static void main(String[] args) {
		int[] arr = { 1, 3, 7, 9, 2 };
		int target = 11;
		System.out.println(Arrays.toString(getIndicesSumUp(arr, target)));
	}

	/**
	 * using two pointers, we sum every number with all the rest numbers and see if
	 * it up to the target value.
	 * 
	 * Time Complexity: O(n): Every n number is subtracted from the target and store
	 * this results as a key in a map with the index of this number as the value. We
	 * then traverse the whole array once more asking if the current number is
	 * stored as a key in the map, if it exist we take his index and the index
	 * stored in the map
	 * 
	 * Space Complexity: O(n): we store in a map all the numbers of the array
	 * 
	 * @param arr
	 * @param target
	 * @return
	 */
	private static int[] getIndicesSumUp(int[] arr, int target) {
		int[] resp = { -1, -1 };

		if (arr != null) {
			Map<Integer, Integer> resultIndex = new HashMap<>();

			for (int index = 0; index < arr.length; index++) {
				resultIndex.put(target - arr[index], index);
			}

			for (int index = 0; index < arr.length; index++) {

				if (resultIndex.containsKey(arr[index])) {
					resp[0] = index;
					resp[1] = resultIndex.get(arr[index]);
				}
			}
		}
		return resp;
	}

}
