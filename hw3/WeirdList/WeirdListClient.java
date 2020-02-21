/** Functions to increment and sum the elements of a WeirdList. */
class WeirdListClient {
private static int val;
    /** Return the result of adding N to each element of L. */
    static WeirdList add(WeirdList L, int n) {
        val = n;
        helper newbie = new helper();
        return L.map(newbie);
    }

    /** Return the sum of all the elements in L. */
    static int sum(WeirdList L) {
        helper2 clever = new helper2();
        L.map(clever);
        return clever.total;
    }
public static class helper implements IntUnaryFunction {
        public int apply(int x) {
            return x + val;
        }
}

public static class helper2 implements IntUnaryFunction {
        private int total = 0;
        public int apply(int x) {
            total += x;
            return x;
        }
    }
    /* IMPORTANT: YOU ARE NOT ALLOWED TO USE RECURSION IN ADD AND SUM
     *
     * As with WeirdList, you'll need to add an additional class or
     * perhaps more for WeirdListClient to work. Again, you may put
     * those classes either inside WeirdListClient as private static
     * classes, or in their own separate files.

     * You are still forbidden to use any of the following:
     *       if, switch, while, for, do, try, or the ?: operator.
     *
     * HINT: Try checking out the IntUnaryFunction interface.
     *       Can we use it somehow?
     */
}
