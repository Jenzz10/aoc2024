package year2024.day19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class main {
    static String file = "src/year2024/day19/test.txt";

    static List<String> availableTowels;
    static List<String> wantedDesigns = new ArrayList<>();

    static Map<String, Long> cache = new HashMap<>();

    public static void main(String[] args) throws IOException {

        int total = 0;
        double start = System.nanoTime();
        findTowels();
        Collections.sort(availableTowels, Comparator.comparing(String::length));
        Collections.reverse(availableTowels);

        int i = 1;
        for (String s : wantedDesigns) {
            List<String> filteredAvailableTowels = new ArrayList<>();

            //Only try with towels that actually fit anyway.
          /*  for (String towel : availableTowels) {
                if (s.contains(towel.trim())) {
                    filteredAvailableTowels.add(towel.trim());
                }
            }*/

            //  System.out.println("Testing wanted design : " + s + "(" + i + "/" + wantedDesigns.size() + ")");

            if (isDesignPossible2(s.trim()) > 0) {
                System.out.println("ok");
                total++;
            } else {
                //System.out.println(s);
            }
            i++;
        }
        System.out.println(availableTowels);
        System.out.println(wantedDesigns);
        System.out.println(total);


        double end = System.nanoTime();
        System.out.println("Found answer in " + (end - start) / 1_000_000);
    }

    static long isDesignPossible2(String wantedDesign) {
        long count = 0;

        if (cache.get(wantedDesign) != null) {
            return cache.get(wantedDesign);
        }

        if(wantedDesign.equals("")){
            count+=1;
        }

        for (String s : availableTowels) {
            s = s.trim();
            if (wantedDesign.indexOf(s) == 0) {
                String filteredDesign = wantedDesign.substring(s.length());
                count += isDesignPossible2(filteredDesign);
            }
        }
        cache.put(wantedDesign, count);

        return count;
    }


    public static void findTowels() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String availableTowelsString = "";
        String line = reader.readLine();
        while (!line.equals("")) {
            availableTowelsString += line;
            line = reader.readLine();
        }
        availableTowels = Arrays.asList(availableTowelsString.split(","));
        line = reader.readLine();
        while (line != null) {
            wantedDesigns.add(line);
            line = reader.readLine();
        }
    }


}

