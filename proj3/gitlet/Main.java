package gitlet;

public class Main {
    public Main() {
    }

    public static void main(String... args) {
        if (args[0].equals("init")) {
            new Commit("initial commit");
        } else if (args[0].equals("add")) {
            Commands.addFile(args[1]);
        } else if (args[0].equals("commit")) {
            new Commit(args[1]);
        }else {
            System.out.println("I don't understand.");
        }
    }
}
