import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.LinkedList;


public class Solver {

    private MinPQ<SearchNode> queue;
    private MinPQ<SearchNode> twinQueue;

    private LinkedList<Board> solution;

    private int moves;

    private boolean unsolvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        if (initial == null) {
            throw new IllegalArgumentException("board is null");
        }

        Comparator<SearchNode> cmp = new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                return Integer.compare(o1.getManhatten(), o2.getManhatten());
            }
        };
        queue = new MinPQ<>(cmp);
        twinQueue = new MinPQ<>(cmp);

        queue.insert(new SearchNode(initial, null, 0));
        twinQueue.insert(new SearchNode(initial.twin(), null, 0));
        while (true) {
            SearchNode min = queue.delMin();
            //queue = new MinPQ<>(cmp);
            SearchNode minTwin = twinQueue.delMin();
            //twinQueue = new MinPQ<>(cmp);
            if (minTwin.getBoard().isGoal()) {
                unsolvable = true;
                break;
            }
           // System.out.println(min.board);
            if (min.getBoard().isGoal()) {
                solution = new LinkedList<>();
                solution.addFirst(min.getBoard());
                while (true) {
                    SearchNode previous = min.getPrevNode();
                    if (previous == null) {
                        break;
                    }
                    solution.addFirst(previous.getBoard());
                    moves += 1;
                    min = previous;
                }
                break;
            }
            for (Board neighbour :  min.getBoard().neighbors()) {
                //critical optimization
                if (min.getPrevNode() != null && min.getPrevNode().getBoard().equals(neighbour)) {
                    continue;
                }

                queue.insert(new SearchNode(neighbour, min, min.getMoves()+ 1));
            }

            for (Board neighbour : minTwin.getBoard().neighbors()) {
                //critical optimization
                if (minTwin.getPrevNode() != null && minTwin.getPrevNode().getBoard().equals(neighbour)) {
                    continue;
                }


                twinQueue.insert(new SearchNode(neighbour, minTwin, minTwin.getMoves() + 1));
            }
        }

       // System.out.println("num-of-calls: " + c);
    }

    public int moves() {
        return unsolvable ? -1 : moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return unsolvable ? null : solution;
    }

    public boolean isSolvable() {
        return !unsolvable;
    }

    // test client (see below)
    public static void main(String[] args){
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private static class SearchNode {

        SearchNode(Board board, SearchNode prev, int moves) {
            this.board = board;
            this.prevNode = prev;
            this.moves = moves;
            //o
            this.hamming = this.board.hamming() + moves;
            this.manhatten = this.board.manhattan() + moves;
        }

        Board getBoard() {
            return board;
        }

        SearchNode getPrevNode() {
            return prevNode;
        }

        int getMoves() {
            return moves;
        }

        public int getHamming() {
            return hamming;
        }

        public int getManhatten() {
            return manhatten;
        }

        private int hamming;
        private int manhatten;
        private final Board board;
        private final SearchNode prevNode;
        private final int moves;
    }

}