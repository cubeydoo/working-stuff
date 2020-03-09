package enigma;

import net.sf.saxon.lib.SaxonOutputKeys;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the Permutation class.
 *  @author
 */
public class MachineTests {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Rotor rotor;
    private String alpha = UPPER_STRING;
    private String testPlugBoard = "(YF)(ZH)";
    private Alphabet alphaa = new Alphabet(UPPER_STRING);
    private Permutation Plugboard1 = new Permutation(testPlugBoard, alphaa);
    private Permutation identity = new Permutation("", alphaa);


    public ArrayList<Rotor> NAVAL = new ArrayList<Rotor>();
    public ArrayList<Rotor> makeNaval() {
        NAVAL.add(new MovingRotor("I", new Permutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", UPPER), "Q"));
        NAVAL.add(new MovingRotor("II", new Permutation("(FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)", UPPER), "E"));
        NAVAL.add(new MovingRotor("III",new Permutation( "(ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)", UPPER), "V"));
        NAVAL.add(new MovingRotor("IV", new Permutation("(AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)", UPPER), "J"));
        NAVAL.add(new MovingRotor("V", new Permutation("(AVOLDRWFIUQ)(BZKSMNHYC) (EGTJPX)", UPPER), "Z"));
        NAVAL.add(new MovingRotor("VI", new Permutation("(AJQDVLEOZWIYTS) (CGMNHFUX) (BPRK) ", UPPER), "ZM"));
        NAVAL.add(new MovingRotor("VII", new Permutation("(ANOUPFRIMBZTLWKSVEGCJYDHXQ) ", UPPER), "ZM"));
        NAVAL.add(new MovingRotor("VIII",new Permutation( "(AFLSETWUNDHOZVICQ) (BKJ) (GXY) (MPR)", UPPER), "ZM"));
        NAVAL.add(new FixedRotor("Beta", new Permutation( "(ALBEVFCYODJWUGNMQTZSKPR) (HIX)", UPPER)));
        NAVAL.add(new FixedRotor("Gamma", new Permutation( "(AFNIRLBSQWVXGUZDKMTPCOYJHE)", UPPER)));
        NAVAL.add(new Reflector("B", new Permutation(
                "(AE) (BN) (CK) (DQ) (FU) (GY) (HW) (IJ) (LO) "
                        + "(MP) (RX) (SZ) (TV)", UPPER)));
        NAVAL.add(new Reflector("C", new Permutation(
                "(AR) (BD) (CO) (EJ) (FN) (GT) (HK) (IV) (LM) "
                        + "(PW) (QZ) (SX) (UY)", UPPER)));

        return NAVAL;
    }


    /* ***** TESTS ***** */

    @Test
    public void MachineTest1() {
        NAVAL = makeNaval();
        Machine machine1 = new Machine(alphaa, 5, 3, NAVAL);
        machine1.insertRotors(new ArrayList<String>(Arrays.asList(new String[]{"B", "Beta", "III", "IV", "I"})));
        machine1.setPlugboard(Plugboard1);
        machine1.setRotors("AXLE");
        int x = machine1.convert(24);
        assertEquals(x, 25);
    }
    @Test
    public void MachineTest2() {
        NAVAL = makeNaval();
        Machine machine1 = new Machine(alphaa, 5, 3, NAVAL);
        machine1.insertRotors(new ArrayList<String>(Arrays.asList(new String[]{"B", "Beta", "I", "II", "III"})));
        machine1.setPlugboard(identity);
        machine1.setRotors("AAAA");
        String check = machine1.convert("HELLO WORLD");
        assertTrue(check.equals("ILBDA AMTAZ"));
    }
    @Test
    public void MachineTest3() {
        NAVAL = makeNaval();
        Machine machine1 = new Machine(alphaa, 5, 3, NAVAL);
        machine1.insertRotors(new ArrayList<String>(Arrays.asList(new String[]{"B", "Beta", "III", "II", "I"})));
        machine1.setPlugboard(identity);
        machine1.setRotors("AAAZ");
        String check = machine1.convert("AAAAA AAAAA AAAAA AAAAA AAAAA AAA");
        System.out.println(check);
        assertTrue(check.equals("NFTZM GISXI PJWGD NJJCO QTYRI GDM"));
    }
    @Test
    public void MachineTest4() {
        NAVAL = makeNaval();
        Machine machine1 = new Machine(alphaa, 5, 3, NAVAL);
        machine1.insertRotors(new ArrayList<String>(Arrays.asList(new String[]{"B", "Beta", "III", "I", "II"})));
        machine1.setPlugboard(identity);
        machine1.setRotors("AAAZ");
        String check = machine1.convert("AAAAA AAAAA AAAAA AAAAA AAAAA AAA");
        System.out.println(check);
        assertTrue(check.equals("JCNBE GNHEJ ZLEKN SCFKB KNQHS PBK"));
    }

    @Test
    public void MachineTest5() {
        NAVAL = makeNaval();
        Machine machine1 = new Machine(alphaa, 5, 3, NAVAL);
        machine1.insertRotors(new ArrayList<String>(Arrays.asList(new String[]{"B", "Beta", "III", "I", "II"})));
        machine1.setPlugboard(identity);
        machine1.setRotors("AAAZ");
        String check = machine1.convert("EJ");
        System.out.println(check);
        assertTrue(check.equals("CD"));
    }
    @Test
    public void MachineTest6() {
        NAVAL = makeNaval();
        Machine machine1 = new Machine(alphaa, 5, 3, NAVAL);
        machine1.insertRotors(new ArrayList<String>(Arrays.asList(new String[]{"B", "Beta", "III", "II", "I"})));
        machine1.setPlugboard(identity);
        machine1.setRotors("AAAZ");
        String check = machine1.convert("NFTZM GISXI PJWGD NJJCO QTYRI GDM");
        System.out.println(check);
        assertTrue(check.equals("AAAAA AAAAA AAAAA AAAAA AAAAA AAA"));
    }

    @Test
    public void MachineTest7() {
        NAVAL = makeNaval();
        Machine machine1 = new Machine(alphaa, 5, 3, NAVAL);
        machine1.insertRotors(new ArrayList<String>(Arrays.asList(new String[]{"B", "Beta", "III", "II", "I"})));
        machine1.setPlugboard(identity);
        machine1.setRotors("AAAP");
        String check = machine1.convert("J");
        System.out.println(check);
        assertTrue(check.equals("A"));
    }


}
