import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int[][] sites;
    private WeightedQuickUnionUF uf;
    private int total;
    private int n;
    private int numOfOpenSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        this.n = N;
        total = n * n;
        uf = new WeightedQuickUnionUF(total + 2); // +2 virtual sites
        sites = new int[n][];
        for (int i = 0; i< n; i++) {
            sites[i] = new int[n];
        }
    }

    private int getVirtualUpSiteIndex(){
        return total;
    }

    private int getVirtualBottomSiteIndex() {
        return  total + 1;
    }

    private int getIndex(int row, int col) {
        return  (row - 1) * n + (col - 1);
    }

    private void validate(int row, int col) {
        if (row < 1 || row > n) {
            throw new IllegalArgumentException("row out of range: " + row);
        }
        if (col < 1 || col > n) {
            throw new IllegalArgumentException("col out of range: " + col);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        validate(row, col);
        if (isOpen(row, col)) {
            return;
        }
        int index = getIndex(row, col);
        sites[row-1][col-1] = 1;
        if (row > 1) {
            if (isOpen(row-1, col)) {
                uf.union(index, getIndex(row-1, col));
            }
        }
        if (row < n) {
            if (isOpen(row+1, col)) {
                uf.union(index, getIndex(row+1, col));
            }
        }
        if (col > 1) {
            if (isOpen(row, col-1)) {
                uf.union(index, getIndex(row, col-1));
            }
        }
        if (col < n) {
            if (isOpen(row, col+1)) {
                uf.union(index, getIndex(row, col+1));
            }
        }
        if (row == 1) {
            uf.union(index, getVirtualUpSiteIndex());
        }
        if (row == n) {
            uf.union(index, getVirtualBottomSiteIndex());
        }

        numOfOpenSites++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        validate(row, col);
        return sites[row-1][col-1] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        validate(row, col);
        int index = getIndex(row, col);
        return uf.find(index) == uf.find(getVirtualUpSiteIndex());
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return numOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates(){
        return uf.find(getVirtualUpSiteIndex()) == uf.find(getVirtualBottomSiteIndex());
    }

}