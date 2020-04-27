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

    public Commit getCommit(String branchHead) {
        File correctBranch;
        if (branchHead.equals("HEAD")) {
            String latestCommit = Utils.readContentsAsString(head);
            correctBranch = new File(latestCommit);
        } else {
            correctBranch = Utils.join(branch, branchHead);
        }
        String shaVal = Utils.readContentsAsString(correctBranch);
        getCommitfromSHA(shaVal);
    }

    public Commit getCommitfromSHA(String shaVal) {
        File correctBranch = Utils.join(commit, shaVal);
        Commit lastCommit = Utils.readObject(correctBranch, Commit.class);
        return lastCommit;
    }
}
