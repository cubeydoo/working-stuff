public class WeirdL extends WeirdList implements IntUnaryFunction {
    /**
     * A new WeirdList whose head is HEAD and tail is TAIL.
     *
     * @param head of the function
     * @param tail the rest of the function (should be null for all WeirdL
     */
    public WeirdL(int head, WeirdList tail) {
        super(0, null);
    }

    public int length() {
        return 0;
    }

    public String toString() {
        return "";
    }
    public WeirdList map(IntUnaryFunction func) {
        return WeirdList.EMPTY;
    }
    public int apply(int x) {
        return 0;
    }
}