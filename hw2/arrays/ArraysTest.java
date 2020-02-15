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
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] a1 = Arrays.remove(a, 3, 2);
        int[] answer1 = {1, 2, 3, 6, 7, 8};
        int[] answer2 = {1};
        int[] a2 = Arrays.remove(a, 1, 7);
        for (int i = 0; i <= 5; i++) {
            assertEquals(answer1[i], a1[i]);
        }
        assertEquals(answer2[0], a2[0]);

    }

    @Test
    public void testnaturalRuns() {
        int[] A =  {1, 3, 7, 5, 4, 6, 9, 10};
        int[][] answer = {{1, 3, 7}, {5}, {4, 6, 9, 10}};
        int[][] answer2 = Arrays.naturalRuns(A);
        for (int i = 0; i <= 2; i++) {
            for (int x = 0; x <= answer[i].length - 1; x++) {
                assertEquals(answer[i][x], answer2[i][x]);
            }
        }
        int[] nullpoint = {};
        Arrays.naturalRuns(nullpoint);
    }
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
