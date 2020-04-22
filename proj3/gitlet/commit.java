package gitlet;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Tyler Rathkamp
 */
public class commit {
    /** A mapping between file names and SHA value of file contents */
    private HashMap files = new HashMap();

    /** SHA-1 hash value referencing parent commit */
    private String parent;

    /** — SHA-1 hash value referencing head of
     * branch that was merged in. */
    private String mergedBranch;

    /** — Time at which commit was created. */
    private String timestamp;

    /** A string that stores the commit message. */
    private String message;

    /** Helper variable for remove(file). */
    private static ArrayList<String> toRemove = new ArrayList<String>();


}
