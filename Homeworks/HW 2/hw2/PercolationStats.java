package hw2;

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    // perform T independent experiments on an N-by-N grid
    double[] threshold;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N < 0 || T < 0) {
            throw new IllegalArgumentException();
        }
        Percolation grid;
        int[] random_list = new int[N];
        for (int i = 0; i < N; i++) {
            random_list[i] = i;
        }
        threshold = new double[T];

        for (int i = 0; i < T; i++) {
            int times = 0;
            do {
                grid = pf.make(N);
                int row;
                int col;
                do {
                    row = StdRandom.discrete(random_list);
                    col = StdRandom.discrete(random_list);
                } while (!grid.open_list[row][col]);
                grid.open(row, col);
                times += 1;
            } while (!grid.percolates());
            threshold[i] = (double) times / (N * N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0;
        for (double item : threshold) {
            sum += item;
        }
        return sum / threshold.length;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (threshold.length == 1) {
            return Double.NaN;
        }
        double std = 0;
        double mean = mean();
        for (double item : threshold) {
            std += Math.pow((item - mean), 2);
        }
        return Math.sqrt(std / (threshold.length) - 1);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(threshold.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(threshold.length);
    }
}
