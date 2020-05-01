package gitlet;
import static gitlet.Objects.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Tyler Rathkamp
 */
public class Display  {
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

    @SuppressWarnings("unchecked")
    public static void status() {
        System.out.println("=== Branches ===\n");
        List<String> branchNames = Utils.plainFilenamesIn(BRANCH);
        Collections.sort(branchNames);
        String curBranch = Utils.readContentsAsString(HEAD);
        for (String branch : branchNames) {
            if (branch.equals(curBranch)) {
                curBranch = "*" + curBranch + "\n";
                System.out.println(curBranch);
            } else {
                System.out.println(branch + "\n");
            }
        }
        System.out.println("\n");
        System.out.println("=== Staged Files ===\n");
        List<String> stagedFiles = Utils.plainFilenamesIn(STAGING);
        for (String fileName : stagedFiles) {
            System.out.println(fileName + "\n");
        }
        System.out.println("\n" +
                "=== Removed Files ===\n");
        ArrayList<String> remFiles = Utils.readObject(TOREMOVE, ArrayList.class);
        for (String file : remFiles) {
            System.out.println(file + "\n");
        }
        System.out.println("\n=== Modifications Not Staged For Commit ===\n");
        System.out.println("\n=== Untracked Files ===\n");
    }
}
