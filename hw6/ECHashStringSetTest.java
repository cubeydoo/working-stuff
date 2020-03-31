import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/**
 * Test of a BST-based String Set.
 * @author
 */
public class ECHashStringSetTest  {

    @Test
    public void testOne() {
        ECHashStringSet me = new ECHashStringSet();
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
