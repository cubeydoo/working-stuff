import static org.junit.Assert.*;
import org.junit.Test;

public class CompoundInterestTest {

    @Test
    public void testNumYears() {
        /** Sample assert statement for comparing integers.

        assertEquals(0, 0); */
        assertEquals(30, CompoundInterest.numYears(2050));
        assertEquals(5, CompoundInterest.numYears(2025));
    }

    @Test
    public void testFutureValue() {
        double tolerance = 0.01;
        assertEquals(12.544, CompoundInterest.futureValue(10, 12, 2022), tolerance);
        assertEquals(10, CompoundInterest.futureValue(10, 12, 2020), tolerance);
        assertEquals(112, CompoundInterest.futureValue(100, 12, 2021), tolerance);

    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;
    }


    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        //System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
