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

  private static final int[] beginner60Rows = {0, 7, 7};
  private static final int[] beginner60Columns = {7, 0, 7};
  private static final int[] beginner50Rows = {0};
  private static final int[] beginner50Columns = {0};
  private static final int[] beginner42Rows = {0, 0, 3, 4, 5, 6, 6, 7, 7, 7};
  private static final int[] beginner42Columns = {3, 6, 0, 7, 7, 0, 7, 1, 4, 5};
  private static final int[] beginner41Rows = {0, 0, 1, 2, 2, 3, 4, 5, 7, 7, 7};
  private static final int[] beginner41Columns = {4, 5, 7, 0, 7, 7, 0, 0, 2, 3, 6};
  private static final int[] beginner40Rows = {0};
  private static final int[] beginner40Columns = {2};
  private static final int[] beginner34Rows = {0, 1};
  private static final int[] beginner34Columns = {1, 0};
  private static final int[] beginner24Rows = {1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6};
  private static final int[] beginner24Columns = {3, 6, 1, 3, 4, 5, 6, 1, 2, 3, 5, 6, 2, 3, 5, 6, 2, 3, 4, 5, 1, 2, 4, 5};
  private static final int[] beginner23Rows = {1, 1, 1, 2, 3, 4, 4, 5, 5, 6, 6};
  private static final int[] beginner23Columns = {2, 4, 5, 2, 4, 1, 4, 1, 6, 3, 6};
  private static final int[] beginner19Rows = {1};
  private static final int[] beginner19Columns = {1};

  private static final int[] intermediate60Rows = {0, 15, 15};
  private static final int[] intermediate60Columns = {15, 0, 15};
  private static final int[] intermediate50Rows = {0};
  private static final int[] intermediate50Columns = {0};
  private static final int[] intermediate43Rows = {0, 0, 0, 0, 2, 4, 6, 6, 7, 9, 9, 10, 12, 13, 14, 14, 15, 15, 15, 15, 15, 15};
  private static final int[] intermediate43Columns = {3, 4, 6, 11, 0, 15, 0, 15, 15, 0, 15, 15, 15, 0, 0, 15, 1, 7, 8, 9, 10, 12};
  private static final int[] intermediate42Rows = {0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 3, 4, 5, 5, 7, 8, 8, 10, 11, 11, 12, 13, 15, 15, 15, 15, 15, 15, 15, 15};
  private static final int[] intermediate42Columns = {5, 7, 8, 9, 10, 12, 13, 14, 15, 15, 0, 15, 0, 0, 15, 0, 0, 15, 0, 0, 15, 0, 15, 2, 3, 4, 5, 6, 11, 13, 14};
  private static final int[] intermediate41Rows = {0};
  private static final int[] intermediate41Columns = {2};
  private static final int[] intermediate36Rows = {0, 1};
  private static final int[] intermediate36Columns = {1, 0};
  private static final int[] intermediate26Rows = {3, 5, 9, 11, 13};
  private static final int[] intermediate26Columns = {12, 6, 10, 4, 14};
  private static final int[] intermediate25Rows = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14};
  private static final int[] intermediate25Columns = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 14, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
  private static final int[] intermediate21Rows = {1};
  private static final int[] intermediate21Columns = {1};

  public void shiftMineOnFirstMove(int currentRow, int currentColumn) {
    cells[currentRow][currentColumn].removeMine();
    int replacedCount = 0;
    while (replacedCount == 0) {
      for (int i = 0; i < currentRow; i++) {
        for (int j = 0; j < currentColumn; j++) {
          if (cells[i][j].checkHasMine() == false) {
            cells[i][j].setMine();
            replacedCount++;
          }
        }
      }
    }
    generateNumbers();
    System.out.println("Mine shifted");
    displayGeneratedBoard();
  }

  public void beginnerFirstMove() {
    Random random = new Random();
    int p = random.nextInt(100);
    if (p < 60) {
      int q = random.nextInt(3);
      if (cells[beginner60Rows[q]][beginner60Columns[q]].checkHasMine() == true) {
        shiftMineOnFirstMove(beginner60Rows[q], beginner60Columns[q]);
      }
      openCell(beginner60Rows[q], beginner60Columns[q]);
      System.out.println(beginner60Rows[q] + " " + beginner60Columns[q]);
    }
    else {
      p = random.nextInt(100);
      if (p < 50) {
        if (cells[beginner50Rows[0]][beginner50Columns[0]].checkHasMine() == true) {
          shiftMineOnFirstMove(beginner50Rows[0], beginner50Columns[0]);
        }
        openCell(beginner50Rows[0], beginner50Columns[0]);
        System.out.println(beginner50Rows[0] + " " + beginner50Columns[0]);
      }
      else {
        p = random.nextInt(100);
        if (p < 42) {
          int q = random.nextInt(10);
          if (cells[beginner42Rows[q]][beginner42Columns[q]].checkHasMine() == true) {
            shiftMineOnFirstMove(beginner42Rows[q], beginner42Columns[q]);
          }
          openCell(beginner42Rows[q], beginner42Columns[q]);
          System.out.println(beginner42Rows[q] + " " + beginner42Columns[q]);
        }
        else {
          p = random.nextInt(100);
          if (p < 41) {
            int q = random.nextInt(11);
            if (cells[beginner41Rows[q]][beginner41Columns[q]].checkHasMine() == true) {
              shiftMineOnFirstMove(beginner41Rows[q], beginner41Columns[q]);
            }
            openCell(beginner41Rows[q], beginner41Columns[q]);
            System.out.println(beginner41Rows[q] + " " + beginner41Columns[q]);
          }
          else {
            p = random.nextInt(100);
            if (p < 40) {
              if (cells[beginner40Rows[0]][beginner40Columns[0]].checkHasMine() == true) {
                shiftMineOnFirstMove(beginner40Rows[0], beginner40Columns[0]);
              }
              openCell(beginner40Rows[0], beginner40Columns[0]);
              System.out.println(beginner40Rows[0] + " " + beginner40Columns[0]);
            }
            else {
              p = random.nextInt(100);
              if (p < 34) {
                int q = random.nextInt(2);
                if (cells[beginner34Rows[q]][beginner34Columns[q]].checkHasMine() == true) {
                  shiftMineOnFirstMove(beginner34Rows[q], beginner34Columns[q]);
                }
                openCell(beginner34Rows[q], beginner34Columns[q]);
                System.out.println(beginner34Rows[q] + " " + beginner34Columns[q]);
              }
              else {
                p = random.nextInt(100);
                if (p < 24) {
                  int q = random.nextInt(24);
                  if (cells[beginner24Rows[q]][beginner24Columns[q]].checkHasMine() == true) {
                    shiftMineOnFirstMove(beginner24Rows[q], beginner24Columns[q]);
                  }
                  openCell(beginner24Rows[q], beginner24Columns[q]);
                  System.out.println(beginner24Rows[q] + " " + beginner24Columns[q]);
                }
                else {
                  p = random.nextInt(100);
                  if (p < 23) {
                    int q = random.nextInt(11);
                    if (cells[beginner23Rows[q]][beginner23Columns[q]].checkHasMine() == true) {
                      shiftMineOnFirstMove(beginner23Rows[q], beginner23Columns[q]);
                    }
                    openCell(beginner23Rows[q], beginner23Columns[q]);
                    System.out.println(beginner23Rows[q] + " " + beginner23Columns[q]);
                  }
                  else {
                    if (cells[beginner19Rows[0]][beginner19Columns[0]].checkHasMine() == true) {
                      shiftMineOnFirstMove(beginner19Rows[0], beginner19Columns[0]);
                    }
                    openCell(beginner19Rows[0], beginner19Columns[0]);
                    System.out.println(beginner19Rows[0] + " " + beginner19Columns[0]);
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  public void intermediateFirstMove() {
    Random random = new Random();
    int p = random.nextInt(100);
    if (p < 60) {
      int q = random.nextInt(3);
      if (cells[intermediate60Rows[q]][intermediate60Columns[q]].checkHasMine() == true) {
        shiftMineOnFirstMove(intermediate60Rows[q], intermediate60Columns[q]);
      }
      openCell(intermediate60Rows[q], intermediate60Columns[q]);
      System.out.println(intermediate60Rows[q] + " " + intermediate60Columns[q]);
    }
    else {
      p = random.nextInt(100);
      if (p < 50) {
        if (cells[intermediate50Rows[0]][intermediate50Columns[0]].checkHasMine() == true) {
          shiftMineOnFirstMove(intermediate50Rows[0], intermediate50Columns[0]);
        }
        openCell(intermediate50Rows[0], intermediate50Columns[0]);
        System.out.println(intermediate50Rows[0] + " " + intermediate50Columns[0]);
      }
      else {
        p = random.nextInt(100);
        if (p < 43) {
          int q = random.nextInt(22);
          if (cells[intermediate43Rows[q]][intermediate43Columns[q]].checkHasMine() == true) {
            shiftMineOnFirstMove(intermediate43Rows[q], intermediate43Columns[q]);
          }
          openCell(intermediate43Rows[q], intermediate43Columns[q]);
          System.out.println(intermediate43Rows[q] + " " + intermediate43Columns[q]);
        }
        else {
          p = random.nextInt(100);
          if (p < 42) {
            int q = random.nextInt(31);
            if (cells[intermediate42Rows[q]][intermediate42Columns[q]].checkHasMine() == true) {
              shiftMineOnFirstMove(intermediate42Rows[q], intermediate42Columns[q]);
            }
            openCell(intermediate42Rows[q], intermediate42Columns[q]);
            System.out.println(intermediate42Rows[q] + " " + intermediate42Columns[q]);
          }
          else {
            p = random.nextInt(100);
            if (p < 41) {
              if (cells[intermediate41Rows[0]][intermediate41Columns[0]].checkHasMine() == true) {
                shiftMineOnFirstMove(intermediate41Rows[0], intermediate41Columns[0]);
              }
              openCell(intermediate41Rows[0], intermediate41Columns[0]);
              System.out.println(intermediate41Rows[0] + " " + intermediate41Columns[0]);
            }
            else {
              p = random.nextInt(100);
              if (p < 36) {
                int q = random.nextInt(2);
                if (cells[intermediate36Rows[q]][intermediate36Columns[q]].checkHasMine() == true) {
                  shiftMineOnFirstMove(intermediate36Rows[q], intermediate36Columns[q]);
                }
                openCell(intermediate36Rows[q], intermediate36Columns[q]);
                System.out.println(intermediate36Rows[q] + " " + intermediate36Columns[q]);
              }
              else {
                p = random.nextInt(100);
                if (p < 26) {
                  int q = random.nextInt(5);
                  if (cells[intermediate26Rows[q]][intermediate26Columns[q]].checkHasMine() == true) {
                    shiftMineOnFirstMove(intermediate26Rows[q], intermediate26Columns[q]);
                  }
                  openCell(intermediate26Rows[q], intermediate26Columns[q]);
                  System.out.println(intermediate26Rows[q] + " " + intermediate26Columns[q]);
                }
                else {
                  p = random.nextInt(100);
                  if (p < 25) {
                    int q = random.nextInt(190);
                    if (cells[intermediate25Rows[q]][intermediate25Columns[q]].checkHasMine() == true) {
                      shiftMineOnFirstMove(intermediate25Rows[q], intermediate25Columns[q]);
                    }
                    openCell(intermediate25Rows[q], intermediate25Columns[q]);
                    System.out.println(intermediate25Rows[q] + " " + intermediate25Columns[q]);
                  }
                  else {
                    if (cells[intermediate21Rows[0]][intermediate21Columns[0]].checkHasMine() == true) {
                      shiftMineOnFirstMove(intermediate21Rows[0], intermediate21Columns[0]);
                    }
                    openCell(intermediate21Rows[0], intermediate21Columns[0]);
                    System.out.println(intermediate21Rows[0] + " " + intermediate21Columns[0]);
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  public void expertFirstMove() {

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