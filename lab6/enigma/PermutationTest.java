package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/**
 * The suite of all JUnit tests for the Permutation class. For the purposes of
 * this lab (in order to test) this is an abstract class, but in proj1, it will
 * be a concrete class. If you want to copy your tests for proj1, you can make
 * this class concrete by removing the 4 abstract keywords and implementing the
 * 3 abstract methods.
 *
 *  @author
 */
public abstract class PermutationTest {

    /**
     * For this lab, you must use this to get a new Permutation,
     * the equivalent to:
     * new Permutation(cycles, alphabet)
     * @return a Permutation with cycles as its cycles and alphabet as
     * its alphabet
     * @see Permutation for description of the Permutation conctructor
     */
    abstract Permutation getNewPermutation(String cycles, Alphabet alphabet);

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet(chars)
     * @return an Alphabet with chars as its characters
     * @see Alphabet for description of the Alphabet constructor
     */
    abstract Alphabet getNewAlphabet(String chars);

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet()
     * @return a default Alphabet with characters ABCD...Z
     * @see Alphabet for description of the Alphabet constructor
     */
    abstract Alphabet getNewAlphabet();

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /** Check that PERM has an ALPHABET whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha,
                           Permutation perm, Alphabet alpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.toInt(c), ei = alpha.toInt(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */

    @Test
    public void checkIdTransform() {
        Alphabet alpha = getNewAlphabet();
        Permutation perm = getNewPermutation("", alpha);
        checkPerm("identity", UPPER_STRING, UPPER_STRING, perm, alpha);
    }
    @Test
    public void testInvertChar() {
        Permutation p = getNewPermutation("(BACD)", getNewAlphabet("ABCD"));
        assertEquals('B', p.invert('A'));
        assertEquals('D', p.invert('B'));
        assertTrue(p.derangement());
        Permutation d = getNewPermutation("(BACD)(E)(PL)", getNewAlphabet("ABCDEPLG"));
        assertEquals('E', d.invert('E'));
        assertEquals('P', d.invert('L'));
        assertEquals('L', d.invert('P'));
        assertFalse(d.derangement());
        assertEquals(1, d.invert(0));
        assertEquals('G', d.invert('G'));
        assertEquals('B', d.invert(d.invert('C')));
        Permutation e = getNewPermutation("(ABC)", getNewAlphabet("ABC"));
        assertEquals(0, e.permute(2));
        assertEquals(0, e.permute(5));

    }
    @Test
    public void testPermuteChar() {
        Permutation p = getNewPermutation("(BACD)", getNewAlphabet("ABCD"));
        assertEquals('B', p.permute('D'));
        assertEquals('D', p.permute('C'));
        assertEquals('A', p.permute('B'));
        assertEquals('D', p.permute('C'));
        Permutation d = getNewPermutation("(BACD) (E) (PL)", getNewAlphabet("ABCDEPLG"));
        assertEquals('E', d.permute('E'));
        assertEquals('L', d.permute('P'));
        assertEquals('P', d.permute('L'));
        assertEquals(7, d.permute(15));
        assertEquals(4, d.permute(4));
        assertEquals('G', d.permute('G'));
        Permutation c = getNewPermutation("(ABCDEFGHI)(J)", getNewAlphabet("ABCDEFGHIJK"));
        assertEquals('A', c.permute(c.permute(c.permute('G'))));
        assertEquals('K', c.permute('K'));

    }
    @Test(expected = EnigmaException.class)
    public void testNotInAlphabet() {
        Permutation p = getNewPermutation("(BACD)", getNewAlphabet("ABCD"));
        p.invert('F');
    }
    @Test(expected = EnigmaException.class)
    public void testBadFormattedCycle() {
        Permutation p = getNewPermutation("()", getNewAlphabet("ABCD"));
        p.invert('F');
    }
    @Test
    public void checkPermTest() {
        Alphabet alpha = getNewAlphabet("ABCD");
        Permutation perm = getNewPermutation("(CD)(AB)", alpha);
        checkPerm("cycle", "ABCD", "BADC", perm, alpha);
    }
}
