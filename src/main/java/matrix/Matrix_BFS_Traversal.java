package matrix;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Traverse a matrix using Breadth First Search:
 * 
 * We can start from any cell, but the rules are the following:
 * 
 * 1 - add the current cell value to the result and marked as visited 2 - add to
 * the queue the upper cell coordinates if possible 2 - add to the queue the
 * right cell coordinates if possible 3 - add to the queue the down cell
 * coordinates if possible 4 - add to the queue the left cell coordinates if
 * possible
 * 
 * Possible mean, not moving out of the index of the matrix or revisiting an
 * already visited cell
 * 
 * Done where you already traverse all the matrix
 * 
 * @author Jose
 *
 */
public class Matrix_BFS_Traversal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer[][] matrix = { { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 }, { 11, 12, 13, 14, 15 }, { 16, 17, 18, 19, 20 } };

		System.out.println(Arrays.toString(traverseMatrix_Iterative_BFS(matrix)));
	}

	/**
	 * Time Complexity: O(n) we traverse every element of the matrix once (because
	 * even that we try to take the neighbors of the current cell, we only stored once in the queue, not duplicates)
	 * 
	 * Space Complexity: O(n), we store the visited matrix and the result array
	 * 
	 * @param matrix
	 * @return
	 */
	public static Integer[] traverseMatrix_Iterative_BFS(Integer[][] matrix) {

		Integer[] result = null;

		if (matrix != null) {
			Queue<MatrixIndexes> breadElements = new LinkedList<>();
			int resultSize = matrix.length * matrix[0].length;
			int index = 0;

			result = new Integer[resultSize];
			boolean[][] visited = new boolean[matrix.length][matrix[0].length];

			breadElements.add(MatrixIndexes.build(0, 0));
			visited[0][0] = true;

			while (!breadElements.isEmpty()) {
				MatrixIndexes currentIndexes = breadElements.poll();
				result[index++] = matrix[currentIndexes.getRow()][currentIndexes.getColumn()];
				addToQueueNeighbors(breadElements, matrix, visited, currentIndexes);

			}

		}

		return result;
	}

	private static void addToQueueNeighbors(Queue<MatrixIndexes> breadElements, Integer[][] matrix, boolean[][] visited,
			MatrixIndexes currentIndexes) {

		int row = currentIndexes.getRow();
		int column = currentIndexes.getColumn();

		if (row - 1 > -1 && !visited[row - 1][column]) {
			breadElements.add(MatrixIndexes.build(row - 1, column));
			visited[row - 1][column] = true;
		}
		if (column + 1 < matrix[row].length && !visited[row][column + 1]) {
			breadElements.add(MatrixIndexes.build(row, column + 1));
			visited[row][column + 1] = true;
		}
		if (row + 1 < matrix.length && !visited[row + 1][column]) {
			breadElements.add(MatrixIndexes.build(row + 1, column));
			visited[row + 1][column] = true;
		}
		if (column - 1 > -1 && !visited[row][column - 1]) {
			breadElements.add(MatrixIndexes.build(row, column - 1));
			visited[row][column - 1] = true;
		}

	}
}
