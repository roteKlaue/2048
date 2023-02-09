package koejad20.bplaced.net.a2048.bl;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Game2048 is a class that represents the 2048 game.
 *
 * The Game2048 class provides a simple implementation of the 2048 game, including methods for moving tiles,
 * adding random tiles, checking if the game is over, and tracking the current score.
 *
 * @author roteKlaue
 * @since 2023-02-09
 */
public class Game2048 {
    private static final int GRID_SIZE = 4;
    private int[][] grid = new int[GRID_SIZE][GRID_SIZE];
    private int score = 0;

    /**
     * Combines the tiles in a row to form a new row.
     *
     * @param row The row of tiles to be combined.
     * @return The new row after the tiles have been combined.
     * @throws NullPointerException if row is null.
     */
    private int[] combineTiles(@NonNull int[] row) {
        final ArrayList<Integer> result = new ArrayList<>();
        int i = 0;
        while (i < row.length) {
            if (i + 1 < row.length && row[i] == row[i + 1]) {
                result.add(2 * row[i]);
                i += 2;
            } else if (row[i] != 0) {
                result.add(row[i]);
                i++;
            } else {
                i++;
            }
        }
        while (result.size() < GRID_SIZE) {
            result.add(0);
        }
        return result.stream().mapToInt(ie -> ie).toArray();
    }

    /**
     * Moves the tiles in a specified direction.
     *
     * @param direction The direction in which the tiles should be moved.
     * @return The new grid after the tiles have been moved.
     * @throws NullPointerException if direction is null.
     */
    private int[][] moveTiles(@NonNull Directions direction) {
        switch (direction) {
            case LEFT: {
                int[][] result = new int[GRID_SIZE][GRID_SIZE];
                for (int i = 0; i < GRID_SIZE; i++) {
                    result[i] = combineTiles(grid[i]);
                }
                return result;
            }
            case RIGHT: {
                int[][] result = new int[GRID_SIZE][GRID_SIZE];
                for (int i = 0; i < GRID_SIZE; i++) {
                    int[] reversed = new int[GRID_SIZE];
                    for (int j = 0; j < GRID_SIZE; j++) {
                        reversed[j] = grid[i][GRID_SIZE - 1 - j];
                    }
                    reversed = combineTiles(reversed);
                    for (int j = 0; j < GRID_SIZE; j++) {
                        result[i][GRID_SIZE - 1 - j] = reversed[j];
                    }
                }
                return result;
            }
            case UP: {
                int[][] result = new int[GRID_SIZE][GRID_SIZE];
                for (int i = 0; i < GRID_SIZE; i++) {
                    int[] column = new int[GRID_SIZE];
                    for (int j = 0; j < GRID_SIZE; j++) {
                        column[j] = grid[j][i];
                    }
                    column = combineTiles(column);
                    for (int j = 0; j < GRID_SIZE; j++) {
                        result[j][i] = column[j];
                    }
                }
                return result;
            }
            case DOWN: {
                int[][] result = new int[GRID_SIZE][GRID_SIZE];
                for (int i = 0; i < GRID_SIZE; i++) {
                    int[] column = new int[GRID_SIZE];
                    for (int j = 0; j < GRID_SIZE; j++) {
                        column[j] = grid[GRID_SIZE - 1 - j][i];
                    }
                    column = combineTiles(column);
                    for (int j = 0; j < GRID_SIZE; j++) {
                        result[GRID_SIZE - 1 - j][i] = column[j];
                    }
                }
                return result;
            }
            default:
                return grid;
        }
    }

    /**
     * Adds a random tile (either 2 or 4) to an empty cell in the grid.
     *
     * This method selects an empty cell in the grid randomly, and sets its value to either 2 or 4.
     * If there are no empty cells in the grid, this method does nothing.
     */
    private void addRandomTile() {
        int res = Math.random() < 0.9 ? 2 : 4;
        final ArrayList<int[]> emptyTiles = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == 0) {
                    emptyTiles.add(new int[]{i, j});
                }
            }
        }

        if (emptyTiles.size() > 0) {
            int[] randomTile = emptyTiles.get((int) (Math.random() * emptyTiles.size()));
            grid[randomTile[0]][randomTile[1]] = res;
        }

        score += res;
    }

    /**
     * Checks if the game is over or not.
     *
     * @return true if there are no more moves available, false otherwise.
     */
    private boolean isGameOver() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == 0) {
                    return false;
                }
                if (i > 0 && grid[i][j] == grid[i - 1][j]) {
                    return false;
                }
                if (i < GRID_SIZE - 1 && grid[i][j] == grid[i + 1][j]) {
                    return false;
                }
                if (j > 0 && grid[i][j] == grid[i][j - 1]) {
                    return false;
                }
                if (j < GRID_SIZE - 1 && grid[i][j] == grid[i][j + 1]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Starts a new game of 2048.
     */
    public void start() {
        grid = new int[GRID_SIZE][GRID_SIZE];
        addRandomTile();
        addRandomTile();
        score = 0;
    }

    /**
     * Move tiles in the game board.
     *
     * @param direction the direction to move the tiles
     * @return `true` if the move was successful, `false` otherwise.
     */
    public boolean move(Directions direction) {
        int[][] moved = moveTiles(direction);
        if (Arrays.deepEquals(moved, grid)) {
            return false;
        }
        grid = moved;
        addRandomTile();
        return true;
    }

    public boolean isWon() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isOver () {
        return isGameOver() || isWon();
    }

    public int[][] getGrid () {
        return grid;
    }

    public int getScore() {
        return score;
    }
}