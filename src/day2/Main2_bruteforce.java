package day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main2_bruteforce {

    public static void main(String[] args) {
        int total = 0;
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("src/day2/input.txt"));
            String line = reader.readLine();
            while (line != null) {
                List<String> split = Arrays.stream(line.split(" "))
                        .collect(Collectors.toList());
                tester result = testLine(split);
                if (result.ok()) {
                    total += 1;
                } else {
                    int j = 0;
                    while (!result.ok() && j < split.size()) {
                        List<String> copy = copy(split);
                        copy.remove(j);
                        result = testLine(copy);
                        j++;
                        if (result.ok()) {
                            total++;
                        }
                    }
                    if (!result.ok()) {
                        System.out.println(split + " failed");
                    }
                }

                line = reader.readLine();
            }

            System.out.println("TOTAL :::::");
            System.out.println(total);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static tester testLine(List<String> split) {
        boolean ok = true;
        int firstDifference = (Integer.parseInt(split.get(0)) - Integer.parseInt(split.get(1)));
        boolean isAscending = firstDifference < 0;
        int i = 1;
        while (ok && i < split.size()) {
            int diff = (Integer.parseInt(split.get(i - 1)) - Integer.parseInt(split.get(i)));
            if (isAscending) {
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
        return new tester(ok, i);
    }


    static List<String> copy(List<String> list) {
        List<String> copy = new ArrayList<>();
        for (String s : list) {
            copy.add(s + "");
        }
        return copy;
    }

}
record tester(boolean ok, int failedLine) { }