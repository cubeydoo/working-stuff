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
    private String source;
    private String key;
    private Reader r;
    public TrReader(Reader reader, String from, String to) {
        source = from;
        key = to;
        r = reader;
    }


    /* TODO: IMPLEMENT ANY MISSING ABSTRACT METHODS HERE
     * NOTE: Until you fill in the necessary methods, the compiler will
     *       reject this file, saying that you must declare TrReader
     *       abstract. Don't do that; define the right methods instead!
     */
    public void close() throws IOException {

    }

    public int read(char[] arr, int offset, int length) throws IOException {
        int counter = 0;
        if (arr.length == 0) {
            return -1;
        } else {
            int numRead = r.read(arr, offset, length);
            for (int i = 0; i <= arr.length -1; i++) {
                char current = arr[i];
                int index = source.indexOf(current);
                if (index != -1) {
                    arr[i] = key.charAt(index);
                }
            }
            return length;
        }


    }
}
