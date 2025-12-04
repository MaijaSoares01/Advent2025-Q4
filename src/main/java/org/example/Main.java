package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static int rows;
    private static int cols;
    private static char[][] rollsearch;

    public static void main(String[] args) {
        BufferedReader reader;
        char roll = '@';
        try {
            reader = new BufferedReader(new FileReader("src/main/resources/Grid.txt"));
            String line = reader.readLine();
            int rowCount = 0;
            int colCount = 0;

            // First pass: count rows and columns
            while (line != null) {
                rowCount++;
                colCount = line.length(); // Assuming all rows have the same length
                line = reader.readLine();
            }

            // Re-initialize BufferedReader and the rollsearch array
            reader.close();
            reader = new BufferedReader(new FileReader("src/main/resources/Grid.txt"));
            rollsearch = new char[rowCount][colCount];

            int row = 0;
            // Second pass: populate the rollsearch array
            while ((line = reader.readLine()) != null) {
                rollsearch[row] = line.toCharArray();
                row++;
            }

            reader.close();

            // Count all accessible rolls
            System.out.println("All accessible rolls count: " + countAccessibleRolls(rollsearch, roll));

            // Count all accessible rolls
            System.out.println("All accessible rolls count: " + countAccessibleRolls(rollsearch, roll));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int countAccessibleRolls(char[][] grid, char roll) {
        int rows = grid.length;
        int cols = grid[0].length;
        int accessibleCount = 0;
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != roll) continue;
                int neighbors = 0;

                // Look only at the 8 adjacent cells
                for (int i = 0; i < 8; i++) {
                    int nr = r + dr[i];
                    int nc = c + dc[i];
                    if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                        if (grid[nr][nc] == roll) {
                            neighbors++;
                        }
                    }
                }
                if (neighbors < 4) {
                    accessibleCount++;
                }
            }
        }
        return accessibleCount;
    }

    public static int countAccessibleRollsRemoved(char[][] grid, char roll) {
        int rows = grid.length;
        int cols = grid[0].length;
        int accessibleCount = 0;
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != roll) continue;
                int neighbors = 0;

                // Look only at the 8 adjacent cells
                for (int i = 0; i < 8; i++) {
                    int nr = r + dr[i];
                    int nc = c + dc[i];
                    if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                        if (grid[nr][nc] == roll) {
                            neighbors++;
                        }
                    }
                }
                if (neighbors < 4) {
                    accessibleCount++;
                }
            }
        }
        return accessibleCount;
    }

    public static boolean accessible(int row, int col, char roll, int direction) {
        // Direction vectors for 8 possible directions
        int[] ROW_DIRECTIONS = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] COL_DIRECTIONS = {-1, 0, 1, -1, 1, -1, 0, 1};

        // Check if cell is out of bounds
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false;
        }

        // Check if the current character matches the expected character = @
        if (rollsearch[row][col] == roll) {
            // Move to the next character in the word in the current direction
            int newRow = row + ROW_DIRECTIONS[direction];
            int newCol = col + COL_DIRECTIONS[direction];
            return accessible(newRow, newCol, roll, direction);
        }

        return false;
    }
}
