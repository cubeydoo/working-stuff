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
        assertEquals(45.13, CompoundInterest.futureValue(50, -5, 2022), tolerance);

    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;
        assertEquals(11.8026496, CompoundInterest.futureValueReal(10, 12, 2022, 3), tolerance);
        assertEquals(100, CompoundInterest.futureValueReal(100, 12, 2020, 3), tolerance);
        assertEquals(116.4, CompoundInterest.futureValueReal(100, 20, 2021, 3), tolerance);
    }


    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
        assertEquals(16550, CompoundInterest.totalSavings(5000, 2022, 10), tolerance);
        assertEquals(16550, CompoundInterest.totalSavings(5000, 2022, 10), tolerance);

    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
        assertEquals(15571.9, CompoundInterest.totalSavingsReal(5000, 2022, 10, 3), tolerance);
        assertEquals(5000, CompoundInterest.totalSavingsReal(5000, 2020, 10, 3), tolerance);


    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        //System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
