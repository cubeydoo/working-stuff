/** Functions to increment and sum the elements of a WeirdList.
 * @author Tyler Rathkamp
 */
class WeirdListClient {
    /** stores the value passed in to add for all numbers. */
    private static int val;
    /** Return the result of adding N to each element of L. */
    static WeirdList add(WeirdList L, int n) {
        val = n;
        Helper newbie = new Helper();
        return L.map(newbie);
    }

    /** Return the sum of all the elements in L. */
    static int sum(WeirdList L) {
        Helper2 clever = new Helper2();
        L.map(clever);
        return clever.total;
    }
    /** helper1 allows us to add a number to each _head. */
    public static class Helper implements IntUnaryFunction {
        /** Changes each value in a list by val.
         * @param x _head.
         * @return _head + val.*/
        public int apply(int x) {
            return x + val;
        }
    }
    /** helper2 allows us to sum all numbers in a WeirdList. */
    public static class Helper2 implements IntUnaryFunction {
        /** sum of the values of the list. */
        private int total = 0;
        /** adds all the values of the list.
         * @param x val at an index.
         * @return pointless, doesn't change anything*/
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
