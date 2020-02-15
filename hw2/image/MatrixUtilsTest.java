package image;

import org.junit.Test;
import static org.junit.Assert.*;

/** Test MatrixUtils to be working as planned.
 *  @author Tyler Rathkamp
 */
public class MatrixUtilsTest {
    @Test
    public void testAccumulate() {

        double[][] M = {{1000000, 1000000, 1000000, 1000000},
                {1000000, 75990, 30003, 1000000},
                {1000000, 30002, 103046, 1000000},
                {1000000, 29515, 38273, 1000000},
                {1000000, 73403, 35399, 1000000},
                {1000000, 1000000, 1000000, 1000000}};
        double[][] A = {{1000000, 1000000, 1000000, 1000000},
                {2000000, 1075990, 1030003, 2000000},
                {2075990, 1060005, 1133049, 2030003},
                {2060005, 1089520, 1098278, 2133049},
                {2089520, 1162923, 1124919, 2098278},
                {2162923, 2124919, 2124919, 2124919}};
        assertEquals(MatrixUtils.accumulateVertical(M), A);
    }
    @Test
    public void testAccumulateOrientation() {

        double[][] A = {{1, 3, 6, 9},
                        {1, 3, 5, 8},
                        {4, 2, 4, 7}};
        double[][] M = {{1, 2, 3, 4},
                        {1, 2, 3, 4},
                        {4, 1, 2, 3}};
        assertEquals(MatrixUtils.accumulate(M, MatrixUtils.Orientation.HORIZONTAL), A);
    }
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(MatrixUtilsTest.class));
    }
}
