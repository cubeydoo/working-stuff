package gitlet;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Tyler Rathkamp
 */
public class Objects {
    /** Gitlet file. */
    public static File gitlet = Utils.join(System.getProperty("user.dir"), ".gitlet");
    /** A mapping between file names and SHA value of file contents. */
    public static File head = Utils.join(gitlet, "HEAD.txt");;
    /** A mapping between file names and SHA value of file contents. */
    public static File objects = Utils.join(gitlet, "objects");
    /** A mapping between file names and SHA value of file contents. */
    public static File staging = Utils.join(gitlet, "staging");
    /** Refs. */
    public static File refs = Utils.join(gitlet, "refs");
    /** A mapping between file names and SHA value of file contents. */
    public static File branch = Utils.join(refs, "branches");
    /** A mapping between file names and SHA value of file contents. */
    public static File commit = Utils.join(refs, "commit");
    /** toRemove. */
    public static File toRemove = Utils.join(gitlet, "toRemove.txt");

}
