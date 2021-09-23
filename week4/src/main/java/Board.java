import java.util.LinkedList;
import java.util.List;

public class Board {

    private int[][] tiles;
    private int zi, zj;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] t) {
        int len = 0;
        tiles = new int[t.length][];
        for (int i = 0; i < t.length; i++ ) {
            if (i > 0 && len != t[i].length) {
                throw new IllegalArgumentException("arrays sizes are not equal");
            }
            if (t[i].length != t.length) {
                throw new IllegalArgumentException("array is not square");
            }
            len = t[i].length;
            tiles[i] = new int[len];
            for (int j = 0; j<len; j++) {
                tiles[i][j] = t[i][j];
                if (t[i][j] == 0) {
                    zi = i;
                    zj = j;
                }
            }
        }
    }

    private int[][] copy(int[][] input) {
        int[][] out = new int[input.length][];
        for (int i = 0; i<input.length; i++) {
            out[i] = new int[input[i].length];
            System.arraycopy(input[i], 0, out[i], 0, out[i].length);
        }
        return out;
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(tiles.length).append('\n');
        for (int i = 0; i<tiles.length; i++) {
            if (i > 0) {
                sb.append('\n');
            }
            for (int j = 0; j<tiles[i].length; j++) {
                sb.append(tiles[i][j]);
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i<tiles.length; i++) {
            for (int j = 0; j<tiles[i].length; j++) {
                if (tiles[i][j]==0)
                    continue;
                int pos = j + i * tiles.length;
                if (tiles[i][j] != pos + 1) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i<tiles.length; i++) {
            for (int j = 0; j<tiles[i].length; j++) {
                if (tiles[i][j]==0)
                    continue;
                int expectedPosition = tiles[i][j] - 1;
                int expectedY = expectedPosition / tiles.length;
                int expectedX = expectedPosition - expectedY * tiles.length;
                manhattan += Math.abs(j - expectedX) + Math.abs(i - expectedY);
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
       if (!(y instanceof Board)) {
           return false;
       }
       Board other = (Board)y;
       if (other.dimension() != this.dimension()) {
           return false;
       }
       for (int i = 0; i<this.dimension(); i++) {
           for (int j = 0; j<this.dimension(); j++) {
                if (this.tiles[i][j] != other.tiles[i][j]) {
                    return false;
                }
           }
       }
       return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbours = new LinkedList<>();
        if (zj > 0) {
            int[][] copy = copy(tiles);
            exch(copy, zi, zj, zi, zj - 1);
            neighbours.add(new Board(copy));
        }
        if (zj < tiles.length - 1) {
            int[][] copy = copy(tiles);
            exch(copy, zi, zj, zi, zj + 1);
            neighbours.add(new Board(copy));
        }
        if (zi > 0) {
            int[][] copy = copy(tiles);
            exch(copy, zi, zj, zi - 1, zj);
            neighbours.add(new Board(copy));
        }
        if (zi < tiles.length - 1) {
            int[][] copy = copy(tiles);
            exch(copy, zi, zj, zi + 1, zj);
            neighbours.add(new Board(copy));
        }

        return neighbours;
    }

    private void exch(int[][] arr, int i1, int j1,int i2, int j2) {
        int v1 = arr[i1][j1];
        int v2 = arr[i2][j2];
        arr[i1][j1] = v2;
        arr[i2][j2] = v1;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] copy = copy(tiles);
        if (zi > 0) {
            exch(copy, 0, 0 , 0 , 1);
        }
        else if (zj > 0) {
            exch(copy, 0, 0 , 1 , 0);
        }
        else {
            exch(copy, 1, 0 , 1 , 1);
        }
        return new Board(copy);
    }

}