/** HW #7, Two-sum problem.
 * @author
 */
public class Sum {

    /** Returns true iff A[i]+B[j] = M for some i and j. */
    public static boolean sumsTo(int[] A, int[] B, int m) {
        int[] fin = new int[A.length + B.length];
        merge(A, B, fin, A.length, B.length);
        int front = 0;
        int back = fin.length;

        while (front < back) {
            if (fin[front] + fin[back] == m) {
                return true;
            } else if (fin[front] + fin[back] < m) {
                front++;
            } else {
                back--;
            }
        }
        return false;
    }

    public static void merge(int[] first, int[] second, int[] fin, int f, int s) {
        int i = 0;
        int x = 0;
        int finIndex = 0;
        while (i < f && x < s) {
            System.out.println(first[i] + " ," + second[x]);
            if (first[i] <= second[x]) {
                fin[finIndex] = first[i];
                i++;
            } else {
                fin[finIndex] = second[x];
                x++;
            }
            finIndex++;
        }
        if (i < f) {
            while (i < f) {
                fin[finIndex] = first[i];
                finIndex++;
                i++;
            }
        } else if (x < s) {
            while (x < s) {
                fin[finIndex] = second[x];
                finIndex++;
                x++;
            }
        }
    }
}
