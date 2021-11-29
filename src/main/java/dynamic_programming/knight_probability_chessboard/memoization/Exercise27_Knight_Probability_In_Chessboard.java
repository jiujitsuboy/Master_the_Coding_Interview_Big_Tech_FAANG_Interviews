package dynamic_programming.knight_probability_chessboard.memoization;

/**
 * In a chessboard of N*N size, we have a knight(horse) piece located at cell
 * (R,C) which can move in 8 different positions, which are picked randomly k
 * times. Calculate the probability that after the K moves the knight remains
 * inside the chessboard (if the knight gets out of the chessboard, it wont be
 * able to keep moving, even that his previous move are less than K).
 * 
 * N = Number of rows*columns in the chessboard K = Number of moves R = row C =
 * Column
 * 
 * 8 possible ways the knight can move comes from the way a Knight moves in
 * chess (two cells in one direction and then one cell to the left or the right)
 * which represents all the possible cells where the knight can land after a
 * move
 * 
 * 
 * Constraints:
 * 
 * 1- How many decimals do we round to the probability value?, Don´t round
 * 
 * 
 * This is not an optimization problem (min/max value) but we still need to find
 * all the possible moves and take the average of all the probabilities. So
 * DYNAMIC PROGRAMMING is the way to get all this solutions in an optimal way
 * 
 * 
 * @author Jose
 *
 */
public class Exercise27_Knight_Probability_In_Chessboard {

	public static void main(String[] args) {

		int n = 3;
		int k = 6;
		int r = 0;
		int c = 1;

//		int n = 6;
//		int k = 3;
//		int r = 2;
//		int c = 2;

//		No chessboard, probability 0		
//		int n = 0;
//		int k = 2;
//		int r = 1;
//		int c = 2;

//      Any move the knight does, put him out of the chessboard, probability 0		
//		int n = 2;
//		int k = 3;
//		int r = 1;
//		int c = 1;

//		No move remaining for the knight, probability 1.		
//		int n = 2;
//		int k = 0;
//		int r = 1;
//		int c = 1;

		System.out.println(getKightProbabilityBeingInsideChessBoard(n, k, r, c));
	}

	public static double getKightProbabilityBeingInsideChessBoard(int chessBoardDimension, int knightMoves,
			int knightRow, int knightColumn) {

		double probability = 1;

		if (chessBoardDimension < 1) {
			probability = 0;
		} else if (knightMoves > 0 && chessBoardDimension > 2) {
			double[][][] cache = new double[knightMoves][chessBoardDimension][chessBoardDimension];

			for (int position = 0; position < knightMoves; position++) {
				for (int row = 0; row < chessBoardDimension; row++) {
					for (int col = 0; col < chessBoardDimension; col++) {
						cache[position][row][col] = -1;
					}
				}
			}

			probability = getKightProbabilityBeingInsideChessBoardAux3(chessBoardDimension, knightMoves, knightRow,
					knightColumn, cache);
			probability = getKightProbabilityBeingInsideChessBoardAux(chessBoardDimension, knightMoves, knightRow,
					knightColumn, cache);
			probability = getKightProbabilityBeingInsideChessBoardAux2(chessBoardDimension, knightMoves, knightRow,
					knightColumn, cache);
		}
		return probability;
	}

	/**
	 * 
	 * 
	 * Time Complexity: O(n^2 * k): iterate k moves on every iteration it could
	 * generate 8 recursive calls and each of those could generate another 8 calls,
	 * k times.., but because we use caching, we in the worst case only have to call
	 * all the cells of the chessboard (n*n) not the 8^k calls previously.
	 * 
	 * Space Complexity: O(n^2 * k): we store one matrix of n*n for each level in an
	 * array of k positions.
	 * 
	 * @param chessBoardDimension
	 * @param knightMoves
	 * @param knightRow
	 * @param knightColumn
	 * @return
	 */
	private static double getKightProbabilityBeingInsideChessBoardAux(int chessBoardDimension, int knightMoves,
			int knightRow, int knightColumn, double[][][] cache) {

		double probabilityAccumulated = 0;
		final int ADJUST_LIMIT = 3;
		final double POSSIBLE_MOVES = 8d;

		int rowPosition = 0;
		int columnPosition = 0;

		// Generate the 8 positions to scan
		// adustRow goes row -2 to 2 and adjustColumn from always be (-1,1) or(-2,2)
		for (int adjustRow = -2, adjustColumn = -1
				* (ADJUST_LIMIT - Math.abs(adjustRow)); adjustRow < ADJUST_LIMIT; adjustRow++, adjustColumn = -1
						* (ADJUST_LIMIT - Math.abs(adjustRow))) {
			if (adjustRow == 0) {
				continue;
			}
			rowPosition = knightRow + adjustRow;

			// adjustColumn change the value from positive to negative
			for (int iteration = 1; iteration < ADJUST_LIMIT; iteration++) {

				columnPosition = knightColumn + adjustColumn;

				// validate the new position is inside the board
				if (rowPosition > -1 && rowPosition < chessBoardDimension && columnPosition > -1
						&& columnPosition < chessBoardDimension) {
					// validate if there are moves left otherwise we finish this move and the
					// probability is 1
					if (knightMoves - 1 > 0) {
						// validate if their is a previous calculation for the same position at the same
						// move
						if (cache[knightMoves - 2][rowPosition][columnPosition] == -1) {
							probabilityAccumulated += getKightProbabilityBeingInsideChessBoardAux(chessBoardDimension,
									knightMoves - 1, rowPosition, columnPosition, cache);
						} else {
							probabilityAccumulated += cache[knightMoves - 2][rowPosition][columnPosition];
						}
					} else {
						// probability of 1
						probabilityAccumulated++;
					}
				}

				adjustColumn *= -1;
			}
		}

		// probability of been inside the board after the eight moves
		probabilityAccumulated /= POSSIBLE_MOVES;
		// cache the result for this move
		cache[knightMoves - 1][knightRow][knightColumn] = probabilityAccumulated;
//		System.out.println(knightMoves + " " + probabilityAccumulated);

		return probabilityAccumulated;
	}

	private static int[][] adjustment = { { -2, -1 }, { -2, 1 }, { -1, -2 }, { -1, 2 }, { 1, -2 }, { 1, 2 }, { 2, -1 },
			{ 2, 1 } };

	/**
	 * Second option using an adjustment array instead of generating the values with
	 * loops
	 * 
	 * @param chessBoardDimension
	 * @param knightMoves
	 * @param knightRow
	 * @param knightColumn
	 * @param cache
	 * @return
	 */
	private static double getKightProbabilityBeingInsideChessBoardAux2(int chessBoardDimension, int knightMoves,
			int knightRow, int knightColumn, double[][][] cache) {

		double probabilityAccumulated = 0;
		int rowPosition = 0;
		int columnPosition = 0;

		for (int adjustIndex = 0; adjustIndex < adjustment.length; adjustIndex++) {

			rowPosition = knightRow + adjustment[adjustIndex][0];
			columnPosition = knightColumn + adjustment[adjustIndex][1];

			if (rowPosition > -1 && rowPosition < chessBoardDimension && columnPosition > -1
					&& columnPosition < chessBoardDimension) {

				if (knightMoves - 1 > 0) {
					if (cache[knightMoves - 2][rowPosition][columnPosition] == -1) {
						probabilityAccumulated += getKightProbabilityBeingInsideChessBoardAux2(chessBoardDimension,
								knightMoves - 1, rowPosition, columnPosition, cache);
					} else {
						probabilityAccumulated += cache[knightMoves - 2][rowPosition][columnPosition];
					}
				} else {
					probabilityAccumulated++;
				}
			}
		}

		probabilityAccumulated /= adjustment.length;
		cache[knightMoves - 1][knightRow][knightColumn] = probabilityAccumulated;
//		System.out.println(probabilityAccumulated);

		return probabilityAccumulated;
	}

	/**
	 * Option from the lecture
	 * 
	 * @param chessBoardDimension
	 * @param knightMoves
	 * @param knightRow
	 * @param knightColumn
	 * @param cache
	 * @return
	 */
	private static double getKightProbabilityBeingInsideChessBoardAux3(int chessBoardDimension, int knightMoves,
			int knightRow, int knightColumn, double[][][] cache) {

		if (knightRow < 0 || knightRow >= chessBoardDimension || knightColumn < 0
				|| knightColumn >= chessBoardDimension) {
			return 0;
		}

		if (knightMoves == 0) {
			return 1;
		}

		if (cache[knightMoves - 1][knightRow][knightColumn] != -1) {
			return cache[knightMoves - 1][knightRow][knightColumn];
		}

		double probabilityAccumulated = 0;
		int rowPosition = 0;
		int columnPosition = 0;

		for (int adjustIndex = 0; adjustIndex < adjustment.length; adjustIndex++) {

			rowPosition = knightRow + adjustment[adjustIndex][0];
			columnPosition = knightColumn + adjustment[adjustIndex][1];

			probabilityAccumulated += getKightProbabilityBeingInsideChessBoardAux3(chessBoardDimension, knightMoves - 1,
					rowPosition, columnPosition, cache) / adjustment.length;
		}

		cache[knightMoves - 1][knightRow][knightColumn] = probabilityAccumulated;

		return probabilityAccumulated;
	}
}
