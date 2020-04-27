package gitlet;

import java.io.File;
import java.util.HashMap;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Tyler Rathkamp
 */
public class Display extends Objects {
    public void log() {
        Commit lastCommit = getCommit("HEAD");
        String returnMe = "";
        while (lastCommit.getParent() != null) {
            returnMe = lastCommit.toString() + returnMe;
            lastCommit = getCommitfromSHA(lastCommit.getParent());
        }
        returnMe = lastCommit.toString() + returnMe;
        System.out.println(returnMe);
    }

}
