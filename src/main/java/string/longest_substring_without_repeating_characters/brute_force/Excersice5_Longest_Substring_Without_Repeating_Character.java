package string.longest_substring_without_repeating_characters.brute_force;

import java.util.HashMap;
import java.util.Map;

/***
 * Find the longest substring inside a string without repeated characters
 * @author Jose
 *
 */
public class Excersice5_Longest_Substring_Without_Repeating_Character {

	public static void main(String[] args) {
//		String text = "abcbdca";
		String text = "a";
		System.out.println(getLongestSubstring(text));
	}

	/***
	 * Brute force approach: Grap every element of the string and start counting characters to it's right
	 *  without repetition (using a map to store unique characters).
	 * Time complexity: O(n^2)
	 * Space complexity: O(n) 
	 * @param text
	 * @return
	 */
	public static int getLongestSubstring(String text) {
		int longestSubstring = 0;
		Map<Character, Character> uniqueCharacters = new HashMap<Character, Character>();

		for (int start = 0; start < text.length(); start++) {

			int currentLength = 0;

			for (int index = start; index < text.length(); index++) {

				char letter = text.charAt(index);

				if (uniqueCharacters.containsKey(letter)) {
					uniqueCharacters.clear();
					index = text.length();
					currentLength = 0;
				} else {
					currentLength++;
					uniqueCharacters.put(letter, letter);
					longestSubstring = Math.max(currentLength, longestSubstring);
				}
			}
		}

		return longestSubstring;
	}

}
