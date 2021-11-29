package backtracking.brute_force;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Generare a 9*9 sudoku resolver. Sudoku is a game where you have 3*3 matrix 9
 * times, and the idea is to fill it out all the cells of those 3*3 matrixes.
 * The rules are the following:
 * 
 * 1 - Every row which cross 3 matrixes should have numbers from 1 to 9 without
 * repetition.
 * 
 * 2 - Every column which cross 3 matrixes should have numbers from 1 to 9 without
 * repetition.
 * 
 * 3 - Each 3*3 matrix should have all the number from 1 to 9
 * 
 * 
 *    - - - - - - - - -  - - - - - - - - -  - - - - - - - - -
 *   |1|2|3|4|5|6|7|8|9||1|2|3|4|5|6|7|8|9||1|2|3|4|5|6|7|8|9|
 *    - - - - - - - - -  - - - - - - - - -  - - - - - - - - -
 *   .
 *   .
 *   .
 *   .
 *  - - - - - - - - -  - - - - - - - - -  - - - - - - - - -
 *   |9|8|7|6|5|4|3|2|1||9|8|7|6|5|4|3|2|1||9|8|7|6|5|4|3|2|1|
 *    - - - - - - - - -  - - - - - - - - -  - - - - - - - - -     
 *   
 *   So the idea is to receive an incomplete sudoku of 9*9 and 
 *   find the solution if it has (fill the empty cells with 
 *   values from 1 to 9 in compliance with the rules state above)
 *   
 * Constraints:
 * 
 * 1- What happens if the board cannot be solved? This mean that the sudoku
 * received has repeated values. so in this case leave the board as you
 * received.
 * 
 * @author Jose
 *
 */
public class Exercise28_Sudoku_Solver {

	private static List<Set<Integer>> columnsUsedValues = new ArrayList<>();
	private static List<Set<Integer>> rowsUsedValues = new ArrayList<>();
	private static List<Set<Integer>> boxUsedValues = new ArrayList<>();

	public static void main(String[] args) {

//		int[][] sudokuBoard = { { 5, 3, -1, -1, 7, -1, -1, -1, -1 }, { 6, -1, -1, 1, 9, 5, -1, -1, -1 },
//				{ -1, 9, 8, -1, -1, -1, -1, 6, -1 }, { 8, -1, -1, -1, 6, -1, -1, -1, 3 },
//				{ 4, -1, -1, 8, -1, 3, -1, -1, 1 }, { 7, -1, -1, -1, 2, -1, -1, -1, 6 },
//				{ -1, 6, -1, -1, -1, -1, 2, 8, -1 }, { -1, -1, -1, 4, 1, 9, -1, -1, 5 },
//				{ -1, -1, -1, -1, -1, 8, -1, 7, 9 } };

//		int[][] sudokuBoard = { { -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 },
//				{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 },
//				{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 },
//				{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 },
//				{ -1, -1, -1, -1, -1, -1, -1, -1, -1 } };

		int[][] sudokuBoard = { { 9, -1, -1, 6, 7, -1, 5, 3, 1 }, { -1, -1, -1, -1, 4, 9, 8, 2, -1 },
				{ 2, 7, -1, -1, 3, 1, -1, 4, 9 }, { -1, -1, -1, 1, 5, -1, 4, -1, -1 },
				{ -1, -1, 4, 3, -1, 6, 2, -1, -1 }, { -1, 6, 9, -1, -1, -1, -1, 5, 3 },
				{ -1, -1, -1, -1, -1, 5, 9, 6, 2 }, { -1, -1, 2, 7, -1, -1, -1, -1, 5 },
				{ 5, -1, -1, -1, 2, -1, 3, 7, -1 } };

		System.out.println("is resolved: " + solveSudoku(sudokuBoard));
		for (int row = 0; row < sudokuBoard.length; row++) {

			System.out.println(Arrays.toString(sudokuBoard[row]));

		}

	}

	/**
	 * 
	 * Time Complexity: O(9^81):   O(9^81) solveSudokuAux + O(n) readCurrentValuesInBoard
	 * 
	 * Space Complexity: O(n): O(1) solveSudokuAux + O(n) readCurrentValuesInBoard
	 * 
	 * @param sudokuBoard
	 * @return
	 */
	public static boolean solveSudoku(int[][] sudokuBoard) {

		boolean isPossibleToResolve = false;

		if (sudokuBoard != null) {
			readCurrentValuesInBoard(sudokuBoard);
			isPossibleToResolve = solveSudokuAux(sudokuBoard, 0, 0);
		}

		return isPossibleToResolve;

	}

	/**
	 * 
	 * Load into the validation matrix the current values in the sudoku
	 * 
	 * Time Complexity: O(n): we traverse the whole sudoku board
	 * 
	 * Space Complexity: O(n): we store 3 times the matrix, one for rows, other for
	 * columns and other for boxes
	 * 
	 * @param sudokuBoard
	 */
	private static void readCurrentValuesInBoard(int[][] sudokuBoard) {

		IntStream.range(0, sudokuBoard.length).forEach(num -> {
			columnsUsedValues.add(new HashSet<Integer>());
			rowsUsedValues.add(new HashSet<Integer>());
			boxUsedValues.add(new HashSet<Integer>());

		});

		for (int row = 0; row < sudokuBoard.length; row++) {
			for (int column = 0; column < sudokuBoard.length; column++) {

				if (sudokuBoard[row][column] != -1) {

					addValueToSets(row, column, sudokuBoard[row][column]);
				}
			}
		}
	}

	/**
	 * 
	 * try from 1 to 9 for each available cell and check if the values is valid
	 * according to the sudoku rules, if not valid, try with the next value and if
	 * non of the 9 values are valid, go back on cell and try with the next value
	 * and so forth.
	 * 
	 * Time Complexity: O(9^81): iterate the whole matrix 9^9 times. This would be
	 * also O(1) because it would take the same time for every 9x9 matrix
	 * 
	 * Space Complexity: O(1) no extra data structure from the input
	 * 
	 * @param sudokuBoard
	 * @param row
	 * @param column
	 * @return
	 */
	private static boolean solveSudokuAux(int[][] sudokuBoard, int row, int column) {

		if (row >= sudokuBoard.length) {
			return true;
		}

		while (sudokuBoard[row][column] != -1) {
			if (column + 1 < sudokuBoard[0].length) {
				column++;
			} else {
				column = 0;
				row++;
			}

			if (row >= sudokuBoard.length) {
				return true;
			}
		}

		int newRow = row;
		int newColumn = column;
		// Next value for recursive call
		if (column + 1 < sudokuBoard[0].length) {
			newColumn++;
		} else {
			newColumn = 0;
			newRow++;
		}

		for (int possibleValue = 1; possibleValue <= 9; possibleValue++) {
			if (isValidValue(row, column, possibleValue)) {

				sudokuBoard[row][column] = possibleValue;
				addValueToSets(row, column, sudokuBoard[row][column]);

				if (solveSudokuAux(sudokuBoard, newRow, newColumn)) {
					return true;
				}
				removeValueFromSets(row, column, sudokuBoard[row][column]);
				sudokuBoard[row][column] = -1;
			}
		}

		return false;

	}

	/**
	 * 
	 * Time Complexity: O(1) constant time operation
	 * 
	 * Space Complexity: O(1) no extra data structure from the input
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	private static int getBoxIndex(int row, int column) {
		return (int) (Math.floor(column / 3.0) + (Math.floor(row / 3.0) * 3));
	}

	/**
	 * 
	 * Time Complexity: O(1) constant time operation
	 * 
	 * Space Complexity: O(1) no extra data structure from the input
	 * 
	 * @param row
	 * @param column
	 * @param value
	 */
	private static void addValueToSets(int row, int column, int value) {
		columnsUsedValues.get(column).add(value);
		rowsUsedValues.get(row).add(value);
		boxUsedValues.get(getBoxIndex(row, column)).add(value);

	}

	/**
	 * 
	 * Time Complexity: O(1) constant time operation
	 * 
	 * Space Complexity: O(1) no extra data structure from the input
	 * 
	 * @param row
	 * @param column
	 * @param value
	 */
	private static void removeValueFromSets(int row, int column, int value) {
		columnsUsedValues.get(column).remove(value);
		rowsUsedValues.get(row).remove(value);
		boxUsedValues.get(getBoxIndex(row, column)).remove(value);

	}

	/**
	 * Time Complexity: O(1) constant time operation
	 * 
	 * Space Complexity: O(1) no extra data structure from the input
	 * 
	 * @param row
	 * @param column
	 * @param possibleValue
	 * @return
	 */
	private static boolean isValidValue(int row, int column, int possibleValue) {

		return !columnsUsedValues.get(column).contains(possibleValue)
				&& !rowsUsedValues.get(row).contains(possibleValue)
				&& !boxUsedValues.get(getBoxIndex(row, column)).contains(possibleValue);

	}

}