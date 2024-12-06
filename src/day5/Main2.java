package day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main2 {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int total = 0;

        BufferedReader reader;
        Map<String, List<String>> order = new HashMap<>();
        try {
            reader = new BufferedReader(new FileReader("src/day5/input.txt"));
            String line = reader.readLine();

            while (line != null) {
                String[] lineArray = line.split("\\|");
                if (order.get(lineArray[0]) == null) {
                    List<String> a = new ArrayList<>();
                    a.add(lineArray[1]);
                    order.put(lineArray[0], a);
                } else {
                    order.get(lineArray[0]).add(lineArray[1]);
                }
                line = reader.readLine();
            }

            reader = new BufferedReader(new FileReader("src/day5/input2.txt"));
            line = reader.readLine();
            while (line != null) {
                String[] lineArray = line.split(",");

                if (!isLineCorrect(lineArray, order)) {
                    //lazy approach. The correctLine function works good enough to fix it in 1 or 2 attempts.
                    while(!isLineCorrect(lineArray, order)){
                        lineArray = correctLine(lineArray, order);
                    }
                    String middle = lineArray[Math.abs((lineArray.length) / 2)];
                    total += Integer.parseInt(middle);
                }
                line = reader.readLine();
            }
            System.out.println("TOTAL :::::");
            System.out.println(total);
            System.out.println((System.currentTimeMillis() - start) + "ms" );
            reader.close();

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] correctLine(String[] lineArray, Map<String, List<String>> order) {
        List<String> pastNumbers = new ArrayList<>();
        pastNumbers.add(lineArray[0]);
        for (int i = 1; i < lineArray.length; i++) {
            String number = lineArray[i];
            //If not, then it has no requirements
            if (order.get(number) != null) {
                List<String> requirements = order.get(number);
                if (!Collections.disjoint(requirements, pastNumbers)) {
                    lineArray = movePositionToFront(lineArray,i);
                    //retry this position
                    i--;
                    continue;
                }
            }
            pastNumbers.add(number);
        }
        return lineArray;
    }

    public static String[] movePositionToFront(String[] array, int position) {
        String value = array[position];
        String tempValue = array[position -1];
        array[position -1 ]= value;
        array[position] = tempValue;
        return array;
    }

    public static boolean isLineCorrect(String[] lineArray, Map<String, List<String>> order) {
        boolean lineIsCorrect = true;
        List<String> pastNumbers = new ArrayList<>();
        pastNumbers.add(lineArray[0]);
        for (int i = 1; i < lineArray.length; i++) {
            String number = lineArray[i];
            //If not, then it has no requirements
            if (order.get(number) != null) {
                List<String> requirements = order.get(number);
                if (!Collections.disjoint(requirements, pastNumbers)) {
                    lineIsCorrect = false;
                    break;
                }
            }
            pastNumbers.add(number);

        }
        return lineIsCorrect;
    }

}