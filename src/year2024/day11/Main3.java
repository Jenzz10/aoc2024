package year2024.day11;

import java.io.IOException;
import java.util.*;

public class Main3 {
    static String input = "125 17";

    public static void main(String[] args) throws IOException {
        double start = System.nanoTime();
        System.out.println(readStones(25));
        double end = System.nanoTime();
        System.out.println((end - start) / 1_000_000);

    }


    public static double readStones(int times) throws IOException {
        List<String> stones = Arrays.asList(input.split(" "));
        for (int i = 0; i < times; i++) {
            Collections.sort(stones);
            stones = countStones(stones);
        }
        return stones.size();
    }


    public static List<String> countStones(List<String> stones) {
        List<String> newList = new ArrayList<>();
        for (String s : stones) {
            String removedLeadingsZeros = (Long.parseLong(s) + "");
            if ("0".equals(removedLeadingsZeros)) {
                newList.add("1");
            } else if ((removedLeadingsZeros.length() % 2 == 0)) {
                newList.add(removedLeadingsZeros.substring(0, removedLeadingsZeros.length() / 2));
                newList.add(removedLeadingsZeros.substring(removedLeadingsZeros.length() / 2));
            } else {
                newList.add((Long.parseLong(s) * 2024) + "");
            }
        }
        return newList;
    }


    public static double countStonesRecursive(String stone, int times, long currentTotal, int i) {
        if (times == i) {
            return currentTotal;
        }
        String removedLeadingsZeros = (Long.parseLong(stone) + "");

        if ("0".equals(removedLeadingsZeros)) {
            return countStones("1", times, currentTotal, i + 1);
        } else if ((removedLeadingsZeros.length() % 2 == 0)) {
            int j = i + 1;
            return countStones(removedLeadingsZeros.substring(0, removedLeadingsZeros.length() / 2), times, currentTotal, j) + countStones(removedLeadingsZeros.substring(removedLeadingsZeros.length() / 2), times, currentTotal, j);
        } else {
            return countStones("" + Long.parseLong(removedLeadingsZeros) * 2024, times, currentTotal, i + 1);
        }
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

            if (removedLeadingsZeros.length() > (stepsToGo * 2) && !removedLeadingsZeros.contains("0")) {
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

