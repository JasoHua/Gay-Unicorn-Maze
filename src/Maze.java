import java.util.LinkedList;
import java.util.Random;

/**
 * The class for generating the maze
 */
public class Maze {
    private int rows;
    private int columns;
    private Cell entrance;
    private Cell exit;

    /**
     * The Maze screen
     * @param columns The desired number of columns for the maze
     * @param rows The desired number of rows for the maze
     */
    public Maze(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        this.entrance = new Cell(1);
        makeGrid();
        pathGenerator(exit);
    }

    /**
     * Returns the entrance for the maze
     * @return The entrance cell to the maze
     */
    public Cell getEntrance() {
        return entrance;
    }

    /**
     * Makes a grid of Cells (Columns * Rows)
     */
    private void makeGrid () {
        int i = 0; //rows
        int j = 0; //columns

        Cell newCell;
        Cell current = entrance;
        Cell previousRow;
        Cell startRow = entrance;

        for (j = 0; j < columns - 1; ++j) {
            newCell = coinCell();
            current.linkRight(newCell);
            current = newCell;
        }

        for (i = 0; i < rows - 1; ++i) {
            previousRow = startRow;
            current = coinCell();
            startRow = current;

            for (j = 0; j < columns - 1; ++j) {
                previousRow.linkUp(current);
                previousRow = previousRow.getRight();
                newCell = coinCell();
                current.linkRight(newCell);
                current = newCell;
            }

            previousRow.linkUp(current);
        }

        newCell = new Cell(2);
        current.linkUp(newCell);
        exit = current;
    }

    /**
     * Determines if a cell will have a coin in it
     * @return A new cell that may hold a coin
     */
    private Cell coinCell() {
        Cell cell = new Cell(0);
        Random r = new Random();
        int rand = r.nextInt(10);

        // 10% chance
        if (rand == 1)	{
            addCoin(cell);
        }
        return cell;
    }

    /**
     * Determines what coin will be in the cell
     * @param cell The cell which to hold/set the coin
     */
    private void addCoin(Cell cell) {
        Random r = new Random();
        int rand = r.nextInt(100);

        if (rand > 60) {
            cell.setSpecial(3);
        } else if (rand > 30) {
            cell.setSpecial(4);
        } else if (rand > 15) {
            cell.setSpecial(5);
        } else if (rand > 8) {
            cell.setSpecial(6);
        } else if (rand > 4) {
            cell.setSpecial(7);
        } else if (rand >= 2) {
            cell.setSpecial(8);
        } else {
            cell.setSpecial(9);
        }
    }

    /**
     * Generates the "maze" (the randomised paths) using a DFS approach
     * Start at a particular cell and call it the "exit."
     * Mark the current cell as visited, and get a list of its neighbors. For each neighbor, starting with a randomly selected neighbor:
     * If that neighbor hasn't been visited, remove the wall between this cell and that neighbor, and then recurse with that neighbor as the current cell.
     * @param cell The starting cell for the DFS algorithm
     */
    private void pathGenerator(Cell cell) {
        int i = 0;
        Random rand = new Random();
        LinkedList<Cell> neighbours;
        cell.setVisited();
        while (cell.getUnvisitedNeighbour().size() != 0) {
            neighbours = cell.getUnvisitedNeighbour();
            i = rand.nextInt(4);
            while (neighbours.size() - 1 < i) {
                i = rand.nextInt(4);

            }
            cell.breakWall(neighbours.get(i));
            pathGenerator(neighbours.get(i));
        }
    }
}
