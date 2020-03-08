package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a reflector in the enigma.
 *  @author
 */
class Reflector extends FixedRotor {
    /** A non-moving rotor named NAME whose permutation at the 0 setting
     * is PERM. */
    Reflector(String name, Permutation perm) {
        super(name, perm);
        if (!permutation().derangement()) {
            throw new EnigmaException("Reflectors must be dearanged.");
        }
    }

    // FIXME?

    @Override
    boolean reflecting() {
        return true;
    }

    @Override
    void set(int posn) {
        if (posn != 0) {
            throw error("reflector has only one position");
        }
    }
    @Override
    void set(char cposn) {
        if (permutation().alphabet().aString().indexOf(cposn) != 0) {
            throw error("reflector has only one position");
        }
    }

}
