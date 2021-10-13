package string.backspace_string_compare.optimal;

/***
 * Validate if both string are equal. Any # character, remove the previous
 * character from the string
 * 
 * @author Jose
 *
 */

public class Excersice4_Backspace_String_Compare {

	public static void main(String[] args) {
		String text1 = "ab##";
		String text2 = "c#d#";
		
		System.out.println(stringsAreEqual(text1, text2));
	}

	/***
	 * Use two pointer, one for each string and start at the end of each string and
	 * compare each character backwards until you reach the begin of both strings.
	 * behind it) and then compare them. Time Complexity O(t1+t2) Space Complexity
	 * O(1) original string
	 * 
	 * @param text1
	 * @param text2
	 * @return
	 */
	public static boolean stringsAreEqual(String text1, String text2) {

		int pointerText1 = text1.length() - 1;
		int pointerText2 = text2.length() - 1;
		int hashesCountText1 = 0;
		int hashesCountText2 = 0;

		boolean skip = false;

		while (pointerText1 >= 0 || pointerText2 >= 0) {

			char letter1 = pointerText1 > -1 ? text1.charAt(pointerText1) : ' ';
			char letter2 = pointerText2 > -1 ? text2.charAt(pointerText2) : ' ';

			if (letter1 == '#') {
				pointerText1--;
				hashesCountText1++;
				skip = true;
			} else if (pointerText1 > -1 && hashesCountText1 > 0) {
				pointerText1--;
				hashesCountText1--;
				skip = true;
			}

			if (letter2 == '#') {
				pointerText2--;
				hashesCountText2++;
				skip = true;
			} else if (pointerText2 > -1 && hashesCountText2 > 0) {
				pointerText2--;
				hashesCountText2--;
				skip = true;
			}

			// Should skip compare?
			if (skip) {
				skip = false;
				continue;
			}

			// Are not equal?, return
			if (letter1 != letter2) {
				break;
			} else {
				pointerText1--;
				pointerText2--;
			}
		}

		return pointerText1 == -1 && pointerText2 == -1;

	}
}
