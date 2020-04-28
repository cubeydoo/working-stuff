package gitlet;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Tyler Rathkamp
 */
public class Objects {
    /** Gitlet file. */
    public static final File GITLET = Utils.join(System.getProperty("user.dir"), ".gitlet");
    /** A mapping between file names and SHA value of file contents. */
    public static final File HEAD = Utils.join(GITLET, "HEAD.txt");;
    /** A mapping between file names and SHA value of file contents. */
    public static final File OBJECTS = Utils.join(GITLET, "objects");
    /** A mapping between file names and SHA value of file contents. */
    public static final File STAGING = Utils.join(GITLET, "staging");
    /** Refs. */
    public static final File REFS = Utils.join(GITLET, "refs");
    /** A mapping between file names and SHA value of file contents. */
    public static final File BRANCH = Utils.join(REFS, "branches");
    /** A mapping between file names and SHA value of file contents. */
    public static final File COMMIT = Utils.join(REFS, "commit");
    /** toRemove. */
    public static final File TOREMOVE = Utils.join(GITLET, "toRemove.txt");
    /** CWD. */
    public static final File CWD = new File(System.getProperty("user.dir"));


    public static Commit getCommit(String branchHead) {
        File correctBranch;
        if (branchHead.equals("HEAD")) {
            String branchName = Utils.readContentsAsString(HEAD);
            correctBranch = Utils.join(BRANCH, branchName);
        } else {
            correctBranch = Utils.join(BRANCH, branchHead);
        }
        String shaVal = Utils.readContentsAsString(correctBranch);
        return getCommitfromSHA(shaVal);
    }

    public static Commit getCommitfromSHA(String shaVal) {
        File correctBranch = Utils.join(COMMIT, shaVal);
        Commit lastCommit = Utils.readObject(correctBranch, Commit.class);
        return lastCommit;
    }
}
