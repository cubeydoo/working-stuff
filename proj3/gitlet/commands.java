package gitlet;

import java.io.File;
import static gitlet.Objects.*;
import java.util.ArrayList;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Tyler Rathkamp
 */
public class Commands {
    /** The last commit made on the current branch. */
    private Commit lastCommit = getCommit("HEAD");

    public static void addFile(String fileName) {
        String[] currentWD = Utils.plainFilenamesIn(cwd).toArray(new String[0]);
        boolean flag = false;
        for (String string : currentWD) {
            if(string.equals(fileName)) {
                flag = true;
            }
        }
        if (flag) {
            File current = new File(fileName);
            String contents = Utils.readContentsAsString(current);
            File output = Utils.join(staging, fileName);
            Utils.writeContents(output, contents);
        } else {
            System.out.println("File does not exist.");
        }
    }
}
