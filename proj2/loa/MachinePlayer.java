/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import java.util.ArrayList;
import java.util.Random;

import static loa.Piece.*;
import static loa.Square.BOARD_SIZE;
import static loa.Square.sq;

/** An automated Player.
 *  @author
 */
class MachinePlayer extends Player {

    /** A position-score magnitude indicating a win (for white if positive,
     *  black if negative). */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 20;
    /** A magnitude greater than a normal value. */
    private static final int INFTY = Integer.MAX_VALUE;

    /** A new MachinePlayer with no piece or controller (intended to produce
     *  a template). */
    MachinePlayer() {
        this(null, null);
    }

    /** A MachinePlayer that plays the SIDE pieces in GAME. */
    MachinePlayer(Piece side, Game game) {
        super(side, game);
    }

    @Override
    String getMove() {
        Move choice;

        assert side() == getGame().getBoard().turn();
        int depth;
        choice = searchForMove();
        getGame().reportMove(choice);
        return choice.toString();
    }

    @Override
    Player create(Piece piece, Game game) {
        return new MachinePlayer(piece, game);
    }

    @Override
    boolean isManual() {
        return false;
    }

    /** Return a move after searching the game tree to DEPTH>0 moves
     *  from the current position. Assumes the game is not over. */
    private Move searchForMove() {
        Board work = new Board(getBoard());
        int value;
        assert side() == work.turn();
        _foundMove = null;
        if (side() == WP) {
            value = findMove(work, chooseDepth(), true, 1, -INFTY, INFTY);
        } else {
            value = findMove(work, chooseDepth(), true, -1, -INFTY, INFTY);
        }
        return _foundMove;
    }

    /** Find a move from position BOARD and return its value, recording
     *  the move found in _foundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _foundMove. If the game is over
     *  on BOARD, does not set _foundMove. */
    private int findMove(Board board, int depth, boolean saveMove,
                         int sense, int alpha, int beta) {
        if (depth == 0) {
            return moveScore(board);
        }
        int _bestScore = 0;
        for (Move move : board.legalMoves()) {
            board.makeMove(move);
            int score = findMove(board, depth - 1, false, sense * -1, alpha, beta);
            board.retract();
            if (_bestScore <= score) {
                _bestScore = score;
                if (saveMove) {
                    _foundMove = move;
                }
            }
            if (sense == 1) {
                alpha = Math.max(score, alpha);
            } else {
                beta = Math.min(score, beta);
            }
            if (alpha > beta) {
                break;
            }
        }
        return _bestScore;
    }

    /** Return a search depth for the current position. */
    private int chooseDepth() {
        return 2;
    }


    private int moveScore(Board board) {
        Random r = new Random();
        if (board.piecesContiguous(board.turn())) {
            return INFTY;
        }
        int rowTotal, colTotal, totalPieces, total;
        rowTotal = colTotal = totalPieces = total = 0;
        ArrayList<Square> squares = new ArrayList<Square>();
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Square curr = sq(y, x);
                Piece current = board.get(curr);
                if (current == board.turn()) {
                    rowTotal += curr.row();
                    colTotal += curr.col();
                    totalPieces += 1;
                    squares.add(curr);
                }
            }
        }
        Square center = sq(colTotal/totalPieces, rowTotal/totalPieces);
        for (Square square : squares) {
            total += center.distance(square);
        }
        int minSum;
        if (squares.size() <= 9) {
            minSum = squares.size() - 1;
        } else {
            minSum = 8 + ((squares.size() - 9) * 2);
        }
        total -= minSum;
        double surplus = total;
        surplus = 1/surplus;
        surplus = surplus * 1000;
        int random = r.nextInt(20);
        surplus += random;
        return (int) surplus;
    }

    /** Used to convey moves discovered by findMove. */
    private Move _foundMove;

}
