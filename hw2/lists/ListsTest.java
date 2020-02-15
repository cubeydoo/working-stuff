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
    public void testNaturalRuns(){
        IntList test1 = new IntList(1, 2, 3, 2);
        IntList piece1 = new IntList(1, 2, 3);
        IntList piece2 = new IntList(2);
        IntListList listtest = new IntListList(piece1, piece2);

    }

    // It might initially seem daunting to try to set up
    // IntListList expected.
    //
    // There is an easy way to get the IntListList that you want in just
    // few lines of code! Make note of the IntListList.list method that
    // takes as input a 2D array.

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}
