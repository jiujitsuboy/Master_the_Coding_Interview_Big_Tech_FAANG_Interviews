package dynamic_programming;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {

	private static Map<Integer, Long> cache = new HashMap<>();

	public static void main(String[] args) {
		int fibonnacciIndex = 10;
		System.out.println("Recursive: " + getFibonacciNumberForIndexRecursive(fibonnacciIndex));
		System.out.println(cache);
	}

	/**
	 * Time Complexity: O(n): We use Memoization (caching) the previous calculation to avoid 2^n calculations
	 * 
	 * Space Complexity: O(n): We store all the pre-calculated values and the Stack for the recursion (as deep as the recursion tree).
	 * 
	 * @param index
	 * @return
	 */
	public static long getFibonacciNumberForIndexRecursive(int index) {
		if (index < 0) {
			return -1;
		}

		if (index < 2) {
			return index;
		}

		Long fibNumber = cache.get(index);

		if (fibNumber == null) {
			fibNumber = getFibonacciNumberForIndexRecursive(index - 1) + getFibonacciNumberForIndexRecursive(index - 2);
			cache.put(index, fibNumber);
		}

		return fibNumber;
	}
}
