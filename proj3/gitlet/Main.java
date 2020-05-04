package gitlet;
import static gitlet.Objects.*;

/** A file storing system.
 * @author Tyler Rathkamp */
public class Main {

    /** Useless as of now. */
    public Main() {
    }
    /** A file storing system. ARGS is a command. */
    public static void main(String... args) {
        if (args[0].equals("init")) {
            new Commit("initial commit");
        } else if (!GITLET.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
        } else {
            switch (args[0]) {
            case "add":
                Commands.addFile(args[1]);
                break;
            case "commit":
                new Commit(args[1]);
                break;
            case "checkout":
                if (args[1].equals("--")) {
                    Commands.checkout(null, args[2]);
                } else if (args[2].equals("++")) {
                    System.out.println("Incorrect operands.");
                } else if (args.length == 4) {
                    Commands.checkout(args[1], args[3]);
                } else if (args.length == 2) {
                    Commands.checkout(args[1]);
                }
                break;
            case "log":
                Display.log();
                break;
            case "rm":
                Commit.rm(args[1]);
                break;
            case "global-log":
                Display.globalLog();
                break;
            case "status":
                Display.status();
                break;
            case "find":
                Display.find(args[1]);
                break;
            default:
                System.out.println("I don't understand.");
            }
        }
    }
}
