package year2024.day11;

import java.io.IOException;
import java.util.*;

public class Main2_2 {
    static String input = "2";

    static Map<Integer, List<String>> blinks = new HashMap<>();



    public static void main(String[] args) throws IOException {
        double start = System.nanoTime();

        //System.out.println(readStones(2,0));
        double end = System.nanoTime();

        System.out.println((end - start) / 1000000);
    }






    public static boolean isPowerOfTwoUsingBitwiseOperation(int n) {
        return (n != 0) && ((n & (n - 1)) == 0);
    }


}

