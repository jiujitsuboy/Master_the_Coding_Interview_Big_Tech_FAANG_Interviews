package stack;

import java.util.Map;
import java.util.Stack;

/***
 * Find if the correct number and order of open and closing parenthesis is present in the string.
 * @author Jose
 *
 */
public class Exercise10_Valid_Parentheses {

	public static void main(String[] args) {
		String text = "{[";
		System.out.println(validParentheses(text));

	}

	/**
	 * Using a stack, we store all the open parenthesis, when ever we find a close parenthesis, 
	 * we pop from the stack and validate if the correct closing parenthesis is retrieved. 
	 * 
	 * If we reach the end of the string, we validate if the stack is empty
	 * 
	 * Time Complexity: O(n): we need to traverse the whole string
	 * Space Complexity: O(n): we need to store in the worst case the whole string
	 * 
	 * @param text
	 * @return
	 */
	private static boolean validParentheses(String text) {

		Map<Character, Character> parenthesisRelation = Map.of('{', '}', '(', ')', '[', ']');
		Stack<Character> letterStack = new Stack<>();

		for (char letter : text.toCharArray()) {

			if (parenthesisRelation.containsKey(letter)) {
				letterStack.push(letter);
			} else {

				if (letterStack.isEmpty()) {
					return false;
				}
				if (parenthesisRelation.get(letterStack.pop()) != letter) {
					break;
				}

			}

		}

		return letterStack.isEmpty();
	}
}
