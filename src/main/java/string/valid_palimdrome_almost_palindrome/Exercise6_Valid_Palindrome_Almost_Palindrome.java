package string.valid_palimdrome_almost_palindrome;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/***
 * Validate if the string is a palindrome or almost a palindrome (by removing one character it could become a palindrome) considering aphanumeric
 * characters and ignore case sensitive
 * 
 * @author Jose
 *
 */
public class Exercise6_Valid_Palindrome_Almost_Palindrome {

	public static void main(String[] args) {

		System.out.println("1- Method");
		System.out.println(isPalidromeUsingInwardPointers("aabbaa", true));
		System.out.println(isPalidromeUsingInwardPointers("aaccbaa", true));
//		System.out.println(isPalidromeUsingInwardPointers("", true));
//		System.out.println(isPalidromeUsingInwardPointers("c", true));
//		System.out.println(isPalidromeUsingInwardPointers("abc", true));
//		System.out.println(isPalidromeUsingInwardPointers("A man, a plan, a canal: Panama", true));

	}
	
	/***
	 * This is what we called a subproblem [is not the original problem, but is needed to solve part of the problem (in this case, seeing if this string is a palidrome), 
	 * to allow to resolve the whole problem]
	 * We choose this palindrome validation technique of the 3 available, because this is the one that is simpler to indicate the indexes of the conflicted letter.
	 * Time Complexity: O(n)
	 * Space Complexity: O(n)
	 * @param text
	 * @param allowAlmost
	 * @return
	 */

	public static boolean isPalidromeUsingInwardPointers(String text, boolean allowAlmost) {

		int mismatchCount = 0;

		if (text == null) {
			return false;
		}

		String normalizedString = normalizeString(text);

		int leftPointer = 0;
		int rightPointer = normalizedString.length() - 1;

		while (leftPointer < rightPointer) {

			if (normalizedString.charAt(leftPointer) != normalizedString.charAt(rightPointer)) {
				if(!allowAlmost) {
					return false;
				}
				else if (mismatchCount++ > 1 || !isAlmostAPalindrome(normalizedString, leftPointer, rightPointer)) {
					return false;
				}
			}

			leftPointer++;
			rightPointer--;
		}

		return true;
	}

	private static boolean isAlmostAPalindrome(String text, int indexLeft, int indexRight) {

		String textVarianteLeft = IntStream.rangeClosed(indexLeft,indexRight).filter(index -> index != indexLeft).boxed()
				.map(index -> String.valueOf(text.charAt(index))).collect(Collectors.joining());
		
		
		String textVarianteRight = IntStream.rangeClosed(indexLeft,indexRight).filter(index -> index != indexRight).boxed()
				.map(index -> String.valueOf(text.charAt(index))).collect(Collectors.joining());

		System.out.println(text + "-" + textVarianteLeft + "-" + textVarianteRight);

		return isPalidromeUsingInwardPointers(textVarianteLeft, false)
				|| isPalidromeUsingInwardPointers(textVarianteRight, false);

	}

	private static String normalizeString(String text) {
		return text.toLowerCase().replaceAll("[^A-Za-z0-9]", "");
	}

}
