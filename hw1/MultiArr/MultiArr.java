import net.sf.saxon.lib.SaxonOutputKeys;

/** Multidimensional array
 *  @author Zoe Plaxco
 */

public class MultiArr {

    /**
    {{“hello”,"you",”world”} ,{“how”,”are”,”you”}} prints:
    Rows: 2
    Columns: 3
    
    {{1,3,4},{1},{5,6,7,8},{7,9}} prints:
    Rows: 4
    Columns: 4
    */
    public static void printRowAndCol(int[][] arr) {
        //TODO: Your code here!
        int columns = 0;
        int rows = arr.length;
        for (int i = 0; i < arr.length; i++){
            columns = Math.max(columns, arr[0].length);
        }
        System.out.println("Rows: " + rows);
        System.out.println("Columns: " + columns);
    } 

    /**
    @param arr: 2d array
    @return maximal value present anywhere in the 2d array
    */
    public static int maxValue(int[][] arr) {
        //TODO: Your code here!
        int max = 0;
        for (int i = 0; i < arr.length; i++){
            for (int x = 0; x < arr[i].length; x++){
                max = Math.max(max, arr[i][x]);
            }
        }
        return max;
    }

    /**Return an array where each element is the sum of the 
    corresponding row of the 2d array*/
    public static int[] allRowSums(int[][] arr) {
        //TODO: Your code here!!
        int[] return_value = new int[arr.length];
        int row_total = 0;
        for (int i = 0; i < arr.length; i++){
            for (int x = 0; x < arr[i].length; x++){
                row_total += arr[i][x];
            }
            return_value[i] = row_total;
            row_total = 0;
        }
        return return_value;
    }
}