package enigma;

import org.junit.Test;

import javax.print.attribute.standard.MediaSize;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author Tyler Rathkamp
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified by ARGS, where 1 <= ARGS.length <= 3.
     *  ARGS[0] is the name of a configuration file.
     *  ARGS[1] is optional; when present, it names an input file
     *  containing messages.  Otherwise, input comes from the standard
     *  input.  ARGS[2] is optional; when present, it names an output
     *  file for processed messages.  Otherwise, output goes to the
     *  standard output. Exits normally if there are no errors in the input;
     *  otherwise with code 1. */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /** Check ARGS and open the necessary files (see comment on main). */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /** Return a Scanner reading from the file named NAME. */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Return a PrintStream writing to the file named NAME. */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Configure an Enigma machine from the contents of configuration
     *  file _config and apply it to the messages in _input, sending the
     *  results to _output. */
    private void process() {
        Machine machine1 = readConfig();
        while (_input.hasNext()) {
            if (_input.hasNext(Pattern.compile("\\*"))) {
                String next = _input.nextLine();
                if (next.equals("")) {
                    printMessageLine("");
                } else {
                    setUp(machine1, next);
                }
            } else {
                String message = machine1.convert(_input.nextLine());
                printMessageLine(message);
            }
        }
    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            _alphabet =  new Alphabet(_config.next());
            int numRotors = _config.nextInt();
            int numPawls = _config.nextInt();
            while (_config.hasNext()) {
                readRotor();
            }
            return new Machine(_alphabet, numRotors, numPawls, _rotors);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /** Return a rotor, reading its description from _config. */
    private void readRotor() {
        try {
            String name = _config.next();
            String settings = _config.next();
            String notches = "";
            if (settings.length() >= 2) {
                for (int i = 1; i < settings.length(); i++) {
                    notches += settings.charAt(i);
                }
            }
            String cycles = "";
            while (_config.hasNext(Pattern.compile("(\\([A-Z]*[0-9]*\\) *\\n* *)+"))) {
                cycles += _config.next(Pattern.compile("(\\([A-Z]*[0-9]*\\) *\\n* *)+"));
            }
            if (settings.charAt(0) == 'M') {
                _rotors.add(new MovingRotor(name, new Permutation(cycles, _alphabet), notches));
            } else if (settings.charAt(0) == 'N') {
                _rotors.add(new FixedRotor(name, new Permutation(cycles, _alphabet)));
            } else if (settings.charAt(0) == 'R') {
                _rotors.add(new Reflector(name, new Permutation(cycles, _alphabet)));
            }
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        Scanner setup = new Scanner(settings);
        ArrayList<String> rotors = new ArrayList<>();
        rotors.clear();
        setup.next();
        for (int i = 0; i < M.numRotors(); i++) {
            rotors.add(setup.next());
        }
        M.insertRotors(rotors);
        M.setRotors(setup.next());
        if (setup.hasNext("(\\\\([A-Z]+\\\\) *\\\\n* *)+")) {
            String cycles = "";
            while (_config.hasNext(Pattern.compile("(\\([A-Z]+\\) *\\n* *)+"))) {
                cycles += _config.next(Pattern.compile("(\\([A-Z]+\\) *\\n* *)+"));
            }
            M.setPlugboard(new Permutation(cycles, M.alphabetGet()));
        }
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        msg = msg.replace(" ", "");
        int groupsOfFive = msg.length()/5;
        int index = 0;
        String printMe = "";
        for (int i = 0; i < groupsOfFive; i++) {
            for (int x = 0; x < 5; x++) {
                printMe += msg.charAt(index);
                index += 1;
            }
            printMe += " ";
        }
        for (int p = index; p < msg.length(); p++) {
            printMe += msg.charAt(p);
        }
        _output.println(printMe);
    }

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;

    /** Source of input messages. */
    private Scanner _input;

    /** Source of machine configuration. */
    private Scanner _config;

    /** File for encoded/decoded messages. */
    private PrintStream _output;
    /** Rotors to use for instantiating a machine. */
    private ArrayList<Rotor> _rotors = new ArrayList<>();
}
