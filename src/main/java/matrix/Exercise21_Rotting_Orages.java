package matrix;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Find how many minutes it takes for all the oranges to get rotten.
 * 
 * Values possible in matrix cell:
 * 
 * 0 - Empty space 1 - Healthy orange 2 - Rotten orange
 * 
 * Every healthy orange adjacent (up, down, left or right) to a rotten orange,
 * will get rotten after a minute.
 * 
 * 
 * Constraints:
 * 
 * 1 - If there is any orange that can´t get rotten, the answer should be -1 2 -
 * If there are not rotten orange, there the answer should be 0
 * 
 * 
 * @author Jose
 *
 */
public class Exercise21_Rotting_Orages {

	private static final int ROTTEN_VISITED = 3;
	private static final int ROTTEN = 2;
	private static final int FRESH = 1;
	private static final MatrixIndexes[] adjacentPositions = { MatrixIndexes.build(-1, 0), MatrixIndexes.build(0, -1),
			MatrixIndexes.build(1, 0), MatrixIndexes.build(0, 1) };

	public static void main(String[] args) {
//		Integer[][] matrix = { { 2, 1, 1, 0, 0 }, { 1, 1, 1, 0, 0 }, { 0, 1, 1, 1, 1 }, { 0, 1, 0, 0, 1 } }; // 7
//		Integer[][] matrix = { { 1, 1, 0, 0, 0 }, { 2, 1, 0, 0, 0 }, { 0, 0, 0, 1, 2 }, { 0, 1, 0, 0, 1 } }; // -1
//		Integer[][] matrix = { {}, {}, {} }; //0
//		Integer[][] matrix = {}; //0
//		Integer[][] matrix = { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } }; //-1
//		Integer[][] matrix = { { 2, 1 } }; //1
		Integer[][] matrix = { { 2, 2 }, { 2, 2 } }; // 0
		int minutes = getTimeAllOrangeGetRotten2(matrix);
		System.out.println("minutes: " + minutes);
	}

	/**
	 * Traverse the matrix looking for ROTTEN oranges. When ever a ROTTEN orange is
	 * found, we added to the queue and using BSF we go over each adjacent orange
	 * and added to the queue tracking the level (the minute frame). Once we
	 * finished processing all the adjacent oranges of the ROTTEN one, we get the
	 * biggest level obtained and we keep moving looking for additional ROTTEN
	 * oranges and repeat the process. Because we process every ROTTEN orange in a
	 * moment of time without consider the other existing ROTTEN oranges, we need to
	 * store the max result of time obtained for every ROTTEN orange
	 * 
	 * Finally when we already processed all the possible ROTTEN oranges we traverse
	 * once again the matrix to see if we find oranges that never get ROTTEN.
	 * 
	 * 
	 * Time Complexity: O(n): we traverse the whole array twice.
	 * 
	 * Space Complexity: O(n): we store in the queue all the rotten oranges which
	 * could be the whole matrix
	 * 
	 * @param matrix
	 * @return
	 */
	private static int getTimeAllOrangeGetRotten(Integer[][] matrix) {

		int numberOfMinutes = 0;

		if (matrix != null && matrix.length > 0) {

			Queue<MatrixIndexesWithLevel> breadElements = new LinkedList<>();

			for (int row = 0; row < matrix.length; row++) {
				for (int column = 0; column < matrix[row].length; column++) {
					if (matrix[row][column] == ROTTEN) {
						matrix[row][column] = ROTTEN_VISITED;
						breadElements.add(MatrixIndexesWithLevel.build(row, column, 0));
						numberOfMinutes = Math.max(numberOfMinutes,
								countTimeToRottenAdjacentOranges_BFS(matrix, breadElements));
					}
				}
			}

			OutCycle: for (int row = 0; row < matrix.length; row++) {
				for (int column = 0; column < matrix[row].length; column++) {
					if (matrix[row][column] == FRESH) {
						numberOfMinutes = -1;
						break OutCycle;
					}
				}
			}
		}

		return numberOfMinutes;
	}

	/**
	 * Using BFS we find all the adjacent FRESH oranges of each minute (level),
	 * changed to ROTTEN_VISITED and store them in the queue. Process until empty
	 * the queue
	 * 
	 * Time Complexity: O(n): we traverse the whole array twice.
	 * 
	 * Space Complexity: O(n): we store in the queue all the rotten oranges which
	 * could be the whole matrix
	 * 
	 * @param matrix
	 * @param breadElements
	 * @return
	 */
	private static int countTimeToRottenAdjacentOranges_BFS(Integer[][] matrix,
			Queue<MatrixIndexesWithLevel> breadElements) {

		int numberOfMinutes = 0;

		while (!breadElements.isEmpty()) {

			MatrixIndexesWithLevel currentIndexes = breadElements.poll();
			int row = currentIndexes.getRow();
			int column = currentIndexes.getColumn();
			int level = currentIndexes.getLevel();

			for (MatrixIndexes matrixIndexes : adjacentPositions) {

				int potentialRow = row + matrixIndexes.getRow();
				int potentialColumn = column + matrixIndexes.getColumn();

				if (potentialRow > -1 && potentialRow < matrix.length && potentialColumn > -1
						&& potentialColumn < matrix[row].length && matrix[potentialRow][potentialColumn] == FRESH) {
					breadElements.add(MatrixIndexesWithLevel.build(potentialRow, potentialColumn, level + 1));
					matrix[potentialRow][potentialColumn] = ROTTEN_VISITED;
				}
			}

			numberOfMinutes = level;

		}

		return numberOfMinutes;
	}

	/**
	 * Solution of the tutorial, where we traverse the matrix and gather all the
	 * ROTTEN oranges and put them in the queue, and the same time count the number
	 * of HEALTHY oranges.
	 * 
	 * Then we start processing the elements in the queue and using the initial size
	 * of the queue, we keep track of the level of the oranges. So every adjacent
	 * orange we ROTTEN, we add it coordinates to the queue and decrease the number
	 * of FRESH oranges
	 * 
	 * Time Complexity: O(n): we traverse the whole array once.
	 * 
	 * Space Complexity: O(n): we store in the queue all the rotten oranges which
	 * could be the whole matrix
	 * 
	 * @param matrix
	 * @return
	 */
	private static int getTimeAllOrangeGetRotten2(Integer[][] matrix) {

		int numberOfMinutes = 0;

		if (matrix != null && matrix.length > 0) {

			int freshOranges = 0;
			Queue<MatrixIndexes> breadElements = new LinkedList<>();

			for (int row = 0; row < matrix.length; row++) {
				for (int column = 0; column < matrix[row].length; column++) {
					if (matrix[row][column] == ROTTEN) {
						breadElements.add(MatrixIndexes.build(row, column));
					} else if (matrix[row][column] == FRESH) {
						freshOranges++;
					}
				}
			}

			if (!breadElements.isEmpty() || freshOranges > 0) {
				numberOfMinutes = countTimeToRottenAdjacentOranges_BFS_2(matrix, breadElements, freshOranges);
			}
		}

		return numberOfMinutes;
	}

	/**
	 * Using BFS we get the adjacent FRESH oranges of the ROTTEN oranges. The level
	 * or minute of those ROTTEN oranges is tracked using the length of the queue
	 * when no cell has been processed yet. We initialize numberOfMinutes to -1,
	 * because the first level or ROTTEN does not take any minutes to get ROTTEN, is
	 * already ROTTEN, so in this way we allow to use the increment strategy even
	 * for the first level.
	 * 
	 * Time Complexity: O(n): we traverse the whole array once.
	 * 
	 * Space Complexity: O(n): we store in the queue all the rotten oranges which
	 * could be the whole matrix
	 * 
	 * @param matrix
	 * @param breadElements
	 * @param freshOranges
	 * @return
	 */
	private static int countTimeToRottenAdjacentOranges_BFS_2(Integer[][] matrix, Queue<MatrixIndexes> breadElements,
			int freshOranges) {

		int numberOfMinutes = -1;
		long queueSize = 0;
		long cellsProcessed = 0;

		while (!breadElements.isEmpty()) {

			if (cellsProcessed == 0) {
				queueSize = breadElements.size();
			}
			cellsProcessed++;

			MatrixIndexes currentIndexes = breadElements.poll();
			int row = currentIndexes.getRow();
			int column = currentIndexes.getColumn();

			for (MatrixIndexes matrixIndexes : adjacentPositions) {

				int potentialRow = row + matrixIndexes.getRow();
				int potentialColumn = column + matrixIndexes.getColumn();

				if (potentialRow > -1 && potentialRow < matrix.length && potentialColumn > -1
						&& potentialColumn < matrix[row].length && matrix[potentialRow][potentialColumn] == FRESH) {
					breadElements.add(MatrixIndexes.build(potentialRow, potentialColumn));
					matrix[potentialRow][potentialColumn] = ROTTEN;
					freshOranges--;
				}
			}

			if (cellsProcessed == queueSize) {
				cellsProcessed = 0;
				numberOfMinutes++;
			}
		}

		return (freshOranges > 0) ? -1 : numberOfMinutes;

	}

	private static class MatrixIndexesWithLevel extends MatrixIndexes {
		protected int level;

		public MatrixIndexesWithLevel(int row, int column, int level) {
			super(row, column);
			this.level = level;
		}

		public static MatrixIndexesWithLevel build(int row, int column, int level) {
			return new MatrixIndexesWithLevel(row, column, level);
		}

		public int getLevel() {
			return level;
		}

		public String toString() {
			return String.format("[%s,%s] - l:%s", row, column, level);
		}
	}

}
