package enigma;
/** An alphabet of encodable characters.  Provides a mapping from characters
 *  to and from indices into the alphabet.
 *  @author Tyler Rathkamp
 */
class Alphabet {
    /** a string of the alphabet's characters*/
    public String _alphabetString;

    /** A new alphabet containing CHARS.  Character number #k has index
     *  K (numbering from 0). No character may be duplicated. */
    Alphabet(String chars) {
        String current = new String("");
        for (int i = 0; i < chars.length(); i++) {
            if (current.indexOf(chars.charAt(i)) == -1) {
                current = current + chars.charAt(i);
            } else {
                System.out.println("Duplicate characters in alphabet");
                throw new EnigmaException("Duplicate characters in alphabet");
            }
        }
        _alphabetString = current;
    }

    /** A default alphabet of all upper-case characters. */
    Alphabet() {
        this("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    /** Returns the size of the alphabet. */
    int size() {
        return _alphabetString.length();
    }

    /** Returns true if CH is in this alphabet. */
    boolean contains(char ch) {
        return _alphabetString.indexOf(ch) != -1;
    }

    /** Returns character number INDEX in the alphabet, where
     *  0 <= INDEX < size(). */
    char toChar(int index) {
        return _alphabetString.charAt(index);
    }

    /** Returns the index of character CH which must be in
     *  the alphabet. This is the inverse of toChar(). */
    int toInt(char ch) {
        return _alphabetString.indexOf(ch);
    }

}
