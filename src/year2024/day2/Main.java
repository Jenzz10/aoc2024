package year2024.day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        int total = 0;
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("src/year2024/day2/input.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] split = line.split(" ");
                System.out.println(line);
                boolean ok = true;
                int firstDifference = (Integer.parseInt(split[0]) - Integer.parseInt(split[1]));
                boolean isAscending = firstDifference < 0;
                System.out.println(isAscending ? " Ascending " : " Descending");
                int i = 1;
                while (ok && i < split.length) {
                    int diff = (Integer.parseInt(split[i - 1]) - Integer.parseInt(split[i]));
                    if (isAscending) {
                        System.out.println("diff = " + diff);
                        if (diff > -1 || diff <= -4) {
                            ok = false;
                        }
                    } else {
                        if (diff < 1 || diff >= 4) {
                            ok = false;
                        }
                    }
                    i++;
                }
                if (ok) {
                    System.out.println(line + " is ok");
                    total += 1;
                } else {
                    System.out.println(line + " is not ok");
                }
                ok = true;
                line = reader.readLine();
            }

            System.out.println("TOTAL :::::");
            System.out.println(total);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}