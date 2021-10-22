package stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/***
 * From a string of only round brackets () remove the minimum number of brackets
 * to make it valid. (correct number and place of open and close brackets)
 * 
 * @author Jose
 *
 */
public class Exervise11_Minimum_Brackets_To_Remove {

	public static void main(String[] args) {
		String text = "abcd((gh)";
//		System.out.print(prueba(text));
		System.out.println(validParenthesesString(text));
	}

	private static String prueba(String text) {

		char[] letters = text.toCharArray();
		letters[4] = (char) 0;
		return new String(letters);
	}

	/***
	 * Traverse the whole string storing in a stack the open parenthesis with their
	 * position, skip letter and when find a closing parenthesis, peek the stack to
	 * see if the corresponding open parenthesis exits, if there is a match, pop the
	 * parenthesis from the stack, otherwise push the closing parenthesis with it
	 * position.
	 * 
	 * Once the complete string has been traversed, we take the remaining
	 * parenthesis in the stack and using their position, we remove those position
	 * from the original string and return a valid parenthesis string.
	 * 
	 * Time Complexity: O(n) Space Complexity: O(n)
	 * 
	 * @param text
	 * @return
	 */
	private static String validParenthesesString(String text) {

		if (text == null) {
			return null;
		}

		final char openParenthesis = '(';
		final char closeParenthesis = ')';

		Stack<BracketInfo> letterStack = new Stack<>();
		Map<Long, Long> positionsToRemove = new HashMap<>();

		char[] originalLetters = text.toCharArray();
		StringBuilder finalLetters = new StringBuilder();

		for (int position = 0; position < originalLetters.length; position++) {

			char letter = originalLetters[position];

			switch (letter) {
			case openParenthesis:
				letterStack.push(new BracketInfo(letter == '(', position));
				break;
			case closeParenthesis:
				if (!letterStack.isEmpty() && letterStack.peek().isOpenBracket()) {
					letterStack.pop();
				} else {
					letterStack.push(new BracketInfo(letter == ')', position));
				}
				break;
			}
		}

		if (!letterStack.isEmpty()) {

			while (!letterStack.isEmpty()) {
				long positionToRemove = letterStack.pop().getPosition();
				positionsToRemove.put(positionToRemove, positionToRemove);
			}

			for (int position = 0; position < originalLetters.length; position++) {
				if (!positionsToRemove.containsValue((long) position)) {
					finalLetters.append(originalLetters[position]);
				}

			}
			return finalLetters.toString();
		}

		return text;
	}

	private static class BracketInfo {
		private boolean openBracket;
		private long position;

		public BracketInfo(boolean openBracket, long position) {
			this.openBracket = openBracket;
			this.position = position;
		}

		public boolean isOpenBracket() {
			return openBracket;
		}

		public long getPosition() {
			return position;
		}

	}
}
