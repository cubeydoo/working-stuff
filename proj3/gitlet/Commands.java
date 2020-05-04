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
     * changed between OLD and CNEW commits.
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
     * changed between OLD and CNEW commits.
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

    /** Returns a HashMap of shavals and their DISTANCE from the head of
     * the given BRANCHNAME.
     * @return Distances. */
    public static HashMap<String, Integer>
        getDistances(String branchName, Integer distance) {
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

    /** Finds the commit where BRANCHNAME and HEAD diverged.
     * @return String. */
    public static String splitPoint(String branchName) {
        HashMap<String, Integer> headDistances = getDistances("HEAD", 0);
        HashMap<String, Integer> branchDistances = getDistances(branchName, 0);
        String currentBest = "";
        Integer currBest = 12;

        for (Map.Entry entry : headDistances.entrySet()) {
            String currentHash = (String) entry.getKey();
            if (branchDistances.containsKey(currentHash)
                    && branchDistances.get(currentHash) < currBest) {
                currBest = branchDistances.get(currentHash);
                currentBest = currentHash;
            }
        }
        Commit givenBranch = getCommit(branchName);
        Commit current = getCommit("HEAD");
        if (currentBest.equals(givenBranch.getShaValue())) {
            System.out.println(
                    "Given branch is an ancestor of the current branch.");
            return null;
        } else if (current.getShaValue().equals(currentBest)) {
            checkout(branchName, 1);
            System.out.println("Current branch fast forwarded.");
            return null;
        }
        return currentBest;
    }
    /** Checks the errors for merge called on BRANCHNAME.
     * @return Returns 1 if an error was found. */
    @SuppressWarnings("unchecked")
    public static int mergeError(String branchName) {
        String curBranchName = Utils.readContentsAsString(HEAD);
        if (curBranchName.equals(branchName)) {
            System.out.println("Cannot merge a branch with itself.");
            return 1;
        }
        File branchFile = Utils.join(BRANCH, branchName);
        if (!branchFile.exists()) {
            System.out.println("A branch with that name does not exist.");
            return 1;
        }
        ArrayList<String> toRemove =
                Utils.readObject(TOREMOVE, ArrayList.class);
        String[] stagedFiles = Utils
                .plainFilenamesIn(STAGING).toArray(new String[0]);
        if (toRemove.size() != 0 || stagedFiles.length != 0) {
            System.out.println("You have uncommitted changes.");
            return 1;
        }
        HashMap<String, String> lastFiles =
                getCommit("HEAD").getFiles();
        String[] cwd =
                Utils.plainFilenamesIn(CWD).toArray(new String[0]);
        for (String fileName : cwd) {
            if (lastFiles.get(fileName) == null) {
                System.out.println("There is an untracked "
                        + "file in the way; delete it, or add and commit it first.");
                return 1;
            }
        }
        return 0;
    }
    /** Merges the current branch with BRANCHNAME. */
    @SuppressWarnings("unchecked")
    public static void merge(String branchName) {
        branchName += ".txt";
        if (mergeError(branchName) == 1) {
            return;
        }
        Commit branch = getCommit(branchName);
        Commit ancestor = getCommitfromSHA(splitPoint(branchName));
        Commit latestCommit = getCommit("HEAD");
        ArrayList<String> test1 = unchangedFiles(ancestor, latestCommit);
        for (String fileName: changedFiles(ancestor, branch)) {
            if (test1.contains(fileName)) {
                checkout(branch.getShaValue(), fileName);
                addFile(fileName);
            }
        }
        HashMap<String, String> branchFiles = branch.getFiles();
        HashMap<String, String> ancestorFiles = ancestor.getFiles();
        HashMap<String, String> latestFiles = latestCommit.getFiles();
        for (Map.Entry entry : branchFiles.entrySet()) {
            String fileName = (String) entry.getKey();
            if (ancestorFiles.get(fileName) == null
                && latestFiles.get(fileName) == null) {
                checkout(branch.getShaValue(), fileName);
                addFile(fileName);
            }
        }
        for (String fileName : test1) {
            if (branchFiles.get(fileName) == null) {
                Commit.rm(fileName);
            }
        }
        ArrayList<String> branchChanged = changedFiles(ancestor, branch);
        ArrayList<String> currChanged = changedFiles(ancestor, latestCommit);
        boolean mergeFlag = false;
        for (String fileName : branchChanged) {
            if (currChanged.contains(fileName)) {
                String hash1 = branchFiles.get(fileName);
                String hash2 = latestFiles.get(fileName);
                if (!hash1.equals(hash2)) {
                    File problem = Utils.join(CWD, fileName);
                    String newContents = "<<<<<<< HEAD\n"
                            + getFileContents(hash1) + "=======\n"
                            + getFileContents(hash2) + ">>>>>>>";
                    Utils.writeContents(problem, newContents);
                    addFile(fileName);
                    mergeFlag = true;
                }
            }
        }
        for (String fileName : unchangedFiles(ancestor, branch)) {
            if (latestFiles.get(fileName) == null) {
                Commit.rm(fileName);
            }
        }
        String curBranch = Utils.readContentsAsString(HEAD);
        curBranch = curBranch.substring(0, curBranch.lastIndexOf('.'));
        branchName = branchName.substring(0, branchName.lastIndexOf('.'));
        String message = "Merged " + branchName + " into "
                + curBranch + ".";
        Commit woo = new Commit(message, branchName);
        if (mergeFlag) {
            System.out.println("Encountered a merge conflict.");
        }
    }
    /** Adds a FILENAME to the commit. */
    @SuppressWarnings("unchecked")
    public static void addFile(String fileName) {
        File current = Utils.join(CWD, fileName);
        Commit lastCommit = getCommit("HEAD");
        String hash = lastCommit.getFiles().get(fileName);
        if (current.exists()) {
            String contents = Utils.readContentsAsString(current);
            if (Utils.sha1(contents).equals(hash)) {
                ArrayList<String> toRemove =
                        Utils.readObject(TOREMOVE, ArrayList.class);
                toRemove.remove(fileName);
                Utils.writeObject(TOREMOVE, toRemove);
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
        if (!branchName.contains(".txt")) {
            branchName += ".txt";
        }
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
        name += ".txt";
        String curBranch = Utils.readContentsAsString(HEAD);
        if (name.equals(curBranch)) {
            System.out.println("Cannot remove the current branch.");
            return;
        }
        File branch = Utils.join(BRANCH, name);
        if (branch.exists()) {
            branch.delete();
        } else {
            System.out.println("A branch with that name does not exist.");
        }
    }
    /** Checks out BRANCHHEAD. NUMCHECK is used to reuse code. */
    @SuppressWarnings("unchecked")
    public static void checkout(String branchHead, int numCheck) {
        if (!branchHead.contains(".txt")) {
            branchHead += ".txt";
        }
        File branch = Utils.join(BRANCH, branchHead);
        if (!branch.exists()) {
            System.out.println("No such branch exists.");
            return;
        }
        String[] branchList = Utils
                .plainFilenamesIn(BRANCH).toArray(new String[0]);
        String currentBranch = Utils.readContentsAsString(HEAD);
        if (numCheck == 0) {
            if (branchHead.equals(currentBranch)) {
                System.out.println("No need to checkout the current branch.");
                return;
            }
        }
        if (doesFileExist(branchList, branchHead)) {
            String[] cwd = Utils.
                    plainFilenamesIn(CWD).toArray(new String[0]);
            boolean flag = false;
            Commit lastCommit = getCommit(currentBranch);
            HashMap<String, String> thesefiles = lastCommit.getFiles();
            for (String fileName : cwd) {
                File staged = Utils.join(STAGING, fileName);
                if (thesefiles.get(fileName) == null && !staged.exists()) {
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
                Utils.writeContents(HEAD, branchHead);
            }
        }
    }
}
