package day8;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainOptimized {
    static int GRIDSIZE = 50;
    static String file = "src/day8/input.txt";

    static Map<String, List<point>> antennas = new HashMap<>();
    static Set<String> antinodes = new HashSet<>();

    public static void main(String[] args) {
        double start = System.nanoTime();
        initiateMap();
        double end = System.nanoTime();
        System.out.println((end - start) / 1000000);
        System.out.println("number of antinodes " + antinodes.size());
    }
    public static void initiateMap() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            int i = 0;
            while (line != null) {
                Pattern pattern = Pattern.compile("\\d|[a-z]|[A-Z]");
                Matcher matcher = pattern.matcher(line);
                while(matcher.find()){
                    String c = matcher.group();
                    List<point> a = antennas.get(c);
                    int j = matcher.start();
                    if (a != null) {
                        point p = new point(i, j);
                        for (point q : a) {
                            int differenceX = p.x - q.x;
                            int differenceY = p.y - q.y;

                            if((q.x - differenceX) >= 0 && (q.x - differenceX) < GRIDSIZE && (q.y - differenceY) >= 0 && (q.y - differenceY < GRIDSIZE)){
                                antinodes.add((q.x - differenceX)+","+(q.y - differenceY));
                            }
                            if((p.x + differenceX) >= 0 && (p.x + differenceX) < GRIDSIZE && (p.y + differenceY) >= 0 && (p.y + differenceY < GRIDSIZE)){
                                antinodes.add((p.x + differenceX) + ","+ (p.y + differenceY ));
                            }
                        }
                        antennas.get(c).add(p);

                    } else {
                        var l = new ArrayList<point>();
                        l.add(new point(i, j));
                        antennas.put(c, l);
                    }
                }
                i++;
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    record point(int x, int y) {
    }

}

