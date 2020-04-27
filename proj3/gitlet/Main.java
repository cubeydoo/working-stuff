package gitlet;

public class Main {
    public Main() {
    }

    public static void main(String... args) {
        if (args[0].equals("init")) {
            new Commit("initial commit");
        } else if (args[0].equals("add")) {
            Commands.addFile(args[1]);
        }

        System.out.println("I don't understand.");
    }
}
