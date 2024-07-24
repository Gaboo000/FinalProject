// Laberinto.java (Modelo)
public class Laberinto {
    private int[][] grid;
    private int rows, cols;

    public Laberinto(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new int[rows][cols];
    }

    public void clear() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = 0;
            }
        }
    }

    public void setCell(int row, int col, int value) {
        grid[row][col] = value;
    }

    public int getCell(int row, int col) {
        return grid[row][col];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
