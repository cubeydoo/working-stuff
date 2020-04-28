package gitlet;

import java.io.File;
import static gitlet.Objects.*;
import java.util.ArrayList;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Tyler Rathkamp
 */
public class Commands {
    /** The last commit made on the current branch. */
    private Commit lastCommit = getCommit("HEAD");

    public static void addFile(String fileName) {
        String[] currentWD = Utils.plainFilenamesIn(CWD).toArray(new String[0]);
        boolean flag = false;
        for (String string : currentWD) {
            if(string.equals(fileName)) {
                flag = true;
            }
        }
        if (flag) {
            File current = new File(fileName);
            String contents = Utils.readContentsAsString(current);
            File output = Utils.join(STAGING, fileName);
            Utils.writeContents(output, contents);
        } else {
            System.out.println("File does not exist.");
        }
    }
    public static void checkout(String commit, String fileName) {
        Commit checkout;
        if (commit == null) {
            checkout = getCommit("HEAD");
        } else {
            checkout = getCommitfromSHA(commit);
        }
        if (checkout == null) {
            return;
        }
        String fileHash = checkout.getFiles().get(fileName);
        String contents = getFileContents(fileHash);
        File current = Utils.join(CWD, fileName);
        if (!(contents == null)) {
            Utils.writeContents(current, contents);
        }

    }
}
