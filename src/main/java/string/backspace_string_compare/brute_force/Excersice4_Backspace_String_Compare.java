package string.backspace_string_compare.brute_force;
/***
 * Validate if both string are equal. Any # character, remove the previous character from the string
 * @param args
 */
public class Excersice4_Backspace_String_Compare {

	public static void main(String[] args) {
		String text1 = "abc ";
		String text2 = "abc";

		System.out.println(stringsAreEqual(text1, text2));
	}

	
	/***
	 * Take each string and decode it (process # where it deletes the former value behind it) and then compare them.
	 * Time Complexity O(t1+t2)
	 * Space Complexity O(t1+t2) -> we create two new string where the length can be the same as the original string
	 * 
	 * @param text1
	 * @param text2
	 * @return
	 */
	public static boolean stringsAreEqual(String text1, String text2) {

		return decodeString(text1).equals(decodeString(text2));
	}

	private static String decodeString(String text) {

		if (text == null) {
			return "";
		}
		char[] letters = text.toCharArray();
		char[] decoded = new char[letters.length];
		int nextDecodedIndex = 0;

		for (int index = 0; index < letters.length; index++) {
			char letter = letters[index];
			if (letter == '#' && nextDecodedIndex > 0) {
				nextDecodedIndex--;
				decoded[nextDecodedIndex] = ' ';

			} else {
				decoded[nextDecodedIndex] = letter;
				nextDecodedIndex++;

			}
		}

		return new String(decoded);
	}

}
