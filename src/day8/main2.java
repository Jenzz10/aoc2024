package day8;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class main2 {
    static int GRIDSIZE = 50;
    static String file = "src/day8/input.txt";

    static Map<Character, List<point>> antennas = new HashMap<>();
    static Set<point> antinodes = new HashSet<>();

    public static void main(String[] args) {
        PositionAndMap position = initiateMap();

        //System.out.println("TOTAL");
        double start = System.nanoTime();
        findAntennas(position);
        findAllAntiNodes();
        double end = System.nanoTime();
        System.out.println((end - start) + " nano seconds or " + (end - start) / 1000000 + "ms");
        //System.out.println("number of antinodes " + antinodes.size());
    }

    public static void findAntennas(PositionAndMap positionAndMap) {

        char[][] map = positionAndMap.map;
        for (int i = 0; i < GRIDSIZE; i++) {
            for (int j = 0; j < GRIDSIZE; j++) {
                char c = map[i][j];
                if (String.valueOf(c).matches("\\d|[a-z]|[A-Z]")) {
                    if (antennas.get(Character.valueOf(c)) != null) {
                        antennas.get(Character.valueOf(c)).add(new point(i, j));
                    } else {
                        var l = new ArrayList<point>();
                        l.add(new point(i, j));
                        antennas.put(Character.valueOf(c), l);
                    }
                }
            }
        }

    }

    public static void findAllAntiNodes() {

        for (Character c : antennas.keySet()) {
            Set<point> uniqueAntiNodesForAntenna = new HashSet<>();
            for (point p : antennas.get(c)) {
                for (point q : antennas.get(c)) {
                    //same point
                    if (!q.equals(p)) {
                        //because we are creating a cartesian product we will encounter the same combination of
                        //points twice. So sort them in mostTopLeft en mostDownRight so the distance calculation is easier
                        point mostTopLeft = null;
                        point mostDownRight = null;

                        if (p.x < q.x) {
                            mostTopLeft = p;
                            mostDownRight = q;
                        } else {
                            if (p.x == q.x && p.y < q.y) {
                                mostTopLeft = p;
                                mostDownRight = q;
                            } else {
                                mostTopLeft = q;
                                mostDownRight = p;
                            }
                        }
                        int differenceX = mostDownRight.x - mostTopLeft.x;
                        int differenceY = mostDownRight.y - mostTopLeft.y;

                        //such a lazy approach
                        for(int multiplier = 0; multiplier<100; multiplier++ ){
                            uniqueAntiNodesForAntenna.add(new point(mostTopLeft.x - (differenceX * multiplier), mostTopLeft.y - (differenceY * multiplier) ));
                            uniqueAntiNodesForAntenna.add(new point(mostDownRight.x + (differenceX * multiplier), mostDownRight.y + (differenceY*multiplier)));
                        }
                    }
                }
            }
            for (point p : uniqueAntiNodesForAntenna) {
                if (p.x < 0 || p.x >= GRIDSIZE || p.y < 0 || p.y >= GRIDSIZE) {
                    //do not keep them
                } else {
                    antinodes.add(p);
                }
            }

        }

    }

    private static String changeDirection(String direction) {
        if (direction == "up") {
            direction = "right";
        } else if (direction.equals("right")) {
            direction = "down";
        } else if (direction.equals("down")) {
            direction = "left";
        } else {
            direction = "up";
        }
        return direction;
    }

    private static frequency takeStep(String direction, char[][] map, int x, int y) {
        try {
            if (direction.equals("up")) {
                return new frequency(map[x - 1][y], x - 1, y);
            }
            if (direction.equals("down")) {
                return new frequency(map[x + 1][y], x + 1, y);
            }
            if (direction.equals("right")) {
                return new frequency(map[x][y + 1], x, y + 1);
            }
            if (direction.equals("left")) {
                return new frequency(map[x][y - 1], x, y - 1);
            }
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return null;
    }

    public static PositionAndMap initiateMap() {
        BufferedReader reader;
        char[][] map = new char[GRIDSIZE][GRIDSIZE];
        int x = 0;
        int y = 0;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();

            int i = 0;
            while (line != null) {
                map[i] = line.toCharArray();
                int position = line.indexOf("^");
                if (position != -1) {
                    x = i;
                    y = position;
                }
                i++;
                line = reader.readLine();
            }
            return new PositionAndMap(map, x, y);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printMap(char[][] map) {
        for (int i = 0; i < GRIDSIZE; i++) {
            System.out.println("" + new String(map[i]));
        }
    }

    record PositionAndMap(char[][] map, int x, int y) {
    }

    record frequency(char object, int x, int y) {
    }

    record point(int x, int y) {
    }

}

