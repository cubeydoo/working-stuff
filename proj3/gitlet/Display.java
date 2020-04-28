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
            returnMe = lastCommit.toString() + returnMe;
            lastCommit = Objects.getCommitfromSHA(lastCommit.getParent());
        }
        returnMe = lastCommit.toString() + returnMe;
        System.out.println(returnMe);
    }

}
