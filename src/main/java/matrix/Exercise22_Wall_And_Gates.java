package matrix;

/**
 * For every cell that contain INF (infinite) find the shortest path to a
 * gateway (0). The path should use up, down right or left cells, no diagonal.
 * Wall are represented with -1 and means that the cell is not crossable
 * 
 * The result is the same matrix with all the INF values replaced with the
 * distance to the closest gateway (if is not possible to find a way to a
 * gateway, we leave the INF value in the cell)
 * 
 * @author Jose
 *
 */
public class Exercise22_Wall_And_Gates {

	private static final MatrixIndexes[] adjacentPositions = { MatrixIndexes.build(-1, 0), MatrixIndexes.build(0, -1),
			MatrixIndexes.build(1, 0), MatrixIndexes.build(0, 1) };
	private static final int GATE = 0;
	private static final int WALL = -1;
	private static final int INF = Integer.MAX_VALUE;

	public static void main(String[] args) {
//		Integer[][] matrix = { { INF, -1, 0, INF }, { -1, INF, INF, 0 }, { INF, -1, INF, -1 }, { 0, -1, INF, INF } };
		// { { INF, -1, 0, 1}, { -1, 2, 1, 0 }, { 1, -1, 2,- 1 }, { 0, -1, 3, 4 } }

		Integer[][] matrix = { { INF, -1, 0, INF }, { INF, INF, INF, -1 }, { INF, -1, INF, -1 }, { 0, -1, INF, INF } };
		// { { 3, -1, 0, 1 }, { 2, 2, 1, -1 }, { 1, -1, 2, -1 }, { 0, -1, 3, 4 } };

		printMatrix(getShortesPathFromINFToGates(matrix));

		Integer[][] matrix2 = { { INF, -1, 0, INF }, { INF, INF, INF, -1 }, { INF, -1, INF, -1 }, { 0, -1, INF, INF } };
		printMatrix(getShortesPathFromGatesToINF(matrix2));

	}

	private static void printMatrix(Integer[][] matrix) {
		// Print the result matrix
		for (int row = 0; row < matrix.length; row++) {
			System.out.println();
			for (int column = 0; column < matrix[row].length; column++) {
				System.out.print(matrix[row][column]);
				if (column + 1 < matrix[row].length) {
					System.out.print(",");
				}
			}
		}
		System.out.println();
	}

	/**
	 * Using sequential order we traverse the matrix looking cells with INF value.
	 * At each INF cell we use DFS to find all the possible paths to gateways and
	 * grab the closest one.
	 * 
	 * For not repeating the same INF cell when recursive traverse the matrix using
	 * DFS, we use a boolean matrix to mark those cells already visited
	 * 
	 * Time Complexity: O(n): For every cell we traverse the whole matrix or great
	 * part of it
	 * 
	 * Space Complexity: O(n): we store a boolean matrix with the already seem cells
	 * 
	 * @param matrix
	 * @return
	 */
	public static Integer[][] getShortesPathFromINFToGates(Integer[][] matrix) {

		if (matrix != null && matrix.length > 0) {

			for (int row = 0; row < matrix.length; row++) {
				for (int column = 0; column < matrix[row].length; column++) {
					if (matrix[row][column] == INF) {
						boolean[][] visited = new boolean[matrix.length][matrix[0].length];
						matrix[row][column] = findShortestPathToGate_DSF(matrix, visited, row, column, 0);
					}
				}
			}
		}
		return matrix;
	}

	/**
	 * From a starting cell, we recursive traverse the matrix in all the directions
	 * (up, down, left, right), looking for gates. Any time we move to a cell we
	 * increment the step counter. At the end if multiple gateways were found, we
	 * take the shortest path.
	 * 
	 * Time Complexity: O(n): For every cell we traverse the whole matrix or great
	 * part of it
	 * 
	 * Space Complexity: O(n): we store a boolean matrix with the already seem cells
	 * 
	 * 
	 * @param matrix
	 * @param visited
	 * @param row
	 * @param column
	 * @param steps
	 * @return
	 */
	private static int findShortestPathToGate_DSF(Integer[][] matrix, boolean[][] visited, int row, int column,
			int steps) {

		if (matrix[row][column] == GATE) {
			return steps;
		} else {
			visited[row][column] = true;
		}

		int minSteps = INF;
		for (MatrixIndexes matrixIndexes : adjacentPositions) {
			int potentialRow = row + matrixIndexes.getRow();
			int potentialColumn = column + matrixIndexes.getColumn();

			if (potentialRow > -1 && potentialRow < matrix.length && potentialColumn > -1
					&& potentialColumn < matrix[row].length && matrix[potentialRow][potentialColumn] != WALL
					&& !visited[potentialRow][potentialColumn]) {

				minSteps = Math.min(minSteps,
						findShortestPathToGate_DSF(matrix, visited, potentialRow, potentialColumn, steps + 1));
			}
		}

		return minSteps;

	}

	/**
	 * THIS SOLUTION IS BETTER THAT THE ABOVE BECAUSE IT HAS LESS CALL TO DFS
	 * BECAUSE WE ONLY SCAN FROM GATES INSTEAD FROM EVERY INF
	 * 
	 * Using sequential order we traverse the matrix looking GATE cells. From each
	 * GATE using DFS we traverse all the paths in the matrix adding 1 for every
	 * step we made through INF cells.
	 * 
	 * Time Complexity: O(n): For every gate we traverse the whole matrix or great
	 * part of it
	 * 
	 * Space Complexity: O(n): No extra data structure depending on the input. Only
	 * the stack which will hold all the INF cells of the matrix in the worst case
	 * 
	 * @param matrix
	 * @return
	 */
	public static Integer[][] getShortesPathFromGatesToINF(Integer[][] matrix) {
		if (matrix != null && matrix.length > 0) {

			for (int row = 0; row < matrix.length; row++) {
				for (int column = 0; column < matrix[row].length; column++) {
					if (matrix[row][column] == GATE) {
						findShortestPathFromGate_DSF(matrix, row, column, 0);
					}
				}
			}
		}
		return matrix;
	}

	/**
	 * From a starting cell, we recursive traverse the matrix in all the directions
	 * (up, down, left, right), looking for cells different to GATES and WALLs. Any
	 * time we move to a cell we increment the step counter and update the value of
	 * the cell if the steps are lower than the current cell value.
	 * 
	 * Time Complexity: O(n): For every gate we traverse the whole matrix or great
	 * part of it
	 * 
	 * Space Complexity: O(n): No extra data structure depending on the input. Only
	 * the stack which will hold all the INF cells of the matrix in the worst case
	 * 
	 * @param matrix
	 * @param row
	 * @param column
	 * @param steps
	 */
	private static void findShortestPathFromGate_DSF(Integer[][] matrix, int row, int column, int steps) {

		if (matrix[row][column] != GATE && matrix[row][column] != WALL) {
			matrix[row][column] = Math.min(matrix[row][column], steps);
		}

		for (MatrixIndexes matrixIndexes : adjacentPositions) {
			int potentialRow = row + matrixIndexes.getRow();
			int potentialColumn = column + matrixIndexes.getColumn();

			if (potentialRow > -1 && potentialRow < matrix.length && potentialColumn > -1
					&& potentialColumn < matrix[row].length && matrix[potentialRow][potentialColumn] != GATE
					&& matrix[potentialRow][potentialColumn] != WALL
					&& matrix[potentialRow][potentialColumn] > steps + 1) {

				findShortestPathFromGate_DSF(matrix, potentialRow, potentialColumn, steps + 1);
			}
		}
	}
}
