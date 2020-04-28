package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import static gitlet.Objects.*;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Tyler Rathkamp
 */
public class Commit implements Serializable {

    public Commit(String message) {
        _message = message;
        if (this._message.equals("initial commit")) {
            this.init();
        } else {
            timestamp = "fixme";
        }

    }

    public String getParent() {
        return this.parent;
    }

    public void init() {
        timestamp = "00:00:00 UTC, Thursday, 1 January 1970";
        parent = null;
        if (!GITLET.exists()) { //CHANGE THIS BACK WHEN UR DONE
            GITLET.mkdir();
            Utils.writeContents(HEAD, "master.txt");
            REFS.mkdir();
            OBJECTS.mkdir();
            STAGING.mkdir();
            BRANCH.mkdir();
            File master = Utils.join(BRANCH, "master.txt");
            Utils.writeContents(master, "hewo");
            COMMIT.mkdir();
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
