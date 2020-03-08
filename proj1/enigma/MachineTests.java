package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import java.util.ArrayList;
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
    private String testPlugBoard = "(AB)(CD)(EF)(GH)(IJ)(KL)(MN)(OP)(QR)(ST)(UV)(WX)(YZ)";
    private Alphabet alphaa = new Alphabet(UPPER_STRING);
    private Permutation Plugboard = new Permutation(testPlugBoard, alphaa);

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
        System.out.println("?????");

    }


}
