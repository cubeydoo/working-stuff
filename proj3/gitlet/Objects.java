package gitlet;

import java.io.File;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Tyler Rathkamp
 */
public class Objects {
    /** Gitlet file. */
    public static final File GITLET =
            Utils.join(System.getProperty("user.dir"), ".gitlet");
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


    /** Gets the commit object at the BRANCHHEAD of a certain branch.
     * @return Commit object. */
    public static Commit getCommit(String branchHead) {
        File correctBranch;
        if (branchHead.equals("HEAD")) {
            String branchName = Utils.readContentsAsString(HEAD);
            correctBranch = Utils.join(BRANCH, branchName);
        } else {
            correctBranch = Utils.join(BRANCH, branchHead);
        }
        if (correctBranch.exists()) {
            String shaVal = Utils.readContentsAsString(correctBranch);
            return getCommitfromSHA(shaVal);
        } else {
            System.out.println("A branch with that name does not exist.");
            return null;
        }
    }

    /** Returns the commit specified by SHAVAL. */
    public static Commit getCommitfromSHA(String shaVal) {
        String[] commitFiles =
                Utils.plainFilenamesIn(COMMIT).toArray(new String[0]);
        for (String commitHash : commitFiles) {
            if (commitHash.contains(shaVal)) {
                shaVal = commitHash;
                break;
            }
        }
        File correctBranch = Utils.join(COMMIT, shaVal);
        String[] objectFiles =
                Utils.plainFilenamesIn(OBJECTS).toArray(new String[0]);
        if (correctBranch.exists()) {
            return Utils.readObject(correctBranch, Commit.class);
        } else {
            for (String fileName : objectFiles) {
                if (fileName.contains(shaVal)) {
                    File correcto = Utils.join(COMMIT, fileName);
                    return Utils.readObject(correcto, Commit.class);
                }
            }
            System.out.println("No commit with that id exists.");
            return null;
        }
    }

    /** Returns the string contents of a HASH file in
     * .gitlet/refs/objects. Returns NULL if File is not found. */
    public static String getFileContents(String hash) {
        String[] objectFiles =
                Utils.plainFilenamesIn(OBJECTS).toArray(new String[0]);
        boolean flag = false;
        for (String string : objectFiles) {
            if (string.equals(hash)) {
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

    /** Returns if FILENAME exists in the FILES, ultimately useless. */
    public static boolean doesFileExist(String[] files, String fileName) {
        boolean flag = false;
        for (String string : files) {
            if (string.equals(fileName)) {
                flag = true;
            }
        }
        return flag;
    }
}
