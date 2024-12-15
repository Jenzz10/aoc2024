package year2024.day11;

import java.io.IOException;
import java.util.*;

public class Main6 {
    static String input = "8069 87014 98 809367 525 0 9494914 5";
    static int times = 75;

    public static void main(String[] args) throws IOException {
        double start = System.nanoTime();
        readStones();
        double end = System.nanoTime();
        System.out.println();
        System.out.println((end - start) / 1_000_000);
    }


    public static void readStones() throws IOException {
        double total = 0;

        String[] stoneArray = input.split(" ");
        Map<String, Double> stones = new HashMap<>();
        for (int j = 0; j < stoneArray.length; j++) {
            stones.put(stoneArray[j], 1.0);
        }

        stones = countStones(stones, times, 0);

        for (String s : stones.keySet()) {
            total += stones.get(s);
        }

        System.out.printf("%f", total);
    }


    public static Map<String, Double> countStones(Map<String, Double> stones, int times, int index) {
        if (times == index) {
            return stones;
        }

        Map<String, Double> blink = new HashMap<>();

        for (String stone : stones.keySet()) {
            String removedLeadingsZeros = (Long.parseLong(stone) + "");
            if ("0".equals(removedLeadingsZeros)) {
                String newStone = "1";
                blink.merge(newStone, stones.get(stone), Double::sum);
            } else if (removedLeadingsZeros.length() % 2 == 0) {
                String stone1 = removedLeadingsZeros.substring(0, removedLeadingsZeros.length() / 2);
                String stone2 = removedLeadingsZeros.substring(removedLeadingsZeros.length() / 2);
                blink.merge(stone1, stones.get(stone), Double::sum);
                blink.merge(stone2, stones.get(stone), Double::sum);
            } else {
                String newStone = Long.parseLong(removedLeadingsZeros) * 2024 + "";
                blink.merge(newStone, stones.get(stone), Double::sum);
            }
        }
        return countStones(blink, times, index + 1);
    }



}

