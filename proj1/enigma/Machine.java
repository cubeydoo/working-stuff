package enigma;

import java.util.ArrayList;
import java.util.Collection;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author Tyler Rathkamp
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        if (numRotors <= 1 || 0 > pawls || pawls > numRotors) {
            throw new EnigmaException("Bad arguments for a machine");
        }
        _alphabet = alpha;
        _numRotors = numRotors;
        _pawls = pawls;
        _allRotors = (ArrayList<Rotor>) allRotors;
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }
    /** Return my alphabet. */
    Alphabet alphabetGet() {
        return _alphabet;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _pawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(ArrayList<String> rotors) {
        _rotors.clear();
        for (int i = 0; i < rotors.size(); i++) {
            String name = rotors.get(i);
            for (int x = 0; x < _allRotors.size(); x++) {
                Rotor current = _allRotors.get(x);
                if (name.equals(current.name()) && !_rotors.contains(current)) {
                    _rotors.add(current);
                    break;
                } else if (x == _allRotors.size() - 1) {
                    throw new EnigmaException("Rotor name not in library.");
                }
            }
        }
        if (!_rotors.get(0).reflecting()) {
            throw new EnigmaException("Leftmost Rotor must be a reflector.");
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        if (setting.length() != numRotors() - 1) {
            throw new EnigmaException("Setting must include each rotor.");
        } else {
            int x = 0;
            for (int i = 0; i < numRotors(); i++) {
                if (!_rotors.get(i).reflecting()) {
                    _rotors.get(i).set(setting.charAt(x));
                    x++;
                }
            }
        }
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        for (int i = 0; i < plugboard.size(); i++) {
            if (plugboard.permute(plugboard.permute(i)) != i) {
                throw new EnigmaException(
                        "Plug board be a derangement.");
            }
        }
        _permutation = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    int convert(int c) {
        c = _permutation.permute(c);
        if (_pawls > 0) {
            int current = _rotors.size() - 1;
            Rotor p = _rotors.get(current);
            if (p.atNotch()) {
                while (p.atNotch() && current >= 0) {
                    p.advance();
                    current -= 1;
                    p = _rotors.get(current);
                }
            }
            p.advance();
        }
        int counter = _rotors.size() - 1;
        while (counter >= 0) {
            Rotor pointer = _rotors.get(counter);
            c = pointer.convertForward(c);
            counter -= 1;
        }
        counter = 1;
        while (counter <= _rotors.size() - 1) {
            Rotor pointer = _rotors.get(counter);
            c = pointer.convertBackward(c);
            counter += 1;
        }
        return _permutation.permute(c);
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        String returnme = "";
        for (int i = 0; i <=  msg.length() - 1; i++) {
            char c = msg.charAt(i);
            if (Character.isWhitespace(c)) {
                returnme += " ";
            } else {
                int x = _alphabet.aString().indexOf(c);
                x = convert(x);
                c = _alphabet.aString().charAt(x);
                returnme += c;
            }
        }
        return returnme;
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;
    /** Number of my rotors. */
    private int _numRotors;
    /**Moving rotor number. */
    private int _pawls;
    /** Plug board for this machine. */
    private Permutation _permutation = new Permutation("", new Alphabet());
    /** All rotors available. */
    private ArrayList<Rotor> _allRotors;
    /** Rotors in my machine. */
    private ArrayList<Rotor> _rotors = new ArrayList<>();
}
