/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;

import java.util.regex.Pattern;

import static loa.Piece.*;
import static loa.Square.*;

/** Represents the state of a game of Lines of Action.
 *  @author Tyler Rathkamp
 */
class Board {

    /** Default number of moves for each side that results in a draw. */
    static final int DEFAULT_MOVE_LIMIT = 60;

    /** Pattern describing a valid square designator (cr). */
    static final Pattern ROW_COL = Pattern.compile("^[a-h][1-8]$");

    /** A Board whose initial contents are taken from INITIALCONTENTS
     *  and in which the player playing TURN is to move. The resulting
     *  Board has
     *        get(col, row) == INITIALCONTENTS[row][col]
     *  Assumes that PLAYER is not null and INITIALCONTENTS is 8x8.
     *
     *  CAUTION: The natural written notation for arrays initializers puts
     *  the BOTTOM row of INITIALCONTENTS at the top.
     */
    Board(Piece[][] initialContents, Piece turn) {
        initialize(initialContents, turn);
    }

    /** A new board in the standard initial position. */
    Board() {
        this(INITIAL_PIECES, BP);
    }

    /** A Board whose initial contents and state are copied from
     *  BOARD. */
    Board(Board board) {
        this();
        copyFrom(board);
    }

    /** Set my state to CONTENTS with SIDE to move. */
    void initialize(Piece[][] contents, Piece side) {
        for (int x = 0; x < contents.length; x++) {
            for (int y = 0; y < contents[0].length; y++) {
                set(sq(y, x), contents[x][y], side);
            }
        }
        _turn = side;
        _moveLimit = DEFAULT_MOVE_LIMIT;
    }

    /** Set me to the initial configuration. */
    void clear() {
        initialize(INITIAL_PIECES, BP);
    }

    /** Set my state to a copy of BOARD. */
    void copyFrom(Board board) {
        if (board == this) {
            return;
        }
        _moveLimit = board._moveLimit;
        _winnerKnown = board._winnerKnown;
        _winner = board._winner;
        _moves.clear();
        _turn = board.turn();
        _moveLimit = board._moveLimit;
        _subsetsInitialized = false;
        for (int i = 0; i < board._board.length; i++) {
            _board[i] = board._board[i];
        }
        for (int i = 0; i < board._moves.size(); i++) {
            _moves.add(board._moves.get(i));
        }
    }

    /** Return the contents of the square at SQ. */
    Piece get(Square sq) {
        return _board[sq.index()];
    }

    /** Set the square at SQ to V and set the side that is to move next
     *  to NEXT, if NEXT is not null. */
    void set(Square sq, Piece v, Piece next) {
        if (next != null) {
            _turn = next;
        }
        _board[sq.index()] = v;
    }

    /** Set the square at SQ to V, without modifying the side that
     *  moves next. */
    void set(Square sq, Piece v) {
        set(sq, v, null);
        _subsetsInitialized = false;
    }

    /** Set limit on number of moves by each side that results in a tie to
     *  LIMIT, where 2 * LIMIT > movesMade(). */
    void setMoveLimit(int limit) {
        if (2 * limit <= movesMade()) {
            throw new IllegalArgumentException("move limit too small");
        }
        _moveLimit = 2 * limit;
    }

    /** Assuming isLegal(MOVE), make MOVE. This function assumes that
     *  MOVE.isCapture() will return false.  If it saves the move for
     *  later retraction, makeMove itself uses MOVE.captureMove() to produce
     *  the capturing move. */
    void makeMove(Move move) {
        assert isLegal(move);
        Piece to = get(move.getTo());
        Piece from = get(move.getFrom());
        if (to != EMP) {
            _moves.add(move.captureMove());
        } else {
            _moves.add(move);
        }
        _board[move.getTo().index()] = from;
        _board[move.getFrom().index()] = EMP;
        _subsetsInitialized = false;
        _turn = _turn.opposite();
    }

    /** Retract (unmake) one move, returning to the state immediately before
     *  that move.  Requires that movesMade () > 0. */
    void retract() {
        assert movesMade() > 0;
        int index = _moves.size() - 1;
        Move move = _moves.get(index);
        _moves.remove(index);
        if (move.isCapture()) {
            _board[move.getTo().index()] = _turn;
        } else {
            _board[move.getTo().index()] = EMP;
        }
        _board[move.getFrom().index()] = _turn.opposite();
        _subsetsInitialized = false;
        _turn = _turn.opposite();
        _winnerKnown = false;
    }

    /** Return the Piece representing who is next to move. */
    Piece turn() {
        return _turn;
    }

    /** Return true iff FROM - TO is a legal move for the player currently on
     *  move. LINETOTAL is the total pieces in the line, 0 if unknown. */
    boolean isLegal(Square from, Square to, int lineTotal) {
        if (to == null || from == null || to.index() > _board.length) {
            return false;
        }
        Piece fromP = _board[from.index()];
        Piece toP = _board[to.index()];
        boolean nullTest = from != null && to != null && fromP != null;
        boolean rightTurn = fromP.name().equals(_turn.name());
        boolean validMove = from.isValidMove(to);
        int distance = from.distance(to);
        int dir = from.direction(to);
        if (lineTotal == 0) {
            lineTotal = lineNum(from, dir);
        }
        boolean distCorrect = distance == lineTotal;
        boolean dist = (from.moveDest(from.direction(to), distance) == to);
        boolean blocked = blocked(from, to);
        return dist && validMove && nullTest && rightTurn
                && !blocked && distCorrect;
    }

    /** Return number of pieces in a Line of Action FROM in the DIR from 0-7. */
    public int lineNum(Square from, int dir) {
        int oppDir = (dir + 4) % 8;
        int totalObj, i, x;
        totalObj = i = x = 1;
        while (true) {
            Square current = from.moveDest(dir, i);
            if (current == null) {
                break;
            } else {
                Piece curr = _board[current.index()];
                if (curr.equals(BP) || curr.equals(WP)) {
                    totalObj += 1;
                }
            }
            i += 1;
        }
        while (true) {
            Square current = from.moveDest(oppDir, x);
            if (current == null) {
                break;
            } else {
                Piece curr = _board[current.index()];
                if (curr.equals(BP) || curr.equals(WP)) {
                    totalObj += 1;
                }
            }
            x += 1;
        }
        return totalObj;
    }

    /** Return true iff MOVE is legal for the player currently on move.
     *  The isCapture() property is ignored. */
    boolean isLegal(Move move) {
        return isLegal(move.getFrom(), move.getTo(), 0);
    }

    /** Return a sequence of all legal moves from this position. */
    List<Move> legalMoves() {
        ArrayList<Move> legalMoves = new ArrayList<Move>();
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Square curr = sq(y, x);
                Piece current = get(curr);
                if (current == _turn) {
                    for (int i = 0; i < 8; i++) {
                        int distance = lineNum(curr, i);
                        Square to = curr.moveDest(i, distance);
                        if (isLegal(curr, to, distance)) {
                            legalMoves.add(Move.mv(curr, to));
                        }
                    }
                }
            }
        }
        return legalMoves;
    }

    /** Return true iff the game is over (either player has all his
     *  pieces continguous or there is a tie). */
    boolean gameOver() {
        return winner() != null;
    }

    /** Return true iff SIDE's pieces are continguous. */
    boolean piecesContiguous(Piece side) {
        List<Integer> thisOne = getRegionSizes(side);
        return thisOne.size() == 1;
    }

    /** Return the winning side, if any.  If the game is not over, result is
     *  null.  If the game has ended in a tie, returns EMP. */
    Piece winner() {
        if (!_winnerKnown) {
            if (piecesContiguous(BP) && piecesContiguous(WP)) {
                _winner = _turn.opposite();
                _winnerKnown = true;
            } else if (piecesContiguous(BP)) {
                _winner = BP;
                _winnerKnown = true;
            } else if (piecesContiguous(WP)) {
                _winnerKnown = true;
                _winner = WP;
            } else if (_moves.size() >= _moveLimit) {
                _winner = EMP;
            } else {
                _winner = null;
            }
        }
        return _winner;
    }

    /** Return the total number of moves that have been made (and not
     *  retracted).  Each valid call to makeMove with a normal move increases
     *  this number by 1. */
    int movesMade() {
        return _moves.size();
    }

    @Override
    public boolean equals(Object obj) {
        Board b = (Board) obj;
        return Arrays.deepEquals(_board, b._board) && _turn == b._turn;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(_board) * 2 + _turn.hashCode();
    }

    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("===%n");
        for (int r = BOARD_SIZE - 1; r >= 0; r -= 1) {
            out.format("    ");
            for (int c = 0; c < BOARD_SIZE; c += 1) {
                out.format("%s ", get(sq(c, r)).abbrev());
            }
            out.format("%n");
        }
        out.format("Next move: %s%n===", turn().fullName());
        return out.toString();
    }

    /** Return true if a move from FROM to TO is blocked by an opposing
     *  piece or by a friendly piece on the target square. */
    private boolean blocked(Square from, Square to) {
        Piece fromP = _board[from.index()];
        Piece toP = _board[to.index()];
        int distance = from.distance(to);
        int dir = from.direction(to);
        for (int i = 1; i < distance; i++) {
            Square curr = from.moveDest(dir, i);
            Piece piece = get(curr);
            if (piece == (_turn.opposite())) {
                return true;
            }
        }
        if (toP.equals(_turn)) {
            return true;
        }
        return false;
    }

    /** Return the size of the as-yet unvisited cluster of squares
     *  containing P at and adjacent to SQ.  VISITED indicates squares that
     *  have already been processed or are in different clusters.  Update
     *  VISITED to reflect squares counted. */
    private int numContig(Square sq, boolean[][] visited, Piece p) {
        if (p == EMP || _board[sq.index()] != p
                || visited[sq.col()][sq.row()]) {
            return 0;
        } else {
            visited[sq.col()][sq.row()] = true;
            int counter = 1;
            for (int i = 0; i < sq.adjacent().length; i++) {
                Piece z = _board[sq.adjacent()[i].index()];
                if (z == p) {
                    counter += numContig(sq.adjacent()[i], visited, z);
                }
            }
            return counter;
        }
    }

    /** Set the values of _whiteRegionSizes and _blackRegionSizes. */
    private void computeRegions() {
        if (_subsetsInitialized) {
            return;
        }
        _whiteRegionSizes.clear();
        _blackRegionSizes.clear();
        boolean[][] visited = new boolean[BOARD_SIZE][BOARD_SIZE];
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Square curr = sq(y, x);
                Piece current = get(curr);
                if (current == WP) {
                    int num = numContig(curr, visited, current);
                    if (num != 0) {
                        _whiteRegionSizes.add(num);
                    }
                } else if (current == BP) {
                    int num = numContig(curr, visited, current);
                    if (num != 0) {
                        _blackRegionSizes.add(num);
                    }
                }
            }
        }
        Collections.sort(_whiteRegionSizes, Collections.reverseOrder());
        Collections.sort(_blackRegionSizes, Collections.reverseOrder());
        _subsetsInitialized = true;
    }

    /** Return the sizes of all the regions in the current union-find
     *  structure for side S. */
    List<Integer> getRegionSizes(Piece s) {
        computeRegions();
        if (s == WP) {
            return _whiteRegionSizes;
        } else {
            return _blackRegionSizes;
        }
    }

    /** The standard initial configuration for Lines of Action (bottom row
     *  first). */
    static final Piece[][] INITIAL_PIECES = {
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
    };

    /** Current contents of the board.  Square S is at _board[S.index()]. */
    private final Piece[] _board = new Piece[BOARD_SIZE  * BOARD_SIZE];

    /** List of all unretracted moves on this board, in order. */
    private final ArrayList<Move> _moves = new ArrayList<>();
    /** Current side on move. */
    private Piece _turn;
    /** Limit on number of moves before tie is declared.  */
    private int _moveLimit;
    /** True iff the value of _winner is known to be valid. */
    private boolean _winnerKnown;
    /** Cached value of the winner (BP, WP, EMP (for tie), or null (game still
     *  in progress).  Use only if _winnerKnown. */
    private Piece _winner;

    /** True iff subsets computation is up-to-date. */
    private boolean _subsetsInitialized;

    /** List of the sizes of continguous clusters of pieces, by color. */
    private final ArrayList<Integer>
        _whiteRegionSizes = new ArrayList<>(),
        _blackRegionSizes = new ArrayList<>();
}
