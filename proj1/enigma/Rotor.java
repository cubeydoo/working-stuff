package enigma;

import static enigma.EnigmaException.*;

/** Superclass that represents a rotor in the enigma machine.
 *  @author Tyler Rathkamp
 */
class Rotor {

    /** A rotor named NAME whose permutation is given by PERM. */
    Rotor(String name, Permutation perm) {
        _name = name;
        _permutation = perm;
        _shift = 0;
    }

    /** Return my name. */
    String name() {
        return _name;
    }

    /** Return my alphabet. */
    Alphabet alphabet() {
        return _permutation.alphabet();
    }

    /** Return my permutation. */
    Permutation permutation() {
        return _permutation;
    }

    /** Return the size of my alphabet. */
    int size() {
        return _permutation.size();
    }

    /** Return true iff I have a ratchet and can move. */
    boolean rotates() {
        return false;
    }

    /** Return true iff I reflect. */
    boolean reflecting() {
        return false;
    }

    /** Return my current setting. */
    int setting() {
        return _shift;
    }

    /** Set setting() to POSN.  */
    void set(int posn) {
        _shift = posn;
    }

    /** Set setting() to character CPOSN. */
    void set(char cposn) {
        _shift = _permutation.alphabet().aString().indexOf(cposn);
    }

    /** Set Ring settings to character CPOSN. */
    void setRing(char cposn) {
        _ringSetting = _permutation.alphabet().aString().indexOf(cposn);
    }

    /** Return the conversion of P (an integer in the range 0..size()-1)
     *  according to my permutation. */
    int convertForward(int p) {
        p = _permutation.permute((p + (wrap(setting() - _ringSetting))));
        if (p >= setting()) {
            return p - wrap(setting() - _ringSetting);
        } else {
            return (p + size()) - wrap(setting() - _ringSetting);
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
    /** Return the conversion of E (an integer in the range 0..size()-1)
     *  according to the inverse of my permutation. */
    int convertBackward(int e) {
        e = _permutation.invert((e + wrap(setting() - _ringSetting)));
        int rv = e - wrap(setting() - _ringSetting);
        if (rv < 0) {
            return rv + size();
        } else {
            return rv;
        }
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        return false;
    }

    /** Advance me one position, if possible. By default, does nothing. */
    void advance() {
    }

    @Override
    public String toString() {
        return "Rotor " + _name;
    }

    /** My name. */
    private final String _name;

    /** The permutation implemented by this rotor in its 0 position. */
    private Permutation _permutation;

    /** The current shift in position. */
    protected int _shift;
    /** Setting of the ring for this rotor. */
    private int _ringSetting = 0;
}
