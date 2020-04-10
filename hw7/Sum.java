import java.util.ArrayList;
import java.util.Collections;

/** HW #7, Two-sum problem.
 * @author Tyler Rathkamp
 */
public class Sum {

    /**
     * Returns true iff A[i]+B[j] = M for some i and j.
     */
    public static boolean sumsTo(int[] A, int[] B, int m) {
        ArrayList<Integer> me = new ArrayList<Integer>();
        for (int i = 0; i < A.length; i++) {
            me.add(A[i]);
        }
        for (int i = 0; i < B.length; i++) {
            me.add(B[i]);
        }
        Collections.sort(me);
        int front = 0;
        int back = me.size() - 1;

        while (front < back) {

            if (me.get(front) + me.get(back) == m) {
                return true;
            } else if (me.get(front) + me.get(back) < m) {
                front++;
            } else {
                back--;
            }
        }
        return false;
    }
}
