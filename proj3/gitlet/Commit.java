package gitlet;

import org.junit.Test;

import javax.xml.crypto.dsig.SignatureMethod;
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import static gitlet.Objects.*;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Tyler Rathkamp
 */
public class Commit implements Serializable {

    public Commit(String message) {
        _message = message;
        boolean firstCommit = false;
        if (this._message.equals("initial commit")) {
            this.init();
            firstCommit = true;
        }
        String[] stageFileNames = Utils.plainFilenamesIn(STAGING).toArray(new String[0]);
        if (stageFileNames.length == 0 && !firstCommit) {
            System.out.println("No changes added to the commit.");
            return;
        } else if (message.equals("")) {
            System.out.println("Please enter a commit message.");
            return;
        }
        if (!firstCommit){
            timestamp = getTimestamp();
            Commit lastCommit = getCommit("HEAD");
            parent = lastCommit.shaValue;
            files = lastCommit.files;
            String[] toRemove = Utils.readObject(TOREMOVE, String[].class);
            for (String fileName : toRemove) {
                files.remove(fileName);
            }
            String[] remove = {};
            Utils.writeObject(TOREMOVE, remove);
            for (String fileName : stageFileNames) {
                File current = Utils.join(STAGING, fileName);
                String contents = Utils.readContentsAsString(current);
                String shaVal = Utils.sha1(contents);
                File destination = Utils.join(OBJECTS, shaVal);
                Utils.writeContents(destination, contents);
                files.put(fileName, shaVal);
                current.delete();
            }
        }
        byte[] byteArray = Utils.serialize(this);
        shaValue = Utils.sha1(byteArray);
        String branchName = Utils.readContentsAsString(HEAD);
        File correctBranch = Utils.join(BRANCH, branchName);
        Utils.writeContents(correctBranch, shaValue);
        File saveMe = Utils.join(COMMIT, shaValue);
        Utils.writeObject(saveMe, this);
    }

    /** Get the timestamp. */
    public String getTimestamp() {
        String pattern = "EEE MMM d HH:mm:ss yyyy Z";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String date = sdf.format(new Date());
        return date;
    }

    /** Get the initial timestamp. */
    public String getNewTimestamp() {
        String pattern = "EEE MMM d HH:mm:ss yyyy Z";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String date = sdf.format(new Date(0));
        return date;
    }

    public String getParent() {
        return this.parent;
    }

    public HashMap<String, String> getFiles() {
        return files;
    }

    public void init() {
        timestamp = getNewTimestamp();
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
                    + _message + "\n\n";
        } else {
            returnme = "===\n"
                    + "commit " + shaValue + "\n"
                    + "Merge: " + parent.substring(0, 7) + " " + mergedBranch.substring(0, 7) + "\n"
                    + "Date: " + timestamp + "\n"
                    + _message + "\n\n";
        }
        return returnme;
    }
    /** A mapping between file names and SHA value of file contents. */
    private HashMap<String, String> files = new HashMap<>();

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
