package matrix;

public class MatrixIndexes {
	protected int row;
	protected int column;

	public MatrixIndexes(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public static MatrixIndexes build(int row, int column) {
		return new MatrixIndexes(row, column);
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public String toString() {
		return String.format("[%s,%s]", row, column);
	}
}
