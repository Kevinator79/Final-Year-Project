package MinesweeperPackage;

public class Cell {
  private boolean hasMine;
  private boolean beenOpened;
  private boolean beenFlagged;
  private int adjacentMines;
  private static final int neighbourCount = 8;

  public Cell() {
    hasMine = false;
    beenOpened = false;
    beenFlagged = false;
    adjacentMines = 0;
  }

  public boolean checkHasMine() {
    return hasMine;
  }

  public void setMine() {
    hasMine = true;
  }

  public boolean checkBeenOpened() {
    return beenOpened;
  }

  public void setBeenOpened() {
    beenOpened = true;
  }

  public boolean checkBeenFlagged() {
    return beenFlagged;
  }

  public void setBeenFlagged() {
    beenFlagged = true;
  }

  public int getAdjacentMines() {
    return adjacentMines;
  }

  public void setAdjacentMines(int adjacentMines) {
    this.adjacentMines = adjacentMines;
  }

  public static int getNeighbourCount() {
    return neighbourCount;
  }
}