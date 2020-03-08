/** A WeirdList holds a sequence of integers.
 * @author Tyler Rathkamp
 */
public class WeirdList  {
    /** head of the List, integer.*/
    private int _head;
    /** rest of the list. */
    private WeirdList _tail;
    /** The empty sequence of integers. */
    public static final WeirdList EMPTY = new WeirdL(0, null);
    /** A new WeirdList whose head is HEAD and tail is TAIL. */
    public WeirdList(int head, WeirdList tail) {
        _head = head;
        _tail = tail;
    }
    /** Returns the number of elements in the sequence that
     *  starts with THIS. */
    public int length() {
        return 1 + _tail.length();
    }
    /** Return a string containing my contents as a sequence of numerals
     *  each preceded by a blank.  Thus, if my list contains
     *  5, 4, and 2, this returns " 5 4 2". */
    @Override
    public String toString() {
        return " " + String.valueOf(_head) + _tail.toString();
    }

    /** Part 3b: Apply FUNC.apply to every element of THIS WeirdList in
     *  sequence, and return a WeirdList of the resulting values. */
    public WeirdList map(IntUnaryFunction func) {
        return new WeirdList(func.apply(_head), _tail.map(func));
    }
}

