package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author Tyler Rathkamp
 */
class MovingRotor extends Rotor {

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
    }

    // FIXME?

    @Override
    void advance() {
        _shift = _shift + 1;
        if (_shift % size() == 0) {
            _shift = 0;
        }
    }

    // FIXME: ADDITIONAL FIELDS HERE, AS NEEDED
}
