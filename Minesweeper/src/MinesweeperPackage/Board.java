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
    if (checkValidCell(i-1, j+2)) {
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
        if ((cells[i][j].getAdjacentMines() == 2) && (cells[i][j].checkBeenOpened() == true) && (checkValidCell(i+1, j-1)) && (cells[i+1][j-1].checkBeenOpened() == true) && (checkValidCell(i+1, j)) && (cells[i+1][j].checkBeenOpened() == true) && (checkValidCell(i+1, j+1)) && (cells[i+1][j+1].checkBeenOpened() == true)) {
          if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() == 1) && (checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (cells[i][j+1].getAdjacentMines() != 0) && (checkValidCell(i-1, j+1))) {
            flagCell(i-1, j+1);
          }
          else if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() != 0) && (checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (cells[i][j+1].getAdjacentMines() == 1) && (checkValidCell(i-1, j-1))) {
            flagCell(i-1, j-1);
          }
        }
        else if ((cells[i][j].getAdjacentMines() == 2) && (cells[i][j].checkBeenOpened() == true) && (checkValidCell(i-1, j-1)) && (cells[i-1][j-1].checkBeenOpened() == true) && (checkValidCell(i-1, j)) && (cells[i-1][j].checkBeenOpened() == true) && (checkValidCell(i-1, j+1)) && (cells[i-1][j+1].checkBeenOpened() == true)) {
          if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() == 1) && (checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (cells[i][j+1].getAdjacentMines() != 0) && (checkValidCell(i+1, j+1))) {
            flagCell(i+1, j+1);
          }
          else if ((checkValidCell(i, j-1)) && (cells[i][j-1].checkBeenOpened() == true) && (cells[i][j-1].getAdjacentMines() != 0) && (checkValidCell(i, j+1)) && (cells[i][j+1].checkBeenOpened() == true) && (cells[i][j+1].getAdjacentMines() == 1) && (checkValidCell(i+1, j-1))) {
            flagCell(i+1, j-1);
          }
        }
        //vertical patterns

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

  public boolean checkAllCellsOpenedOrFlagged() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        if ((cells[i][j].checkBeenOpened() == false) || (cells[i][j].checkBeenFlagged() == false)) {
          return false;
        }
      }
    }
    return true;
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