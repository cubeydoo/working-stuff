/** A WeirdL overrides a weirdList as the empty List.
 * @author Tyler Rathkamp
 */
public class WeirdL extends WeirdList implements IntUnaryFunction {
    /**
     * A new WeirdList whose head is HEAD and tail is TAIL.
     *
     * @param head of the function.
     * @param tail the rest of the function (should be null for all WeirdL.
     */
    public WeirdL(int head, WeirdList tail) {
        super(0, null);
    }

    /** override length of a null object to be 0.
     * @return 0*/
    public int length() {
        return 0;
    }
/** override toString of a null object to be the empty string.
 * @return the empty string */
    public String toString() {
        return "";
    }
    /** override map of a null object to be the empty WeirdList.
     * @param func Function passed in.
     * @return  the empty WeirdList*/
    public WeirdList map(IntUnaryFunction func) {
        return WeirdList.EMPTY;
    }
    /** override sum of a null object to be 0.
     * @param x number passed in from a weirdList, normally the head.
     * @return  0*/
    public int apply(int x) {
        return 0;
    }
}
