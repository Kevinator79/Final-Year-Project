package MinesweeperPackage;

import java.util.Scanner;

public class MinesweeperGenerator {
  private Board board;

  public MinesweeperGenerator (int rows, int columns, int numberOfMines) {
    board = new Board(rows, columns, numberOfMines);
    board.initBoard();
  }

  public static void main(String[] args) {
    Scanner difficulty = new Scanner(System.in);
    System.out.println("Enter difficulty: (beginner, intermediate, expert) ");
    String chosenDifficulty = difficulty.nextLine();
    Scanner solver = new Scanner(System.in);
    System.out.println("Select solver: (baseline, reversebaseline, winrate, time, inverse, guesses) ");
    String chosenSolver = solver.nextLine();
    //set parameters depending on difficulty
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
    int winCounter = 0;
    int numberOfGuesses = 0;
    int totalGuesses = 0;
    int guessesInWins = 0;
    long loopStart = 0;
    long loopEnd = 0;
    long winStart = 0;
    long winEnd = 0;
    long winTime = 0;
    //loop here for results
    loopStart = System.nanoTime();
    for (int i=0; i<100; i++) {
      //set up a new board
      MinesweeperGenerator minesweeper = new MinesweeperGenerator(rows, columns, numberOfMines);
      numberOfGuesses = 0;
      winStart = System.nanoTime();
      //choose firstMove depending on difficulty
      if (chosenDifficulty.equals("beginner")) {
        minesweeper.board.beginnerFirstMove(1);
        while ((minesweeper.board.checkAllCellsOpenedOrFlagged() != (rows * columns)) && (minesweeper.board.gameState == true)) {
          if (chosenSolver.equals("baseline")) {
            minesweeper.board.beginnerFirstMove(2);
            minesweeper.board.baselineSolver();
            numberOfGuesses++;
          }
          else if (chosenSolver.equals("reversebaseline")) {
            minesweeper.board.beginnerFirstMoveReversed(2);
            minesweeper.board.baselineSolver();
            numberOfGuesses++;
          }
          else if (chosenSolver.equals("winrate")) {
            minesweeper.board.beginnerFirstMove(2);
            minesweeper.board.winRateSolver();
          }
          else if (chosenSolver.equals("time")) {
            minesweeper.board.beginnerFirstMove(2);
            minesweeper.board.timeToCompleteSolver();
          }
          else if (chosenSolver.equals("inverse")) {
            minesweeper.board.beginnerFirstMoveReversed(2);
            minesweeper.board.timeToCompleteSolver();
          }
          else if (chosenSolver.equals("guesses")) {
            minesweeper.board.beginnerFirstMoveReversed(2);
            minesweeper.board.numberOfGuesses();
            numberOfGuesses++;
          }
          System.out.println("Openings revealed");
        }
        if ((minesweeper.board.checkAllCellsOpenedOrFlagged() == (rows * columns)) && (minesweeper.board.gameState == true)) {
          winCounter++;
          guessesInWins = guessesInWins + numberOfGuesses;
          winEnd = System.nanoTime();
          winTime = winTime + (winEnd - winStart);
        }
        totalGuesses = totalGuesses + numberOfGuesses;
        System.out.println("End Test");
        System.out.println("Win count: " + winCounter + " / " + (i+1));
        System.out.println("Total no. of guesses: " + (totalGuesses));
        System.out.println("No. of guesses in winning games: " + (guessesInWins));
        System.out.println("Total time for wins: " + (winTime));
      }
      else if (chosenDifficulty.equals("intermediate")) {
        minesweeper.board.intermediateFirstMove(1);
        while ((minesweeper.board.checkAllCellsOpenedOrFlagged() != (rows * columns)) && (minesweeper.board.gameState == true)) {
          if (chosenSolver.equals("baseline")) {
            minesweeper.board.intermediateFirstMove(2);
            minesweeper.board.baselineSolver();
            numberOfGuesses++;
          }
          else if (chosenSolver.equals("reversebaseline")) {
            minesweeper.board.intermediateFirstMoveReversed(2);
            minesweeper.board.baselineSolver();
            numberOfGuesses++;
          }
          else if (chosenSolver.equals("winrate")) {
            minesweeper.board.intermediateFirstMove(2);
            minesweeper.board.winRateSolver();
          }
          else if (chosenSolver.equals("time")) {
            minesweeper.board.intermediateFirstMove(2);
            minesweeper.board.timeToCompleteSolver();
          }
          else if (chosenSolver.equals("inverse")) {
            minesweeper.board.intermediateFirstMoveReversed(2);
            minesweeper.board.timeToCompleteSolver();
          }
          else if (chosenSolver.equals("guesses")) {
            minesweeper.board.intermediateFirstMoveReversed(2);
            minesweeper.board.numberOfGuesses();
            numberOfGuesses++;
          }
          System.out.println("Openings revealed");
        }
        if ((minesweeper.board.checkAllCellsOpenedOrFlagged() == (rows * columns)) && (minesweeper.board.gameState == true)) {
          winCounter++;
          guessesInWins = guessesInWins + numberOfGuesses;
          winEnd = System.nanoTime();
          winTime = winTime + (winEnd - winStart);
        }
        totalGuesses = totalGuesses + numberOfGuesses;
        System.out.println("End Test");
        System.out.println("Win count: " + winCounter + " / " + (i+1));
        System.out.println("Total no. of guesses: " + (totalGuesses));
        System.out.println("No. of guesses in winning games: " + (guessesInWins));
        System.out.println("Total time for wins: " + (winTime));
      }
      else if (chosenDifficulty.equals("expert")) {
        minesweeper.board.expertFirstMove(1);
        while ((minesweeper.board.checkAllCellsOpenedOrFlagged() != (rows * columns)) && (minesweeper.board.gameState == true)) {
          if (chosenSolver.equals("baseline")) {
            minesweeper.board.expertFirstMove(2);
            minesweeper.board.baselineSolver();
            numberOfGuesses++;
          }
          else if (chosenSolver.equals("reversebaseline")) {
            minesweeper.board.expertFirstMoveReversed(2);
            minesweeper.board.baselineSolver();
            numberOfGuesses++;
          }
          else if (chosenSolver.equals("winrate")) {
            minesweeper.board.expertFirstMove(2);
            minesweeper.board.winRateSolver();
          }
          else if (chosenSolver.equals("time")) {
            minesweeper.board.expertFirstMove(2);
            minesweeper.board.timeToCompleteSolver();
          }
          else if (chosenSolver.equals("inverse")) {
            minesweeper.board.expertFirstMoveReversed(2);
            minesweeper.board.timeToCompleteSolver();
          }
          else if (chosenSolver.equals("guesses")) {
            minesweeper.board.expertFirstMoveReversed(2);
            minesweeper.board.numberOfGuesses();
            numberOfGuesses++;
          }
          System.out.println("Openings revealed");
        }
        if ((minesweeper.board.checkAllCellsOpenedOrFlagged() == (rows * columns)) && (minesweeper.board.gameState == true)) {
          winCounter++;
          guessesInWins = guessesInWins + numberOfGuesses;
          winEnd = System.nanoTime();
          winTime = winTime + (winEnd - winStart);
        }
        totalGuesses = totalGuesses + numberOfGuesses;
        System.out.println("End Test");
        System.out.println("Win count: " + winCounter + " / " + (i+1));
        System.out.println("Total no. of guesses: " + (totalGuesses));
        System.out.println("No. of guesses in winning games: " + (guessesInWins));
        System.out.println("Total time for wins: " + (winTime));
      }
    }
    loopEnd = System.nanoTime();
    System.out.println("Time for 100 runs: " + (loopEnd - loopStart));
    //Testing:
    //System.out.println("\n");
    //minesweeper.board.displayBoardForSolving();
    //System.out.println("Openings revealed");
    /*while ((minesweeper.board.checkAllCellsOpenedOrFlagged() != (rows *
    columns)) && (minesweeper.board.gameState == true)) {
      //Scanner sc = new Scanner(System.in);
      //System.out.println("Enter row number - 1: "); //remember 0 indexed
      //int row = sc.nextInt();
      //System.out.println("Enter column number - 1: "); //remember 0 indexed
      //int column = sc.nextInt();
      //minesweeper.board.playMove(row, column);
    }*/
    //System.out.println("Success");
    //System.out.println("End Test");
  }
}