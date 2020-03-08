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
        NAVAL.add(new Rotor("I", new Permutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", UPPER)));
        NAVAL.add(new Rotor("II", new Permutation("(FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)", UPPER)));
        NAVAL.add(new Rotor("III",new Permutation( "(ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)", UPPER)));
        NAVAL.add(new Rotor("IV", new Permutation("(AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)", UPPER)));
        NAVAL.add(new Rotor("V", new Permutation("(AVOLDRWFIUQ)(BZKSMNHYC) (EGTJPX)", UPPER)));
        NAVAL.add(new Rotor("VI", new Permutation("(AJQDVLEOZWIYTS) (CGMNHFUX) (BPRK) ", UPPER)));
        NAVAL.add(new Rotor("VII", new Permutation("(ANOUPFRIMBZTLWKSVEGCJYDHXQ) ", UPPER)));
        NAVAL.add(new Rotor("VIII",new Permutation( "(AFLSETWUNDHOZVICQ) (BKJ) (GXY) (MPR)", UPPER)));
        NAVAL.add(new Rotor("Beta", new Permutation( "(ALBEVFCYODJWUGNMQTZSKPR) (HIX)", UPPER)));
        NAVAL.add(new Rotor("Gamma", new Permutation( "(AFNIRLBSQWVXGUZDKMTPCOYJHE)", UPPER)));
        NAVAL.add(new Rotor("B", new Permutation(
                "(AE) (BN) (CK) (DQ) (FU) (GY) (HW) (IJ) (LO) "
                        + "(MP) (RX) (SZ) (TV)", UPPER)));
        NAVAL.add(new Rotor("C", new Permutation(
                "(AR) (BD) (CO) (EJ) (FN) (GT) (HK) (IV) (LM) "
                        + "(PW) (QZ) (SX) (UY)"UPPER)));

        return NAVAL;
    }


    /* ***** TESTS ***** */

    @Test
    public void MachineTest1() {
        NAVAL = makeNaval();
        Machine machine1 = new Machine(alphaa, 5, 3, NAVAL);

    }


}
