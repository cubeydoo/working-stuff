package gitlet;
import static gitlet.Objects.*;

import java.io.File;
import java.util.HashMap;

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
        String[] fileNames = Utils.plainFilenamesIn(BRANCH).toArray(new String[0]);
        String returnMe = "";
        for (String branchName : fileNames) {
            Commit lastCommit = getCommit(branchName);
            returnMe = returnMe + lastCommit.toString();
        }
        System.out.println(returnMe);
    }
}
