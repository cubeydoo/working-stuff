package arrays;

import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *  @author Tyler Rathkamp
 */

public class ArraysTest {
    /** Tests to make sure Arrays.java are workin
     */
    @Test
    public void testCatenate() {
        int[] a = {1};
        int[] a1 = {1, 2, 3};
        int[] b2 = {4, 5, 6};
        int[] answer = {1, 2, 3, 4, 5, 6};
        int[] answer2 = Arrays.catenate(a1, b2);
        for (int i = 0; i <= answer2.length - 1; i++) {
            assertEquals(answer[i], answer2[i]);
        }
        int[] answerme = {1, 1, 2, 3};
        int[] answeryou = Arrays.catenate(a, a1);
        for (int i = 0; i <= 3; i++) {
            assertEquals(answerme[i], answeryou[i]);
        }
    }

    @Test
    public void testRemove() {
        int a[] = {1, 2, 3, 4, 5, 6, 7, 8};
        int a1[] = Arrays.remove(a, 3, 2);
        int answer1[] = {1, 2, 3, 6, 7, 8};
        for (int i = 0; i <= 5; i++) {
            assertEquals(answer1[i], a1[i]);
        }

        }
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
