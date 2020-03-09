package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the Machine class.
 *  @author Tyler Rathkamp
 */
public class MachineTests {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Rotor rotor;
    private String alpha = UPPER_STRING;
    private String testPlugBoard = "(YF)(ZH)";
    private Alphabet basic = new Alphabet(UPPER_STRING);
    private Permutation plug1 = new Permutation(testPlugBoard, basic);
    private Permutation identity = new Permutation("", basic);


    public ArrayList<Rotor> _NAVAL = new ArrayList<Rotor>();
    public ArrayList<Rotor> makeNaval() {
        _NAVAL.add(new MovingRotor("I",
                new Permutation("(AELTPHQXRU) (BKNW) "
                        + "(CMOY) (DFG) (IV) (JZ) (S)", UPPER), "Q"));
        _NAVAL.add(new MovingRotor("II",
                new Permutation("(FIXVYOMW) (CDKLHUP) "
                        + "(ESZ) (BJ) (GR) (NT) (A) (Q)", UPPER), "E"));
        _NAVAL.add(new MovingRotor("III",
                new Permutation("(ABDHPEJT) (CFLVMZOYQIRWUKXSG) "
                        + "(N)", UPPER), "V"));
        _NAVAL.add(new MovingRotor("IV",
                new Permutation("(AEPLIYWCOXMRFZBSTGJQNH) "
                        + "(DV) (KU)", UPPER), "J"));
        _NAVAL.add(new MovingRotor("V",
                new Permutation("(AVOLDRWFIUQ)(BZKSMNHYC) "
                        + "(EGTJPX)", UPPER), "Z"));
        _NAVAL.add(new MovingRotor("VI",
                new Permutation("(AJQDVLEOZWIYTS) "
                        + "(CGMNHFUX) (BPRK) ", UPPER), "ZM"));
        _NAVAL.add(new MovingRotor("VII",
                new Permutation("(ANOUPFRIMBZTLWKSVEGCJYDHXQ) ",
                        UPPER), "ZM"));
        _NAVAL.add(new MovingRotor("VIII",
                new Permutation("(AFLSETWUNDHOZVICQ) (BKJ) "
                        + "(GXY) (MPR)", UPPER), "ZM"));
        _NAVAL.add(new FixedRotor("Beta",
                new Permutation("(ALBEVFCYODJWUGNMQTZSKPR) "
                        + "(HIX)", UPPER)));
        _NAVAL.add(new FixedRotor("Gamma",
                new Permutation("(AFNIRLBSQWVXGUZDKMTPCOYJHE)", UPPER)));
        _NAVAL.add(new Reflector("B", new Permutation(
                "(AE) (BN) (CK) (DQ) (FU) (GY) (HW) (IJ) (LO) "
                        + "(MP) (RX) (SZ) (TV)", UPPER)));
        _NAVAL.add(new Reflector("C", new Permutation(
                "(AR) (BD) (CO) (EJ) (FN) (GT) (HK) (IV) (LM) "
                        + "(PW) (QZ) (SX) (UY)", UPPER)));

        return _NAVAL;
    }


    /* ***** TESTS ***** */

    @Test
    public void machineTest1() {
        _NAVAL = makeNaval();
        Machine machine1 = new Machine(basic, 5, 3, _NAVAL);
        machine1.insertRotors(new ArrayList<String>(
                Arrays.asList(new String[]{"B", "Beta", "III", "IV", "I"})));
        machine1.setPlugboard(plug1);
        machine1.setRotors("AXLE");
        int x = machine1.convert(24);
        assertEquals(x, 25);
    }
    @Test
    public void machineTest2() {
        _NAVAL = makeNaval();
        Machine machine1 = new Machine(basic, 5, 3, _NAVAL);
        machine1.insertRotors(new ArrayList<String>(
                Arrays.asList(new String[]{"B", "Beta", "I", "II", "III"})));
        machine1.setPlugboard(identity);
        machine1.setRotors("AAAA");
        String check = machine1.convert("HELLO WORLD");
        assertTrue(check.equals("ILBDA AMTAZ"));
    }
    @Test
    public void machineTest3() {
        _NAVAL = makeNaval();
        Machine machine1 = new Machine(basic, 5, 3, _NAVAL);
        machine1.insertRotors(new ArrayList<String>(
                Arrays.asList(new String[]{"B", "Beta", "III", "II", "I"})));
        machine1.setPlugboard(identity);
        machine1.setRotors("AAAZ");
        String check = machine1.convert(
                "AAAAA AAAAA AAAAA AAAAA AAAAA AAA");
        System.out.println(check);
        assertTrue(check.equals(
                "NFTZM GISXI PJWGD NJJCO QTYRI GDM"));
    }
    @Test
    public void machineTest4() {
        _NAVAL = makeNaval();
        Machine machine1 = new Machine(basic, 5, 3, _NAVAL);
        machine1.insertRotors(new ArrayList<String>(
                Arrays.asList(new String[]{"B", "Beta", "III", "I", "II"})));
        machine1.setPlugboard(identity);
        machine1.setRotors("AAAZ");
        String check = machine1.convert(
                "AAAAA AAAAA AAAAA AAAAA AAAAA AAA");
        System.out.println(check);
        assertTrue(check.equals(
                "JCNBE GNHEJ ZLEKN SCFKB KNQHS PBK"));
    }

    @Test
    public void machineTest5() {
        _NAVAL = makeNaval();
        Machine machine1 = new Machine(basic, 5, 3, _NAVAL);
        machine1.insertRotors(new ArrayList<String>(
                Arrays.asList(new String[]{"B", "Beta", "III", "I", "II"})));
        machine1.setPlugboard(identity);
        machine1.setRotors("AAAZ");
        String check = machine1.convert("EJ");
        System.out.println(check);
        assertTrue(check.equals("CD"));
    }
    @Test
    public void machineTest6() {
        _NAVAL = makeNaval();
        Machine machine1 = new Machine(basic, 5, 3, _NAVAL);
        machine1.insertRotors(new ArrayList<String>(
                Arrays.asList(new String[]{"B", "Beta", "III", "II", "I"})));
        machine1.setPlugboard(identity);
        machine1.setRotors("AAAZ");
        String check = machine1.convert(
                "NFTZM GISXI PJWGD NJJCO QTYRI GDM");
        System.out.println(check);
        assertTrue(check.equals(
                "AAAAA AAAAA AAAAA AAAAA AAAAA AAA"));
    }

    @Test
    public void machineTest7() {
        _NAVAL = makeNaval();
        Machine machine1 = new Machine(basic, 5, 3, _NAVAL);
        machine1.insertRotors(new ArrayList<String>(
                Arrays.asList(new String[]{"B", "Beta", "III", "II", "I"})));
        machine1.setPlugboard(identity);
        machine1.setRotors("AAAP");
        String check = machine1.convert("J");
        System.out.println(check);
        assertTrue(check.equals("A"));
    }


}
