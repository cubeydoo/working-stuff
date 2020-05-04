package gitlet;
import static gitlet.Objects.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Tyler Rathkamp
 */
public class Display  {
    /** Logs of commits in this branch. */
    public static void log() {
        Commit lastCommit = Objects.getCommit("HEAD");
        String returnMe = "";
        while (lastCommit.getParent() != null) {
            returnMe = returnMe + lastCommit.toString();
            lastCommit = Objects.getCommitfromSHA(lastCommit.getParent());
        }
        returnMe = returnMe + lastCommit.toString();
        System.out.println(returnMe);
    }
    /** Logs of all commits. */
    public static void globalLog() {
        String[] fileNames = Utils.plainFilenamesIn
                (BRANCH).toArray(new String[0]);
        String returnMe = "";
        for (String branchName : fileNames) {
            Commit lastCommit = getCommit(branchName);
            returnMe = returnMe + lastCommit.toString();
        }
        System.out.println(returnMe);
    }

    /** Returns the status. */
    @SuppressWarnings("unchecked")
    public static void status() {
        System.out.println("=== Branches ===");
        List<String> branchNames = Utils.plainFilenamesIn(BRANCH);
        Collections.sort(branchNames);
        String curBranch = Utils.readContentsAsString(HEAD);
        for (String branch : branchNames) {
            if (branch.equals(curBranch)) {
                curBranch = curBranch.substring(0, curBranch.lastIndexOf('.'));
                curBranch = "*" + curBranch;
                System.out.println(curBranch);
            } else {
                branch = branch.substring(0, branch.lastIndexOf('.'));
                System.out.println(branch);
            }
        }
        System.out.println("\n=== Staged Files ===");
        List<String> stagedFiles = Utils.plainFilenamesIn(STAGING);
        for (String fileName : stagedFiles) {
            System.out.println(fileName);
        }
        System.out.println("\n=== Removed Files ===");
        ArrayList<String> remFiles =
                Utils.readObject(TOREMOVE, ArrayList.class);
        for (String file : remFiles) {
            System.out.println(file);
        }
        System.out.println("\n=== Modifications Not Staged For Commit ===");
        System.out.println("\n=== Untracked Files ===\n");
    }
}
