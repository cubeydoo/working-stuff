public class utility {
    public static boolean threeSum(int[] array){
        for (int first = 0; first < array.length; first++) {
            for (int second = 0; second < array.length; second++) {
                for (int i = 0; i < array.length; i++) {
                    if (array[first] + array[second] + array[i] == 0) {
                        return true;
                    }

                }
            }
        }
        return false;
    }
    public static void main(String[] args){
        System.out.println(threeSum(new int[]{-6, 2, 5}));
    }
}