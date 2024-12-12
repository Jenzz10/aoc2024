package year2024.day11;

import java.io.IOException;
import java.util.Calendar;
import java.util.Formatter;

public class Main2 {
    static String input = "8069 87014 98 809367 525 0 9494914 5";


    public static void main(String[] args) throws IOException {
        double start = System.nanoTime();
        System.out.println(readStones(25));
        double end = System.nanoTime();

        System.out.println((end - start) / 1000000);
    }


    public static double readStones(int times) throws IOException {
        Formatter format = new Formatter();

        Calendar now = Calendar.getInstance();

        // Get the current hour and minute as parameters

        double total = 0;

        String[] stones = input.split(" ");

        for (int i = 0; i < stones.length; i++) {
            total += countStones(stones[i], times, 1, 0);
        }
        return total;
    }


    public static double countStones(String stone, int times, long currentTotal, int i) {
        if (times == i) {
            return currentTotal;
        }
        String removedLeadingsZeros = (Long.parseLong(stone) + "");

        if ("0".equals(removedLeadingsZeros)) {
            return countStones("1", times, currentTotal, i + 1);
        } else if ((removedLeadingsZeros.length() % 2 == 0) && isPowerOfTwoUsingBitwiseOperation(removedLeadingsZeros.length())) {
            int stepsToGo = times - i;
            if (removedLeadingsZeros.length() > stepsToGo && !removedLeadingsZeros.contains("0") && (Math.log(removedLeadingsZeros.length()) / Math.log(2) > stepsToGo)) {
                return Math.pow(2, stepsToGo);
            }
            int j = i + 1;
            return countStones(removedLeadingsZeros.substring(0, removedLeadingsZeros.length() / 2), times, currentTotal, j) + countStones(removedLeadingsZeros.substring(removedLeadingsZeros.length() / 2), times, currentTotal, j);
        } else if ((removedLeadingsZeros.length() % 2 == 0)) {
            int j = i + 1;
            return countStones(removedLeadingsZeros.substring(0, removedLeadingsZeros.length() / 2), times, currentTotal, j) + countStones(removedLeadingsZeros.substring(removedLeadingsZeros.length() / 2), times, currentTotal, j);
        } else {
            return countStones("" + Long.parseLong(removedLeadingsZeros) * 2024, times, currentTotal, i + 1);
        }
    }

    public static boolean isPowerOfTwoUsingBitwiseOperation(int n) {
        return (n != 0) && ((n & (n - 1)) == 0);
    }


}

