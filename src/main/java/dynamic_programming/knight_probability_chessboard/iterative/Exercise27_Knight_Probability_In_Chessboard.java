package dynamic_programming.knight_probability_chessboard.iterative;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

	private static int[][] adjustment = { { -2, -1 }, { -2, 1 }, { -1, -2 }, { -1, 2 }, { 1, -2 }, { 1, 2 }, { 2, -1 },
			{ 2, 1 } };

	/**
	 * Using a queue to store the next move to process, we start by putting the
	 * initial position in the queue and iterate while the queue is not empty. For
	 * every position retrieved from the queue, we got 3 values, row, column and
	 * multiplier. for every position (x,y) we calculate all the possible 8 moves
	 * and each that land inside the chessboard, we store it in the queue. We keep
	 * doing this until we reach to max number of K moves, at that moment we return
	 * the probability of that last level (level is the k-esima move).
	 * 
	 * The idea of the algorithm is not to add the probability of each K move, but
	 * on every k move (iteration) calculate the valid positions positions and
	 * continue with the next k iteration. At the final iteration the number of
	 * positions generates are going to be divided by the 8^k which would return the
	 * probability. So we don´t care about the probabilities of the previous K
	 * iterations, the only interest is to calculate how many valid positions where
	 * archived from the previous iteration k.
	 * 
	 * Memoization or cache is used in following way. Every valid position at a
	 * level, is stored in a matrix who only exists during the whole processing of
	 * all the valid positions of that k level (all the positions from the level k-1
	 * that were stored in the queue). If a position already exists in the cache, we
	 * take the position stored in the queue and increase by one his multiplier
	 * value. This value is going to multiply the processing of that position in the
	 * next iteration, like we were processing that value N times, but without
	 * recalculation (not adding the duplicate position to the queue). This way,
	 * allows us to reduce the number of calculations but generating the same value
	 * of positions for the next level.
	 * 
	 * 
	 * Time Complexity: O(n^2*k): n^2 is the size of the chessboard which are in the
	 * worst case, all the positions of the chessboard that could be stored in the
	 * queue. And this value is multiply by the number of moves (iterations) k.
	 * 
	 * Space Complexity: O(n^2): On each level we have a cache of size n*n*3. when the
	 * level finish processing, this cache is deleted and recreated for the next
	 * level, so we only store one cache at the time,
	 * 
	 * 
	 * @param chessBoardDimension
	 * @param knightMoves
	 * @param knightRow
	 * @param knightColumn
	 * @return
	 */
	public static double getKightProbabilityBeingInsideChessBoard(int chessBoardDimension, int knightMoves,
			int knightRow, int knightColumn) {

		double probability = 1;

		if (chessBoardDimension < 1) {
			probability = 0;
		} else if (knightMoves > 0 && chessBoardDimension > 2) {

			Queue<List<Integer>> probabilityAtMoves = new LinkedList<>();
			List<List<List<Integer>>> cache = null;

			double probabilityAccumulated = 0;
			int rowPosition = 0;
			int columnPosition = 0;
			int positionMultiplier = 0;
			int positionsProcessed = 0;
			int positionsPerMove = 0;
			int movesProcessed = 0;

			probabilityAtMoves.add(List.of(knightRow, knightColumn, 0));

			while (!probabilityAtMoves.isEmpty()) {

				if (knightMoves < 1) {
					break;
				}
				if (positionsProcessed == 0) {
					movesProcessed++;
					probability = 0;
//					cache = new double[chessBoardDimension][chessBoardDimension];
//					for (int row = 0; row < chessBoardDimension; row++) {
//						for (int col = 0; col < chessBoardDimension; col++) {
//							cache[row][col] = -1;
//						}
//					}

					cache = IntStream.range(0, chessBoardDimension).boxed()
							.map(row -> IntStream.range(0, chessBoardDimension).boxed()
									.map(col -> Arrays.asList(-1, -1, -1)).collect(Collectors.toList()))
							.collect(Collectors.toList());

					positionsPerMove = probabilityAtMoves.size();
				}
				positionsProcessed++;

				List<Integer> knightPositionAndMove = probabilityAtMoves.poll();

				for (int adjustIndex = 0; adjustIndex < adjustment.length; adjustIndex++) {

					rowPosition = knightPositionAndMove.get(0) + adjustment[adjustIndex][0];
					columnPosition = knightPositionAndMove.get(1) + adjustment[adjustIndex][1];
					positionMultiplier = knightPositionAndMove.get(2);

					if (rowPosition > -1 && rowPosition < chessBoardDimension && columnPosition > -1
							&& columnPosition < chessBoardDimension) {

						probabilityAccumulated += positionMultiplier + 1;

						if (cache.get(rowPosition).get(columnPosition).get(2) == -1) {

							List<Integer> positionCoordinates = Arrays.asList(rowPosition, columnPosition,
									positionMultiplier);
							probabilityAtMoves.add(positionCoordinates);
							cache.get(rowPosition).set(columnPosition, positionCoordinates);

						} else {

							cache.get(rowPosition).get(columnPosition).set(2,
									cache.get(rowPosition).get(columnPosition).get(2) + positionMultiplier + 1);

//							// O(n^2), we need to traverse the whole queue, search for the element
//							Iterator<List<Integer>> queueIterator = probabilityAtMoves.iterator();
//
//							while (queueIterator.hasNext()) {
//
//								List<Integer> probabilityAtMove = queueIterator.next();
//
//								if (probabilityAtMove.get(0) == rowPosition
//										&& probabilityAtMove.get(1) == columnPosition) {
//									probabilityAtMove.set(2, probabilityAtMove.get(2) + positionMultiplier + 1);
//									break;
//								}
//							}
						}
					}
				}

				probability += probabilityAccumulated / Math.pow(adjustment.length, movesProcessed);
				probabilityAccumulated = 0;
				if (positionsProcessed == positionsPerMove) {
					knightMoves--;
					positionsProcessed = 0;
				}

			}
		}
		return probability;
	}
}