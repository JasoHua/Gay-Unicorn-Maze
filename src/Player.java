import java.util.LinkedList;

/**
 * The class represents a player in the maze
 */
public class Player {
	/**
	 * @param start The starting location
	 */
	public Player(Cell start) {
		currentPosition = start;
		currentPosition.setPlayer(true);
	}
	
	/**
	 * Returns the current position of the player, as a Cell
	 */
	public Cell getCurrentPosition() {
		return currentPosition;
	}
	
	/**
	 * Issues a move left command for the player
	 * returns null if move is impossible
	 */
	public Boolean moveLeft() {
		if(currentPosition.getLeft() != null && currentPosition.hasWallLeft() == true) {
			currentPosition.setPlayer(false);
			currentPosition = currentPosition.getLeft();
			currentPosition.setPlayer(true);
			column--;
			coinInteraction(currentPosition);
			return true;
		}
		return false;
	}
	
	/**
	 * Issues a move right command for the player
	 * returns null if move is impossible
	 */
	
	public Boolean moveRight() {
		if(currentPosition.getRight() != null && currentPosition.hasWallRight() == true) {
			currentPosition.setPlayer(false);
			currentPosition = currentPosition.getRight();
			currentPosition.setPlayer(true);
			column++;
			coinInteraction(currentPosition);
			return true;
		}
		return false;
	}
	
	/**
	 * Issues a move up command for the player
	 * returns null if move is impossible
	 */
	
	public Boolean moveUp() {
		if(currentPosition.getUp() != null && currentPosition.hasWallUp() == true) {
			currentPosition.setPlayer(false);
			currentPosition = currentPosition.getUp();
			currentPosition.setPlayer(true);
			row++;
			coinInteraction(currentPosition);
			return true;
		}
		return false;
		
	}
	
	/**
	 * Issues a move down command for the player
	 * returns null if move is impossible
	 */
	
	public Boolean moveDown() {
		if(currentPosition.getDown() != null && currentPosition.hasWallDown() == true) {
			currentPosition.setPlayer(false);
			currentPosition = currentPosition.getDown();
			currentPosition.setPlayer(true);
			row--;
			coinInteraction(currentPosition);
			return true;
		}
		return false;
	}
	
	/**
	 * This method deals with the interactions involved when a player walks over a coin
	 * @param cell The cell which contains the coin
	 */
	
	private void coinInteraction(Cell cell) {
		if (currentPosition.getSpecial() >= 3 && !currentPosition.isCoinCollected()) {
			currentPosition.setCoinCollected();
			points += currentPosition.getSpecial();
		}
	}
	
	/**
	 * Get x coordinate of player
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Get y coordinate of player
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Get players current score
	 */
	public int getScore() {
		return points;
	}
	
	Cell currentPosition;
	int points = 0;
	int row = 0;
	int column = 0;
}
