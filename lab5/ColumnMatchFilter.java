/**
 * TableFilter to filter for entries whose two columns match.
 *
 * @author Matthew Owen
 */
public class ColumnMatchFilter extends TableFilter {
    public int index1;
    public int index2;
    public Table table;
    public ColumnMatchFilter(Table input, String colName1, String colName2) {
        super(input);
        index1 = input.colNameToIndex(colName1);
        index2 = input.colNameToIndex(colName2);
        table = input;


    }

    @Override
    protected boolean keep() {
        Table.TableRow current = _next;
        if (current.getValue(index1) == current.getValue(index2)) {
            return true;
        }
        return false;
    }
}
