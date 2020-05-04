package gitlet;

import java.io.File;
import static gitlet.Objects.*;
import java.util.HashMap;
import java.util.Map;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Tyler Rathkamp
 */
public class Commands {
    /** The last commit made on the current branch. */
    private Commit lastCommit = getCommit("HEAD");

    /** Adds a FILENAME to the commit. */
    public static void addFile(String fileName) {
        File current = Utils.join(CWD, fileName);
        Commit lastCommit = getCommit("HEAD");
        String hash = lastCommit.getFiles().get(fileName);
        if (current.exists()) {
            String contents = Utils.readContentsAsString(current);
            if (Utils.sha1(contents).equals(hash)) {
                return;
            }
            File output = Utils.join(STAGING, fileName);
            Utils.writeContents(output, contents);
        } else {
            System.out.println("File does not exist.");
        }
    }
    /** Reverts a FILENAME to a COMMIT. */
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
    /** Checks out BRANCHHEAD. */
    public static void checkout(String branchHead) {
        String[] branchList = Utils
                .plainFilenamesIn(BRANCH).toArray(new String[0]);
        String currentBranch = Utils.readContentsAsString(HEAD);
        if (branchHead.equals(currentBranch)) {
            System.out.println("No need to checkout the current branch.");
        } else if (doesFileExist(branchList, branchHead)) {
            String[] cwd = Utils.
                    plainFilenamesIn(CWD).toArray(new String[0]);
            boolean flag = false;
            for (String fileName : cwd) {
                if (!doesFileExist(cwd, fileName)) {
                    System.out.println("There is an untracked file in the way; "
                            + "delete it, or add and commit it first.");
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                for (String fileName : cwd) {
                    File current = Utils.join(CWD, fileName);
                    current.delete();
                }
                Commit checkout = getCommit(branchHead);
                HashMap<String, String> files = checkout.getFiles();
                for (Map.Entry curr : files.entrySet()) {
                    String fileName = (String) curr.getKey();
                    String hash = (String) curr.getValue();
                    String contents = getFileContents(hash);
                    File current = Utils.join(CWD, fileName);
                    if (!(contents == null)) {
                        Utils.writeContents(current, contents);
                    }
                }
            }
        } else {
            System.out.println("No such branch exists.");
        }
    }
}
