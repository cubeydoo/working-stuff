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
        boolean flag = false;
        String[] objectFiles = Utils.plainFilenamesIn(COMMIT).toArray(new String[0]);
        for (String string : objectFiles) {
            if(string.equals(shaVal)) {
                flag = true;
            }
        }
        if (flag) {
            File correctBranch = Utils.join(COMMIT, shaVal);
            Commit lastCommit = Utils.readObject(correctBranch, Commit.class);
            return lastCommit;
        } else {
            System.out.println("No commit with that id exists.");
            return null;
        }
    }

    /** Returns the string contents of a HASH file in .gitlet/refs/objects. Returns NULL if File is not found. */
    public static String getFileContents(String hash) {
        String[] objectFiles = Utils.plainFilenamesIn(OBJECTS).toArray(new String[0]);
        boolean flag = false;
        for (String string : objectFiles) {
            if(string.equals(hash)) {
                flag = true;
            }
        }
        if (flag) {
            File file = Utils.join(OBJECTS, hash);
            return Utils.readContentsAsString(file);
        } else {
            System.out.println("File does not exist in that commit.");
            return null;
        }
    }

    public static boolean doesFileExist(String[] files, String fileName) {
        boolean flag = false;
        for (String string : files) {
            if(string.equals(fileName)) {
                flag = true;
            }
        }
        return flag;
    }
}
