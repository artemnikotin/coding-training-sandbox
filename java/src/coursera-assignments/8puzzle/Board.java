import java.util.ArrayList;
import java.util.Arrays;

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
public class Board {
    private final int[][] tiles;
    private final int dimension;
    private final int twinRow;

    public Board(int[][] tiles) {
        if (tiles == null || !validateSymmetric(tiles)) {
            throw new IllegalArgumentException("board is not symmetric");
        }
        this.tiles = cloneTiles(tiles);
        dimension = tiles.length;

        var blank = getBlankTilePosition();
        if (blank == null) {
            throw new IllegalArgumentException("board has no blanc");
        }

        twinRow = tiles[0][0] == 0 || tiles[0][1] == 0 ? 1 : 0;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension());
        s.append("\n");
        for (int row = 0; row < dimension(); row++) {
            for (int col = 0; col < dimension(); col++) {
                s.append(String.format("%2d ", tiles[row][col]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public int dimension() {
        return dimension;
    }

    /**
     * number of tiles out of place
     */
    public int hamming() {
        int hamming = 0;
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                if (row == dimension - 1 && col == dimension - 1) {
                    continue;
                }
                int validTile = row * dimension + col + 1;
                if (tiles[row][col] != validTile) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    /**
     * sum of Manhattan distances between tiles and goal
     */
    public int manhattan() {
        int manhattan = 0;
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                int tile = tiles[row][col];
                if (tile == 0) {
                    continue;
                }
                int validRow = (tile - 1) / dimension;
                int validCol = (tile - 1) % dimension;
                manhattan += Math.abs(validRow - row) + Math.abs(validCol - col);
            }
        }
        return manhattan;
    }

    /**
     * Is this board the goal board?
     */
    public boolean isGoal() {
        return hamming() == 0;
    }

    /**
     * Does this board equal y?
     */
    @Override
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null || y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) y;
        return Arrays.deepEquals(this.tiles, that.tiles);
    }

    /**
     * Returns all neighboring boards
     */
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();

        Position blank = getBlankTilePosition();
        if (blank == null) {
            return neighbors;
        }
        if (!blank.isTop()) {
            neighbors.add(buildNeighbor(blank, blank.up()));
        }
        if (!blank.isBottom()) {
            neighbors.add(buildNeighbor(blank, blank.down()));
        }
        if (!blank.isLeft()) {
            neighbors.add(buildNeighbor(blank, blank.left()));
        }
        if (!blank.isRight()) {
            neighbors.add(buildNeighbor(blank, blank.right()));
        }
        return neighbors;
    }

    /**
     * A board that is obtained by exchanging any pair of tiles
     */
    public Board twin() {
        var newTiles = cloneTiles(tiles);
        swapTiles(newTiles, new Position(twinRow, 0), new Position(twinRow, 1));
        return new Board(newTiles);
    }

    private boolean validateSymmetric(int[][] inputTiles) {
        for (int[] row : inputTiles) {
            if (row.length != inputTiles.length) {
                return false;
            }
        }
        return true;
    }

    private Position getBlankTilePosition() {
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                if (tiles[row][col] == 0) {
                    return new Position(row, col);
                }
            }
        }
        return null;
    }

    private Board buildNeighbor(Position i, Position j) {
        int[][] neighborTiles = cloneTiles(tiles);
        swapTiles(neighborTiles, i, j);
        return new Board(neighborTiles);
    }

    private static int[][] cloneTiles(int[][] tiles) {
        int[][] clone = new int[tiles.length][];
        for (int i = 0; i < tiles.length; i++) {
            clone[i] = tiles[i].clone();
        }
        return clone;
    }

    private static void swapTiles(int[][] tiles, Position i, Position j) {
        int swap = tiles[i.row][i.col];
        tiles[i.row][i.col] = tiles[j.row][j.col];
        tiles[j.row][j.col] = swap;
    }

    public static void main(String[] args) {
        int[][] initialTiles = {
                {4, 7, 8},
                {0, 5, 6},
                {3, 1, 2},
        };

        int[][] goalTitles = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0},
        };

        Board board = new Board(initialTiles);
        Board copyBoard = new Board(initialTiles);
        Board goalBoard = new Board(goalTitles);

        System.out.println("Random board:");
        System.out.println(board);

        System.out.println("Random: solved: " + board.isGoal());

        System.out.println("Random copy equals to it's copy: " + board.equals(copyBoard));

        Board twin = board.twin();
        System.out.println("Twin board:");
        System.out.println(twin.toString());

        System.out.println("Random board: Hamming distance " + board.hamming());
        System.out.println("Random board: Manhattan distance " + board.manhattan());
        System.out.println("Twin board: Hamming distance " + twin.hamming());
        System.out.println("Twin board: Manhattan distance " + twin.manhattan());
        System.out.println("Goal board: Hamming distance " + goalBoard.hamming());
        System.out.println("Goal board: Manhattan distance " + goalBoard.manhattan());

        System.out.println("Random board: Neighbors");
        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor.toString());
        }
        System.out.println();
    }

    private class Position {
        public final int row;
        public final int col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public boolean isTop() {
            return row == 0;
        }

        public boolean isBottom() {
            return row == dimension - 1;
        }

        public boolean isLeft() {
            return col == 0;
        }

        public boolean isRight() {
            return col == dimension - 1;
        }

        public Position up() {
            return new Position(row - 1, col);
        }

        public Position down() {
            return new Position(row + 1, col);
        }

        public Position left() {
            return new Position(row, col - 1);
        }

        public Position right() {
            return new Position(row, col + 1);
        }
    }
}
