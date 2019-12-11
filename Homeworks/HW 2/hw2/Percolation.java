package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    int n;
    int size;
    int open_size = 0;
    WeightedQuickUnionUF grid;
    boolean[][] open_list;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        n = N;
        size = n * n;
        open_list = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                open_list[i][j] = false;
            }
        }
        grid = new WeightedQuickUnionUF(size + 2);
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            int index = n * row + col;
            open_size++;
            open_list[row][col] = true;
            if (row == 0) {
                grid.union(index, size);
            }
            if (row > 0 && open_list[row - 1][col]) {
                grid.union(index, index - n);
            }
            if (row < n - 1 && open_list[row + 1][col]) {
                grid.union(index, index + n);
            }
            if (col > 0 && open_list[row][col - 1]) {
                grid.union(index, index - 1);
            }
            if (col < n - 1 && open_list[row][col + 1]) {
                grid.union(index, index + 1);
            }
            if (row == n - 1 && !percolates()) {
                grid.union(index, size + 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return open_list[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int index = n * row + col;
        return grid.connected(size, index);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return open_size;
    }

    // does the system percolate?
    public boolean percolates() {
        return grid.connected(size, size + 1);
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {

    }
}