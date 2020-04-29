package gitlet;
import static gitlet.Objects.*;

public class Main {
    public Main() {
    }

    public static void main(String... args) {
        if (args[0].equals("init")) {
            new Commit("initial commit");
        } else if (!GITLET.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
        } else if (args[0].equals("add")) {
            Commands.addFile(args[1]);
        } else if (args[0].equals("commit")) {
            new Commit(args[1]);
        } else if (args[0].equals("checkout")) {
            if (args[1].equals("--")) {
                Commands.checkout(null, args[2]);
            } else if (args.length == 4) {
                Commands.checkout(args[1], args[3]);
            } else if (args.length == 2) {
                Commands.checkout(args[1]);
            }
        } else if (args[0].equals("log")) {
            Display.log();
        } else {
            System.out.println("I don't understand.");
        }
    }
}
