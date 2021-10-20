package string.valid_palimdrome_almost_palindrome;

/***
 * Validate if the string is a palindrome only considering aphanumeric
 * characters and ignore case sensitive
 * 
 * @author Jose
 *
 */
public class Exercise6_Valid_Palindrome {

	public static void main(String[] args) {

		System.out.println("1- Method");
		System.out.println(isPalidromeUsingInwardPointers("aabbaa"));
		System.out.println(isPalidromeUsingInwardPointers(""));
		System.out.println(isPalidromeUsingInwardPointers("c"));
		System.out.println(isPalidromeUsingInwardPointers("abc"));
		System.out.println(isPalidromeUsingInwardPointers("A man, a plan, a canal: Panama"));

		System.out.println("2- Method");
		System.out.println(isPalidromeUsingOutwardPointers("aabbaa"));
		System.out.println(isPalidromeUsingOutwardPointers(""));
		System.out.println(isPalidromeUsingOutwardPointers("c"));
		System.out.println(isPalidromeUsingOutwardPointers("abc"));
		System.out.println(isPalidromeUsingOutwardPointers("A man, a plan, a canal: Panama"));

		System.out.println("3- Method");
		System.out.println(isPalidromeUsingReverseClone("aabbaa"));
		System.out.println(isPalidromeUsingReverseClone(""));
		System.out.println(isPalidromeUsingReverseClone("c"));
		System.out.println(isPalidromeUsingReverseClone("abc"));
		System.out.println(isPalidromeUsingReverseClone("A man, a plan, a canal: Panama"));
	}

	public static boolean isPalidromeUsingInwardPointers(String text) {

		if (text == null) {
			return false;
		}
		
		String normalizedString = normalizeString(text);
	
		int leftPointer = 0;
		int rightPointer = normalizedString.length() - 1;

		while (leftPointer < rightPointer) {

			if (normalizedString.charAt(leftPointer) != normalizedString.charAt(rightPointer)) {
				return false;
			}

			leftPointer++;
			rightPointer--;
		}

		return true;
	}

	public static boolean isPalidromeUsingOutwardPointers(String text) {

		if (text == null) {
			return false;
		}
		
		String normalizedString = normalizeString(text);

		int leftPointer = 0;
		int rightPointer = 0;

		// is even
		if (normalizedString.length() % 2 == 0) {
			int middle = normalizedString.length() / 2;
			leftPointer = middle - 1;
			rightPointer = middle;
		}
		// is odd
		else {
			int middle = (int) Math.round(normalizedString.length() / 2d);
			leftPointer = middle - 2;
			rightPointer = middle;
		}

		while (leftPointer > -1 && rightPointer < normalizedString.length()) {

			if (normalizedString.charAt(leftPointer) != normalizedString.charAt(rightPointer)) {
				return false;
			}

			leftPointer--;
			rightPointer++;
		}

		return true;
	}

	public static boolean isPalidromeUsingReverseClone(String text) {

		if (text == null) {
			return false;
		}

		String normalizedString = normalizeString(text);
		
		String reverseText = new StringBuilder(normalizedString).reverse().toString();

		for (int index = 0; index < normalizedString.length(); index++) {
			if (normalizedString.charAt(index) != reverseText.charAt(index)) {
				return false;
			}
		}

		return true;

	}
	
	private static String normalizeString(String text) {
		return text.toLowerCase().replaceAll("[^A-Za-z0-9]", "");
	}

}
