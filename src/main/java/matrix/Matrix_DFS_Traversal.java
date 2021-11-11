package matrix;

import java.util.Arrays;

/**
 * Traverse a matrix using Depth First Search:
 * 
 * We can start from any cell, but the rules are the following:
 * 
 * 1 - Move up if possible 2 - Move right if possible 3 - Move Down if possible
 * 4 - Move Left if possible
 * 
 * Possible mean, not moving out of the index of the matrix or revisiting an
 * already visited cell
 * 
 * Done where you already traverse all the matrix
 * 
 * @author Jose
 *
 */
public class Matrix_DFS_Traversal {

	public static void main(String[] args) {

		Integer[][] matrix = { { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 }, { 11, 12, 13, 14, 15 }, { 16, 17, 18, 19, 20 } };

		System.out.println(Arrays.toString(traverseMatrix_Iterative_DFS(matrix)));
		System.out.println(Arrays.toString(traverseMatrix_Recursive_DFS(matrix)));
	}

	/**
	 * Time Complexity: O(n): visited all the cells of the matrix
	 * 
	 * Space Complexity: o(n): using a same size matrix to track already visited
	 * cells and the result array
	 * 
	 * @param matrix
	 * @return
	 */
	public static Integer[] traverseMatrix_Iterative_DFS(Integer[][] matrix) {

		Integer[] result = null;
		if (matrix != null) {
			int resultSize = matrix.length * matrix[0].length;
			int row = 0;
			int column = 0;
			int index = 0;

			result = new Integer[resultSize];

			boolean[][] visited = new boolean[matrix.length][matrix[0].length];

			while (index < resultSize) {

				result[index++] = matrix[row][column];
				visited[row][column] = true;

				MatrixIndexes matrixIndexes = getNextMatrixIndexes(matrix, visited, row, column);
				row = matrixIndexes.getRow();
				column = matrixIndexes.getColumn();

			}
		}
		return result;
	}

	/**
	 * Time Complexity: O(n): visited all the cells of the matrix
	 * 
	 * Space Complexity: o(n): using a same size matrix to track already visited,
	 * plus the stack info which will store all the matrix elements until we fill
	 * the whole result array cells
	 * 
	 * @param matrix
	 * @return
	 */
	public static Integer[] traverseMatrix_Recursive_DFS(Integer[][] matrix) {

		Integer[] result = null;

		if (matrix != null) {
			int resultSize = matrix.length * matrix[0].length;
			int row = 0;
			int column = 0;
			int index = 0;

			result = new Integer[resultSize];

			boolean[][] visited = new boolean[matrix.length][matrix[0].length];

			result = traverseMatrix_Recursive_DFS_Aux(matrix, visited, index, row, column, result);
		}

		return result;
	}

	private static Integer[] traverseMatrix_Recursive_DFS_Aux(Integer[][] matrix, boolean[][] visited, int index,
			int row, int column, Integer[] result) {

		if (index >= result.length) {
			return result;
		}

		visited[row][column] = true;
		result[index++] = matrix[row][column];

		MatrixIndexes matrixIndexes = getNextMatrixIndexes(matrix, visited, row, column);

		return traverseMatrix_Recursive_DFS_Aux(matrix, visited, index, matrixIndexes.getRow(),
				matrixIndexes.getColumn(), result);
	}

	private static MatrixIndexes getNextMatrixIndexes(Integer[][] matrix, boolean[][] visited, int row, int column) {

		if (row - 1 > -1 && !visited[row - 1][column]) {
			row--;
		} else if (column + 1 < matrix[row].length && !visited[row][column + 1]) {
			column++;
		} else if (row + 1 < matrix.length && !visited[row + 1][column]) {
			row++;
		} else if (column - 1 > -1 && !visited[row][column - 1]) {
			column--;
		}

		return MatrixIndexes.build(row, column);
	}
}
