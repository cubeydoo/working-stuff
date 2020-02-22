/**
 * TableFilter to filter for containing substrings.
 *
 * @author Matthew Owen
 */
public class SubstringFilter extends TableFilter {
    public int index1;
    public String mat;
    public Table table;
    public SubstringFilter(Table input, String colName, String subStr) {
        super(input);
        index1 = input.colNameToIndex(colName);
        table = input;
        mat = subStr;        }

    @Override
    protected boolean keep() {
        Table.TableRow current = _next;
        String one = new String (current.getValue(index1));
        if (one.contains(mat)) {
            return true;
        }
        return false;
    }

    // FIXME: Add instance variables?
}
