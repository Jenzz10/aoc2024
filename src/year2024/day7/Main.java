package year2024.day7;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {


    public static List<Long> possibleAnswer = new ArrayList<>();
    public static void main(String[] args) {
        long start = System.nanoTime();
        System.out.println(solve());
        long end = System.nanoTime();
        System.out.println((end - start) / 1000000 );

    }


    public static long solve() {
        long total = 0;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("src/year2024/day7/input.txt"));
            String line = reader.readLine();

            while (line != null) {
                String[] a = line.split(":");
                long sum = Long.parseLong(a[0]);
                List<Long> numbers = Arrays.stream(a[1].trim().split(" ")).map(e -> Long.parseLong(e)).toList();
                boolean success = recursion(0, numbers, 0, sum);
                if (success) {
                    total += sum;
                }
                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return total;
    }


    public static boolean recursion(int i, List<Long> remaining, long value, long sum){
        if(i == remaining.size() || value > sum){
            return value == sum;
        }
        return recursion(i+1, remaining, value + remaining.get(i), sum)
                || recursion(i+1, remaining, value * remaining.get(i), sum);
    }



}

