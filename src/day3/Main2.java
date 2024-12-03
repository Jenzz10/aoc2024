package day3;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main2 {

    public static void main(String[] args) {
        int total = 0;
        try {
            String content = new Scanner(new File("src/day3/input.txt")).useDelimiter("\\Z").next();
            String[] enabled = content.split("do\\(\\)");
            for (String s : enabled) {
                String[] disabled = s.split("don't\\(\\)");
                total += findTotal(disabled[0]);
            }
            System.out.println(total);
        } catch (FileNotFoundException e) {
        }
    }

    public static int findTotal(String si) {
        int total = 0;
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile("(mul\\(\\d*,\\d*\\))")
                .matcher(si);
        while (m.find()) {
            allMatches.add(m.group());
        }
        for (String s : allMatches) {
            String r = s.replace("mul(", "").replace(")", "");
            String[] mi = r.split(",");
            total += Integer.parseInt(mi[0]) * Integer.parseInt(mi[1]);
        }
        return total;
    }
}