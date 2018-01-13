import java.util.LinkedList;

/**
 * This class represents a single cell in the maze
 * Contains 4 walls (initially) and has is connected to 4 other cells in each direction
 */
public class Cell {
	
/* Special List
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
 * 
 * 
 */
	/**
	 * @param special The special property associated with the cell
	 */
	public Cell(int special) {				
		this.special = special;
	}
	
	
	/**
	 * Puts a special property in the cell
	 * @param i The special property according to the list
	 */
	public void setSpecial(int i) {
		special = i;
		if (i >= 3 && i <= 9) {
			coinCollected = false;
		}
	}
	
	/**
	 * Gets the special property associated with the cell
	 */
	
	public int getSpecial() {
		return special;
	}
	
	/**
	 * Links this cell to another cell by the right direction
	 * @param neighbour The cell to link this cell to
	 */
	
	public void linkRight(Cell neighbour) {
		cRight = neighbour;
		neighbour.cLeft = this;
	}
	
	/**
	 * Links this cell to another cell by the up direction
	 * @param neighbour The cell to link this cell to
	 */
	
	public void linkUp(Cell neighbour) {
		cUp = neighbour;
		neighbour.cDown = this;
	}
	
	/**
	 * Get this cells right neighbour
	 */
	public Cell getRight() {
		return cRight;
	}
	
	/**
	 * Get this cells left neighbour
	 */
	public Cell getLeft() {
		return cLeft;
	}
	
	/**
	 * Get this cells down neighbour
	 */
	public Cell getDown() {
		return cDown;
	}

	/**
	 * Get this cells up neighbour
	 */
	public Cell getUp() {
		return cUp;
	}
	
	/**
	 * Open the walls between this cell and a neighbour
	 * @param neighbour The neighbouring cell (must be a neighbour or nothing happens)
	 */
	public void breakWall(Cell neighbour) {
		if (cRight != null && cRight.equals(neighbour)) {
			right = true;
			cRight.left = true;
		} else if (cLeft != null && cLeft.equals(neighbour)) {
			left = true;
			cLeft.right = true;
		} else if (cUp != null && cUp.equals(neighbour)) {
			up = true;
			cUp.down = true;
		} else if (cDown != null && cDown.equals(neighbour)) {
			down = true;
			cDown.up = true;
		}
	}
	

	/**
	 * Sets this cell as visited (for maze generation)
	 */
	public void setVisited() {
		visited = true;
	}
	
	/**
	 * Checks if this cell has been visited
	 */
	public Boolean isVisited() {
		return visited;
	}
	
	/**
	 * Returns the neighbours to this cell which have not been visited as a LinkedList
	 */
	public LinkedList<Cell> getUnvisitedNeighbour() {
		LinkedList<Cell> neighbours = new LinkedList<Cell>();
		if (cRight != null && !this.cRight.isVisited()) {
			neighbours.add(cRight);
		}
		if (cLeft != null && !this.cLeft.isVisited()) {
			neighbours.add(cLeft);
		}
		if (cDown != null && !this.cDown.isVisited()) {
			neighbours.add(cDown);
		}
		if (cUp != null && !this.cUp.isVisited()) {
			neighbours.add(cUp);
		}
		return neighbours;
	}

	/**
	 * Check if there is a wall in the UP direction
	 */
	public boolean hasWallUp() {
		return up;
	}

	/**
	 * Check if there is a wall in the Right direction
	 */
	public boolean hasWallRight() {
		return right;
	}
	
	/**
	 * Check if there is a wall in the Left direction
	 */
	public boolean hasWallLeft() {
		return left;
	}

	/**
	 * Check if there is a wall in the Down direction
	 */
	public boolean hasWallDown() {
		return down;
	}
	
	/**
	 * Marks this cell as having a player on or off of it. True = player on, false = player off
	 */
	public void setPlayer(boolean b) {
		player = b;
	}

	/**
	 * Checks if this cell has a player on it, true = Player is on cell
	 */
	public boolean hasPlayer() {
		return player;
	}

	/**
	 * Marks the coin in this cell as already collected
	 */
	public void setCoinCollected() {
		coinCollected = true;
		
	}
	
	/**
	 * Checks if the coin in this cell is already collected
	 */
	public boolean isCoinCollected() {
		return coinCollected;
	}
	
	
	//Booleans directions represent walls
	//false = wall
	//true = open path
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	private boolean player = false;
	private boolean coinCollected = true;
	private boolean visited = false;
	private Cell cLeft = null;
	private Cell cRight = null;
	private Cell cUp = null;
	private Cell cDown = null;
	private int special;
}

