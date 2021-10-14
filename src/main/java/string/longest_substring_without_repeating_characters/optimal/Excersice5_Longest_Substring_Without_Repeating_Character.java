package string.longest_substring_without_repeating_characters.optimal;

import java.util.HashMap;
import java.util.Map;

/***
 * Find the longest substring inside a string without repeated characters
 * 
 * @author Jose
 *
 */
public class Excersice5_Longest_Substring_Without_Repeating_Character {

	public static void main(String[] args) {
//		String text = "abcbdca";
//		String text = "dvdf";
		String text = "abcabcbb";
		System.out.println(getLongestSubstring(text));
	}

	/***
	 * Optimize approach: using a slide window composed by two pointers, we set the
	 * two pointer at the beginning of the string and start adding the non existing
	 * characters to the map with also their index position. We continue moving the
	 * second pointer toward left and increasing the length counter until we find a
	 * character that is already in the map. In that case, if the index of the
	 * character previously stored in the map is lower than the right pointer, we
	 * consider it as a repeated character and update the current index position of
	 * the repeated character and move the first pointer to the position of the
	 * repeated character and continue from this place (this is based on the fact
	 * that everything before this point is unique, so we need to start from here to
	 * try to find a longest substring that the current find so far) Time
	 * complexity: O(n) Space complexity: O(n)
	 * 
	 * @param text
	 * @return
	 */
	public static int getLongestSubstring(String text) {
		int longestSubstring = 0;
		int startPointer = 0;
		int movingPointer = 0;

		Map<Character, Integer> uniqueCharacters = new HashMap<Character, Integer>();

		while (movingPointer < text.length()) {

			char letter = text.charAt(movingPointer);
			int letterLastIndex = uniqueCharacters.getOrDefault(letter, -1);

			if (letterLastIndex >= startPointer) {
				startPointer = letterLastIndex + 1;
			} 
				
			uniqueCharacters.put(letter, movingPointer);
			longestSubstring = Math.max((movingPointer - startPointer) + 1, longestSubstring);
			movingPointer++;

		}

		return longestSubstring;
	}

}
