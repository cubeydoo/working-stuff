/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import ucb.junit.textui;
import org.junit.Test;

import static loa.Piece.*;
import static org.junit.Assert.*;

/** The suite of all JUnit tests for the loa package.
 *  @author Tyler Rathkamp
 */
public class UnitTests {

    /** Run the JUnit tests in the loa package. */
    public static void main(String[] ignored) {
        textui.runClasses(UnitTests.class);
        textui.runClasses(BoardTest.class);
    }

    static final Piece[][] BOARD1 = {
            { EMP, BP,  EMP,  BP,  BP, EMP, EMP, EMP },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
            { WP,  EMP, EMP, EMP,  BP,  BP, EMP, WP  },
            { WP,  EMP,  BP, EMP, EMP,  WP, EMP, EMP  },
            { WP,  EMP,  WP,  WP, EMP,  WP, EMP, EMP  },
            { WP,  EMP, EMP, EMP,  BP, EMP, EMP, WP  },
            { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP  },
            { EMP, BP,  BP,  BP,  EMP,  BP,  BP, EMP }
    };

    /** A dummy test to avoid complaint. */
    @Test
    public void Test() {
        Board b1 = new Board(BOARD1, BP);
        assertFalse(b1.piecesContiguous(BP));
        assertFalse(b1.piecesContiguous(WP));
        assertTrue(b1.lineNum(Square.sq(0,0), 0) == 6);
    }

    @Test
    public void testCopy() {
        Board b1 = new Board(BOARD1, BP);
        Board b2 = new Board(b1);
        assertEquals(b1, b2);
    }

}


