import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double mean, s, confidentLo, confidentHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        double[] thresholds = new double[trials];
        int totalSites = n * n;
        for (int i = 0; i<trials; i++) {
            int[] frequences = new int[totalSites];
            for (int j = 0; j<totalSites; j++) {
                frequences[j] = 1;
            }
            Percolation p = new Percolation(n);
            do {
                int index = StdRandom.discrete(frequences);
                frequences[index] = 0;
                int row = index / n + 1;
                int col = index % n + 1;
                p.open(row, col);
            }
            while (!p.percolates());

            thresholds[i] = p.numberOfOpenSites() / (double) totalSites;
        }

        mean = StdStats.mean(thresholds);
        s = StdStats.stddev(thresholds);
        confidentLo = mean - 1.96 * s / Math.sqrt(trials);
        confidentHi = mean + 1.96 * s / Math.sqrt(trials);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return s;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidentLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidentHi;
    }

    public static void main(String[] args) {
        int n = Integer.valueOf(args[0]);
        int T = Integer.valueOf(args[1]);


        PercolationStats stats = new PercolationStats(n, T);
        StdOut.printf("mean                    = %f\n", stats.mean());
        StdOut.printf("stddev                  = %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]\n", stats.confidenceLo(), stats.confidenceHi());
    }
}
