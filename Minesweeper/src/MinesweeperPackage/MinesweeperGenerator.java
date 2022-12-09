package MinesweeperPackage;

import java.util.Scanner;

public class MinesweeperGenerator {
  private Board board;
  private enum gameState {ONGOING, WON, LOST};

  public MinesweeperGenerator (int rows, int columns, int numberOfMines) {
    board = new Board(rows, columns, numberOfMines);
    board.initBoard();
    board.displayGeneratedBoard();
    //System.out.print("\n");
    //board.displayBoardForSolving();
  }

  public static void main(String[] args) {
    //Scanner sc = new Scanner(System.in);
    MinesweeperGenerator minesweeper = new MinesweeperGenerator(9, 9, 10);
    while ((minesweeper.board.checkAllCellsOpenedOrFlagged() == false)) {
      Scanner sc = new Scanner(System.in);
      System.out.println("Enter row number - 1: "); //remember 0 indexed
      int row = sc.nextInt();
      System.out.println("Enter column number - 1: "); //remember 0 indexed
      int column = sc.nextInt();
      minesweeper.board.playMove(row, column);
    }
    System.out.println("End Test");
  }
}