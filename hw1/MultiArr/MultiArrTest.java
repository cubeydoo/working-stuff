import static org.junit.Assert.*;
import org.junit.Test;

public class MultiArrTest {

    @Test
    public void testMaxValue() {
        //TODO: Your code here!
        assertEquals(5, MultiArr.maxValue(new int[][]{{1,2,3}, {1,4,5}}));
        assertEquals(11, MultiArr.maxValue(new int[][]{{1,2,9, 4, 3, 2, 11}, {1,4,5}}));

    }
    @Test
    public void testRowCol() {
        MultiArr.printRowAndCol(new int[][]{{1,2,3}, {1,4,5}});
    }

    @Test
    public void testAllRowSums() {
        //TODO: Your code here!
        assertArrayEquals(new int[]{3, 4, 5}, MultiArr.allRowSums(new int[][]{{1, 2}, {1, 1, 1, 1}, {5}}));
        assertArrayEquals(new int[]{4, 3, 5}, MultiArr.allRowSums(new int[][]{{1, 3}, {1, 2}, {5}}));
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(MultiArrTest.class));
    }
}
