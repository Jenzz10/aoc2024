package year2024.day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class main2 {

    public static void main(String[] args) {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        Map<Integer, Integer> rightMap = new HashMap<>();
        int total = 0;
        BufferedReader reader;

        try {
            System.out.println("READING FILE");
            reader = new BufferedReader(new FileReader("src/year2024/day1/input.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] split = line.split("   ");
                left.add(Integer.parseInt(split[0]));
                right.add(Integer.parseInt(split[1]));
                line = reader.readLine();
            }

            for (int i : right) {
                if(rightMap.get(i) == null){
                    rightMap.put(i, 1);
                }else{
                    rightMap.put(i, rightMap.get(i) + 1);
                }
            }

            System.out.println(left);
            System.out.println(rightMap);

            for (int j : left) {
                total += (j * (rightMap.get(j) == null ? 0 : rightMap.get(j)));
            }

            System.out.println("TOTAL :::::");
            System.out.println(total);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}