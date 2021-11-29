package trie;

import java.util.HashMap;
import java.util.Map;

/**
 * Implement a trie with the following methods:
 * 
 * 1 - insert 2 - search 3 - startsWith
 * 
 * 
 * interface Trie {
 * 
 * void insert(String word);
 * 
 * boolean search(String word);
 * 
 * boolean startsWith(String prefix);
 * 
 * }
 * 
 * A Trie can be used to retrieve similar words (for auto completion), or to see
 * if that word exists in the Trie.
 * 
 * Constraints
 * 
 * 1- Can we implement helper classes/objects?, Yes, you can use any features
 * you see fit.
 * 
 * 
 * @author Jose
 *
 */
public class Exercise30_Implement_Prefix_Trie implements Trie {

	private Map<Character, Object> rootMap;
	private final char END = '\0';

	public Exercise30_Implement_Prefix_Trie() {
		rootMap = new HashMap<>();
	}

	/**
	 * Time Complexity: O(w): the length of the word
	 * 
	 * Space Complexity: O(w): we create in the worst case as many maps as letter it
	 * has the word.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void insert(String word) {

		Map<Character, Object> upperMap = rootMap;
		Map<Character, Object> lowerMap = null;

		char[] letters = word.toCharArray();
		for (int letterIndex = 0; letterIndex < letters.length; letterIndex++) {
			char letter = letters[letterIndex];
			lowerMap = (Map<Character, Object>) upperMap.get(letter);
			if (lowerMap == null) {
				lowerMap = new HashMap<Character, Object>();
				upperMap.put(letter, lowerMap);
			}
			if (letterIndex == letters.length - 1) {
				lowerMap.put(END, null);
			}
			upperMap = lowerMap;
		}

	}

	/**
	 * Time Complexity: O(w): the length of the word
	 * 
	 * Space Complexity: O(1): no extra data structure depending on the input has
	 * the word.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean search(String word) {

		boolean exist = false;

		if (word != null) {
			Map<Character, Object> upperMap = rootMap;

			char[] letters = word.toCharArray();

			for (int letterIndex = 0; letterIndex < letters.length; letterIndex++) {

				upperMap = (Map<Character, Object>) upperMap.get(letters[letterIndex]);
				if (upperMap == null) {
					break;
				}

				else if (letterIndex == letters.length - 1) {
					exist = upperMap.containsKey(END);
				}

			}
		}
		return exist;
	}

	/**
	 * Time Complexity: O(p): the length of the prefix
	 * 
	 * Space Complexity: O(1): no extra data structure depending on the input has
	 * the word.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean startsWith(String prefix) {
		boolean exist = false;

		if (prefix != null) {
			Map<Character, Object> upperMap = rootMap;

			char[] letters = prefix.toCharArray();

			for (int letterIndex = 0; letterIndex < letters.length; letterIndex++) {

				upperMap = (Map<Character, Object>) upperMap.get(letters[letterIndex]);
				if (upperMap == null) {
					break;
				} else if (letterIndex == letters.length - 1) {
					exist = true;
				}
			}

		}
		return exist;
	}

	public static void main(String[] args) {
		Exercise30_Implement_Prefix_Trie trie = new Exercise30_Implement_Prefix_Trie();
		System.out.println("insert apple");
		trie.insert("apple");
		System.out.println("search dog: " + trie.search("dog"));
		System.out.println("insert dog");
		trie.insert("dog");
		System.out.println("search dog: " + trie.search("dog"));
		System.out.println("prefix app: " + trie.startsWith("app"));
		System.out.println("search app: " + trie.search("app"));
		System.out.println("insert app");
		trie.insert("app");
		System.out.println("search app: " + trie.search("app"));
		trie.insert("application");
	}
}
