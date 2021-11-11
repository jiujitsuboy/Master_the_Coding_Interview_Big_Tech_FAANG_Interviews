package matrix;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Count the number of Islands in the matrix.
 * 
 * An Island is consider cells with value equal to 1 (earth), and contiguous
 * earth are 1's up, down, left and right (not diagonals).
 * 
 * 
 * 
 * @author Jose
 *
 */
public class Exercise20_Number_Of_Islands {

	private static final int EARTH = 1;

	public static void main(String[] args) {
//		Integer[][] matrix = { { 1, 1, 1, 1, 0 }, { 1, 1, 0, 1, 0 }, { 1, 1, 0, 0, 1 }, { 0, 0, 0, 1, 1 } }; // 2
		Integer[][] matrix = { { 0, 1, 0, 1, 0 }, { 1, 0, 1, 0, 1 }, { 0, 1, 0, 1, 0 }, { 1, 0, 1, 0, 1 } }; // 10
//		Integer[][] matrix = { { 0, 1, 0, 1, 0 }, { 1, 0, 1, 0, 1 }, { 0, 1, 1, 1, 0 }, { 1, 0, 1, 0, 1 } }; //7
//		Integer[][] matrix = { { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 } }; //0
//		Integer[][] matrix = {};
		int nroOfIslands = getNumberOfIslands(matrix);

		System.out.println("Number of Islands: " + nroOfIslands);

	}

	/**
	 * Using sequential order we traverse the matrix from left to right, up to
	 * bottom looking for 1's when ever we find a 1, we found an island, so the next
	 * step is to see how big is the island, so using BFS we take the upper, lower,
	 * right and left cells of the current 1, that has a 1 value and repeat until we
	 * can't find anymore 1's. Once we finish we have store the cells that form the
	 * current island and continue with the sequential order traversal until we
	 * reach the end of the matrix
	 * 
	 * Time Complexity O(n): we touch every element of the matrix at least once (and
	 * a couple of times more during BFS
	 * 
	 * Space Complexity O(n): No additional structure used, we just mark cell as
	 * part of islands change their values to zero. But we store the neighbors (in
	 * the worst case the number of elements is going to be the diagonal of the
	 * matrix, O(max(R*C)))
	 * 
	 * @param matrix
	 * @return
	 */
	private static int getNumberOfIslands(Integer[][] matrix) {
		int nroIslands = 0;

		if (matrix != null && matrix.length > 0) {
			Queue<MatrixIndexes> breadElements = new LinkedList<>();

			for (int row = 0; row < matrix.length; row++) {
				for (int column = 0; column < matrix[row].length; column++) {
					if (matrix[row][column] == EARTH) {
						breadElements.add(MatrixIndexes.build(row, column));
						matrix[row][column] = 0;
						nroIslands++;
//						calculateSizeOfIsland_BFS(matrix, breadElements);
						calculateSizeOfIsland_DFS(matrix, row, column);
					}
				}
			}
		}

		return nroIslands;
	}

	/**
	 * Using BFS we search for contiguous cells that have value of 1 (that are part
	 * of the current island). Every cell that is 1, we store it in the queue and
	 * repeat this process until the queue is empty. Every cell that is part of the
	 * island is changed to 0 value to avoid recount it later
	 * 
	 * Time Complexity: O(n): we iterate over a couple of neighbors that could be
	 * the whole matrix
	 * 
	 * Space Complexity: O(n): we store the neighbors (in the worst case the number
	 * of elements is going to be the diagonal of the matrix, O(max(R*C)))
	 * 
	 * @param matrix:       original matrix
	 * @param breadElements queue that store the contiguous cells of the current
	 *                      island
	 * 
	 */
	private static void calculateSizeOfIsland_BFS(Integer[][] matrix, Queue<MatrixIndexes> breadElements) {

		while (!breadElements.isEmpty()) {
			MatrixIndexes currentIndexes = breadElements.poll();
			int row = currentIndexes.getRow();
			int column = currentIndexes.getColumn();

			if (row - 1 > -1 && matrix[row - 1][column] == EARTH) {
				breadElements.add(MatrixIndexes.build(row - 1, column));
				matrix[row - 1][column] = 0;
			}
			if (column + 1 < matrix[row].length && matrix[row][column + 1] == EARTH) {
				breadElements.add(MatrixIndexes.build(row, column + 1));
				matrix[row][column + 1] = 0;
			}
			if (row + 1 < matrix.length && matrix[row + 1][column] == EARTH) {
				breadElements.add(MatrixIndexes.build(row + 1, column));
				matrix[row + 1][column] = 0;
			}
			if (column - 1 > -1 && matrix[row][column - 1] == EARTH) {
				breadElements.add(MatrixIndexes.build(row, column - 1));
				matrix[row][column - 1] = 0;
			}

		}

	}

	/***
	 * We can use DFS to calculate the size of the island, using a iterative or
	 * recursive approach, each one of those, would use a Stack.
	 * 
	 * Time Complexity O(n): we need to touch all the elements of the matrix
	 * 
	 * Space Complexity O(n): in the worst scenario we need to store in the stack
	 * every element of the matrix. This is why in this problem BFS is better in
	 * terms of space complexity. N is assuming Row and Columns are equal, but it
	 * could be different values of each one of those.
	 * 
	 * In the examples, instead of having four ifs, they use a for loop that iterate
	 * over an array that have the 4 options evaluated with the 4 ifs
	 * 
	 */

	private static void calculateSizeOfIsland_DFS(Integer[][] matrix, int row, int column) {

		if (row - 1 > -1 && matrix[row - 1][column] == EARTH) {
			matrix[row - 1][column] = 0;
			calculateSizeOfIsland_DFS(matrix, row - 1, column);
		}
		if (column + 1 < matrix[row].length && matrix[row][column + 1] == EARTH) {
			matrix[row][column + 1] = 0;
			calculateSizeOfIsland_DFS(matrix, row, column + 1);
		}
		if (row + 1 < matrix.length && matrix[row + 1][column] == EARTH) {
			matrix[row + 1][column] = 0;
			calculateSizeOfIsland_DFS(matrix, row + 1, column);
		}
		if (column - 1 > -1 && matrix[row][column - 1] == EARTH) {
			matrix[row][column - 1] = 0;
			calculateSizeOfIsland_DFS(matrix, row, column - 1);
		}

	}
}
