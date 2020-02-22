/**
 * TableFilter to filter for entries equal to a given string.
 *
 * @author Matthew Owen
 */
public class EqualityFilter extends TableFilter {
    public int index1;
    public String mat;
    public Table table;
    public EqualityFilter(Table input, String colName, String match) {
        super(input);
        index1 = input.colNameToIndex(colName);
        table = input;
        mat = match;
    }

    @Override
    protected boolean keep() {
        Table.TableRow current = _next;
        String one = new String (current.getValue(index1));
        if (one.equals(mat)) {
            return true;
        }

        return false;
    }

    // FIXME: Add instance variables?
}
