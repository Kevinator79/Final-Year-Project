package MinesweeperPackage;

import java.util.Scanner;

public class MinesweeperClass {
  private Board board;
  private enum gameState {ONGOING, WON, LOST};

  public MinesweeperClass (int rows, int columns, int numberOfMines) {
    board = new Board(rows, columns, numberOfMines);
    board.initBoard();
    board.display();
  }

  public static void main(String[] args) {
    MinesweeperClass minesweeper = new MinesweeperClass(9, 9, 10);
  }
}
