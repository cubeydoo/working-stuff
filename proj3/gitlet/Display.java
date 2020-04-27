package gitlet;

import java.io.File;
import java.util.HashMap;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Tyler Rathkamp
 */
public class Display extends Objects {
    public void log() {
        String latestCommit = Utils.readContentsAsString(head);
        File correctBranch = new File(latestCommit);
        String shaVal = Utils.readContentsAsString(correctBranch);
        correctBranch = Utils.join(commit, shaVal);
        Commit lastCommit = Utils.readObject(correctBranch, Commit);
    }
}
