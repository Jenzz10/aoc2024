package day8;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainOptimizedScanner {
    static int GRIDSIZE = 50;
    static String file = "src/day8/input.txt";
    static Map<String, List<point>> antennas = new HashMap<>();
    static int o = 0;
    static int size = 2500; // Number of booleans
    static int longSize = (size + 63) / 64; // Number of longs needed
    static long[] bitArray = new long[longSize];


    public static void main(String[] args) throws IOException {
        String content = new Scanner(new File("src/day8/input.txt")).useDelimiter("\\Z").next();

        double start = System.nanoTime();
        findNumberOfAntinodes(content.split("\n"));
        double end = System.nanoTime();

        for(int i = 0; i<GRIDSIZE*GRIDSIZE; i++){
            if(getBit(i)){
                o++;
            }
        }

        System.out.println((end - start) / 1000000);
        System.out.println("number of antinodes " + o);
    }

    public static void findNumberOfAntinodes(String[] lines) throws IOException {
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            Pattern pattern = Pattern.compile("\\d|[a-z]|[A-Z]");
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                String c = matcher.group();
                List<point> a = antennas.get(c);
                int j = matcher.start();
                if (a != null) {
                    point p = new point(i, j);
                    for (point q : a) {
                        int differenceX = p.x - q.x;
                        int differenceY = p.y - q.y;
                        if ((q.x - differenceX) >= 0 && (q.x - differenceX) < GRIDSIZE && (q.y - differenceY) >= 0 && (q.y - differenceY < GRIDSIZE)) {
                            setBit(((q.x - differenceX) * GRIDSIZE) + (q.y - differenceY));
                        }
                        if ((p.x + differenceX) >= 0 && (p.x + differenceX) < GRIDSIZE && (p.y + differenceY) >= 0 && (p.y + differenceY < GRIDSIZE)) {
                            setBit(((p.x + differenceX) * GRIDSIZE) + (p.y + differenceY));
                        }
                    }
                    antennas.get(c).add(p);
                } else {
                    var l = new ArrayList<point>();
                    l.add(new point(i, j));
                    antennas.put(c, l);
                }
            }
        }
    }

    record point(int x, int y) {
    }

    public static void setBit(int index) {
        int longIndex = index / 64;
        int bitIndex = index % 64;
        bitArray[longIndex] |= (1L << bitIndex); // Set bit to 1
    }
    // Get a specific bit
    public static boolean getBit(int index) {
        int longIndex = index / 64;
        int bitIndex = index % 64;
        return (bitArray[longIndex] & (1L << bitIndex)) != 0;
    }

}

