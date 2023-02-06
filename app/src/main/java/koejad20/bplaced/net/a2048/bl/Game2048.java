package koejad20.bplaced.net.a2048.bl;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Game2048 {
    private static final int GRID_SIZE = 4;
    private int[][] grid = new int[GRID_SIZE][GRID_SIZE];
    private int score = 0;

    private int[] combineTiles(@NonNull int[] row) {
        ArrayList<Integer> result = new ArrayList<>();
        boolean skip = false;
        for (int i = 0; i < row.length; i++) {
            if (skip) {
                skip = false;
                continue;
            }
            if (i + 1 < row.length && row[i] == row[i + 1]) {
                result.add(2 * row[i]);
                skip = true;
            } else if (row[i] != 0) {
                result.add(row[i]);
            }
        }
        while (result.size() < GRID_SIZE) {
            result.add(0);
        }
        return result.stream().mapToInt(i -> i).toArray();
    }

    private int[][] moveTiles(@NonNull String direction) {
        switch (direction) {
            case "left": {
                int[][] result = new int[GRID_SIZE][GRID_SIZE];
                for (int i = 0; i < GRID_SIZE; i++) {
                    result[i] = combineTiles(grid[i]);
                }
                return result;
            }
            case "right": {
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
            case "up": {
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
            case "down": {
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

    private void addRandomTile() {
        ArrayList<int[]> emptyTiles = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == 0) {
                    emptyTiles.add(new int[]{i, j});
                }
            }
        }
        if (emptyTiles.size() > 0) {
            int[] randomTile = emptyTiles.get((int) (Math.random() * emptyTiles.size()));
            grid[randomTile[0]][randomTile[1]] = Math.random() < 0.9 ? 2 : 4;
        }

        score += Math.random() < 0.9 ? 2 : 4;
    }

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

    public void start() {
        grid = new int[GRID_SIZE][GRID_SIZE];
        addRandomTile();
        addRandomTile();
        score = 0;
    }

    public boolean move(String direction) {
        int[][] moved = moveTiles(direction);
        if (moved == grid) {
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
        return isGameOver();
    }

    public int[][] getGrid () {
        return grid;
    }

    public int getScore() {
        return score;
    }
}