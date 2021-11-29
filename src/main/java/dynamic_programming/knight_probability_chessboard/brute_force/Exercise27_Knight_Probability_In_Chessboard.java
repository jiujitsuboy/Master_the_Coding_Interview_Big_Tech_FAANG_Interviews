package dynamic_programming.knight_probability_chessboard.brute_force;

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
		int k = 3;
		int r = 0;
		int c = 1;

		
//		int n = 9;
//		int k = 2;
//		int r = 5;
//		int c = 5;

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
		System.out.println(getKightProbabilityBeingInsideChessBoard2(n, k, r, c));

	}

	public static double getKightProbabilityBeingInsideChessBoard(int chessBoardDimension, int knightMoves,
			int knightRow, int knightColumn) {

		double probability = 1;

		if (chessBoardDimension < 1) {
			probability = 0;
		} else if (knightMoves > 0 && chessBoardDimension > 2) {
			probability = getKightProbabilityBeingInsideChessBoardAux(chessBoardDimension, knightMoves, knightRow,
					knightColumn);
		}
		return probability;
	}

	/**
	 * 
	 * 
	 * Time Complexity: O(8^k): iterate k moves on every iteration it could generate
	 * 8 recursive calls and each of those could generate another 8 calls, k times..
	 * 
	 * Space Complexity: O(8^k): we store in the stack the 8 calls per k iteration,
	 * until we reach k = 0
	 * 
	 * @param chessBoardDimension
	 * @param knightMoves
	 * @param knightRow
	 * @param knightColumn
	 * @return
	 */
	private static double getKightProbabilityBeingInsideChessBoardAux(int chessBoardDimension, int knightMoves,
			int knightRow, int knightColumn) {

		double probabilityAccumulated = 0;
		
		final int ADJUST_LIMIT = 3;
		final double POSSIBLE_MOVES = 8d;

		int rowPosition = 0;
		int columnPosition = 0;

		for (int adjustRow = -2, adjustColumn = -1
				* (ADJUST_LIMIT - Math.abs(adjustRow)); adjustRow < ADJUST_LIMIT; adjustRow++, adjustColumn = -1
						* (ADJUST_LIMIT - Math.abs(adjustRow))) {
			if (adjustRow == 0) {
				continue;
			}
			rowPosition = knightRow + adjustRow;

			for (int iteration = 1; iteration < ADJUST_LIMIT; iteration++) {

				columnPosition = knightColumn + adjustColumn;

				if (rowPosition > -1 && rowPosition < chessBoardDimension && columnPosition > -1
						&& columnPosition < chessBoardDimension) {					
					if (knightMoves - 1 > 0) {
						probabilityAccumulated += getKightProbabilityBeingInsideChessBoardAux(chessBoardDimension,
								knightMoves - 1, rowPosition, columnPosition);
					}
					else {
						probabilityAccumulated++;
					}
				}

				adjustColumn *= -1;
			}
		}

		probabilityAccumulated /= POSSIBLE_MOVES;

		return probabilityAccumulated;
	}

	
	/**
	 * Second option using an adjustment array instead of generating the values with
	 * loops
	 * @param chessBoardDimension
	 * @param knightMoves
	 * @param knightRow
	 * @param knightColumn
	 * @return
	 */
	public static double getKightProbabilityBeingInsideChessBoard2(int chessBoardDimension, int knightMoves,
			int knightRow, int knightColumn) {

		double probability = 1;

		if (chessBoardDimension < 1) {
			probability = 0;
		} else if (knightMoves > 0 && chessBoardDimension > 2) {
			probability = getKightProbabilityBeingInsideChessBoardAux2(chessBoardDimension, knightMoves, knightRow,
					knightColumn);
		}
		return probability;
	}

	private static int[][] adjustment = { { -2, -1 }, { -2, 1 }, { -1, -2 }, { -1, 2 }, { 1, -2 }, { 1, 2 }, { 2, -1 },
			{ 2, 1 } };

	private static double getKightProbabilityBeingInsideChessBoardAux2(int chessBoardDimension, int knightMoves,
			int knightRow, int knightColumn) {

		double probabilityAccumulated = 0;

		int rowPosition = 0;
		int columnPosition = 0;

		for (int adjustIndex = 0; adjustIndex < adjustment.length; adjustIndex++) {

			rowPosition = knightRow + adjustment[adjustIndex][0];
			columnPosition = knightColumn + adjustment[adjustIndex][1];

			if (rowPosition > -1 && rowPosition < chessBoardDimension && columnPosition > -1
					&& columnPosition < chessBoardDimension) {

				if (knightMoves - 1 > 0) {
					probabilityAccumulated += getKightProbabilityBeingInsideChessBoardAux2(chessBoardDimension,
							knightMoves - 1, rowPosition, columnPosition);
				} else {
					probabilityAccumulated++;
				}
			}
		}

		probabilityAccumulated /= adjustment.length;

		return probabilityAccumulated;
	}
}
