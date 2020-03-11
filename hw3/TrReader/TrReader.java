import java.io.Reader;
import java.io.IOException;

/** Translating Reader: a stream that is a translation of an
 *  existing reader.
 *  @author Tyler Rathkamp
 */
public class TrReader extends Reader {
    /**
     * A new TrReader that produces the stream of characters produced
     * by STR, converting all characters that occur in FROM to the
     * corresponding characters in TO.  That is, change occurrences of
     * FROM.charAt(i) to TO.charAt(i), for all i, leaving other characters
     * in STR unchanged.  FROM and TO must have the same length.
     */
    /** Original side of the key. */
    private String source;
    /** Key that corresponds to source. */
    private String key;
    /** Reader passed in on initialization. */
    private Reader r;
    /** Initializes the object.
     * @param reader Reader.
     * @param from Characters that should be changed to match to.
     * @param to characters that from should change into.*/
    public TrReader(Reader reader, String from, String to) {
        source = from;
        key = to;
        r = reader;
    }

<<<<<<< HEAD

/**
     * NOTE: Until you fill in the necessary methods, the compiler will
     *       reject this file, saying that you must declare TrReader
     *       abstract. Don't do that; define the right methods instead!
=======
/**
     closes the file.
>>>>>>> origin/best
     */
    public void close() throws IOException {
        r.close();
    }
    /** Reads the file from a certain index.
     * @param arr source of the text.
     * @param offset how far into the array we should start.
     * @param length how many lines the program should read.
     * @return number of chars read, or -1 if array was null*/
    public int read(char[] arr, int offset, int length) throws IOException {
        int counter = 0;
        if (arr.length == 0) {
            return -1;
        } else {
            int numRead = r.read(arr, offset, length);
            for (int i = 0; i <= arr.length - 1; i++) {
                char current = arr[i];
                int index = source.indexOf(current);
                if (index != -1) {
                    arr[i] = key.charAt(index);
                }
            }
            return numRead;
        }


    }
}
