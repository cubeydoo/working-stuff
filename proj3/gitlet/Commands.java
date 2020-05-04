package gitlet;

import java.io.File;
import static gitlet.Objects.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Tyler Rathkamp
 */
public class Commands {
    /** The last commit made on the current branch. */
    private Commit lastCommit = getCommit("HEAD");


    /** Returns an ArrayList of all files and filehashes that have
     * changed between OLD and NEW commits.
     * @return ArrayList of changedFiles by name. */
     public static ArrayList<String> changedFiles(Commit old, Commit cnew) {
         ArrayList<String> changed = new ArrayList<>();
         HashMap<String, String> oldfiles = old.getFiles();
         HashMap<String, String> newFiles = cnew.getFiles();
         for (Map.Entry<String, String> entry : oldfiles.entrySet()) {
             String filename = entry.getKey();
             String oldhash = oldfiles.get(filename);
             String newhash = newFiles.get(filename);
             if (oldhash != null && newhash != null
             && !oldhash.equals(newhash)) {
                 changed.add(filename);
             }
         }
         return changed;
     }

    /** Returns an ArrayList of all files and filehashes that have not
     * changed between OLD and NEW commits.
     * @return ArrayList of unchangedFiles by name. */
    public static ArrayList<String> unchangedFiles(Commit old, Commit cnew) {
        ArrayList<String> unchanged = new ArrayList<>();
        HashMap<String, String> oldfiles = old.getFiles();
        HashMap<String, String> newFiles = cnew.getFiles();
        for (Map.Entry<String, String> entry : oldfiles.entrySet()) {
            String filename = entry.getKey();
            String oldhash = oldfiles.get(filename);
            String newhash = newFiles.get(filename);
            if (oldhash != null && oldhash.equals(newhash)) {
                unchanged.add(filename);
            }
        }
        return unchanged;
    }

    /** Returns a HashMap of shavals and their distance from the head of
     * the given BRANCHNAME.
     * @return Distances. */
    public static HashMap<String, Integer> getDistances(String branchName, Integer distance) {
        Commit head = getCommit(branchName);
        HashMap<String, Integer> headCommits = new HashMap<>();
        while (head.getParent() != null) {
            headCommits.put(head.getShaValue(), distance);
            distance += 1;
            if (head.getMergedBranch() != null) {
                HashMap<String, Integer> mergedStuff =
                        getDistances(head.getMergedBranch(), distance);
                headCommits.putAll(mergedStuff);
            }
            head = getCommitfromSHA(head.getParent());
        }
        headCommits.put(head.getShaValue(), distance);
        return headCommits;
    }

    /** Finds the commit where BRANCHNAME and HEAD diverged. */
    public static String splitPoint(String branchName) {
        HashMap<String, Integer> headDistances = getDistances("HEAD", 0);
        HashMap<String, Integer> branchDistances = getDistances(branchName, 0);
        String currentBest = "";
        Integer currBest = 99;

        for (Map.Entry entry : headDistances.entrySet()) {
            String currentHash = (String) entry.getKey();
            if (branchDistances.containsKey(currentHash) &&
                    branchDistances.get(currentHash) < currBest) {
                currBest = branchDistances.get(currentHash);
                currentBest = currentHash;
            }
        }
        return currentBest;
    }
    /** Merges the current branch with BRANCHNAME. */
    public static void merge(String branchName) {

    }
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
    /** Makes a new branch with name BRANCHNAME. */
    public static void branch(String branchName) {
        branchName = branchName + ".txt";
        File branch = Utils.join(BRANCH, branchName);
        if (branch.exists()) {
            System.out.println("A branch with that name already exists.");
        } else {
            Commit current = getCommit("HEAD");
            Utils.writeContents(branch, current.getShaValue());
        }
    }

    /** Resets the COMMIT. */
    public static void reset(String commit) {
        Commit thisCommit = getCommitfromSHA(commit);
        if (thisCommit != null) {
            String branchName = Utils.readContentsAsString(HEAD);
            File branch = Utils.join(BRANCH, branchName);
            Utils.writeContents(branch, commit);
            checkout(branchName, 1);
        }
    }
    /** Deletes a branch NAME if it exists. */
    public static void rmbranch(String name) {
        String curBranch = Utils.readContentsAsString(HEAD);
        if (name.equals(curBranch)) {
            System.out.println("Cannot remove the current branch.");
        }
        File branch = Utils.join(BRANCH, name);
        if (branch.exists()) {
            branch.delete();
        } else {
            System.out.println("A branch with that name does not exist.");
        }
    }
    /** Checks out BRANCHHEAD. */
    public static void checkout(String branchHead, int numCheck) {
        if (!branchHead.contains(".txt")) {
            branchHead += ".txt";
        }
        String[] branchList = Utils
                .plainFilenamesIn(BRANCH).toArray(new String[0]);
        String currentBranch = Utils.readContentsAsString(HEAD);
        if (numCheck == 0) {
            if (branchHead.equals(currentBranch)) {
                System.out.println("No need to checkout the current branch.");
            }
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
