package MinesweeperPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board {
  private final int rows;
  private final int columns;
  private final int numberOfMines;
  private int flaggedCount;
  private Cell cells[][];
  private static final int listOfNeighbouringCellRows[] = {-1, -1, -1, 0, 0, 1,
    1, 1};
  private static final int listOfNeighbouringCellColumns[] = {-1, 0, 1, -1, 1
      , -1, 0, 1};

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
    return ((currentRow >= 0) && (currentRow < rows) && (currentColumn >= 0) && (currentColumn < columns));
  }

  private void generateNumbers() {
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

  private boolean checkValidOpening(int currentRow, int currentColumn) {
    return ((currentRow >= 0) && (currentRow < rows) && (currentColumn >= 0) && (currentColumn < columns) && (!(cells[currentRow][currentColumn].checkBeenFlagged())) && (!(cells[currentRow][currentColumn].checkBeenOpened())));
  }

  private void openNeighboursRecursively(int currentRow, int currentColumn) {
    cells[currentRow][currentColumn].setBeenOpened();
    if (checkValidOpening(currentRow-1,currentColumn) == true) {  //North
      if (cells[currentRow-1][currentColumn].getAdjacentMines() > 0) {
        cells[currentRow-1][currentColumn].setBeenOpened();
      }
      else {
        openNeighboursRecursively(currentRow-1, currentColumn);
      }
    }
    if (checkValidOpening(currentRow-1,currentColumn+1) == true) {  //North-East
      if (cells[currentRow-1][currentColumn+1].getAdjacentMines() > 0) {
        cells[currentRow-1][currentColumn+1].setBeenOpened();
      }
      else {
        openNeighboursRecursively(currentRow-1, currentColumn+1);
      }
    }
    if (checkValidOpening(currentRow,currentColumn+1) == true) {  //East
      if (cells[currentRow][currentColumn+1].getAdjacentMines() > 0) {
        cells[currentRow][currentColumn+1].setBeenOpened();
      }
      else {
        openNeighboursRecursively(currentRow, currentColumn+1);
      }
    }
    if (checkValidOpening(currentRow+1,currentColumn+1) == true) {  //South-East
      if (cells[currentRow+1][currentColumn+1].getAdjacentMines() > 0) {
        cells[currentRow+1][currentColumn+1].setBeenOpened();
      }
      else {
        openNeighboursRecursively(currentRow+1, currentColumn+1);
      }
    }
    if (checkValidOpening(currentRow+1,currentColumn) == true) {  //South
      if (cells[currentRow+1][currentColumn].getAdjacentMines() > 0) {
        cells[currentRow+1][currentColumn].setBeenOpened();
      }
      else {
        openNeighboursRecursively(currentRow+1, currentColumn);
      }
    }
    if (checkValidOpening(currentRow+1,currentColumn-1) == true) {  //South-West
      if (cells[currentRow+1][currentColumn-1].getAdjacentMines() > 0) {
        cells[currentRow+1][currentColumn-1].setBeenOpened();
      }
      else {
        openNeighboursRecursively(currentRow+1, currentColumn-1);
      }
    }
    if (checkValidOpening(currentRow,currentColumn-1) == true) {  //West
      if (cells[currentRow][currentColumn-1].getAdjacentMines() > 0) {
        cells[currentRow][currentColumn-1].setBeenOpened();
      }
      else {
        openNeighboursRecursively(currentRow, currentColumn-1);
      }
    }
    if (checkValidOpening(currentRow-1,currentColumn-1) == true) {  //North-West
      if (cells[currentRow-1][currentColumn-1].getAdjacentMines() > 0) {
        cells[currentRow-1][currentColumn-1].setBeenOpened();
      }
      else {
        openNeighboursRecursively(currentRow-1, currentColumn-1);
      }
    }
  }

  public void flagCell(int currentRow, int currentColumn) {
    cells[currentRow][currentColumn].setBeenFlagged();
  }

  public void openCell(int currentRow, int currentColumn) {
    cells[currentRow][currentColumn].setBeenOpened();
    if (cells[currentRow][currentColumn].getAdjacentMines() == 0) {
      openNeighboursRecursively(currentRow, currentColumn);
    }
  }

  public void openCellsFor1_2_1HorizontalPattern(int i, int j) {
    if (checkValidCell(i-1, j)) {
      openCell(i-1, j);
    }
    if (checkValidCell(i+1, j)) {
      openCell(i+1, j);
    }
    if (checkValidCell(i-1, j-2)) {
      openCell(i-1, j-2);
    }
    if (checkValidCell(i, j-2)) {
      openCell(i, j-2);
    }
    if (checkValidCell(i+1, j-2)) {
      openCell(i+1, j-2);
    }
    if (checkValidCell(i-1, j+2)) {
      openCell(i-1, j+2);
    }
    if (checkValidCell(i, j+2)) {
      openCell(i, j+2);
    }
    if (checkValidCell(i+1, j+2)) {
      openCell(i+1, j+2);
    }
  }

  public void openCellsFor1_2_1VerticalPattern(int i, int j) {
    if (checkValidCell(i-2, j-1)) {
      openCell(i-2, j-1);
    }
    if (checkValidCell(i-2, j)) {
      openCell(i-2, j);
    }
    if (checkValidCell(i-2, j+1)) {
      openCell(i-2, j+1);
    }
    if (checkValidCell(i, j-1)) {
      openCell(i, j-1);
    }
    if (checkValidCell(i, j+1)) {
      openCell(i, j+1);
    }
    if (checkValidCell(i+2, j-1)) {
      openCell(i+2, j-1);
    }
    if (checkValidCell(i+2, j)) {
      openCell(i+2, j);
    }
    if (checkValidCell(i+2, j+1)) {
      openCell(i+2, j+1);
    }
  }

  public void openCellsFor1_2_2_1HorizontalPattern(int i, int j) {
    if (checkValidCell(i-1, j-1)) {
      openCell(i-1, j-1);
    }
    if (checkValidCell(i-1, j-2)) {
      openCell(i-1, j-2);
    }
    if (checkValidCell(i, j-2)) {
      openCell(i, j-2);
    }
    if (checkValidCell(i+1, j-2)) {
      openCell(i+1, j-2);
    }
    if (checkValidCell(i+1, j-1)) {
      openCell(i+1, j-1);
    }
    if (checkValidCell(i-1, j+2)) {
      openCell(i-1, j+2);
    }
    if (checkValidCell(i-1, j+3)) {
      openCell(i-1, j+3);
    }
    if (checkValidCell(i, j+3)) {
      openCell(i, j+3);
    }
    if (checkValidCell(i+1, j+3)) {
      openCell(i+1, j+3);
    }
    if (checkValidCell(i+1, j+2)) {
      openCell(i+1, j+2);
    }
  }

  public void openCellsFor1_2_2_1VerticalPattern(int i, int j) {
    if (checkValidCell(i-2, j-1)) {
      openCell(i-2, j-1);
    }
    if (checkValidCell(i-2, j)) {
      openCell(i-2, j);
    }
    if (checkValidCell(i-2, j+1)) {
      openCell(i-2, j+1);
    }
    if (checkValidCell(i-1, j-1)) {
      openCell(i-1, j-1);
    }
    if (checkValidCell(i-1, j+1)) {
      openCell(i-1, j+1);
    }
    if (checkValidCell(i+2, j-1)) {
      openCell(i+2, j-1);
    }
    if (checkValidCell(i+2, j+1)) {
      openCell(i+2, j+1);
    }
    if (checkValidCell(i+3, j-1)) {
      openCell(i+3, j-1);
    }
    if (checkValidCell(i+3, j)) {
      openCell(i+3, j);
    }
    if (checkValidCell(i+3, j+1)) {
      openCell(i+3, j+1);
    }
  }

  public void openCellsForSingleCell() {
    for (int i=0; i<rows; i++) {
      for (int j = 0; j < columns; j++) {
        if (cells[i][j].checkBeenOpened() == true) {
          int numberOfFlaggedNeighbours = 0;
          Integer arrayOfNonFlaggedNeighbourRows[] = {};
          Integer arrayOfNonFlaggedNeighbourColumns[] = {};
          ArrayList<Integer> arrayListOfNonFlaggedNeighbourRows = new ArrayList<Integer>(Arrays.asList(arrayOfNonFlaggedNeighbourRows));
          ArrayList<Integer> arrayListOfNonFlaggedNeighbourColumns = new ArrayList<Integer>(Arrays.asList(arrayOfNonFlaggedNeighbourColumns));
          for (int k = 0; k < 8; k++) {
            int rowOfNextCellToCheck = i + listOfNeighbouringCellRows[k];
            int columnOfNextCellToCheck = j + listOfNeighbouringCellColumns[k];
            if ((checkValidCell(rowOfNextCellToCheck, columnOfNextCellToCheck) == true) && (cells[rowOfNextCellToCheck][columnOfNextCellToCheck].checkBeenFlagged() == false)) {
              arrayListOfNonFlaggedNeighbourRows.add(rowOfNextCellToCheck);
              arrayListOfNonFlaggedNeighbourColumns.add(columnOfNextCellToCheck);
            }
            else if ((checkValidCell(rowOfNextCellToCheck, columnOfNextCellToCheck) == true) && (cells[rowOfNextCellToCheck][columnOfNextCellToCheck].checkBeenFlagged() == true)) {
              numberOfFlaggedNeighbours++;
            }
          }
          arrayOfNonFlaggedNeighbourRows = arrayListOfNonFlaggedNeighbourRows.toArray(arrayOfNonFlaggedNeighbourRows);
          arrayOfNonFlaggedNeighbourColumns = arrayListOfNonFlaggedNeighbourColumns.toArray(arrayOfNonFlaggedNeighbourColumns);
          if ((numberOfFlaggedNeighbours == 1) && (cells[i][j].getAdjacentMines() == 1)) {
            for (int l = 0; l < arrayListOfNonFlaggedNeighbourRows.size(); l++) {
              openCell(arrayOfNonFlaggedNeighbourRows[l], arrayOfNonFlaggedNeighbourColumns[l]);
              //patternMatching();
            }
          }
          else if ((numberOfFlaggedNeighbours == 2) && (cells[i][j].getAdjacentMines() == 2)) {
            for (int l = 0; l < arrayListOfNonFlaggedNeighbourRows.size(); l++) {
              openCell(arrayOfNonFlaggedNeighbourRows[l], arrayOfNonFlaggedNeighbourColumns[l]);
              //patternMatching();
            }
          }
          else if ((numberOfFlaggedNeighbours == 3) && (cells[i][j].getAdjacentMines() == 3)) {
            for (int l = 0; l < arrayListOfNonFlaggedNeighbourRows.size(); l++) {
              openCell(arrayOfNonFlaggedNeighbourRows[l], arrayOfNonFlaggedNeighbourColumns[l]);
              //patternMatching();
            }
          }
          else if ((numberOfFlaggedNeighbours == 4) && (cells[i][j].getAdjacentMines() == 4)) {
            for (int l = 0; l < arrayListOfNonFlaggedNeighbourRows.size(); l++) {
              openCell(arrayOfNonFlaggedNeighbourRows[l], arrayOfNonFlaggedNeighbourColumns[l]);
              //patternMatching();
            }
          }
          else if ((numberOfFlaggedNeighbours == 5) && (cells[i][j].getAdjacentMines() == 5)) {
            for (int l = 0; l < arrayListOfNonFlaggedNeighbourRows.size(); l++) {
              openCell(arrayOfNonFlaggedNeighbourRows[l], arrayOfNonFlaggedNeighbourColumns[l]);
              //patternMatching();
            }
          }
          else if ((numberOfFlaggedNeighbours == 6) && (cells[i][j].getAdjacentMines() == 6)) {
            for (int l = 0; l < arrayListOfNonFlaggedNeighbourRows.size(); l++) {
              openCell(arrayOfNonFlaggedNeighbourRows[l], arrayOfNonFlaggedNeighbourColumns[l]);
              //patternMatching();
            }
          }
          else if ((numberOfFlaggedNeighbours == 7) && (cells[i][j].getAdjacentMines() == 7)) {
            for (int l = 0; l < arrayListOfNonFlaggedNeighbourRows.size(); l++) {
              openCell(arrayOfNonFlaggedNeighbourRows[l], arrayOfNonFlaggedNeighbourColumns[l]);
              //patternMatching();
            }
          }
        }
      }
    }
  }

  private void patternMatching() {
    for (int i=0; i<rows; i++) {
      for (int j = 0; j < columns; j++) {
        //pattern match for 1-2-1
        if (cells[i][j].getAdjacentMines() == 2) {
          //horizontal patterns
          if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() == 1) && (checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (cells[i][j+1].getAdjacentMines() == 1) && (checkValidCell(i+1, j)) && (cells[i+1][j].checkBeenOpened() == true) && (checkValidCell(i+1, j-1)) && (cells[i+1][j-1].checkBeenOpened() == true) && (checkValidCell(i+1, j+1)) && (cells[i+1][j+1].checkBeenOpened() == true)) {
            flagCell(i-1, j-1);
            flagCell(i-1, j+1);
            openCellsFor1_2_1HorizontalPattern(i, j);
          }
          else if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() == 1) && (checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (cells[i][j+1].getAdjacentMines() == 1) && (checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (checkValidCell(i-1, j-1)) && (cells[i-1][j-1].checkBeenOpened() == true) && (checkValidCell(i-1, j+1)) && (cells[i-1][j+1].checkBeenOpened() == true)) {
            flagCell(i+1, j-1);
            flagCell(i+1, j+1);
            openCellsFor1_2_1HorizontalPattern(i, j);
          }
          else if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() == 1) && (checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (cells[i][j+1].getAdjacentMines() == 1) && (checkValidCell(i-1, j+1)) && (cells[i-1][j+1].checkBeenOpened() == true) && (checkValidCell(i+1, j-1)) && (cells[i+1][j-1].checkBeenOpened() == true)) {
            flagCell(i-1, j-1);
            flagCell(i+1, j+1);
            openCellsFor1_2_1HorizontalPattern(i, j);
          }
          else if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() == 1) && (checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (cells[i][j+1].getAdjacentMines() == 1) && (checkValidCell(i-1, j-1)) && (cells[i-1][j-1].checkBeenOpened() == true) && (checkValidCell(i+1, j+1)) && (cells[i+1][j+1].checkBeenOpened() == true)) {
            flagCell(i-1, j+1);
            flagCell(i+1, j-1);
            openCellsFor1_2_1HorizontalPattern(i, j);
          }
          //vertical patterns
          else if ((checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (cells[i-1][j].getAdjacentMines() == 1) && (checkValidCell(i+1, j)) && (cells[i+1][j].checkBeenOpened() == true) && (cells[i+1][j].getAdjacentMines() == 1) && (checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (checkValidCell(i-1, j-1)) && (cells[i-1][j-1].checkBeenOpened() == true) && (checkValidCell(i+1, j-1)) && (cells[i+1][j-1].checkBeenOpened() == true)) {
            flagCell(i-1, j+1);
            flagCell(i+1, j+1);
            openCellsFor1_2_1VerticalPattern(i, j);
          }
          else if ((checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (cells[i-1][j].getAdjacentMines() == 1) && (checkValidCell(i+1, j)) && (cells[i+1][j].checkBeenOpened() == true) && (cells[i+1][j].getAdjacentMines() == 1) && (checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (checkValidCell(i-1, j+1)) && (cells[i-1][j+1].checkBeenOpened() == true) && (checkValidCell(i+1, j+1)) && (cells[i+1][j+1].checkBeenOpened() == true)) {
            flagCell(i-1, j-1);
            flagCell(i+1, j-1);
            openCellsFor1_2_1VerticalPattern(i, j);
          }
          else if ((checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (cells[i-1][j].getAdjacentMines() == 1) && (checkValidCell(i+1, j)) && (cells[i+1][j].checkBeenOpened() == true) && (cells[i+1][j].getAdjacentMines() == 1) && (checkValidCell(i-1, j+1)) && (cells[i-1][j+1].checkBeenOpened() == true) && (checkValidCell(i+1, j-1)) && (cells[i+1][j-1].checkBeenOpened() == true)) {
            flagCell(i-1, j-1);
            flagCell(i+1, j+1);
            openCellsFor1_2_1VerticalPattern(i, j);
          }
          else if ((checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (cells[i-1][j].getAdjacentMines() == 1) && (checkValidCell(i+1, j)) && (cells[i+1][j].checkBeenOpened() == true) && (cells[i+1][j].getAdjacentMines() == 1) && (checkValidCell(i-1, j-1)) && (cells[i-1][j-1].checkBeenOpened() == true) && (checkValidCell(i+1, j+1)) && (cells[i+1][j+1].checkBeenOpened() == true)) {
            flagCell(i-1, j+1);
            flagCell(i+1, j-1);
            openCellsFor1_2_1VerticalPattern(i, j);
          }
          //horizontal patterns on edge
          else if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() == 1) && (checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (cells[i][j+1].getAdjacentMines() == 1) && (!checkValidCell(i+1, j))) {
            flagCell(i-1, j-1);
            flagCell(i-1, j+1);
            openCellsFor1_2_1HorizontalPattern(i, j);
          }
          else if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() == 1) && (checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (cells[i][j+1].getAdjacentMines() == 1) && (!checkValidCell(i-1, j))) {
            flagCell(i+1, j-1);
            flagCell(i+1, j+1);
            openCellsFor1_2_1HorizontalPattern(i, j);
          }
          //vertical patterns on edge
          else if ((checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (cells[i-1][j].getAdjacentMines() == 1) && (checkValidCell(i+1, j)) && (cells[i+1][j].checkBeenOpened() == true) && (cells[i+1][j].getAdjacentMines() == 1) && (!checkValidCell(i, j-1))) {
            flagCell(i-1, j+1);
            flagCell(i+1, j+1);
            openCellsFor1_2_1VerticalPattern(i, j);
          }
          else if ((checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (cells[i-1][j].getAdjacentMines() == 1) && (checkValidCell(i+1, j)) && (cells[i+1][j].checkBeenOpened() == true) && (cells[i+1][j].getAdjacentMines() == 1) && (!checkValidCell(i, j+1))) {
            flagCell(i-1, j-1);
            flagCell(i+1, j-1);
            openCellsFor1_2_1VerticalPattern(i, j);
          }
        }
        //pattern match for 1-2-2-1
        //horizontal patterns
        if ((cells[i][j].getAdjacentMines() == 2) && (checkValidCell(i, j+1) == true) && (cells[i][j+1].getAdjacentMines() == 2) && (cells[i][j+1].checkBeenOpened() == true)) {
          if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() == 1) && (checkValidCell(i, j+2)) && (cells[i][j+2].checkBeenOpened() == true) && (cells[i][j+2].getAdjacentMines() == 1) && (checkValidCell(i+1, j)) && (cells[i+1][j].checkBeenOpened() == true) && (checkValidCell(i+1, j+1)) && (cells[i+1][j+1].checkBeenOpened() == true)) {
            flagCell(i-1, j);
            flagCell(i-1, j+1);
            openCellsFor1_2_2_1HorizontalPattern(i, j);
          }
          else if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() == 1) && (checkValidCell(i, j+2)) && (cells[i][j+2].checkBeenOpened() == true) && (cells[i][j+2].getAdjacentMines() == 1) && (checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (checkValidCell(i-1, j+1)) && (cells[i-1][j+1].checkBeenOpened() == true)) {
            flagCell(i+1, j);
            flagCell(i+1, j+1);
            openCellsFor1_2_2_1HorizontalPattern(i, j);
          }
          else if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() == 1) && (checkValidCell(i, j+2)) && (cells[i][j+2].checkBeenOpened() == true) && (cells[i][j+2].getAdjacentMines() == 1) && (checkValidCell(i-1, j+1)) && (cells[i-1][j+1].checkBeenOpened() == true) && (checkValidCell(i+1, j)) && (cells[i+1][j].checkBeenOpened() == true)) {
            flagCell(i-1, j);
            flagCell(i+1, j+1);
            openCellsFor1_2_2_1HorizontalPattern(i, j);
          }
          else if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() == 1) && (checkValidCell(i, j+2)) && (cells[i][j+2].checkBeenOpened() == true) && (cells[i][j+2].getAdjacentMines() == 1) && (checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (checkValidCell(i+1, j+1)) && (cells[i+1][j+1].checkBeenOpened() == true)) {
            flagCell(i-1, j+1);
            flagCell(i+1, j);
            openCellsFor1_2_2_1HorizontalPattern(i, j);
          }
          //horizontal patterns on edge
          else if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() == 1) && (checkValidCell(i, j+2)) && (cells[i][j+2].checkBeenOpened() == true) && (cells[i][j+2].getAdjacentMines() == 1) && (!checkValidCell(i-1, j))) {
            flagCell(i+1, j);
            flagCell(i+1, j+1);
            openCellsFor1_2_2_1HorizontalPattern(i, j);
          }
          else if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() == 1) && (checkValidCell(i, j+2)) && (cells[i][j+2].checkBeenOpened() == true) && (cells[i][j+2].getAdjacentMines() == 1) && (!checkValidCell(i+1, j))) {
            flagCell(i-1, j);
            flagCell(i-1, j+1);
            openCellsFor1_2_2_1HorizontalPattern(i, j);
          }
        }
        //vertical patterns
        if ((cells[i][j].getAdjacentMines() == 2) && (checkValidCell(i+1, j) == true) && (cells[i+1][j].getAdjacentMines() == 2) && (cells[i+1][j].checkBeenOpened() == true)) {
          if ((checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (cells[i-1][j].getAdjacentMines() == 1) && (checkValidCell(i+2, j)) && (cells[i+2][j].checkBeenOpened() == true) && (cells[i+2][j].getAdjacentMines() == 1) && (checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (checkValidCell(i+1, j-1)) && (cells[i+1][j-1].checkBeenOpened() == true)) {
            flagCell(i, j+1);
            flagCell(i+1, j+1);
            openCellsFor1_2_2_1VerticalPattern(i, j);
          }
          else if ((checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (cells[i-1][j].getAdjacentMines() == 1) && (checkValidCell(i+2, j)) && (cells[i+2][j].checkBeenOpened() == true) && (cells[i+2][j].getAdjacentMines() == 1) && (checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (checkValidCell(i+1, j+1)) && (cells[i+1][j+1].checkBeenOpened() == true)) {
            flagCell(i, j-1);
            flagCell(i+1, j-1);
            openCellsFor1_2_2_1VerticalPattern(i, j);
          }
          else if ((checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (cells[i-1][j].getAdjacentMines() == 1) && (checkValidCell(i+2, j)) && (cells[i+2][j].checkBeenOpened() == true) && (cells[i+2][j].getAdjacentMines() == 1) && (checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (checkValidCell(i+1, j-1)) && (cells[i+1][j-1].checkBeenOpened() == true)) {
            flagCell(i, j-1);
            flagCell(i+1, j+1);
            openCellsFor1_2_2_1VerticalPattern(i, j);
          }
          else if ((checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (cells[i-1][j].getAdjacentMines() == 1) && (checkValidCell(i+2, j)) && (cells[i+2][j].checkBeenOpened() == true) && (cells[i+2][j].getAdjacentMines() == 1) && (checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (checkValidCell(i+1, j+1)) && (cells[i+1][j+1].checkBeenOpened() == true)) {
            flagCell(i, j+1);
            flagCell(i+1, j-1);
            openCellsFor1_2_2_1VerticalPattern(i, j);
          }
          //vertical patterns on edge
          else if ((checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (cells[i-1][j].getAdjacentMines() == 1) && (checkValidCell(i+2, j)) && (cells[i+2][j].checkBeenOpened() == true) && (cells[i+2][j].getAdjacentMines() == 1) && (!checkValidCell(i, j+1))) {
            flagCell(i, j-1);
            flagCell(i+1, j-1);
            openCellsFor1_2_2_1VerticalPattern(i, j);
          }
          else if ((checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (cells[i-1][j].getAdjacentMines() == 1) && (checkValidCell(i+2, j)) && (cells[i+2][j].checkBeenOpened() == true) && (cells[i+2][j].getAdjacentMines() == 1) && (!checkValidCell(i, j-1))) {
            flagCell(i, j+1);
            flagCell(i+1, j+1);
            openCellsFor1_2_2_1VerticalPattern(i, j);
          }
        }
        //pattern match for 1-2-X variations
        //horizontal patterns
        if ((cells[i][j].getAdjacentMines() == 2) && (cells[i][j].checkBeenOpened() == true) && (((checkValidCell(i+1, j-1)) && (cells[i+1][j-1].checkBeenOpened() == true) && (checkValidCell(i+1, j)) && (cells[i+1][j].checkBeenOpened() == true) && (checkValidCell(i+1, j+1)) && (cells[i+1][j+1].checkBeenOpened() == true)) || ((!checkValidCell(i+1, j-1)) && (!checkValidCell(i+1, j)) && (!checkValidCell(i+1, j+1))))) {
          if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() == 1) && (checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (cells[i][j+1].getAdjacentMines() != 0) && (checkValidCell(i-1, j+1))) {
            flagCell(i-1, j+1);
          }
          else if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() != 0) && (checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (cells[i][j+1].getAdjacentMines() == 1) && (checkValidCell(i-1, j-1))) {
            flagCell(i-1, j-1);
          }
        }
        else if ((cells[i][j].getAdjacentMines() == 2) && (cells[i][j].checkBeenOpened() == true) && (((checkValidCell(i-1, j-1)) && (cells[i-1][j-1].checkBeenOpened() == true) && (checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (checkValidCell(i-1, j+1)) && (cells[i-1][j+1].checkBeenOpened() == true)) || ((!checkValidCell(i-1, j-1)) && (checkValidCell(i-1, j)) && (checkValidCell(i-1, j+1))))) {
          if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() == 1) && (checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (cells[i][j+1].getAdjacentMines() != 0) && (checkValidCell(i+1, j+1))) {
            flagCell(i+1, j+1);
          }
          else if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() != 0) && (checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (cells[i][j+1].getAdjacentMines() == 1) && (checkValidCell(i+1, j-1))) {
            flagCell(i+1, j-1);
          }
        }
        //vertical patterns
        if ((cells[i][j].getAdjacentMines() == 2) && (cells[i][j].checkBeenOpened() == true) && (((checkValidCell(i-1, j-1)) && (cells[i-1][j-1].checkBeenOpened() == true) && (checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (checkValidCell(i+1, j-1)) && (cells[i+1][j-1].checkBeenOpened() == true)) || ((!checkValidCell(i-1, j-1)) && (!checkValidCell(i, j-1)) && (!checkValidCell(i+1, j-1))))) {
          if ((checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (cells[i-1][j].getAdjacentMines() == 1) && (checkValidCell(i+1, j)) && (cells[i+1][j].checkBeenOpened() == true) && (cells[i+1][j].getAdjacentMines() != 0) && (checkValidCell(i+1, j+1))) {
            flagCell(i+1, j+1);
          }
          else if ((checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (cells[i-1][j].getAdjacentMines() != 0) && (checkValidCell(i+1, j)) && (cells[i+1][j].checkBeenOpened() == true) && (cells[i+1][j].getAdjacentMines() == 1) && (checkValidCell(i-1, j+1))) {
            flagCell(i-1, j+1);
          }
        }
        else if ((cells[i][j].getAdjacentMines() == 2) && (cells[i][j].checkBeenOpened() == true) && (((checkValidCell(i-1, j+1)) && (cells[i-1][j+1].checkBeenOpened() == true) && (checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (checkValidCell(i+1, j+1)) && (cells[i+1][j+1].checkBeenOpened() == true)) || ((!checkValidCell(i-1, j+1)) && (!checkValidCell(i, j+1)) && (!checkValidCell(i+1, j+1))))) {
          if ((checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (cells[i-1][j].getAdjacentMines() == 1) && (checkValidCell(i+1, j)) && (cells[i+1][j].checkBeenOpened() == true) && (cells[i+1][j].getAdjacentMines() != 0) && (checkValidCell(i+1, j-1))) {
            flagCell(i+1, j-1);
          }
          else if ((checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (cells[i-1][j].getAdjacentMines() != 0) && (checkValidCell(i+1, j)) && (cells[i+1][j].checkBeenOpened() == true) && (cells[i+1][j].getAdjacentMines() == 1) && (checkValidCell(i-1, j-1))) {
            flagCell(i-1, j-1);
          }
        }
        //pattern match for 1-1-X
        if ((cells[i][j].getAdjacentMines() == 1)  && (cells[i][j].checkBeenOpened() == true)) {
          //horizontal patterns
          //1-1-X
          if ((checkValidCell(i, j+1)) && (cells[i][j+1].getAdjacentMines() == 1) && (cells[i][j+1].checkBeenOpened() == true) && (checkValidCell(i, j+2)) && (cells[i][j+2].getAdjacentMines() != 0) && (cells[i][j+2].checkBeenOpened() == true)) {
            if (((!checkValidCell(i-1, j-1)) && (!checkValidCell(i, j-1)) && (!checkValidCell(i+1, j-1))) || ((checkValidCell(i-1, j-1)) && (cells[i-1][j-1].checkBeenOpened() == true) && (cells[i-1][j-1].getAdjacentMines() != 0) && (checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() != 0) && (checkValidCell(i+1, j-1)) && (cells[i+1][j-1].checkBeenOpened() == true) && (cells[i+1][j-1].getAdjacentMines() != 0))) {
              if ((checkValidCell(i+1, j)) && (cells[i+1][j].checkBeenOpened() == true) && (checkValidCell(i+1, j+1)) && (cells[i+1][j+1].checkBeenOpened() == true) && (checkValidCell(i+1, j+2)) && (cells[i+1][j+2].checkBeenOpened() == true) && (checkValidCell(i-1, j+2))) {
                openCell(i-1, j+2);
              }
              else if ((checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (checkValidCell(i-1, j+1)) && (cells[i-1][j+1].checkBeenOpened() == true) && (checkValidCell(i-1, j+2)) && (cells[i-1][j+2].checkBeenOpened() == true) && (checkValidCell(i+1, j+2))) {
                openCell(i+1, j+2);
              }
            }
            //1-1-X on edge
            else if ((!checkValidCell(i, j-1)) && (!checkValidCell(i-1, j-1)) && (!checkValidCell(i+1, j)) && (!checkValidCell(i+1, j+1)) && (!checkValidCell(i+1, j+2)) && (checkValidCell(i-1, j+2))) {
              openCell(i-1, j+2);
            }
            else if ((!checkValidCell(i, j-1)) && (!checkValidCell(i+1, j-1)) && (!checkValidCell(i-1, j)) && (!checkValidCell(i-1, j+1)) && (!checkValidCell(i-1, j+2)) && (checkValidCell(i+1, j+2))) {
              openCell(i+1, j+2);
            }
          }
          //X-1-1
          else if ((checkValidCell(i, j-1)) && (cells[i][j-1].getAdjacentMines() == 1) && (cells[i][j-1].checkBeenOpened() == true) && (checkValidCell(i, j-2)) && (cells[i][j-2].getAdjacentMines() != 0) && (cells[i][j-2].checkBeenOpened() == true)) {
            if (((!checkValidCell(i-1, j+1)) && (!checkValidCell(i, j+1)) && (!checkValidCell(i+1, j+1))) || ((checkValidCell(i-1, j+1)) && (cells[i-1][j+1].checkBeenOpened() == true) && (cells[i-1][j+1].getAdjacentMines() != 0) && (checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (cells[i][j+1].getAdjacentMines() != 0) && (checkValidCell(i+1, j+1)) && (cells[i+1][j+1].checkBeenOpened() == true) && (cells[i+1][j+1].getAdjacentMines() != 0))) {
              if ((checkValidCell(i+1, j)) && (cells[i+1][j].checkBeenOpened() == true) && (checkValidCell(i+1, j-1)) && (cells[i+1][j-1].checkBeenOpened() == true) && (checkValidCell(i+1, j-2)) && (cells[i+1][j-2].checkBeenOpened() == true) && (checkValidCell(i-1, j-2))) {
                openCell(i-1, j-2);
              }
              else if ((checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (checkValidCell(i-1, j-1)) && (cells[i-1][j-1].checkBeenOpened() == true) && (checkValidCell(i-1, j-2)) && (cells[i-1][j-2].checkBeenOpened() == true) && (checkValidCell(i+1, j-2))) {
                openCell(i+1, j-2);
              }
            }
            //X-1-1 on edge
            else if ((!checkValidCell(i, j+1)) && (!checkValidCell(i-1, j+1)) && (!checkValidCell(i+1, j)) && (!checkValidCell(i+1, j-1)) && (!checkValidCell(i+1, j-2)) && (checkValidCell(i-1, j-2))) {
              openCell(i-1, j-2);
            }
            else if ((!checkValidCell(i, j+1)) && (!checkValidCell(i+1, j+1)) && (!checkValidCell(i-1, j)) && (!checkValidCell(i-1, j-1)) && (!checkValidCell(i-1, j-2)) && (checkValidCell(i+1, j-2))) {
              openCell(i+1, j-2);
            }
          }
          //vertical patterns
          //1-1-X top-down
          if ((checkValidCell(i+1, j)) && (cells[i+1][j].getAdjacentMines() == 1) && (cells[i+1][j].checkBeenOpened() == true) && (checkValidCell(i+2, j)) && (cells[i+2][j].getAdjacentMines() != 0) && (cells[i+2][j].checkBeenOpened() == true)) {
            if (((!checkValidCell(i-1, j-1)) && (!checkValidCell(i-1, j)) && (!checkValidCell(i-1, j+1))) || ((checkValidCell(i-1, j-1)) && (cells[i-1][j-1].checkBeenOpened() == true) && (cells[i-1][j-1].getAdjacentMines() != 0) && (checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (cells[i-1][j].getAdjacentMines() != 0) && (checkValidCell(i-1, j+1)) && (cells[i-1][j+1].checkBeenOpened() == true) && (cells[i-1][j+1].getAdjacentMines() != 0))) {
              if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (checkValidCell(i+1, j-1)) && (cells[i+1][j-1].checkBeenOpened() == true) && (checkValidCell(i+2, j-1)) && (cells[i+2][j-1].checkBeenOpened() == true) && (checkValidCell(i+2, j+1))) {
                openCell(i+2, j+1);
              }
              else if ((checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (checkValidCell(i+1, j+1)) && (cells[i+1][j+1].checkBeenOpened() == true) && (checkValidCell(i+2, j+1)) && (cells[i+2][j+1].checkBeenOpened() == true) && (checkValidCell(i+2, j-1))) {
                openCell(i+2, j-1);
              }
            }
            //top-down on edge
            else if ((!checkValidCell(i-1, j+1)) && (!checkValidCell(i-1, j)) && (!checkValidCell(i, j-1)) && (!checkValidCell(i+1, j-1)) && (!checkValidCell(i+2, j-1)) && (checkValidCell(i+2, j+1))) {
              openCell(i+2, j+1);
            }
            else if ((!checkValidCell(i-1, j-1)) && (!checkValidCell(i-1, j)) && (!checkValidCell(i, j+1)) && (!checkValidCell(i+1, j+1)) && (!checkValidCell(i+2, j+1)) && (checkValidCell(i+2, j-1))) {
              openCell(i+2, j-1);
            }
          }
          //1-1-X bottom-up
          else if ((checkValidCell(i-1, j)) && (cells[i-1][j].getAdjacentMines() == 1) && (cells[i-1][j].checkBeenOpened() == true) && (checkValidCell(i-2, j)) && (cells[i-2][j].getAdjacentMines() != 0) && (cells[i-2][j].checkBeenOpened() == true)) {
            if (((!checkValidCell(i+1, j-1)) && (!checkValidCell(i+1, j)) && (!checkValidCell(i+1, j+1))) || ((checkValidCell(i+1, j-1)) && (cells[i+1][j-1].checkBeenOpened() == true) && (cells[i+1][j-1].getAdjacentMines() != 0) && (checkValidCell(i+1, j)) && (cells[i+1][j].checkBeenOpened() == true) && (cells[i+1][j].getAdjacentMines() != 0) && (checkValidCell(i+1, j+1)) && (cells[i+1][j+1].checkBeenOpened() == true) && (cells[i+1][j+1].getAdjacentMines() != 0))) {
              if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (checkValidCell(i-1, j-1)) && (cells[i-1][j-1].checkBeenOpened() == true) && (checkValidCell(i-2, j-1)) && (cells[i-2][j-1].checkBeenOpened() == true) && (checkValidCell(i-2, j+1))) {
                openCell(i-2, j+1);
              }
              else if ((checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (checkValidCell(i-1, j+1)) && (cells[i-1][j+1].checkBeenOpened() == true) && (checkValidCell(i-2, j+1)) && (cells[i-2][j+1].checkBeenOpened() == true) && (checkValidCell(i-2, j-1))) {
                openCell(i-2, j-1);
              }
            }
            //bottom-up on edge
            else if ((!checkValidCell(i+1, j)) && (!checkValidCell(i+1, j-1)) && (!checkValidCell(i, j-1)) && (!checkValidCell(i-1, j-1)) && (!checkValidCell(i-2, j-1)) && (checkValidCell(i-2, j+1))) {
              openCell(i-2, j+1);
            }
            else if ((!checkValidCell(i+1, j)) && (!checkValidCell(i+1, j+1)) && (!checkValidCell(i, j+1)) && (!checkValidCell(i-1, j+1)) && (!checkValidCell(i-2, j+1)) && (checkValidCell(i-2, j-1))) {
              openCell(i-2, j-1);
            }
          }
        }
        //pattern match for a single cell
        int numberOfClosedNeighbours = 0;
        Integer arrayOfClosedNeighbourRows[] = {};
        Integer arrayOfClosedNeighbourColumns[] = {};
        ArrayList<Integer> arrayListOfClosedNeighbourRows = new ArrayList<Integer>(Arrays.asList(arrayOfClosedNeighbourRows));
        ArrayList<Integer> arrayListOfClosedNeighbourColumns = new ArrayList<Integer>(Arrays.asList(arrayOfClosedNeighbourColumns));
        for (int k = 0; k < 8; k++) {
          int rowOfNextCellToCheck = i + listOfNeighbouringCellRows[k];
          int columnOfNextCellToCheck = j + listOfNeighbouringCellColumns[k];
          if ((checkValidCell(rowOfNextCellToCheck, columnOfNextCellToCheck) == true) && (cells[rowOfNextCellToCheck][columnOfNextCellToCheck].checkBeenOpened() == false)) {
            numberOfClosedNeighbours++;
            arrayListOfClosedNeighbourRows.add(rowOfNextCellToCheck);
            arrayListOfClosedNeighbourColumns.add(columnOfNextCellToCheck);
          }
        }
        arrayOfClosedNeighbourRows = arrayListOfClosedNeighbourRows.toArray(arrayOfClosedNeighbourRows);
        arrayOfClosedNeighbourColumns = arrayListOfClosedNeighbourColumns.toArray(arrayOfClosedNeighbourColumns);
        if ((cells[i][j].getAdjacentMines() == 1) && (numberOfClosedNeighbours == 1) && (cells[i][j].checkBeenOpened() == true)) {
          flagCell(arrayOfClosedNeighbourRows[0], arrayOfClosedNeighbourColumns[0]);
        }
        else if ((cells[i][j].getAdjacentMines() == 2) && (numberOfClosedNeighbours == 2) && (cells[i][j].checkBeenOpened() == true)) {
          for (int l = 0; l < arrayListOfClosedNeighbourRows.size(); l++) {
            flagCell(arrayOfClosedNeighbourRows[l], arrayOfClosedNeighbourColumns[l]);
          }
        }
        else if ((cells[i][j].getAdjacentMines() == 3) && (numberOfClosedNeighbours == 3) && (cells[i][j].checkBeenOpened() == true)) {
          for (int l = 0; l < arrayListOfClosedNeighbourRows.size(); l++) {
            flagCell(arrayOfClosedNeighbourRows[l], arrayOfClosedNeighbourColumns[l]);
          }
        }
        else if ((cells[i][j].getAdjacentMines() == 4) && (numberOfClosedNeighbours == 4) && (cells[i][j].checkBeenOpened() == true)) {
          for (int l = 0; l < arrayListOfClosedNeighbourRows.size(); l++) {
            flagCell(arrayOfClosedNeighbourRows[l], arrayOfClosedNeighbourColumns[l]);
          }
        }
        else if ((cells[i][j].getAdjacentMines() == 5) && (numberOfClosedNeighbours == 5) && (cells[i][j].checkBeenOpened() == true)) {
          for (int l = 0; l < arrayListOfClosedNeighbourRows.size(); l++) {
            flagCell(arrayOfClosedNeighbourRows[l], arrayOfClosedNeighbourColumns[l]);
          }
        }
        else if ((cells[i][j].getAdjacentMines() == 6) && (numberOfClosedNeighbours == 6) && (cells[i][j].checkBeenOpened() == true)) {
          for (int l = 0; l < arrayListOfClosedNeighbourRows.size(); l++) {
            flagCell(arrayOfClosedNeighbourRows[l], arrayOfClosedNeighbourColumns[l]);
          }
        }
        else if ((cells[i][j].getAdjacentMines() == 7) && (numberOfClosedNeighbours == 7) && (cells[i][j].checkBeenOpened() == true)) {
          for (int l = 0; l < arrayListOfClosedNeighbourRows.size(); l++) {
            flagCell(arrayOfClosedNeighbourRows[l], arrayOfClosedNeighbourColumns[l]);
          }
        }
        else if ((cells[i][j].getAdjacentMines() == 8) && (numberOfClosedNeighbours == 8) && (cells[i][j].checkBeenOpened() == true)) {
          for (int l = 0; l < arrayListOfClosedNeighbourRows.size(); l++) {
            flagCell(arrayOfClosedNeighbourRows[l], arrayOfClosedNeighbourColumns[l]);
          }
        }
      }
    }
  }

  public static ArrayList<Integer> beginner60Rows() {
    ArrayList<Integer> beginner60Rows = new ArrayList<Integer>();
    beginner60Rows.add(0);
    beginner60Rows.add(7);
    beginner60Rows.add(7);
    return beginner60Rows;
  }
  public static ArrayList<Integer> beginner60Columns() {
    ArrayList<Integer> beginner60Columns = new ArrayList<Integer>();
    beginner60Columns.add(7);
    beginner60Columns.add(0);
    beginner60Columns.add(7);
    return beginner60Columns;
  }
  public static ArrayList<Integer> beginner50Rows() {
    ArrayList<Integer> beginner50Rows = new ArrayList<Integer>();
    beginner50Rows.add(0);
    return beginner50Rows;
  }
  public static ArrayList<Integer> beginner50Columns() {
    ArrayList<Integer> beginner50Columns = new ArrayList<Integer>();
    beginner50Columns.add(0);
    return beginner50Columns;
  }
  public static ArrayList<Integer> beginner42Rows() {
    ArrayList<Integer> beginner42Rows = new ArrayList<Integer>();
    beginner42Rows.add(0);
    beginner42Rows.add(0);
    beginner42Rows.add(3);
    beginner42Rows.add(4);
    beginner42Rows.add(5);
    beginner42Rows.add(6);
    beginner42Rows.add(6);
    beginner42Rows.add(7);
    beginner42Rows.add(7);
    beginner42Rows.add(7);
    return beginner42Rows;
  }
  public static ArrayList<Integer> beginner42Columns() {
    ArrayList<Integer> beginner42Columns = new ArrayList<Integer>();
    beginner42Columns.add(3);
    beginner42Columns.add(6);
    beginner42Columns.add(0);
    beginner42Columns.add(7);
    beginner42Columns.add(7);
    beginner42Columns.add(0);
    beginner42Columns.add(7);
    beginner42Columns.add(1);
    beginner42Columns.add(4);
    beginner42Columns.add(5);
    return beginner42Columns;
  }
  public static ArrayList<Integer> beginner41Rows() {
    ArrayList<Integer> beginner41Rows = new ArrayList<Integer>();
    beginner41Rows.add(0);
    beginner41Rows.add(0);
    beginner41Rows.add(1);
    beginner41Rows.add(2);
    beginner41Rows.add(2);
    beginner41Rows.add(3);
    beginner41Rows.add(4);
    beginner41Rows.add(5);
    beginner41Rows.add(7);
    beginner41Rows.add(7);
    beginner41Rows.add(7);
    return beginner41Rows;
  }
  public static ArrayList<Integer> beginner41Columns() {
    ArrayList<Integer> beginner41Columns = new ArrayList<Integer>();
    beginner41Columns.add(4);
    beginner41Columns.add(5);
    beginner41Columns.add(7);
    beginner41Columns.add(0);
    beginner41Columns.add(7);
    beginner41Columns.add(7);
    beginner41Columns.add(0);
    beginner41Columns.add(0);
    beginner41Columns.add(2);
    beginner41Columns.add(3);
    beginner41Columns.add(6);
    return beginner41Columns;
  }
  public static ArrayList<Integer> beginner40Rows() {
    ArrayList<Integer> beginner40Rows = new ArrayList<Integer>();
    beginner40Rows.add(0);
    return beginner40Rows;
  }
  public static ArrayList<Integer> beginner40Columns() {
    ArrayList<Integer> beginner40Columns = new ArrayList<Integer>();
    beginner40Columns.add(2);
    return beginner40Columns;
  }
  public static ArrayList<Integer> beginner34Rows() {
    ArrayList<Integer> beginner34Rows = new ArrayList<Integer>();
    beginner34Rows.add(0);
    beginner34Rows.add(1);
    return beginner34Rows;
  }
  public static ArrayList<Integer> beginner34Columns() {
    ArrayList<Integer> beginner34Columns = new ArrayList<Integer>();
    beginner34Columns.add(1);
    beginner34Columns.add(0);
    return beginner34Columns;
  }
  public static ArrayList<Integer> beginner24Rows() {
    ArrayList<Integer> beginner24Rows = new ArrayList<Integer>();
    beginner24Rows.add(1);
    beginner24Rows.add(1);
    beginner24Rows.add(2);
    beginner24Rows.add(2);
    beginner24Rows.add(2);
    beginner24Rows.add(2);
    beginner24Rows.add(2);
    beginner24Rows.add(3);
    beginner24Rows.add(3);
    beginner24Rows.add(3);
    beginner24Rows.add(3);
    beginner24Rows.add(3);
    beginner24Rows.add(4);
    beginner24Rows.add(4);
    beginner24Rows.add(4);
    beginner24Rows.add(4);
    beginner24Rows.add(5);
    beginner24Rows.add(5);
    beginner24Rows.add(5);
    beginner24Rows.add(5);
    beginner24Rows.add(6);
    beginner24Rows.add(6);
    beginner24Rows.add(6);
    beginner24Rows.add(6);
    return beginner24Rows;
  }
  public static ArrayList<Integer> beginner24Columns() {
    ArrayList<Integer> beginner24Columns = new ArrayList<Integer>();
    beginner24Columns.add(3);
    beginner24Columns.add(6);
    beginner24Columns.add(1);
    beginner24Columns.add(3);
    beginner24Columns.add(4);
    beginner24Columns.add(5);
    beginner24Columns.add(6);
    beginner24Columns.add(1);
    beginner24Columns.add(2);
    beginner24Columns.add(3);
    beginner24Columns.add(5);
    beginner24Columns.add(6);
    beginner24Columns.add(2);
    beginner24Columns.add(3);
    beginner24Columns.add(5);
    beginner24Columns.add(6);
    beginner24Columns.add(2);
    beginner24Columns.add(3);
    beginner24Columns.add(4);
    beginner24Columns.add(5);
    beginner24Columns.add(1);
    beginner24Columns.add(2);
    beginner24Columns.add(4);
    beginner24Columns.add(5);
    return beginner24Columns;
  }
  public static ArrayList<Integer> beginner23Rows() {
    ArrayList<Integer> beginner23Rows = new ArrayList<Integer>();
    beginner23Rows.add(1);
    beginner23Rows.add(1);
    beginner23Rows.add(1);
    beginner23Rows.add(2);
    beginner23Rows.add(3);
    beginner23Rows.add(4);
    beginner23Rows.add(4);
    beginner23Rows.add(5);
    beginner23Rows.add(5);
    beginner23Rows.add(6);
    beginner23Rows.add(6);
    return beginner23Rows;
  }
  public static ArrayList<Integer> beginner23Columns() {
    ArrayList<Integer> beginner23Columns = new ArrayList<Integer>();
    beginner23Columns.add(2);
    beginner23Columns.add(4);
    beginner23Columns.add(5);
    beginner23Columns.add(2);
    beginner23Columns.add(4);
    beginner23Columns.add(1);
    beginner23Columns.add(4);
    beginner23Columns.add(1);
    beginner23Columns.add(6);
    beginner23Columns.add(3);
    beginner23Columns.add(6);
    return beginner23Columns;
  }
  public static ArrayList<Integer> beginner19Rows() {
    ArrayList<Integer> beginner19Rows = new ArrayList<Integer>();
    beginner19Rows.add(1);
    return beginner19Rows;
  }
  public static ArrayList<Integer> beginner19Columns() {
    ArrayList<Integer> beginner19Columns = new ArrayList<Integer>();
    beginner19Columns.add(1);
    return beginner19Columns;
  }

  public static ArrayList<Integer> intermediate60Rows() {
    ArrayList<Integer> intermediate60Rows = new ArrayList<Integer>();
    intermediate60Rows.add(0);
    intermediate60Rows.add(15);
    intermediate60Rows.add(15);
    return intermediate60Rows;
  }
  public static ArrayList<Integer> intermediate60Columns() {
    ArrayList<Integer> intermediate60Columns = new ArrayList<Integer>();
    intermediate60Columns.add(15);
    intermediate60Columns.add(0);
    intermediate60Columns.add(15);
    return intermediate60Columns;
  }
  public static ArrayList<Integer> intermediate50Rows() {
    ArrayList<Integer> intermediate50Rows = new ArrayList<Integer>();
    intermediate50Rows.add(0);
    return intermediate50Rows;
  }
  public static ArrayList<Integer> intermediate50Columns() {
    ArrayList<Integer> intermediate50Columns = new ArrayList<Integer>();
    intermediate50Columns.add(0);
    return intermediate50Columns;
  }
  public static ArrayList<Integer> intermediate43Rows() {
    ArrayList<Integer> intermediate43Rows = new ArrayList<Integer>();
    intermediate43Rows.add(0);
    intermediate43Rows.add(0);
    intermediate43Rows.add(0);
    intermediate43Rows.add(0);
    intermediate43Rows.add(2);
    intermediate43Rows.add(4);
    intermediate43Rows.add(6);
    intermediate43Rows.add(6);
    intermediate43Rows.add(7);
    intermediate43Rows.add(9);
    intermediate43Rows.add(9);
    intermediate43Rows.add(10);
    intermediate43Rows.add(12);
    intermediate43Rows.add(13);
    intermediate43Rows.add(14);
    intermediate43Rows.add(14);
    intermediate43Rows.add(15);
    intermediate43Rows.add(15);
    intermediate43Rows.add(15);
    intermediate43Rows.add(15);
    intermediate43Rows.add(15);
    intermediate43Rows.add(15);
    return intermediate43Rows;
  }
  public static ArrayList<Integer> intermediate43Columns() {
    ArrayList<Integer> intermediate43Columns = new ArrayList<Integer>();
    intermediate43Columns.add(3);
    intermediate43Columns.add(4);
    intermediate43Columns.add(6);
    intermediate43Columns.add(11);
    intermediate43Columns.add(0);
    intermediate43Columns.add(15);
    intermediate43Columns.add(0);
    intermediate43Columns.add(15);
    intermediate43Columns.add(15);
    intermediate43Columns.add(0);
    intermediate43Columns.add(15);
    intermediate43Columns.add(15);
    intermediate43Columns.add(15);
    intermediate43Columns.add(0);
    intermediate43Columns.add(0);
    intermediate43Columns.add(15);
    intermediate43Columns.add(1);
    intermediate43Columns.add(7);
    intermediate43Columns.add(8);
    intermediate43Columns.add(9);
    intermediate43Columns.add(10);
    intermediate43Columns.add(12);
    return intermediate43Columns;
  }
  public static ArrayList<Integer> intermediate42Rows() {
    ArrayList<Integer> intermediate42Rows = new ArrayList<Integer>();
    intermediate42Rows.add(0);
    intermediate42Rows.add(0);
    intermediate42Rows.add(0);
    intermediate42Rows.add(0);
    intermediate42Rows.add(0);
    intermediate42Rows.add(0);
    intermediate42Rows.add(0);
    intermediate42Rows.add(0);
    intermediate42Rows.add(1);
    intermediate42Rows.add(2);
    intermediate42Rows.add(3);
    intermediate42Rows.add(3);
    intermediate42Rows.add(4);
    intermediate42Rows.add(5);
    intermediate42Rows.add(5);
    intermediate42Rows.add(7);
    intermediate42Rows.add(8);
    intermediate42Rows.add(8);
    intermediate42Rows.add(10);
    intermediate42Rows.add(11);
    intermediate42Rows.add(11);
    intermediate42Rows.add(12);
    intermediate42Rows.add(13);
    intermediate42Rows.add(15);
    intermediate42Rows.add(15);
    intermediate42Rows.add(15);
    intermediate42Rows.add(15);
    intermediate42Rows.add(15);
    intermediate42Rows.add(15);
    intermediate42Rows.add(15);
    intermediate42Rows.add(15);
    return intermediate42Rows;
  }
  public static ArrayList<Integer> intermediate42Columns() {
    ArrayList<Integer> intermediate42Columns = new ArrayList<Integer>();
    intermediate42Columns.add(5);
    intermediate42Columns.add(7);
    intermediate42Columns.add(8);
    intermediate42Columns.add(9);
    intermediate42Columns.add(10);
    intermediate42Columns.add(12);
    intermediate42Columns.add(13);
    intermediate42Columns.add(14);
    intermediate42Columns.add(15);
    intermediate42Columns.add(15);
    intermediate42Columns.add(0);
    intermediate42Columns.add(15);
    intermediate42Columns.add(0);
    intermediate42Columns.add(0);
    intermediate42Columns.add(15);
    intermediate42Columns.add(0);
    intermediate42Columns.add(0);
    intermediate42Columns.add(15);
    intermediate42Columns.add(0);
    intermediate42Columns.add(0);
    intermediate42Columns.add(15);
    intermediate42Columns.add(0);
    intermediate42Columns.add(15);
    intermediate42Columns.add(2);
    intermediate42Columns.add(3);
    intermediate42Columns.add(4);
    intermediate42Columns.add(5);
    intermediate42Columns.add(6);
    intermediate42Columns.add(11);
    intermediate42Columns.add(13);
    intermediate42Columns.add(14);
    return intermediate42Columns;
  }
  public static ArrayList<Integer> intermediate41Rows() {
    ArrayList<Integer> intermediate41Rows = new ArrayList<Integer>();
    intermediate41Rows.add(0);
    return intermediate41Rows;
  }
  public static ArrayList<Integer> intermediate41Columns() {
    ArrayList<Integer> intermediate41Columns = new ArrayList<Integer>();
    intermediate41Columns.add(2);
    return intermediate41Columns;
  }
  public static ArrayList<Integer> intermediate36Rows() {
    ArrayList<Integer> intermediate36Rows = new ArrayList<Integer>();
    intermediate36Rows.add(0);
    intermediate36Rows.add(1);
    return intermediate36Rows;
  }
  public static ArrayList<Integer> intermediate36Columns() {
    ArrayList<Integer> intermediate36Columns = new ArrayList<Integer>();
    intermediate36Columns.add(1);
    intermediate36Columns.add(0);
    return intermediate36Columns;
  }
  public static ArrayList<Integer> intermediate26Rows() {
    ArrayList<Integer> intermediate26Rows = new ArrayList<Integer>();
    intermediate26Rows.add(3);
    intermediate26Rows.add(5);
    intermediate26Rows.add(9);
    intermediate26Rows.add(11);
    intermediate26Rows.add(13);
    return intermediate26Rows;
  }
  public static ArrayList<Integer> intermediate26Columns() {
    ArrayList<Integer> intermediate26Columns = new ArrayList<Integer>();
    intermediate26Columns.add(12);
    intermediate26Columns.add(6);
    intermediate26Columns.add(10);
    intermediate26Columns.add(4);
    intermediate26Columns.add(14);
    return intermediate26Columns;
  }
  public static ArrayList<Integer> intermediate25Rows() {
    ArrayList<Integer> intermediate25Rows = new ArrayList<Integer>();
    intermediate25Rows.add(1);
    intermediate25Rows.add(1);
    intermediate25Rows.add(1);
    intermediate25Rows.add(1);
    intermediate25Rows.add(1);
    intermediate25Rows.add(1);
    intermediate25Rows.add(1);
    intermediate25Rows.add(1);
    intermediate25Rows.add(1);
    intermediate25Rows.add(1);
    intermediate25Rows.add(1);
    intermediate25Rows.add(1);
    intermediate25Rows.add(1);
    intermediate25Rows.add(2);
    intermediate25Rows.add(2);
    intermediate25Rows.add(2);
    intermediate25Rows.add(2);
    intermediate25Rows.add(2);
    intermediate25Rows.add(2);
    intermediate25Rows.add(2);
    intermediate25Rows.add(2);
    intermediate25Rows.add(2);
    intermediate25Rows.add(2);
    intermediate25Rows.add(2);
    intermediate25Rows.add(2);
    intermediate25Rows.add(2);
    intermediate25Rows.add(2);
    intermediate25Rows.add(3);
    intermediate25Rows.add(3);
    intermediate25Rows.add(3);
    intermediate25Rows.add(3);
    intermediate25Rows.add(3);
    intermediate25Rows.add(3);
    intermediate25Rows.add(3);
    intermediate25Rows.add(3);
    intermediate25Rows.add(3);
    intermediate25Rows.add(3);
    intermediate25Rows.add(3);
    intermediate25Rows.add(3);
    intermediate25Rows.add(3);
    intermediate25Rows.add(4);
    intermediate25Rows.add(4);
    intermediate25Rows.add(4);
    intermediate25Rows.add(4);
    intermediate25Rows.add(4);
    intermediate25Rows.add(4);
    intermediate25Rows.add(4);
    intermediate25Rows.add(4);
    intermediate25Rows.add(4);
    intermediate25Rows.add(4);
    intermediate25Rows.add(4);
    intermediate25Rows.add(4);
    intermediate25Rows.add(4);
    intermediate25Rows.add(4);
    intermediate25Rows.add(5);
    intermediate25Rows.add(5);
    intermediate25Rows.add(5);
    intermediate25Rows.add(5);
    intermediate25Rows.add(5);
    intermediate25Rows.add(5);
    intermediate25Rows.add(5);
    intermediate25Rows.add(5);
    intermediate25Rows.add(5);
    intermediate25Rows.add(5);
    intermediate25Rows.add(5);
    intermediate25Rows.add(5);
    intermediate25Rows.add(5);
    intermediate25Rows.add(6);
    intermediate25Rows.add(6);
    intermediate25Rows.add(6);
    intermediate25Rows.add(6);
    intermediate25Rows.add(6);
    intermediate25Rows.add(6);
    intermediate25Rows.add(6);
    intermediate25Rows.add(6);
    intermediate25Rows.add(6);
    intermediate25Rows.add(6);
    intermediate25Rows.add(6);
    intermediate25Rows.add(6);
    intermediate25Rows.add(6);
    intermediate25Rows.add(6);
    intermediate25Rows.add(7);
    intermediate25Rows.add(7);
    intermediate25Rows.add(7);
    intermediate25Rows.add(7);
    intermediate25Rows.add(7);
    intermediate25Rows.add(7);
    intermediate25Rows.add(7);
    intermediate25Rows.add(7);
    intermediate25Rows.add(7);
    intermediate25Rows.add(7);
    intermediate25Rows.add(7);
    intermediate25Rows.add(7);
    intermediate25Rows.add(7);
    intermediate25Rows.add(7);
    intermediate25Rows.add(8);
    intermediate25Rows.add(8);
    intermediate25Rows.add(8);
    intermediate25Rows.add(8);
    intermediate25Rows.add(8);
    intermediate25Rows.add(8);
    intermediate25Rows.add(8);
    intermediate25Rows.add(8);
    intermediate25Rows.add(8);
    intermediate25Rows.add(8);
    intermediate25Rows.add(8);
    intermediate25Rows.add(8);
    intermediate25Rows.add(8);
    intermediate25Rows.add(8);
    intermediate25Rows.add(9);
    intermediate25Rows.add(9);
    intermediate25Rows.add(9);
    intermediate25Rows.add(9);
    intermediate25Rows.add(9);
    intermediate25Rows.add(9);
    intermediate25Rows.add(9);
    intermediate25Rows.add(9);
    intermediate25Rows.add(9);
    intermediate25Rows.add(9);
    intermediate25Rows.add(9);
    intermediate25Rows.add(9);
    intermediate25Rows.add(9);
    intermediate25Rows.add(10);
    intermediate25Rows.add(10);
    intermediate25Rows.add(10);
    intermediate25Rows.add(10);
    intermediate25Rows.add(10);
    intermediate25Rows.add(10);
    intermediate25Rows.add(10);
    intermediate25Rows.add(10);
    intermediate25Rows.add(10);
    intermediate25Rows.add(10);
    intermediate25Rows.add(10);
    intermediate25Rows.add(10);
    intermediate25Rows.add(10);
    intermediate25Rows.add(10);
    intermediate25Rows.add(11);
    intermediate25Rows.add(11);
    intermediate25Rows.add(11);
    intermediate25Rows.add(11);
    intermediate25Rows.add(11);
    intermediate25Rows.add(11);
    intermediate25Rows.add(11);
    intermediate25Rows.add(11);
    intermediate25Rows.add(11);
    intermediate25Rows.add(11);
    intermediate25Rows.add(11);
    intermediate25Rows.add(11);
    intermediate25Rows.add(11);
    intermediate25Rows.add(12);
    intermediate25Rows.add(12);
    intermediate25Rows.add(12);
    intermediate25Rows.add(12);
    intermediate25Rows.add(12);
    intermediate25Rows.add(12);
    intermediate25Rows.add(12);
    intermediate25Rows.add(12);
    intermediate25Rows.add(12);
    intermediate25Rows.add(12);
    intermediate25Rows.add(12);
    intermediate25Rows.add(12);
    intermediate25Rows.add(12);
    intermediate25Rows.add(12);
    intermediate25Rows.add(13);
    intermediate25Rows.add(13);
    intermediate25Rows.add(13);
    intermediate25Rows.add(13);
    intermediate25Rows.add(13);
    intermediate25Rows.add(13);
    intermediate25Rows.add(13);
    intermediate25Rows.add(13);
    intermediate25Rows.add(13);
    intermediate25Rows.add(13);
    intermediate25Rows.add(13);
    intermediate25Rows.add(13);
    intermediate25Rows.add(13);
    intermediate25Rows.add(14);
    intermediate25Rows.add(14);
    intermediate25Rows.add(14);
    intermediate25Rows.add(14);
    intermediate25Rows.add(14);
    intermediate25Rows.add(14);
    intermediate25Rows.add(14);
    intermediate25Rows.add(14);
    intermediate25Rows.add(14);
    intermediate25Rows.add(14);
    intermediate25Rows.add(14);
    intermediate25Rows.add(14);
    intermediate25Rows.add(14);
    intermediate25Rows.add(14);
    return intermediate25Rows;
  }
  public static ArrayList<Integer> intermediate25Columns() {
    ArrayList<Integer> intermediate25Columns = new ArrayList<Integer>();
    intermediate25Columns.add(2);
    intermediate25Columns.add(3);
    intermediate25Columns.add(4);
    intermediate25Columns.add(5);
    intermediate25Columns.add(6);
    intermediate25Columns.add(7);
    intermediate25Columns.add(8);
    intermediate25Columns.add(9);
    intermediate25Columns.add(10);
    intermediate25Columns.add(11);
    intermediate25Columns.add(12);
    intermediate25Columns.add(13);
    intermediate25Columns.add(14);
    intermediate25Columns.add(1);
    intermediate25Columns.add(2);
    intermediate25Columns.add(3);
    intermediate25Columns.add(4);
    intermediate25Columns.add(5);
    intermediate25Columns.add(6);
    intermediate25Columns.add(7);
    intermediate25Columns.add(8);
    intermediate25Columns.add(9);
    intermediate25Columns.add(10);
    intermediate25Columns.add(11);
    intermediate25Columns.add(12);
    intermediate25Columns.add(13);
    intermediate25Columns.add(14);
    intermediate25Columns.add(1);
    intermediate25Columns.add(2);
    intermediate25Columns.add(3);
    intermediate25Columns.add(4);
    intermediate25Columns.add(5);
    intermediate25Columns.add(6);
    intermediate25Columns.add(7);
    intermediate25Columns.add(8);
    intermediate25Columns.add(9);
    intermediate25Columns.add(10);
    intermediate25Columns.add(11);
    intermediate25Columns.add(13);
    intermediate25Columns.add(14);
    intermediate25Columns.add(1);
    intermediate25Columns.add(2);
    intermediate25Columns.add(3);
    intermediate25Columns.add(4);
    intermediate25Columns.add(5);
    intermediate25Columns.add(6);
    intermediate25Columns.add(7);
    intermediate25Columns.add(8);
    intermediate25Columns.add(9);
    intermediate25Columns.add(10);
    intermediate25Columns.add(11);
    intermediate25Columns.add(12);
    intermediate25Columns.add(13);
    intermediate25Columns.add(14);
    intermediate25Columns.add(1);
    intermediate25Columns.add(2);
    intermediate25Columns.add(3);
    intermediate25Columns.add(4);
    intermediate25Columns.add(5);
    intermediate25Columns.add(7);
    intermediate25Columns.add(8);
    intermediate25Columns.add(9);
    intermediate25Columns.add(10);
    intermediate25Columns.add(11);
    intermediate25Columns.add(12);
    intermediate25Columns.add(13);
    intermediate25Columns.add(14);
    intermediate25Columns.add(1);
    intermediate25Columns.add(2);
    intermediate25Columns.add(3);
    intermediate25Columns.add(4);
    intermediate25Columns.add(5);
    intermediate25Columns.add(6);
    intermediate25Columns.add(7);
    intermediate25Columns.add(8);
    intermediate25Columns.add(9);
    intermediate25Columns.add(10);
    intermediate25Columns.add(11);
    intermediate25Columns.add(12);
    intermediate25Columns.add(13);
    intermediate25Columns.add(14);
    intermediate25Columns.add(1);
    intermediate25Columns.add(2);
    intermediate25Columns.add(3);
    intermediate25Columns.add(4);
    intermediate25Columns.add(5);
    intermediate25Columns.add(6);
    intermediate25Columns.add(7);
    intermediate25Columns.add(8);
    intermediate25Columns.add(9);
    intermediate25Columns.add(10);
    intermediate25Columns.add(11);
    intermediate25Columns.add(12);
    intermediate25Columns.add(13);
    intermediate25Columns.add(14);
    intermediate25Columns.add(1);
    intermediate25Columns.add(2);
    intermediate25Columns.add(3);
    intermediate25Columns.add(4);
    intermediate25Columns.add(5);
    intermediate25Columns.add(6);
    intermediate25Columns.add(7);
    intermediate25Columns.add(8);
    intermediate25Columns.add(9);
    intermediate25Columns.add(10);
    intermediate25Columns.add(11);
    intermediate25Columns.add(12);
    intermediate25Columns.add(13);
    intermediate25Columns.add(14);
    intermediate25Columns.add(1);
    intermediate25Columns.add(2);
    intermediate25Columns.add(3);
    intermediate25Columns.add(4);
    intermediate25Columns.add(5);
    intermediate25Columns.add(6);
    intermediate25Columns.add(7);
    intermediate25Columns.add(8);
    intermediate25Columns.add(9);
    intermediate25Columns.add(11);
    intermediate25Columns.add(12);
    intermediate25Columns.add(13);
    intermediate25Columns.add(14);
    intermediate25Columns.add(1);
    intermediate25Columns.add(2);
    intermediate25Columns.add(3);
    intermediate25Columns.add(4);
    intermediate25Columns.add(5);
    intermediate25Columns.add(6);
    intermediate25Columns.add(7);
    intermediate25Columns.add(8);
    intermediate25Columns.add(9);
    intermediate25Columns.add(10);
    intermediate25Columns.add(11);
    intermediate25Columns.add(12);
    intermediate25Columns.add(13);
    intermediate25Columns.add(14);
    intermediate25Columns.add(1);
    intermediate25Columns.add(2);
    intermediate25Columns.add(3);
    intermediate25Columns.add(5);
    intermediate25Columns.add(6);
    intermediate25Columns.add(7);
    intermediate25Columns.add(8);
    intermediate25Columns.add(9);
    intermediate25Columns.add(10);
    intermediate25Columns.add(11);
    intermediate25Columns.add(12);
    intermediate25Columns.add(13);
    intermediate25Columns.add(14);
    intermediate25Columns.add(1);
    intermediate25Columns.add(2);
    intermediate25Columns.add(3);
    intermediate25Columns.add(4);
    intermediate25Columns.add(5);
    intermediate25Columns.add(6);
    intermediate25Columns.add(7);
    intermediate25Columns.add(8);
    intermediate25Columns.add(9);
    intermediate25Columns.add(10);
    intermediate25Columns.add(11);
    intermediate25Columns.add(12);
    intermediate25Columns.add(13);
    intermediate25Columns.add(14);
    intermediate25Columns.add(1);
    intermediate25Columns.add(2);
    intermediate25Columns.add(3);
    intermediate25Columns.add(4);
    intermediate25Columns.add(5);
    intermediate25Columns.add(6);
    intermediate25Columns.add(7);
    intermediate25Columns.add(8);
    intermediate25Columns.add(9);
    intermediate25Columns.add(10);
    intermediate25Columns.add(11);
    intermediate25Columns.add(12);
    intermediate25Columns.add(13);
    intermediate25Columns.add(1);
    intermediate25Columns.add(2);
    intermediate25Columns.add(3);
    intermediate25Columns.add(4);
    intermediate25Columns.add(5);
    intermediate25Columns.add(6);
    intermediate25Columns.add(7);
    intermediate25Columns.add(8);
    intermediate25Columns.add(9);
    intermediate25Columns.add(10);
    intermediate25Columns.add(11);
    intermediate25Columns.add(12);
    intermediate25Columns.add(13);
    intermediate25Columns.add(14);
    return intermediate25Columns;
  }
  public static ArrayList<Integer> intermediate21Rows() {
    ArrayList<Integer> intermediate21Rows = new ArrayList<Integer>();
    intermediate21Rows.add(1);
    return intermediate21Rows;
  }
  public static ArrayList<Integer> intermediate21Columns() {
    ArrayList<Integer> intermediate21Columns = new ArrayList<Integer>();
    intermediate21Columns.add(1);
    return intermediate21Columns;
  }

  private static final int[] expert50Rows = {0, 15, 15};
  private static final int[] expert50Columns = {29, 0, 29};
  private static final int[] expert40Rows = {0};
  private static final int[] expert40Columns = {0};
  private static final int[] expert32Rows = {0, 0, 0, 11, 12, 13, 15, 15, 15, 15, 15, 15};
  private static final int[] expert32Columns = {13, 18, 20, 29, 29, 29, 7, 14, 25, 26, 27, 28};
  private static final int[] expert31Rows = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 12, 13, 14, 14, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
  private static final int[] expert31Columns = {4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16, 17, 19, 21, 22, 23, 24, 25, 26, 27, 28, 29, 0, 29, 0, 29, 0, 29, 0, 29, 0, 29, 0, 29, 0, 29, 0, 29, 0, 29, 0, 0, 0, 0, 29, 1, 2, 3, 4, 5, 6, 8, 9, 10, 11, 12, 13, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
  private static final int[] expert30Rows = {0};
  private static final int[] expert30Columns = {3};
  private static final int[] expert25Rows = {0, 0, 1};
  private static final int[] expert25Columns = {1, 2, 0};
  private static final int[] expert16Rows = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14};
  private static final int[] expert16Columns = {6, 7, 8, 10, 12, 13, 14, 15, 17, 18, 19, 20, 21, 22, 23, 24, 25, 27, 28, 2, 6, 10, 12, 13, 14, 15, 19, 20, 21, 22, 24, 25, 26, 276, 7, 8, 10, 12, 14, 17, 18, 21, 22, 23, 24, 25, 26, 6, 10, 11, 12, 13, 17, 18, 21, 22, 23, 24, 25, 26, 3, 5, 9, 10, 11, 12, 13, 14, 16, 17, 18, 21, 22, 23, 24, 25, 26, 6, 8, 10, 11, 12, 13, 14, 15, 16, 17, 19, 20, 21, 22, 23, 25, 26, 27, 4, 9, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 3, 8, 9, 10, 11, 15, 16, 17, 18, 20, 21, 22, 24, 25, 26, 27, 28, 5, 6, 8, 9, 10, 11, 15, 17, 18, 19, 20, 22, 25, 26, 27, 28, 1, 2, 3, 6, 7, 10, 13, 14, 15, 16, 17, 18, 20, 21, 22, 23, 24, 25, 28, 2, 3, 4, 5, 6, 7, 8, 11, 12, 13, 16, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 18, 19, 20, 22, 26, 27, 28, 3, 5, 6, 8, 9, 10, 11, 12, 13, 14, 16, 17, 19, 22, 23, 25, 26, 27, 28, 6, 7, 8, 9, 12, 13, 14, 15, 16, 17, 18, 21, 25, 26, 27, 28};
  private static final int[] expert15Rows = {1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 12, 12, 13, 13, 13, 13, 13, 13, 13, 13, 13, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14};
  private static final int[] expert15Columns = {3, 4, 5, 9, 11, 16, 26, 1, 3, 4, 5, 7, 8, 9, 11, 16, 17, 18, 23, 28, 1, 2, 3, 4, 5, 9, 11, 13, 15, 16, 19, 20, 27, 28, 1, 2, 3, 4, 5, 7, 8, 9, 14, 15, 16, 18, 19, 21, 25, 26, 27, 28, 1, 2, 4, 6, 7, 8, 15, 19, 20, 27, 28, 1, 2, 3, 4, 5, 7, 9, 18, 24, 28, 1, 2, 3, 5, 6, 7, 8, 10, 11, 12, 13, 14, 28, 1, 2, 4, 5, 6, 7, 12, 13, 14, 19, 23, 1, 2, 3, 4, 7, 12, 13, 14, 16, 21, 23, 24, 4, 5, 8, 9, 11, 12, 19, 26, 27, 1, 9, 10, 14, 15, 17, 18, 5, 14, 17, 21, 23, 24, 25, 1, 2, 4, 7, 15, 18, 20, 21, 24, 1, 2, 3, 4, 5, 10, 11, 19, 20, 22, 23, 24};
  private static final int[] expert12Rows = {1, 1};
  private static final int[] expert12Columns = {1, 2};

  public void shiftMineOnFirstMove(int currentRow, int currentColumn, int rows, int columns) {
    int replacedCount = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        if (cells[i][j].checkHasMine() == false) {
          while (replacedCount == 0) {
            cells[i][j].setMine();
            replacedCount++;
          }
        }
      }
    }
    cells[currentRow][currentColumn].removeMine();
    generateNumbers();
    System.out.println("Mine shifted");
    displayGeneratedBoard();
  }

  public void beginnerFirstMove(int firstMoveCheck) {
    Random random = new Random();
    int p = random.nextInt(100);
    int setCounter = 0;
    if (p < 60) {
      ArrayList<Integer> beginner60Rows = beginner60Rows();
      ArrayList<Integer> beginner60Columns = beginner60Columns();
      int q = random.nextInt(3);
      if ((cells[beginner60Rows.get(q)][beginner60Columns.get(q)].checkHasMine() == true) && (firstMoveCheck == 1)) { //only shift mines on first move; 1 == first move
        shiftMineOnFirstMove(beginner60Rows.get(q), beginner60Columns.get(q), 8, 8);
      }
      setCounter = 0;
      while (cells[beginner60Rows.get(q)][beginner60Columns.get(q)].checkBeenOpened() == true) {
        beginner60Rows.remove(q);
        beginner60Columns.remove(q);
        setCounter++;
        q = random.nextInt(3-setCounter);
      }
      if (setCounter == 3) {
        beginnerFirstMove(2);
      }
      openCell(beginner60Rows.get(q), beginner60Columns.get(q));
      System.out.println(beginner60Rows.get(q) + " " + beginner60Columns.get(q));
    }
    else {
      p = random.nextInt(100);
      if (p < 50) {
        ArrayList<Integer> beginner50Rows = beginner50Rows();
        ArrayList<Integer> beginner50Columns = beginner50Columns();
        if ((cells[beginner50Rows.get(0)][beginner50Columns.get(0)].checkHasMine() == true) && (firstMoveCheck == 1)) {
          shiftMineOnFirstMove(beginner50Rows.get(0), beginner50Columns.get(0), 8, 8);
        }
        if (cells[beginner50Rows.get(0)][beginner50Columns.get(0)].checkBeenOpened() == true) {
          beginnerFirstMove(2);
        }
        openCell(beginner50Rows.get(0), beginner50Columns.get(0));
        System.out.println(beginner50Rows.get(0) + " " + beginner50Columns.get(0));
      }
      else {
        p = random.nextInt(100);
        if (p < 42) {
          ArrayList<Integer> beginner42Rows = beginner42Rows();
          ArrayList<Integer> beginner42Columns = beginner42Columns();
          int q = random.nextInt(10);
          if ((cells[beginner42Rows.get(q)][beginner42Columns.get(q)].checkHasMine() == true) && (firstMoveCheck == 1)) {
            shiftMineOnFirstMove(beginner42Rows.get(q), beginner42Columns.get(q), 8, 8);
          }
          setCounter = 0;
          while (cells[beginner42Rows.get(q)][beginner42Columns.get(q)].checkBeenOpened() == true) {
            beginner42Rows.remove(q);
            beginner42Columns.remove(q);
            setCounter++;
            q = random.nextInt(10-setCounter);
          }
          if (setCounter == 10) {
            beginnerFirstMove(2);
          }
          openCell(beginner42Rows.get(q), beginner42Columns.get(q));
          System.out.println(beginner42Rows.get(q) + " " + beginner42Columns.get(q));
        }
        else {
          p = random.nextInt(100);
          if (p < 41) {
            ArrayList<Integer> beginner41Rows = beginner41Rows();
            ArrayList<Integer> beginner41Columns = beginner41Columns();
            int q = random.nextInt(11);
            if ((cells[beginner41Rows.get(q)][beginner41Columns.get(q)].checkHasMine() == true) && (firstMoveCheck == 1)) {
              shiftMineOnFirstMove(beginner41Rows.get(q), beginner41Columns.get(q), 8, 8);
            }
            setCounter = 0;
            while (cells[beginner41Rows.get(q)][beginner41Columns.get(q)].checkBeenOpened() == true) {
              beginner41Rows.remove(q);
              beginner41Columns.remove(q);
              setCounter++;
              q = random.nextInt(11-setCounter);
            }
            if (setCounter == 11) {
              beginnerFirstMove(2);
            }
            openCell(beginner41Rows.get(q), beginner41Columns.get(q));
            System.out.println(beginner41Rows.get(q) + " " + beginner41Columns.get(q));
          }
          else {
            p = random.nextInt(100);
            if (p < 40) {
              ArrayList<Integer> beginner40Rows = beginner40Rows();
              ArrayList<Integer> beginner40Columns = beginner40Columns();
              if ((cells[beginner40Rows.get(0)][beginner40Columns.get(0)].checkHasMine() == true) && (firstMoveCheck == 1)) {
                shiftMineOnFirstMove(beginner40Rows.get(0), beginner40Columns.get(0), 8, 8);
              }
              if (cells[beginner40Rows.get(0)][beginner40Columns.get(0)].checkBeenOpened() == true) {
                beginnerFirstMove(2);
              }
              openCell(beginner40Rows.get(0), beginner40Columns.get(0));
              System.out.println(beginner40Rows.get(0) + " " + beginner40Columns.get(0));
            }
            else {
              p = random.nextInt(100);
              if (p < 34) {
                ArrayList<Integer> beginner34Rows = beginner34Rows();
                ArrayList<Integer> beginner34Columns = beginner34Columns();
                int q = random.nextInt(2);
                if ((cells[beginner34Rows.get(q)][beginner34Columns.get(q)].checkHasMine() == true) && (firstMoveCheck == 1)) {
                  shiftMineOnFirstMove(beginner34Rows.get(q), beginner34Columns.get(q), 8, 8);
                }
                setCounter = 0;
                while (cells[beginner34Rows.get(q)][beginner34Columns.get(q)].checkBeenOpened() == true) {
                  beginner34Rows.remove(q);
                  beginner34Columns.remove(q);
                  setCounter++;
                  q = random.nextInt(2-setCounter);
                }
                if (setCounter == 2) {
                  beginnerFirstMove(2);
                }
                openCell(beginner34Rows.get(q), beginner34Columns.get(q));
                System.out.println(beginner34Rows.get(q) + " " + beginner34Columns.get(q));
              }
              else {
                p = random.nextInt(100);
                if (p < 24) {
                  ArrayList<Integer> beginner24Rows = beginner24Rows();
                  ArrayList<Integer> beginner24Columns = beginner24Columns();
                  int q = random.nextInt(24);
                  if ((cells[beginner24Rows.get(q)][beginner24Columns.get(q)].checkHasMine() == true) && (firstMoveCheck == 1)) {
                    shiftMineOnFirstMove(beginner24Rows.get(q), beginner24Columns.get(q), 8, 8);
                  }
                  setCounter = 0;
                  while (cells[beginner24Rows.get(q)][beginner24Columns.get(q)].checkBeenOpened() == true) {
                    beginner24Rows.remove(q);
                    beginner24Columns.remove(q);
                    setCounter++;
                    q = random.nextInt(24-setCounter);
                  }
                  if (setCounter == 24) {
                    beginnerFirstMove(2);
                  }
                  openCell(beginner24Rows.get(q), beginner24Columns.get(q));
                  System.out.println(beginner24Rows.get(q) + " " + beginner24Columns.get(q));
                }
                else {
                  p = random.nextInt(100);
                  if (p < 23) {
                    ArrayList<Integer> beginner23Rows = beginner23Rows();
                    ArrayList<Integer> beginner23Columns = beginner23Columns();
                    int q = random.nextInt(11);
                    if ((cells[beginner23Rows.get(q)][beginner23Columns.get(q)].checkHasMine() == true) && (firstMoveCheck == 1)) {
                      shiftMineOnFirstMove(beginner23Rows.get(q), beginner23Columns.get(q), 8, 8);
                    }
                    setCounter = 0;
                    while (cells[beginner23Rows.get(q)][beginner23Columns.get(q)].checkBeenOpened() == true) {
                      beginner23Rows.remove(q);
                      beginner23Columns.remove(q);
                      setCounter++;
                      q = random.nextInt(11);
                    }
                    if (setCounter == 11) {
                      beginnerFirstMove(2);
                    }
                    openCell(beginner23Rows.get(q), beginner23Columns.get(q));
                    System.out.println(beginner23Rows.get(q) + " " + beginner23Columns.get(q));
                  }
                  else {
                    ArrayList<Integer> beginner19Rows = beginner19Rows();
                    ArrayList<Integer> beginner19Columns = beginner19Columns();
                    if ((cells[beginner19Rows.get(0)][beginner19Columns.get(0)].checkHasMine() == true) && (firstMoveCheck == 1)) {
                      shiftMineOnFirstMove(beginner19Rows.get(0), beginner19Columns.get(0), 8, 8);
                    }
                    if (cells[beginner19Rows.get(0)][beginner19Columns.get(0)].checkBeenOpened() == true) {
                      beginnerFirstMove(2);
                    }
                    openCell(beginner19Rows.get(0), beginner19Columns.get(0));
                    System.out.println(beginner19Rows.get(0) + " " + beginner19Columns.get(0));
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  public void intermediateFirstMove(int firstMoveCheck) {
    Random random = new Random();
    int p = random.nextInt(100);
    int setCounter = 0;
    if (p < 60) {
      ArrayList<Integer> intermediate60Rows = intermediate60Rows();
      ArrayList<Integer> intermediate60Columns = intermediate60Columns();
      int q = random.nextInt(3);
      if ((cells[intermediate60Rows.get(q)][intermediate60Columns.get(q)].checkHasMine() == true) && (firstMoveCheck == 1)) {
        shiftMineOnFirstMove(intermediate60Rows.get(q), intermediate60Columns.get(q), 16, 16);
      }
      setCounter = 0;
      while (cells[intermediate60Rows.get(q)][intermediate60Columns.get(q)].checkBeenOpened() == true) {
        intermediate60Rows.remove(q);
        intermediate60Columns.remove(q);
        setCounter++;
        q = random.nextInt(3-setCounter);
      }
      if (setCounter == 3) {
        intermediateFirstMove(2);
      }
      openCell(intermediate60Rows.get(q), intermediate60Columns.get(q));
      System.out.println(intermediate60Rows.get(q) + " " + intermediate60Columns.get(q));
    }
    else {
      p = random.nextInt(100);
      if (p < 50) {
        ArrayList<Integer> intermediate50Rows = intermediate50Rows();
        ArrayList<Integer> intermediate50Columns = intermediate50Columns();
        if ((cells[intermediate50Rows.get(0)][intermediate50Columns.get(0)].checkHasMine() == true) && (firstMoveCheck == 1)) {
          shiftMineOnFirstMove(intermediate50Rows.get(0), intermediate50Columns.get(0), 16, 16);
        }
        if ((cells[intermediate50Rows.get(0)][intermediate50Columns.get(0)].checkBeenOpened() == true)) {
          intermediateFirstMove(2);
        }
        openCell(intermediate50Rows.get(0), intermediate50Columns.get(0));
        System.out.println(intermediate50Rows.get(0) + " " + intermediate50Columns.get(0));
      }
      else {
        p = random.nextInt(100);
        if (p < 43) {
          ArrayList<Integer> intermediate43Rows = intermediate43Rows();
          ArrayList<Integer> intermediate43Columns = intermediate43Columns();
          int q = random.nextInt(22);
          if ((cells[intermediate43Rows.get(q)][intermediate43Columns.get(q)].checkHasMine() == true) && (firstMoveCheck == 1)) {
            shiftMineOnFirstMove(intermediate43Rows.get(q), intermediate43Columns.get(q), 16, 16);
          }
          setCounter = 0;
          while (cells[intermediate43Rows.get(q)][intermediate43Columns.get(q)].checkBeenOpened() == true) {
            intermediate43Rows.remove(q);
            intermediate43Columns.remove(q);
            setCounter++;
            q = random.nextInt(22-setCounter);
          }
          if (setCounter == 22) {
            intermediateFirstMove(2);
          }
          openCell(intermediate43Rows.get(q), intermediate43Columns.get(q));
          System.out.println(intermediate43Rows.get(q) + " " + intermediate43Columns.get(q));
        }
        else {
          p = random.nextInt(100);
          if (p < 42) {
            ArrayList<Integer> intermediate42Rows = intermediate42Rows();
            ArrayList<Integer> intermediate42Columns = intermediate42Columns();
            int q = random.nextInt(31);
            if ((cells[intermediate42Rows.get(q)][intermediate42Columns.get(q)].checkHasMine() == true) && (firstMoveCheck == 1)) {
              shiftMineOnFirstMove(intermediate42Rows.get(q), intermediate42Columns.get(q), 16, 16);
            }
            setCounter = 0;
            while (cells[intermediate42Rows.get(q)][intermediate42Columns.get(q)].checkBeenOpened() == true) {
              intermediate42Rows.remove(q);
              intermediate42Columns.remove(q);
              setCounter++;
              q = random.nextInt(31-setCounter);
            }
            if (setCounter == 31) {
              intermediateFirstMove(2);
            }
            openCell(intermediate42Rows.get(q), intermediate42Columns.get(q));
            System.out.println(intermediate42Rows.get(q) + " " + intermediate42Columns.get(q));
          }
          else {
            p = random.nextInt(100);
            if (p < 41) {
              ArrayList<Integer> intermediate41Rows = intermediate41Rows();
              ArrayList<Integer> intermediate41Columns = intermediate41Columns();
              if ((cells[intermediate41Rows.get(0)][intermediate41Columns.get(0)].checkHasMine() == true) && (firstMoveCheck == 1)) {
                shiftMineOnFirstMove(intermediate41Rows.get(0), intermediate41Columns.get(0), 16, 16);
              }
              if (cells[intermediate41Rows.get(0)][intermediate41Columns.get(0)].checkBeenOpened() == true) {
                intermediateFirstMove(2);
              }
              openCell(intermediate41Rows.get(0), intermediate41Columns.get(0));
              System.out.println(intermediate41Rows.get(0) + " " + intermediate41Columns.get(0));
            }
            else {
              p = random.nextInt(100);
              if (p < 36) {
                ArrayList<Integer> intermediate36Rows = intermediate36Rows();
                ArrayList<Integer> intermediate36Columns = intermediate36Columns();
                int q = random.nextInt(2);
                if ((cells[intermediate36Rows.get(q)][intermediate36Columns.get(q)].checkHasMine() == true) && (firstMoveCheck == 1)) {
                  shiftMineOnFirstMove(intermediate36Rows.get(q), intermediate36Columns.get(q), 16, 16);
                }
                setCounter = 0;
                while (cells[intermediate36Rows.get(q)][intermediate36Columns.get(q)].checkBeenOpened() == true) {
                  intermediate36Rows.remove(q);
                  intermediate36Columns.remove(q);
                  setCounter++;
                  q = random.nextInt(2-setCounter);
                }
                if (setCounter == 2) {
                  intermediateFirstMove(2);
                }
                openCell(intermediate36Rows.get(q), intermediate36Columns.get(q));
                System.out.println(intermediate36Rows.get(q) + " " + intermediate36Columns.get(q));
              }
              else {
                p = random.nextInt(100);
                if (p < 26) {
                  ArrayList<Integer> intermediate26Rows = intermediate26Rows();
                  ArrayList<Integer> intermediate26Columns = intermediate26Columns();
                  int q = random.nextInt(5);
                  if ((cells[intermediate26Rows.get(q)][intermediate26Columns.get(q)].checkHasMine() == true) && (firstMoveCheck == 1)) {
                    shiftMineOnFirstMove(intermediate26Rows.get(q), intermediate26Columns.get(q), 16, 16);
                  }
                  setCounter = 0;
                  while (cells[intermediate26Rows.get(q)][intermediate26Columns.get(q)].checkBeenOpened() == true) {
                    intermediate26Rows.remove(q);
                    intermediate26Columns.remove(q);
                    setCounter++;
                    q = random.nextInt(5-setCounter);
                  }
                  if (setCounter == 5) {
                    intermediateFirstMove(2);
                  }
                  openCell(intermediate26Rows.get(q), intermediate26Columns.get(q));
                  System.out.println(intermediate26Rows.get(q) + " " + intermediate26Columns.get(q));
                }
                else {
                  p = random.nextInt(100);
                  if (p < 25) {
                    ArrayList<Integer> intermediate25Rows = intermediate25Rows();
                    ArrayList<Integer> intermediate25Columns = intermediate25Columns();
                    int q = random.nextInt(190);
                    if ((cells[intermediate25Rows.get(q)][intermediate25Columns.get(q)].checkHasMine() == true) && (firstMoveCheck == 1)) {
                      shiftMineOnFirstMove(intermediate25Rows.get(q), intermediate25Columns.get(q), 16, 16);
                    }
                    setCounter = 0;
                    while (cells[intermediate25Rows.get(q)][intermediate25Columns.get(q)].checkBeenOpened() == true) {
                      intermediate25Rows.remove(q);
                      intermediate25Columns.remove(q);
                      setCounter++;
                      q = random.nextInt(190-setCounter);
                    }
                    if (setCounter == 190) {
                      intermediateFirstMove(2);
                    }
                    openCell(intermediate25Rows.get(q), intermediate25Columns.get(q));
                    System.out.println(intermediate25Rows.get(q) + " " + intermediate25Columns.get(q));
                  }
                  else {
                    ArrayList<Integer> intermediate21Rows = intermediate21Rows();
                    ArrayList<Integer> intermediate21Columns = intermediate21Columns();
                    if ((cells[intermediate21Rows.get(0)][intermediate21Columns.get(0)].checkHasMine() == true) && (firstMoveCheck == 1)) {
                      shiftMineOnFirstMove(intermediate21Rows.get(0), intermediate21Columns.get(0), 16, 16);
                    }
                    if (cells[intermediate21Rows.get(0)][intermediate21Columns.get(0)].checkBeenOpened() == true) {
                      intermediateFirstMove(2);
                    }
                    openCell(intermediate21Rows.get(0), intermediate21Columns.get(0));
                    System.out.println(intermediate21Rows.get(0) + " " + intermediate21Columns.get(0));
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  public void expertFirstMove(int firstMoveCheck) {
    Random random = new Random();
    int p = random.nextInt(100);
    if (p < 50) {
      int q = random.nextInt(3);
      if ((cells[expert50Rows[q]][expert50Columns[q]].checkHasMine() == true) && (firstMoveCheck == 1)) {
        shiftMineOnFirstMove(expert50Rows[q], expert50Columns[q], 16, 30);
      }
      openCell(expert50Rows[q], expert50Columns[q]);
      System.out.println(expert50Rows[q] + " " + expert50Columns[q]);
    }
    else {
      p = random.nextInt(100);
      if (p < 40) {
        if ((cells[expert40Rows[0]][expert40Columns[0]].checkHasMine() == true) && (firstMoveCheck == 1)) {
          shiftMineOnFirstMove(expert40Rows[0], expert40Columns[0], 16, 30);
        }
        openCell(expert40Rows[0], expert40Columns[0]);
        System.out.println(expert40Rows[0] + " " + expert40Columns[0]);
      }
      else {
        p = random.nextInt(100);
        if (p < 32) {
          int q = random.nextInt(12);
          if ((cells[expert32Rows[q]][expert32Columns[q]].checkHasMine() == true) && (firstMoveCheck == 1)) {
            shiftMineOnFirstMove(expert32Rows[q], expert32Columns[q], 16, 30);
          }
          openCell(expert32Rows[q], expert32Columns[q]);
          System.out.println(expert32Rows[q] + " " + expert32Columns[q]);
        }
        else {
          p = random.nextInt(100);
          if (p < 31) {
            int q = random.nextInt(68);
            if ((cells[expert31Rows[q]][expert31Columns[q]].checkHasMine() == true) && (firstMoveCheck == 1)) {
              shiftMineOnFirstMove(expert31Rows[q], expert31Columns[q], 16, 30);
            }
            openCell(expert31Rows[q], expert31Columns[q]);
            System.out.println(expert31Rows[q] + " " + expert31Columns[q]);
          }
          else {
            p = random.nextInt(100);
            if (p < 30) {
              if ((cells[expert30Rows[0]][expert30Columns[0]].checkHasMine() == true) && (firstMoveCheck == 1)) {
                shiftMineOnFirstMove(expert30Rows[0], expert30Columns[0], 16, 30);
              }
              openCell(expert30Rows[0], expert30Columns[0]);
              System.out.println(expert30Rows[0] + " " + expert30Columns[0]);
            }
            else {
              p = random.nextInt(100);
              if (p < 25) {
                int q = random.nextInt(3);
                if ((cells[expert25Rows[q]][expert25Columns[q]].checkHasMine() == true) && (firstMoveCheck == 1)) {
                  shiftMineOnFirstMove(expert25Rows[q], expert25Columns[q], 16, 30);
                }
                openCell(expert25Rows[q], expert25Columns[q]);
                System.out.println(expert25Rows[q] + " " + expert25Columns[q]);
              }
              else {
                p = random.nextInt(100);
                if (p < 16) {
                  int q = random.nextInt(237);
                  if ((cells[expert16Rows[q]][expert16Columns[q]].checkHasMine() == true) && (firstMoveCheck == 1)) {
                    shiftMineOnFirstMove(expert16Rows[q], expert16Columns[q], 16, 30);
                  }
                  openCell(expert16Rows[q], expert16Columns[q]);
                  System.out.println(expert16Rows[q] + " " + expert16Columns[q]);
                }
                else {
                  p = random.nextInt(100);
                  if (p < 15) {
                    int q = random.nextInt(153);
                    if ((cells[expert15Rows[q]][expert15Columns[q]].checkHasMine() == true) && (firstMoveCheck == 1)) {
                      shiftMineOnFirstMove(expert15Rows[q], expert15Columns[q], 16, 30);
                    }
                    openCell(expert15Rows[q], expert15Columns[q]);
                    System.out.println(expert15Rows[q] + " " + expert15Columns[q]);
                  }
                  else {
                    int q = random.nextInt(2);
                    if ((cells[expert12Rows[q]][expert12Columns[q]].checkHasMine() == true) && (firstMoveCheck == 1)) {
                      shiftMineOnFirstMove(expert12Rows[q], expert12Columns[q], 16, 30);
                    }
                    openCell(expert12Rows[q], expert12Columns[q]);
                    System.out.println(expert12Rows[q] + " " + expert12Columns[q]);
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  public int checkAllCellsOpenedOrFlagged() {
    int openedOrFlaggedCounter = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        if (((cells[i][j].checkBeenOpened() == false) && (cells[i][j].checkBeenFlagged() == true)) || ((cells[i][j].checkBeenOpened() == true) && (cells[i][j].checkBeenFlagged() == false))) { //xor
          openedOrFlaggedCounter++;
        }
      }
    }
    return openedOrFlaggedCounter;
  }

  public boolean playMove(int row, int column) {
    if (cells[row][column].checkHasMine() == true) {
      System.out.println("Game Over!");
    }
    else {
      cells[row][column].setBeenOpened();
      if (cells[row][column].getAdjacentMines() == 0) {
        openNeighboursRecursively(row, column);
      }
      patternMatching();
      openCellsForSingleCell();
      displayBoardForSolving();
      System.out.println("Openings revealed");
    }
    return true;
  }

  public void displayGeneratedBoard() {
    for (int i = 0; i < rows; i++) {
      System.out.print("\t ");
      for (int j = 0; j < columns; j++) {
        if (cells[i][j].checkHasMine() == false) {
          if ((cells[i][j].getAdjacentMines()) != 0) {
            System.out.print(cells[i][j].getAdjacentMines());
          }
          else {
            System.out.print(" ");
          }
        }
        else if (cells[i][j].checkHasMine() == true) {
          System.out.print("X");
        }
        else {
          System.out.print(cells[i][j]);
        }
        System.out.print(" | ");
      }
      System.out.print("\n");
    }
  }

  public void displayBoardForSolving() {
    for (int i = 0; i < rows; i++) {
      System.out.print("\t ");
      for (int j = 0; j < columns; j++) {
        if (cells[i][j].checkBeenOpened() == true) {
          if (cells[i][j].checkHasMine() == true) {
            System.out.print("X");
          }
          else {
            System.out.print(cells[i][j].getAdjacentMines());
          }
        }
        else if (cells[i][j].checkBeenFlagged() == true) {
          System.out.print("!");
        }
        else {
          System.out.print("?");
        }
        System.out.print(" | ");
      }
      System.out.print("\n");
    }
  }
}