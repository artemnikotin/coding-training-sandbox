import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Coursera | Algorithms, Part I | Week 04 Assignment
 * ASSESSMENT SUMMARY
 * Compilation:  PASSED
 * API:          PASSED
 * SpotBugs:     PASSED
 * PMD:          PASSED
 * Checkstyle:   FAILED (0 errors, 1 warning)
 * Correctness:  51/51 tests passed
 * Memory:       22/22 tests passed
 * Timing:       125/125 tests passed
 * Aggregate score: 100.00%
 */
public class Solver {
    private final SearchNode initialNode;
    private SearchNode searchNode;
    private boolean solved;

    private static class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode previous;
        private final int moves;
        private final int manhattan;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
            // Caching priorities
            this.manhattan = board.manhattan();
        }

        public int compareTo(SearchNode that) {
            return (this.manhattan + this.moves) - (that.manhattan + that.moves);
        }
    }

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        initialNode = new SearchNode(initial, 0, null);
        solved = false;
        findSolution();
    }

    private void findSolution() {
        MinPQ<SearchNode> originalQueue = new MinPQ<>();
        /*
         * To detect unreachable situations, use the fact that boards are divided into two equivalence classes with respect to reachability:
         *  - Those that can lead to the goal board
         *  - Those that can lead to the goal board if we modify the initial board by swapping any pair of tiles (the blank square is not a tile).
         */
        MinPQ<SearchNode> twinQueue = new MinPQ<>();

        originalQueue.insert(initialNode);
        twinQueue.insert(new SearchNode(initialNode.board.twin(), 0, null));

        SearchNode originalNode;
        SearchNode twinNode;

        boolean twinSolved = false;

        while (!solved && !twinSolved) {
            originalNode = originalQueue.delMin();
            solved = originalNode.board.isGoal();

            twinNode = twinQueue.delMin();
            twinSolved = twinNode.board.isGoal();

            for (Board board : originalNode.board.neighbors()) {
                if (originalNode.previous != null && originalNode.previous.board.equals(board)) {
                    continue;
                }
                originalQueue.insert(new SearchNode(board, originalNode.moves + 1, originalNode));
            }

            for (Board board : twinNode.board.neighbors()) {
                if (twinNode.previous != null && twinNode.previous.board.equals(board)) {
                    continue;
                }
                twinQueue.insert(new SearchNode(board, twinNode.moves + 1, twinNode));
            }
            searchNode = originalNode;
        }
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        SearchNode current = searchNode;

        Stack<Board> solution = new Stack<>();

        while (current.previous != null) {
            solution.push(current.board);
            current = current.previous;
        }

        solution.push(current.board);

        return solution;
    }

    public boolean isSolvable() {
        return solved;
    }

    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return searchNode.moves;
    }


    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}
