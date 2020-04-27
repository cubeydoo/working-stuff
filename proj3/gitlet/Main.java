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
            Commit init = new Commit("initial commit");
        } else if (args[0].equals("add")) {
            Commands.addFile(args[1]);
        }


        System.out.println("I don't understand.");
    }
}
