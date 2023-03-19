package MinesweeperPackage;

public class cellToOpen {
  private final int row;
  private final int column;

  public cellToOpen(int row, int column) {
    this.row = row;
    this.column = column;
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }
}