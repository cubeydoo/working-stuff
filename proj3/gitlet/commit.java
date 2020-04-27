package gitlet;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Tyler Rathkamp
 */
public class Commit extends Objects {
    /** A mapping between file names and SHA value of file contents. */
    private HashMap files = new HashMap();

    /** SHA-1 hash value referencing parent commit */
    private String parent;

    /** — SHA-1 hash value referencing head of
     * branch that was merged in. */
    private String mergedBranch;

    /** — Time at which commit was created. */
    private String timestamp;

    /** A string that stores the commit message. */
    private String _message;

    public Commit(String message) {
        _message = message;
        if (this._message.equals("initial commit")) {
            this.init();
        } else {
            timestamp = "fixme";
        }

    }

    public void init() {
        timestamp = "00:00:00 UTC, Thursday, 1 January 1970";
        parent = null;
        if (!gitlet.exists()) { //CHANGE THIS BACK WHEN UR DONE
            gitlet.mkdir();
            Utils.writeObject(head, "placeholder");
            refs.mkdir();
            objects.mkdir();
            staging.mkdir();
            branch.mkdir();
            commit.mkdir();
        }

    }
}
