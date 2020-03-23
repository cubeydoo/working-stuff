import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Test of a BST-based String Set.
 * @author
 */
public class BSTStringSetTest  {

    @Test
    public void testNothing() {
    }

    @Test
    public void testOne() {
        BSTStringSet me = new BSTStringSet();
        me.put("woo");
        me.put("ok");
        String[] answers = new String[2];
        answers[0] = "ok";
        answers[1] = "woo";
        List<String> answers2 = me.asList();
        for (int i = 0; i < answers.length - 1; i++) {
            assertTrue(answers[0].equals(answers2.get(i)));
        }
    }
}
