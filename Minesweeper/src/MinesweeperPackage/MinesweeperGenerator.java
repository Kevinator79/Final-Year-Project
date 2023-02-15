package MinesweeperPackage;

import java.util.Scanner;

public class MinesweeperGenerator {
  private Board board;
  private enum GameState {ONGOING, WON, LOST};

  public MinesweeperGenerator (int rows, int columns, int numberOfMines) {
    board = new Board(rows, columns, numberOfMines);
    board.initBoard();
    board.displayGeneratedBoard();
    //System.out.print("\n");
    //board.displayBoardForSolving();
  }

  public static void main(String[] args) {
    Scanner difficulty = new Scanner(System.in);
    System.out.println("Enter difficulty: (beginner, intermediate, expert) ");
    String chosenDifficulty = difficulty.nextLine();
    int rows = 0;
    int columns = 0;
    int numberOfMines = 0;
    if (chosenDifficulty.equals("beginner")) {
      rows = 8;
      columns = 8;
      numberOfMines = 10;
    }
    else if (chosenDifficulty.equals("intermediate")) {
      rows = 16;
      columns = 16;
      numberOfMines = 40;
    }
    else if (chosenDifficulty.equals("expert")) {
      rows = 16;
      columns = 30;
      numberOfMines = 99;
    }
    MinesweeperGenerator minesweeper = new MinesweeperGenerator(rows, columns, numberOfMines);
    //GameState gameState = GameState.ONGOING;
    if (chosenDifficulty.equals("beginner")) {
      minesweeper.board.beginnerFirstMove();
    }
    else if (chosenDifficulty.equals("intermediate")) {
      minesweeper.board.intermediateFirstMove();
    }
    else if (chosenDifficulty.equals("expert")) {
      minesweeper.board.expertFirstMove();
    }
    System.out.println("\n");
    minesweeper.board.displayBoardForSolving();
    System.out.println("Openings revealed");
    while ((minesweeper.board.checkAllCellsOpenedOrFlagged() == false)/* && (!(gameState == GameState.LOST))*/) {
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