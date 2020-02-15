package arrays;

/* NOTE: The file Arrays/Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2 */

/** Array utilities.
 *  @author Tyler Rathkamp
 */
class Arrays {

    /* C1. */
    /** Returns a new array consisting of the elements of A followed by the
     *  the elements of B. */
    static int[] catenate(int[] A, int[] B) {
        if (A == null && B == null) {
            return null;
        } else if (A == null) {
            return B;
        } else if (B == null) {
            return A;
        }
        int[] array = new int[A.length + B.length];
        for (int i = 0; i <= A.length - 1; i++) {
            array[i] = A[i];
        }
        for (int i = A.length; i <= array.length - 1; i++) {
            array[i] = B[i - A.length];
        }
        return array;
    }

    /* C2. */
    /** Returns the array formed by removing LEN items from A,
     *  beginning with item #START. */
    static int[] remove(int[] A, int start, int len) {
        int[] array = new int[A.length - len];
        for (int i = 0; i < start; i++) {
            array[i] = A[i];
        }
        for (int i = (start + len); i <= A.length - 1; i++) {
            array[i - len] = A[i];
        }
        return array;
    }

    /* C3. */
    /** Returns the array of arrays formed by breaking up A into
     *  maximal ascending lists, without reordering.
     *  For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     *  returns the three-element array
     *  {{1, 3, 7}, {5}, {4, 6, 9, 10}}. */
    static int[][] naturalRuns(int[] A) {
        /* *Replace this body with the solution. */
        int size = 1;
        int length = 0;
        int index = 0;
        for (int i = 1; i <= A.length - 1; i++) {
            if (A[i] < A[i - 1]) {
                size++;
            }
        }
        int[][] array = new int[size][1];
        int[] curr = {A[0]};
        for (int i = 1; i <= A.length - 1; i++) {
            if (A[i] < A[i - 1]) {
                array[index] = curr;
                curr = null;
                index++;
            }
            int[] currindex = {A[i]};
            curr = catenate(curr, currindex);
        }
        array[index] = curr;
        return array;
    }
}
