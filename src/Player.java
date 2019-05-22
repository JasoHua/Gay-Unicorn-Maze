/**
 * The class represents a player in the maze
 */
public class Player {

    private Cell currentPosition;
    private int points = 0;
    private int row = 0;
    private int column = 0;

    /**
     * Creates a Player referencing its starting cell
     * @param start The starting location
     */
    public Player(Cell start) {
        this.currentPosition = start;
        this.currentPosition.setPlayer(true);
    }

    /**
     * Returns the current position of the player, as a Cell
     * @return The current cell of the player
     */
    public Cell getCurrentPosition() {
        return this.currentPosition;
    }

    /**
     * Issues a move left command for the player
     * @return Null if move is impossible
     */
    public Boolean moveLeft() {
        if (this.currentPosition.getLeft() != null && this.currentPosition.hasWallLeft()) {
            this.currentPosition.setPlayer(false);
            this.currentPosition = currentPosition.getLeft();
            this.currentPosition.setPlayer(true);
            this.column--;
            coinInteraction(currentPosition);
            return true;
        }

        return false;
    }

    /**
     * Issues a move right command for the player
     * @return Null if move is impossible
     */
    public Boolean moveRight() {
        if (this.currentPosition.getRight() != null && this.currentPosition.hasWallRight()) {
            this.currentPosition.setPlayer(false);
            this.currentPosition = currentPosition.getRight();
            this.currentPosition.setPlayer(true);
            this.column++;
            coinInteraction(currentPosition);
            return true;
        }

        return false;
    }

    /**
     * Issues a move up command for the player
     * @return Null if move is impossible
     */
    public Boolean moveUp() {
        if (this.currentPosition.getUp() != null && this.currentPosition.hasWallUp()) {
            this.currentPosition.setPlayer(false);
            this.currentPosition = currentPosition.getUp();
            this.currentPosition.setPlayer(true);
            this.row++;
            coinInteraction(currentPosition);
            return true;
        }

        return false;
    }

    /**
     * Issues a move down command for the player
     * @return Null if move is impossible
     */
    public Boolean moveDown() {
        if (this.currentPosition.getDown() != null && this.currentPosition.hasWallDown()) {
            this.currentPosition.setPlayer(false);
            this.currentPosition = currentPosition.getDown();
            this.currentPosition.setPlayer(true);
            this.row--;
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
            this.currentPosition.setCoinCollected();
            this.points += this.currentPosition.getSpecial();
        }
    }

    /**
     * Get x coordinate of player
     * @return The column coordinate of the player
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Get y coordinate of player
     * @return The row coordinate of the player
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Get players current score
     * @return The current score
     */
    public int getScore() {
        return this.points;
    }
}
