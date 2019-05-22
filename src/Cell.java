import java.util.LinkedList;

/**
 * This class represents a single cell in the maze
 * Contains 4 walls (initially) and has is connected to 4 other cells in each direction
 */
public class Cell {

    /**
     * Booleans directions represent walls:
     * false = wall
     * true = open path
     */
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    private boolean player = false;
    private boolean coinCollected = true;
    private boolean visited = false;

    /**
     * The cell's neighbours
     */
    private Cell cellLeft = null;
    private Cell cellRight = null;
    private Cell cellUp = null;
    private Cell cellDown = null;

    private int specialCell;

    /**
     * Special List
     * 0 = Nothing
     * 1 = Entrance
     * 2 = Exit
     * 3 = Red coin
     * 4 = Orange coin
     * 5 = Yellow coin
     * 6 = Green coin
     * 7 = Blue coin
     * 8 = Indigo coin
     * 9 = Violet coin
     * @param special The special property associated with the cell
     */
    public Cell(int special) {
        this.specialCell = special;
    }

    /**
     * Puts a special property in the cell
     * @param specialCell The special property according to the list
     */
    public void setSpecial(int specialCell) {
        this.specialCell = specialCell;
        if (specialCell >= 3 && specialCell <= 9) {
            coinCollected = false;
        }
    }

    /**
     * Gets the special property associated with the cell
     * @return The cell's special value
     */
    public int getSpecial() {
        return this.specialCell;
    }

    /**
     * Links this cell to another cell by the right direction
     * @param neighbourCell The cell to link this cell to
     */
    public void linkRight(Cell neighbourCell) {
        this.cellRight = neighbourCell;
        neighbourCell.cellLeft = this;
    }

    /**
     * Links this cell to another cell by the up direction
     * @param neighbourCell The cell to link this cell to
     */
    public void linkUp(Cell neighbourCell) {
        this.cellUp = neighbourCell;
        neighbourCell.cellDown = this;
    }

    /**
     * Sets this cell's left neighbour
     * @param cell The cell the to set as the west neighbour
     */
    public void setLeft(Cell cell) {
        this.cellLeft = cell;
    }

    /**
     * Sets this cell's down neighbour
     * @param cell The cell the to set as the south neighbour
     */
    public void setDown(Cell cell) {
        this.cellDown = cell;
    }

    /**
     * Get this cells right neighbour
     * @return This cell's east neighbour
     */
    public Cell getRight() {
        return this.cellRight;
    }

    /**
     * Get this cells left neighbour
     * @return This cell's west neighbour
     */
    public Cell getLeft() {
        return this.extracted();
    }

    private Cell extracted() {
        return cellLeft;
    }

    /**
     * Get this cells down neighbour
     * @return This cell's south neighbour
     */
    public Cell getDown() {
        return this.cellDown;
    }

    /**
     * Get this cells up neighbour
     * @return This cell's north neighbour
     */
    public Cell getUp() {
        return this.cellUp;
    }

    /**
     * Open/break the walls between this cell and a neighbour
     * @param neighbour The neighbouring cell (must be a neighbour or nothing happens)
     */
    public void breakWall(Cell neighbour) {
        if (cellRight != null && cellRight.equals(neighbour)) {
            right = true;
            cellRight.left = true;
        } else if (extracted() != null && extracted().equals(neighbour)) {
            left = true;
            extracted().right = true;
        } else if (cellUp != null && cellUp.equals(neighbour)) {
            up = true;
            cellUp.down = true;
        } else if (cellDown != null && cellDown.equals(neighbour)) {
            down = true;
            cellDown.up = true;
        }
    }

    /**
     * Sets this cell as visited (for maze generation)
     */
    public void setVisited() {
        this.visited = true;
    }

    /**
     * Checks if this cell has been visited
     * @return If the cell has been visited
     */
    public Boolean isVisited() {
        return this.visited;
    }

    /**
     * Returns the neighbours to this cell which have not been visited as a list
     * @return The list of unvisited neighbours for this cell
     */
    public LinkedList<Cell> getUnvisitedNeighbour() {
        LinkedList<Cell> neighbours = new LinkedList<Cell>();
        if (cellRight != null && !this.cellRight.isVisited()) {
            neighbours.add(cellRight);
        }
        if (extracted() != null && !this.extracted().isVisited()) {
            neighbours.add(extracted());
        }
        if (cellDown != null && !this.cellDown.isVisited()) {
            neighbours.add(cellDown);
        }
        if (cellUp != null && !this.cellUp.isVisited()) {
            neighbours.add(cellUp);
        }
        return neighbours;
    }

    /**
     * Check if there is a wall in the UP direction
     * @return True if this cell has a north wall
     */
    public boolean hasWallUp() {
        return this.up;
    }

    /**
     * Check if there is a wall in the Right direction
     * @return True if this cell has a east wall
     */
    public boolean hasWallRight() {
        return this.right;
    }

    /**
     * Check if there is a wall in the Left direction
     * @return True if this cell has a west wall
     */
    public boolean hasWallLeft() {
        return this.left;
    }

    /**
     * Check if there is a wall in the Down direction
     * @return True if this cell has a south wall
     */
    public boolean hasWallDown() {
        return this.down;
    }

    /**
     * Set cell if player exists on it. True = player on, false = player off
     * @param playerExists True if the player exists on this cell
     */
    public void setPlayer(boolean playerExists) {
        this.player = playerExists;
    }

    /**
     * Checks if this cell has a player on it
     * @return True if player is on cell
     */
    public boolean hasPlayer() {
        return this.player;
    }

    /**
     * Marks the coin in this cell as already collected
     */
    public void setCoinCollected() {
        this.coinCollected = true;
    }

    /**
     * Checks if the coin in this cell is already collected
     * @return True if the coin in this cell has be collected
     */
    public boolean isCoinCollected() {
        return this.coinCollected;
    }
}
