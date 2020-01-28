public class max {
    public static void main(String[] args) {
      System.out.println(max(new int[] {1, 2, 3}));
      System.out.println(max(new int[] {5, 2, 3}));
    }
    public static int max(int[] array){
        int current = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > current) {
                current = array[i];
            }

        }
        return current;
    }
}
