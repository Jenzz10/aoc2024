package day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    static int MAX = 140;

    public static void main(String[] args) {
        int total = 0;

        BufferedReader reader;
        Map<String, List<String>> order = new HashMap<>();
        try {
            reader = new BufferedReader(new FileReader("src/day5/input.txt"));
            String line = reader.readLine();

            while (line != null) {
                String[] lineArray = line.split("\\|");
                if(order.get(lineArray[0]) == null){
                    List<String> a = new ArrayList<>();
                    a.add(lineArray[1]);
                    order.put(lineArray[0],a);
                }else{
                   order.get(lineArray[0]).add(lineArray[1]);
                }
                line = reader.readLine();
            }

            reader = new BufferedReader(new FileReader("src/day5/input2.txt"));
             line = reader.readLine();
            while (line != null) {
                String[] lineArray = line.split(",");

                if(isLineCorrect(lineArray, order)){
                    String middle = lineArray[Math.abs((lineArray.length)/2)];
                    total+=Integer.parseInt(middle);
                }
                line = reader.readLine();
            }


            System.out.println(order);

            System.out.println("TOTAL :::::");
            System.out.println(total);
            reader.close();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }



    public static boolean isLineCorrect(String[] lineArray, Map<String,List<String>> order) {
        boolean lineIsCorrect = true;
        List<String> pastNumbers = new ArrayList<>();
        pastNumbers.add(lineArray[0]);
        int numberOfFaults=0;
        for (int i = 1; i < lineArray.length; i++) {
            String number = lineArray[i];
            //If not, then it has no requirements
            if (order.get(number) != null) {
                List<String> requirements = order.get(number);
                if (!Collections.disjoint(requirements, pastNumbers)) {
                    lineIsCorrect = false;
                    numberOfFaults++;
                }
            }
            pastNumbers.add(number);

        }
        if(numberOfFaults > 1)
            System.out.println("number of faults = " +numberOfFaults);
        return lineIsCorrect;
    }

}