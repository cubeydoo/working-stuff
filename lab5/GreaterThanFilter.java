/**
 * TableFilter to filter for entries greater than a given string.
 *
 * @author Matthew Owen
 */
public class GreaterThanFilter extends TableFilter {
    public int index1;
    public String mat;
    public Table table;
    public GreaterThanFilter(Table input, String colName, String ref) {
        super(input);
        index1 = input.colNameToIndex(colName);
        table = input;
        mat = ref;
    }

    @Override
    protected boolean keep() {
        Table.TableRow current = _next;
        String one = new String (current.getValue(index1));
        if (one.compareTo(mat) >= 0) {
            return true;
        }
        return false;
    }


}
