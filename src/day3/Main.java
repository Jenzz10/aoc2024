package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        int total = 0;
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("/Users/jens.steppe/Projects/aoc/AoC/src/day3/control.txt"));
            String line = reader.readLine();



            List<String> allMatches = new ArrayList<String>();
            while (line != null) {
                ///String[] split = line.split("(mul\\\\(\\d*,\\d*\\\\))");
                Matcher m = Pattern.compile("(mul\\(\\d*,\\d*\\))")
                        .matcher(line);
                while (m.find()) {
                    allMatches.add(m.group());
                }
                System.out.println(allMatches);
                line = reader.readLine();
            }
            for(String s : allMatches){
                String r = s.replace("mul(", "").replace(")", "");
                String[] m = r.split(",");
                total += Integer.parseInt(m[0]) * Integer.parseInt(m[1]);
            }



            System.out.println("TOTAL :::::");
            System.out.println(total);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}