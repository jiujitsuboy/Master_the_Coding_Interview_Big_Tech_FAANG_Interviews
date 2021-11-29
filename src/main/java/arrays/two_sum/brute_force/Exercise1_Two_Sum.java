package arrays.two_sum.brute_force;

import java.util.Arrays;

/**
 * From an array of integer, return the indices of those two numbers that add up
 * to a given target
 * 
 * 
 * Constraints:
 * 
 * 1 - Are all the number positive or we can have negative numbers? All the number are positive
 * 2 - Are duplicate numbers in the array? No they are no duplicates
 * 3 - Will be always a solution available? No, they may be no always be a solution
 * 4 - What we return if there is no solution?  return -1 for both indices
 * 5 - Can be multiple pair that can add to the target number?, no, only one pair 
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
	 * Time Complexity: O(n^2): Every n number should be compared n-1 times. So the
	 * outer loop iterates n times (traverse the whole array), the inner array
	 * traverse n-1 times (n-1 + n-2 + n-3 => n).
	 * 
	 *  Space Complexity: O(1): No extra data structure dependan from the input
	 * 
	 * @param arr
	 * @param target
	 * @return
	 */
	private static int[] getIndicesSumUp(int[] arr, int target) {
		int[] resp = { -1, -1 };

		if (arr != null) {
			outOfLoop: for (int index1 = 0; index1 < arr.length - 1; index1++) {
				for (int index2 = index1 + 1; index2 < arr.length; index2++) {
					if (arr[index1] + arr[index2] == target) {
						resp[0] = index1;
						resp[1] = index2;
						break outOfLoop;
					}
				}
			}
		}
		return resp;
	}

}
