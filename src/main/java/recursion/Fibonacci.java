package recursion;

public class Fibonacci {

	public static void main(String[] args) {
		int fibonnacciIndex = -8;
		System.out.println("Iterative: " + getFibonacciNumberForIndexIterative(fibonnacciIndex));
		System.out.println("Recursive: " + getFibonacciNumberForIndexRecursive(fibonnacciIndex));
	}

	/**
	 * Time Complexity: O(n)
	 * 
	 * Space Complexity: O(1)
	 * 
	 * @param index
	 * @return
	 */
	public static int getFibonacciNumberForIndexIterative(int index) {
		int fibonacciValue0 = 0;
		int fibonacciValue1 = 1;

		if (index < 0) {
			return -1;
		}

		if (index < 2) {
			return index;
		}

		for (int item = 2; item <= index; item++) {
			int temp = fibonacciValue1;
			fibonacciValue1 += fibonacciValue0;
			fibonacciValue0 = temp;
		}

		return fibonacciValue1;
	}

	/**
	 * Time Complexity: O(2^n)
	 * 
	 * Space Complexity: O(n)
	 * 
	 * @param index
	 * @return
	 */
	public static int getFibonacciNumberForIndexRecursive(int index) {
		if (index < 0) {
			return -1;
		}

		if (index < 2) {
			return index;
		}

		return getFibonacciNumberForIndexRecursive(index - 1) + getFibonacciNumberForIndexRecursive(index - 2);
	}

}
