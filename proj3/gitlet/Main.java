package gitlet;

import java.io.File;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Tyler Rathkamp
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) {
        if (args[0].equals("init")) {
            File cwd = new File(System.getProperty("user.dir"));
        }


        System.out.println("I don't understand.");
    }

}
