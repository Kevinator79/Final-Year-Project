package MinesweeperPackage;

import java.util.Random;

public class Board {
  private final int rows;
  private final int columns;
  private final int numberOfMines;
  private int flaggedCount;
  private Cell cells[][];

  public Board(int rows, int columns, int numberOfMines) {
    this.rows = rows;
    this.columns = columns;
    this.numberOfMines = numberOfMines;

    cells = new Cell[rows][columns];
    for (int i=0; i<rows; i++) {
      for (int j=0; j<columns; j++) {
        cells[i][j] = new Cell();
      }
    }
  }

  private void generateMines() {
    int generatedMines = 0;
    int randomRange = rows * columns;
    Random rand = new Random();

    while (generatedMines<numberOfMines) {
      int randomNumber = rand.nextInt(randomRange);
      int currentRow = randomNumber / columns;
      int currentColumn = randomNumber % columns;
      if (cells[currentRow][currentColumn].checkHasMine() == false) {
        cells[currentRow][currentColumn].setMine();
        generatedMines++;
      }
    }
  }

  private boolean checkValidCell(int currentRow, int currentColumn) {
    return (currentRow >= 0) && (currentRow < rows) && (currentColumn >= 0) && (currentColumn < columns);
  }

  private void generateNumbers() {
    int neighbourCount = Cell.getNeighbourCount();

    for (int i=0; i<rows; i++) {
      for (int j=0; j<columns; j++) {
        if (cells[i][j].checkHasMine() == false) {
          int adjacentMines = 0;
          if (checkValidCell(i-1,j) == true) {  //North
            if (cells[i-1][j].checkHasMine() == true) {
              adjacentMines++;
            }
          }
          if (checkValidCell(i-1,j+1) == true) {  //North-East
            if (cells[i-1][j+1].checkHasMine() == true) {
              adjacentMines++;
            }
          }
          if (checkValidCell(i,j+1) == true) {  //East
            if (cells[i][j+1].checkHasMine() == true) {
              adjacentMines++;
            }
          }
          if (checkValidCell(i+1,j+1) == true) {  //South-East
            if (cells[i+1][j+1].checkHasMine() == true) {
              adjacentMines++;
            }
          }
          if (checkValidCell(i+1,j) == true) {  //South
            if (cells[i+1][j].checkHasMine() == true) {
              adjacentMines++;
            }
          }
          if (checkValidCell(i+1,j-1) == true) {  //South-West
            if (cells[i+1][j-1].checkHasMine() == true) {
              adjacentMines++;
            }
          }
          if (checkValidCell(i,j-1) == true) {  //West
            if (cells[i][j-1].checkHasMine() == true) {
              adjacentMines++;
            }
          }
          if (checkValidCell(i-1,j-1) == true) {  //North-West
            if (cells[i-1][j-1].checkHasMine() == true) {
              adjacentMines++;
            }
          }
          cells[i][j].setAdjacentMines(adjacentMines);
        }
      }
    }
  }

  public void initBoard() {
    generateMines();
    generateNumbers();
  }
}
