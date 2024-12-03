package day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        int total = 0;
        BufferedReader reader;

        try {
            System.out.println("READING FILE");
            reader = new BufferedReader(new FileReader("src/day1/input.txt"));
            String line = reader.readLine();
            while (line != null) {
                System.out.printf("Line : "+line);
                String[] split = line.split("   ");
                left.add(Integer.parseInt(split[0]));
                right.add(Integer.parseInt(split[1]));
                line = reader.readLine();
            }

            Collections.sort(left);
            Collections.sort(right);
            System.out.printf("LEFT : "+left.toString());
            System.out.printf("RIGHT : "+right.toString());

            for (int i = 0; i < left.size(); i++) {
                total += Math.abs(left.get(i) - right.get(i));
            }

            System.out.println("TOTAL :::::");
            System.out.println(total);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}