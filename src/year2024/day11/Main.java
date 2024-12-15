package year2024.day11;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    static int GRIDSIZE = 57;
    static String input = "125 17";


    public static void main(String[] args) throws IOException {
        double start = System.nanoTime();
        System.out.println(readStones());
        double end = System.nanoTime();
        System.out.println((end - start) / 1_000_000);
    }


    public static int readStones() throws IOException {
        int total = 0;
        String[] stones = input.split(" ");
        for (int i = 0; i < stones.length; i++) {
            total += countStones(stones[i], 25, 1, 0);
        }
        return total;
    }


    public static int countStones(String stone, int times, int currentTotal, int i) {
        if (times == i) {
            return currentTotal;
        }

        String removedLeadingsZeros = (Long.parseLong(stone) + "");

        if ("0".equals(removedLeadingsZeros)) {
            return countStones("1", times, currentTotal, i + 1);
        } else if (removedLeadingsZeros.length() % 2 == 0) {
            int j = i + 1;
            return countStones(removedLeadingsZeros.substring(0, removedLeadingsZeros.length() / 2), times, currentTotal, j) + countStones(removedLeadingsZeros.substring(removedLeadingsZeros.length() / 2), times, currentTotal, j);
        } else {
            return countStones("" + Long.parseLong(removedLeadingsZeros) * 2024, times, currentTotal, i + 1);
        }
    }


}

