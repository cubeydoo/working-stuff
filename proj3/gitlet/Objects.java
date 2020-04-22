package gitlet;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Tyler Rathkamp
 */
public class Objects {
    /** A mapping between file names and SHA value of file contents. */
    public static File head;
    /** A mapping between file names and SHA value of file contents. */
    public static File objects;
    /** A mapping between file names and SHA value of file contents. */
    public static File staging;
    /** A mapping between file names and SHA value of file contents. */
    public static File branch;
    /** A mapping between file names and SHA value of file contents. */
    public static File commit;
    /** Refs. */
    public static File refs;
    /** toRemove. */
    public static File toRemove;


}
