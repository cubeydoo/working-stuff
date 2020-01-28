public class Max {
    public static void main(String[] args) {
      System.out.println(max(new int[] {1, 2, 3}));    
    }
    public Max(int[] array){
        int current = array[0];
        for (int i = 0; i < array.length-1; i++) {
            if (array[i] > current) {
                current = array[i];
            }

        }
        return current;
    }
}
