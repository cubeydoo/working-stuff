package lists;

import org.junit.Test;
import static org.junit.Assert.*;

/** Tests for the code written in List.java
 *
 *  @author Tyler Rathkamp
 */

public class ListsTest {
    /** FIXME
     */
    @Test
    public void testNaturalRuns() {
        int[] ints = new int[]{1, 2};
        int[] int2 = new int[]{1, 2, 1};
        int[] p1 = new int[]{1, 2};
        int[] p2 = new int[]{1};
        IntList test2 = IntList.list(int2);

        IntList x = IntList.list(ints);
        IntList piece1 = IntList.list(p1);
        IntList piece2 = IntList.list(p2);
        IntListList y = IntListList.list(x);
        IntListList z = IntListList.list(piece1, piece2);
        assertTrue(y.equals(Lists.naturalRuns(x)));
        IntListList test1 = Lists.naturalRuns(test2);
        assertEquals(z, test1);
    }


    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}
