package recursion;

public class Factorial {

	public static void main(String[] args) {
		int value = 5;
		System.out.println("Iterative: " + factorialIterative(value));
		System.out.println("Recursive: " + factorialRecursive(value));

	}

	/**
	 * Time Complexity: O(n)
	 * 
	 * Space Complexity: O(1);
	 * 
	 * @param value
	 * @return
	 */
	public static int factorialIterative(int value) {
		int factorial = 1;
		for (int number = 2; number <= value; number++) {
			factorial *= number;
		}
		return factorial;
	}

	/**
	 * Time Complexity: O(n) 
	 * 
	 * Space Complexity: O(1);
	 * 
	 * @param value
	 * @return
	 */
	public static int factorialRecursive(int value) {
		return factorialRecursiveAux(value, 1);
	}

	private static int factorialRecursiveAux(int value, int accumulate) {
		if (value == 1) {
			return accumulate;
		}
		return factorialRecursiveAux(value - 1, value * accumulate);
	}

}
