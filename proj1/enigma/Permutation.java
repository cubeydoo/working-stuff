package enigma;
import java.util.ArrayList;


/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Tyler Rathkamp
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        cycles.replaceAll(" ", "");
        _permKey = new String[alphabet.size()];
        _invertKey = new String [alphabet.size()];
        for (int i = 0; i < cycles.length(); i++) {
            String current = new String(String.valueOf(cycles.charAt(i)));
            String next;
            String first;
            String previous;
            if (current.equals("(")) {
                i++;
                first = new String(String.valueOf(cycles.charAt(i)));
                current = new String(String.valueOf(cycles.charAt(i)));
                int ogindex = alphabet._alphabetString.indexOf(current);
                next = new String(String.valueOf(cycles.charAt(i + 1)));
                previous = new String(String.valueOf(cycles.charAt(i - 1)));
                while (!current.equals(")")) {
                    next = new String(String.valueOf(cycles.charAt(i + 1)));
                    int index = alphabet._alphabetString.indexOf(current);
                    if (next.equals(")")) {
                        _permKey[index] = first;
                        _invertKey[index] = previous;
                    } else {
                        _permKey[index] = next;
                        _invertKey[index] = previous;
                    }

                    i++;
                    current = new String(String.valueOf(cycles.charAt(i)));
                    previous = new String(String.valueOf(cycles.charAt(i - 1)));
                }
                _invertKey[ogindex] = previous;
            }
        }
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        cycle.replaceAll(" ", "");
        for (int i = 0; i < cycle.length(); i++) {
            String current = new String(String.valueOf(cycle.charAt(i)));
            if (current.equals("(")) {
                i++;
                current = new String(String.valueOf(cycle.charAt(i)));
                while (!current.equals(")")) {
                    int index = _alphabet._alphabetString.indexOf(current);
                    if (index == 0) {
                        _permKey[_permKey.length - 1] = current;
                        _invertKey[index + 1] = current;
                    } else if (index == _alphabet.size() - 1) {
                        _permKey[index - 1] = current;
                        _invertKey[0] = current;
                    }
                    else {
                        _permKey[index - 1] = current;
                        _invertKey[index + 1] = current;
                    }
                    i++;
                    current = new String(String.valueOf(cycle.charAt(i)));
                }
            }
        }
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        return 0;  // FIXME
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        return 0;  // FIXME
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        int index = _alphabet._alphabetString.indexOf(p);
        return _permKey[index].charAt(0);
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        int index = _alphabet._alphabetString.indexOf(c);
        return _invertKey[index].charAt(0);
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        for (int i = 0; i < _permKey.length; i++) {
            if (_permKey[i] == null) {
                return false;
            }
        }
        return true;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;
    private String[] _permKey;
    private String[] _invertKey;

}
