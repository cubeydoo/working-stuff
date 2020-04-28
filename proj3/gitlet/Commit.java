package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import static gitlet.Objects.*;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Tyler Rathkamp
 */
public class Commit implements Serializable {

    public Commit(String message) {
        _message = message;
        String[] stageFileNames = Utils.plainFilenamesIn(STAGING).toArray(new String[0]);
        if (stageFileNames.length == 0) {
            System.out.println("No changes added to the commit.");
            return;
        } else if (message.equals("")) {
            System.out.println("Please enter a commit message.");
            return;
        }
        timestamp = "fixme";

        if (this._message.equals("initial commit")) {
            this.init();
        } else {
            Commit lastCommit = getCommit("HEAD");
            parent = lastCommit.shaValue;
            files = lastCommit.files;
            String[] toRemove = Utils.readObject(TOREMOVE, String[].class);
            for (String fileName : toRemove) {
                files.remove(fileName);
            }
            String[] remove = {};
            Utils.writeObject(TOREMOVE, remove);
        }
        for (String fileName : stageFileNames) {
            File current = Utils.join(STAGING, fileName);
            String shaVal = Utils.sha1(current);
            File destination = Utils.join(OBJECTS, shaVal);
            files.put(fileName, shaVal);
            Utils.restrictedDelete(current);
        }
        shaValue = Utils.sha1(this);
        String branchName = Utils.readContentsAsString(HEAD);
        File correctBranch = Utils.join(BRANCH, branchName);
        Utils.writeContents(correctBranch, shaValue);
        File saveMe = Utils.join(COMMIT, shaValue);
        Utils.writeObject(saveMe, this);
    }

    public String getParent() {
        return this.parent;
    }

    public void init() {
        timestamp = "00:00:00 UTC, Thursday, 1 January 1970";
        parent = null;
        if (GITLET.exists()) { //CHANGE THIS BACK WHEN UR DONE
            GITLET.mkdir();
            Utils.writeContents(HEAD, "master.txt");
            REFS.mkdir();
            OBJECTS.mkdir();
            STAGING.mkdir();
            BRANCH.mkdir();
            File master = Utils.join(BRANCH, "master.txt");
            Utils.writeContents(master, "hewo");
            COMMIT.mkdir();
            String[] remove = {};
            Utils.writeObject(TOREMOVE, remove);
        }

    }

    public String toString() {
        String returnme;
        if (mergedBranch == null) {
            returnme = "===\n"
                    + "commit " + shaValue + "\n"
                    + "Date: " + timestamp + "\n"
                    + _message + "\n";
        } else {
            returnme = "===\n"
                    + "commit " + shaValue + "\n"
                    + "Merge: " + parent.substring(0, 7) + " " + mergedBranch.substring(0, 7) + "\n"
                    + "Date: " + timestamp + "\n"
                    + _message + "\n";
        }
        return returnme;
    }
    /** A mapping between file names and SHA value of file contents. */
    private HashMap files = new HashMap();

    /** SHA1 hash value referencing parent commit. */
    private String parent;

    /** SHA1 hash value referencing mergeHead. */
    private String mergedBranch;

    /** Time at which commit was created. */
    private String timestamp;

    /** A string that stores the commit message. */
    private String _message;

    /** A string that stores the sha value. */
    private String shaValue;
}
